package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageGroundBookingsActivity extends AppCompatActivity {

    private GroundRepository groundRepository;
    private RegistrationRepository registrationRepository;
    private ListView groundListView;
    private Button addGroundButton;
    private Button fetchRegistrationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ground_bookings);

        groundRepository = new GroundRepository(this);
        registrationRepository = new RegistrationRepository(this);
        groundRepository.open();
        registrationRepository.open();

        groundListView = findViewById(R.id.ground_list_view);
        addGroundButton = findViewById(R.id.add_ground_button);
        fetchRegistrationsButton = findViewById(R.id.fetch_registrations_button);

        loadGrounds();

        addGroundButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManageGroundBookingsActivity.this, AddGroundActivity.class);
            startActivity(intent);
        });

        // Update to navigate to the AdminGroundRegistrationsView activity
        fetchRegistrationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManageGroundBookingsActivity.this, AdminGroundRegistrationsView.class);
            startActivity(intent);
        });
    }

    private void loadGrounds() {
        ArrayList<Ground> grounds = groundRepository.getAllGrounds();
        GroundAdapter adapter = new GroundAdapter(this, grounds);
        groundListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        groundRepository.close();
        registrationRepository.close();
    }
}
