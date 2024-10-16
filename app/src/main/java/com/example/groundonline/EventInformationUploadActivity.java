package com.example.groundonline;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Calendar;
import java.util.List;

public class EventInformationUploadActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 100;

    private EditText eventDateInput, eventTypeInput, eventFeeInput, eventParticipationDetailsInput;
    private EditText eventLocationInput, inChargeNameInput, inChargeNumberInput;
    private Button uploadEventButton;
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private SmsSender smsSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information_upload);

        eventDateInput = findViewById(R.id.eventDateInput);
        eventTypeInput = findViewById(R.id.eventTypeInput);
        eventFeeInput = findViewById(R.id.eventFeeInput);
        eventParticipationDetailsInput = findViewById(R.id.eventParticipationDetailsInput);
        eventLocationInput = findViewById(R.id.eventLocationInput); // New field
        inChargeNameInput = findViewById(R.id.inChargeNameInput); // New field
        inChargeNumberInput = findViewById(R.id.inChargeNumberInput); // New field
        uploadEventButton = findViewById(R.id.uploadEventButton);

        eventRepository = new EventRepository(this);
        userRepository = new UserRepository(this);
        smsSender = new SmsSender(this);
        eventRepository.open();
        userRepository.open();

        // Check for SMS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }

        // Set up date picker for eventDateInput
        eventDateInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    EventInformationUploadActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        String date = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
                        eventDateInput.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });

        uploadEventButton.setOnClickListener(v -> {
            String date = eventDateInput.getText().toString().trim();
            String type = eventTypeInput.getText().toString().trim();
            String fee = eventFeeInput.getText().toString().trim();
            String participationDetails = eventParticipationDetailsInput.getText().toString().trim();
            String location = eventLocationInput.getText().toString().trim(); // New field
            String inChargeName = inChargeNameInput.getText().toString().trim(); // New field
            String inChargeNumber = inChargeNumberInput.getText().toString().trim(); // New field

            if (date.isEmpty() || type.isEmpty() || fee.isEmpty() || participationDetails.isEmpty() ||
                    location.isEmpty() || inChargeName.isEmpty() || inChargeNumber.isEmpty()) {
                Toast.makeText(EventInformationUploadActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = eventRepository.insertEvent(date, type, fee, participationDetails, location, inChargeName, inChargeNumber);
            if (result != -1) {
                Toast.makeText(EventInformationUploadActivity.this, "Event uploaded successfully", Toast.LENGTH_SHORT).show();
                sendSmsToAllUsers(date, type, fee, participationDetails, location, inChargeName, inChargeNumber);  // Send SMS after event upload
            } else {
                Toast.makeText(EventInformationUploadActivity.this, "Event upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSmsToAllUsers(String date, String type, String fee, String participationDetails, String location, String inChargeName, String inChargeNumber) {
        new Thread(() -> {
            try {
                // Get all user phone numbers from the userRepository
                List<String> userPhoneNumbers = userRepository.getAllUserPhones();  // Fetch phone numbers from your database

                // Create and send SMS message
                String message = "New Event: " + type + "\n"
                        + "Date: " + date + "\n"
                        + "Location: " + location + "\n" // Include location
                        + "Fee: " + fee + "\n"
                        + "In-Charge: " + inChargeName + " (" + inChargeNumber + ")\n" // Include in-charge details
                        + "Details: " + participationDetails;

                for (String phoneNumber : userPhoneNumbers) {
                    smsSender.sendSms(phoneNumber, message);
                }

                // Update UI on SMS success
                runOnUiThread(() -> Toast.makeText(EventInformationUploadActivity.this, "SMS sent to all users", Toast.LENGTH_SHORT).show());

            } catch (Exception e) {
                e.printStackTrace();
                // Update UI on SMS failure
                runOnUiThread(() -> Toast.makeText(EventInformationUploadActivity.this, "SMS sending failed", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventRepository.close();
        userRepository.close();
    }

    // Handle the result of permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
