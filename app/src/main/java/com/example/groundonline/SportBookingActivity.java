package com.example.groundonline;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SportBookingActivity extends AppCompatActivity {

    private TextInputEditText sportNameEditText, bookingDateEditText, sessionEditText,
            participantNameEditText, contactNumberEditText, additionalInfoEditText;
    private Button bookButton;
    private SportRegistrationRepository sportRegistrationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_booking_activity);

        // Initialize the repository
        sportRegistrationRepository = new SportRegistrationRepository(this);
        sportRegistrationRepository.open();

        // Initialize UI elements
        sportNameEditText = findViewById(R.id.sportNameEditText);
        bookingDateEditText = findViewById(R.id.bookingDateEditText);
        sessionEditText = findViewById(R.id.sessionEditText);
        participantNameEditText = findViewById(R.id.participantNameEditText);
        contactNumberEditText = findViewById(R.id.contactNumberEditText);
        additionalInfoEditText = findViewById(R.id.additionalInfoEditText);
        bookButton = findViewById(R.id.bookButton);

        // Get the intent data
        String sportType = getIntent().getStringExtra("SPORT_TYPE");
        String bookingDate = getIntent().getStringExtra("BOOKING_DATE");

        // Set the sport type and booking date in the corresponding fields
        sportNameEditText.setText(sportType); // Place sport type in sport name field
        bookingDateEditText.setText(bookingDate); // Place booking date in booking date field

        // Set up book button click listener
        bookButton.setOnClickListener(view -> {
            if (validateInput()) {
                // Gather input data and attempt to book
                String session = sessionEditText.getText().toString();
                String participantName = participantNameEditText.getText().toString();
                String contactNumber = contactNumberEditText.getText().toString();
                String additionalInfo = additionalInfoEditText.getText().toString();

                // Assuming sportId can be fetched from sport data, using 1 as example
                int sportId = 1; // Replace with actual sport ID if available

                boolean success = sportRegistrationRepository.insertSportBooking(sportId, bookingDate, session, participantName, contactNumber, additionalInfo);

                if (success) {
                    Toast.makeText(SportBookingActivity.this, "Sport booked successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(SportBookingActivity.this, "Failed to book sport. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(bookingDateEditText.getText()) ||
                TextUtils.isEmpty(sessionEditText.getText()) ||
                TextUtils.isEmpty(participantNameEditText.getText()) ||
                TextUtils.isEmpty(contactNumberEditText.getText())) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        sessionEditText.setText("");
        participantNameEditText.setText("");
        contactNumberEditText.setText("");
        additionalInfoEditText.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sportRegistrationRepository.close();
    }
}
