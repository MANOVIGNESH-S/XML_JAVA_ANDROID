package com.example.groundonline;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminSportRegistrationsView extends AppCompatActivity {

    private SportRegistrationRepository sportRegistrationRepository; // Updated to use sport repository
    private ListView registrationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sport_registrations_view); // Ensure correct layout

        sportRegistrationRepository = new SportRegistrationRepository(this);
        sportRegistrationRepository.open();

        registrationListView = findViewById(R.id.registration_list_view); // Make sure this ID exists in your layout

        loadSportBookings(); // Load sport bookings
    }

    private void loadSportBookings() {
        ArrayList<SportBooking> bookings = sportRegistrationRepository.getAllSportBookings(); // Fetch all sport bookings

        if (bookings.isEmpty()) {
            Toast.makeText(this, "No sport bookings found", Toast.LENGTH_SHORT).show();
        } else {
            SportBookingAdapter bookingAdapter = new SportBookingAdapter(this, bookings); // Use the new adapter
            registrationListView.setAdapter(bookingAdapter); // Set the adapter
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sportRegistrationRepository.close(); // Close the repository
    }
}
