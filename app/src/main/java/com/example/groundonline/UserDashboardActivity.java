package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UserDashboardActivity extends AppCompatActivity {

    private Button sportsEventButton, groundBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);  // Ensure this matches your XML layout file name

        // Initialize the UI components
        sportsEventButton = findViewById(R.id.sportsEventButton);
        groundBookingButton = findViewById(R.id.groundBookingButton);

        // Set button click listeners
        sportsEventButton.setOnClickListener(v -> {
            // Navigate to Sports Event Booking
            Intent intent = new Intent(UserDashboardActivity.this, SportsEventActivity.class);
            startActivity(intent);
        });

        groundBookingButton.setOnClickListener(v -> {
            // Navigate to Ground Details Activity (before booking)
            Intent intent = new Intent(UserDashboardActivity.this, GroundDetailsActivity.class);
            startActivity(intent);
        });
    }
}
