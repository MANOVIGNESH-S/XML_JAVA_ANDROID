// AddGroundActivity.java
package com.example.groundonline;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class AddGroundActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 100;

    private GroundRepository groundRepository;
    private UserRepository userRepository;
    private SmsSender smsSender;

    private EditText nameEditText, locationEditText, capacityEditText, chargeEditText, contactEditText;
    private Button saveGroundButton;

    // Admin Email and Password
    private final String adminEmail = "727722eucs100@skcet.ac.in";
    private final String adminPassword = "kmsk vkxk rbqu okcw"; // Ensure no spaces

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ground);

        setTitle("Add Ground Details");

        // Initialize repositories
        groundRepository = new GroundRepository(this);
        groundRepository.open();

        userRepository = new UserRepository(this);
        userRepository.open();

        smsSender = new SmsSender(this);

        // Initialize UI components
        nameEditText = findViewById(R.id.name_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        capacityEditText = findViewById(R.id.capacity_edit_text);
        chargeEditText = findViewById(R.id.charge_edit_text);
        contactEditText = findViewById(R.id.contact_edit_text);
        saveGroundButton = findViewById(R.id.save_ground_button);

        // Check and request SMS permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }

        // Set save button click listener
        saveGroundButton.setOnClickListener(v -> saveGround());
    }

    private void saveGround() {
        String name = nameEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String capacityStr = capacityEditText.getText().toString().trim();
        String chargeStr = chargeEditText.getText().toString().trim();
        String contactNumber = contactEditText.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty() || location.isEmpty() || capacityStr.isEmpty() || chargeStr.isEmpty() || contactNumber.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int capacity;
        double charge;
        try {
            capacity = Integer.parseInt(capacityStr);
            charge = Double.parseDouble(chargeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Capacity and Charge must be numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add ground to repository
        long id = groundRepository.addGround(name, location, capacity, charge, contactNumber);
        if (id != -1) {
            Toast.makeText(this, "Ground added successfully", Toast.LENGTH_SHORT).show();
            sendSmsToAllUsers(name, location, capacity, charge, contactNumber);
            sendEmailToAllUsers(name, location, capacity, charge, contactNumber); // Send email to users
            finish();
        } else {
            Toast.makeText(this, "Failed to add ground", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSmsToAllUsers(String name, String location, int capacity, double charge, String contactNumber) {
        new Thread(() -> {
            try {
                List<String> userPhoneNumbers = userRepository.getAllUserPhones();
                String message = "New Ground Added: " + name + "\n"
                        + "Location: " + location + "\n"
                        + "Capacity: " + capacity + "\n"
                        + "Charge: $" + charge + "\n"
                        + "Incharge Contact: " + contactNumber;

                for (String phoneNumber : userPhoneNumbers) {
                    Log.d("SMS", "Sending to: " + phoneNumber);
                    smsSender.sendSmsWithRetry(phoneNumber, message, 3);
                }

                runOnUiThread(() -> Toast.makeText(AddGroundActivity.this, "SMS sent to all users", Toast.LENGTH_SHORT).show());

            } catch (Exception e) {
                Log.e("SMS", "Error sending SMS", e);
                runOnUiThread(() -> Toast.makeText(AddGroundActivity.this, "SMS sending failed", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void sendEmailToAllUsers(String name, String location, int capacity, double charge, String contactNumber) {
        new Thread(() -> {
            try {
                List<String> userEmails = userRepository.getAllUserEmails(); // Fetch user emails
                String subject = "New Ground Added: " + name;
                String message = "Dear User,\n\nA new ground has been added to our system with the following details:\n"
                        + "Name: " + name + "\n"
                        + "Location: " + location + "\n"
                        + "Capacity: " + capacity + "\n"
                        + "Charge: $" + charge + "\n"
                        + "Incharge Contact: " + contactNumber + "\n\n"
                        + "Regards,\nGround Management Team";

                EmailSender emailSender = new EmailSender(adminEmail, adminPassword);

                for (String emailAddress : userEmails) {
                    try {
                        emailSender.sendEmail(emailAddress, subject, message);
                    } catch (Exception e) {
                        Log.e("EmailSender", "Failed to send email to: " + emailAddress, e);
                        // Optionally, notify the user or log which emails failed
                    }
                }

                runOnUiThread(() -> Toast.makeText(AddGroundActivity.this, "Emails sent to all users", Toast.LENGTH_SHORT).show());

            } catch (Exception e) {
                Log.e("Email", "Error sending emails", e);
                runOnUiThread(() -> Toast.makeText(AddGroundActivity.this, "Email sending failed", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        groundRepository.close();
        userRepository.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
                saveGroundButton.setEnabled(false);
            }
        }
    }
}
