package com.example.groundonline;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class AdminGroundRegistrationsView extends AppCompatActivity implements GroundBookingAdapter.OnApproveClickListener {

    private RegistrationRepository registrationRepository;
    private GroundRepository groundRepository; // Assuming you have a repository for Ground
    private ListView registrationListView;
    private SmsSender smsSender;  // Instance of SmsSender to send SMS
    private static final int SMS_PERMISSION_CODE = 1; // Code for SMS permission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ground_registrations_view);

        registrationRepository = new RegistrationRepository(this);
        groundRepository = new GroundRepository(this); // Initialize GroundRepository
        registrationRepository.open();
        groundRepository.open(); // Open the ground repository

        registrationListView = findViewById(R.id.registration_list_view);

        // Initialize SmsSender
        smsSender = new SmsSender(this);

        // Request SMS permission if not already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }

        loadGroundBookings();
    }

    private void loadGroundBookings() {
        ArrayList<GroundBooking> bookings = registrationRepository.getAllGroundBookings();

        if (bookings.isEmpty()) {
            Toast.makeText(this, "No ground bookings found", Toast.LENGTH_SHORT).show();
        } else {
            GroundBookingAdapter bookingAdapter = new GroundBookingAdapter(this, bookings, this);
            registrationListView.setAdapter(bookingAdapter);
        }
    }

    @Override
    public void onApproveClick(GroundBooking booking) {
        booking.setBookingStatus("Approved");

        // Update the status in the database
        registrationRepository.updateBookingStatus(booking.getBookingId(), "Approved");

        // Fetch the ground name using the groundId
        Ground ground = groundRepository.getGroundById(booking.getGroundId()); // Implement this method in GroundRepository
        if (ground != null) {
            // Create a detailed SMS notification
            String message = "Your booking for Ground ID " + booking.getGroundId() +
                    " (Booking ID: " + booking.getBookingId() + ") has been approved. Thank you!";
            smsSender.sendSms(booking.getContactNumber(), message);
        }

        // Notify the user and refresh the list
        Toast.makeText(this, "Booking approved", Toast.LENGTH_SHORT).show();
        loadGroundBookings(); // Reload bookings to reflect changes
    }

    @Override
    public void onDenyClick(GroundBooking booking) {
        booking.setBookingStatus("Denied");

        // Update the status in the database
        registrationRepository.updateBookingStatus(booking.getBookingId(), "Denied");

        // Fetch the ground name using the groundId
        Ground ground = groundRepository.getGroundById(booking.getGroundId()); // Implement this method in GroundRepository
        if (ground != null) {
            // Create a detailed SMS notification
            String message = "Your booking for Ground ID " + booking.getGroundId() +
                    " (Booking ID: " + booking.getBookingId() + ") has been denied. Please contact us for more information.";
            smsSender.sendSms(booking.getContactNumber(), message);
        }

        // Notify the user and refresh the list
        Toast.makeText(this, "Booking denied", Toast.LENGTH_SHORT).show();
        loadGroundBookings(); // Reload bookings to reflect changes
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registrationRepository.close();
        groundRepository.close(); // Close the ground repository
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
