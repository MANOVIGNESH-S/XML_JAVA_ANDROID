package com.example.groundonline;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class GroundBookingActivity extends AppCompatActivity {

    private TextInputEditText groundNameEditText, groundLocationEditText, dateEditText, sessionEditText,
            bookingPersonNameEditText, contactNumberEditText, userInfoEditText, bookingStatusEditText; // Include bookingStatusEditText
    private Button bookButton;
    private RegistrationRepository registrationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ground_booking_activity);

        registrationRepository = new RegistrationRepository(this);
        registrationRepository.open();

        // Initialize UI elements
        groundNameEditText = findViewById(R.id.groundNameEditText);
        groundLocationEditText = findViewById(R.id.groundLocationEditText);
        dateEditText = findViewById(R.id.dateEditText);
        sessionEditText = findViewById(R.id.sessionEditText);
        bookingPersonNameEditText = findViewById(R.id.bookingPersonNameEditText);
        contactNumberEditText = findViewById(R.id.contactNumberEditText);
        userInfoEditText = findViewById(R.id.userInfoEditText);
        bookingStatusEditText = findViewById(R.id.bookingStatusEditText); // Initialize hidden booking status field
        bookButton = findViewById(R.id.bookButton);

        // Retrieve ground details from intent and set them in the EditTexts
        String groundName = getIntent().getStringExtra("groundName");
        String groundLocation = getIntent().getStringExtra("groundLocation");

        if (groundName != null) {
            groundNameEditText.setText(groundName);
        }
        if (groundLocation != null) {
            groundLocationEditText.setText(groundLocation);
        }

        // Set default booking status
        bookingStatusEditText.setText("Pending"); // Default value for booking status

        // Set up book button click listener
        bookButton.setOnClickListener(view -> {
            if (validateInput()) {
                String bookingDate = dateEditText.getText().toString();
                String session = sessionEditText.getText().toString();
                String bookingPersonName = bookingPersonNameEditText.getText().toString();
                String contactNumber = contactNumberEditText.getText().toString();
                String userInfo = userInfoEditText.getText().toString();
                String bookingStatus = bookingStatusEditText.getText().toString(); // Get booking status

                // Assuming groundId can be fetched from ground data; using 1 as an example
                int groundId = 1;

                boolean success = registrationRepository.insertGroundBooking(groundId, bookingDate, session, bookingPersonName, contactNumber, userInfo, bookingStatus); // Pass booking status

                if (success) {
                    Toast.makeText(GroundBookingActivity.this, "Ground booked successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(GroundBookingActivity.this, "Failed to book ground. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(groundNameEditText.getText().toString()) ||
                TextUtils.isEmpty(groundLocationEditText.getText().toString()) ||
                TextUtils.isEmpty(dateEditText.getText().toString()) ||
                TextUtils.isEmpty(sessionEditText.getText().toString()) ||
                TextUtils.isEmpty(bookingPersonNameEditText.getText().toString()) ||
                TextUtils.isEmpty(contactNumberEditText.getText().toString())) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        groundNameEditText.setText("");
        groundLocationEditText.setText("");
        dateEditText.setText("");
        sessionEditText.setText("");
        bookingPersonNameEditText.setText("");
        contactNumberEditText.setText("");
        userInfoEditText.setText("");
        bookingStatusEditText.setText("Pending"); // Reset booking status to default
    }

    @Override
    protected void onPause() {
        super.onPause();
        registrationRepository.close();
    }
}
