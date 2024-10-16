// File: AdminDashboardActivity.java
package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button manageSportsEventsButton, manageGroundBookingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        manageSportsEventsButton = findViewById(R.id.manageSportsEventsButton);
        manageGroundBookingsButton = findViewById(R.id.manageGroundBookingsButton);

        manageSportsEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ManageSportsEventsActivity.class);
            startActivity(intent);
        });

        manageGroundBookingsButton.setOnClickListener(v -> {
            // Navigate to Manage Ground Bookings Activity
            Intent intent = new Intent(AdminDashboardActivity.this, ManageGroundBookingsActivity.class);
            startActivity(intent);
        });
    }
}
