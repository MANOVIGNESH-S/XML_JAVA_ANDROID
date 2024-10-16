package com.example.groundonline;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RegistrationStatusActivity extends AppCompatActivity {

    private ListView bookingListView;
    private RegistrationRepository registrationRepository;
    private RegistrationStatusAdapter bookingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_status);

        bookingListView = findViewById(R.id.bookingListView);
        registrationRepository = new RegistrationRepository(this);
        registrationRepository.open();

        // Fetch the user's bookings from the repository
        ArrayList<GroundBooking> userBookings = registrationRepository.getAllGroundBookings();

        // Create the adapter and set it to the ListView
        bookingAdapter = new RegistrationStatusAdapter(this, userBookings);
        bookingListView.setAdapter(bookingAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        registrationRepository.close();
    }
}
