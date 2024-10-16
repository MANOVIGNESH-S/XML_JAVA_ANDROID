package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; // Import the View class
import android.widget.AdapterView; // Import AdapterView for the listener
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GroundDetailsActivity extends AppCompatActivity {

    private ListView groundListView;
    private Button registrationStatusButton; // Rename variable for clarity
    private GroundRepository groundRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_details);

        groundListView = findViewById(R.id.groundListView);
        registrationStatusButton = findViewById(R.id.registrationStatusButton); // Change to new ID

        // Initialize GroundRepository to fetch ground data
        groundRepository = new GroundRepository(this);
        groundRepository.open();

        // Fetch ground details from the repository and display them
        ArrayList<Ground> groundList = groundRepository.getAllGrounds();
        GroundAdapter adapter = new GroundAdapter(this, groundList);
        groundListView.setAdapter(adapter);

        // Set up an item click listener for the ground list
        groundListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Ground selectedGround = groundList.get(position);

            // Create an intent to pass the ground details to the booking activity
            Intent intent = new Intent(GroundDetailsActivity.this, GroundBookingActivity.class);
            intent.putExtra("groundName", selectedGround.getName());
            intent.putExtra("groundLocation", selectedGround.getLocation());
            startActivity(intent);
        });

        // Set up the registration status button
        registrationStatusButton.setOnClickListener(v -> {
            // Create an intent to navigate to the RegistrationStatusActivity
            Intent intent = new Intent(GroundDetailsActivity.this, RegistrationStatusActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        groundRepository.close();
    }
}
