package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ManageSportsEventsActivity extends AppCompatActivity {

    private EventRepository eventRepository;
    private LinearLayout linearLayoutEvents;
    private FloatingActionButton addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sports_events); // Ensure the correct layout is used

        // Initialize EventRepository and UI components
        eventRepository = new EventRepository(this);
        linearLayoutEvents = findViewById(R.id.linearLayoutEvents);
        addEventButton = findViewById(R.id.addEventButton);

        // Open repository to access events
        eventRepository.open();

        // Load events for the admin view
        loadEventsForAdmin();

        // Button to navigate to the registrations view
        findViewById(R.id.fetch_registrations_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageSportsEventsActivity.this, AdminSportRegistrationsView.class);
                startActivity(intent);
            }
        });

        // Add event button click listener
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageSportsEventsActivity.this, EventInformationUploadActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to dynamically load events for admin view
    private void loadEventsForAdmin() {
        // Get the list of events directly
        List<Event> eventList = eventRepository.getAllEvents();

        for (Event event : eventList) {
            // Create a TextView to display event details
            TextView eventTextView = new TextView(this);
            // Customize the displayed text
            String eventDetails = "Date: " + event.getDate() + "\n" +
                    "Type: " + event.getType() + "\n" +
                    "Fee: " + event.getFee() + "\n" +
                    "Location: " + event.getLocation() + "\n" +
                    "In-Charge: " + event.getInChargeName() + "\n" +
                    "Contact: " + event.getInChargeNumber();
            eventTextView.setText(eventDetails);
            eventTextView.setTextSize(18);
            eventTextView.setPadding(0, 20, 0, 20);
            eventTextView.setContentDescription("Event details: " + eventDetails);

            LinearLayout eventLayout = new LinearLayout(this);
            eventLayout.setOrientation(LinearLayout.VERTICAL);
            eventLayout.setPadding(0, 20, 0, 20);
            eventLayout.addView(eventTextView);

            linearLayoutEvents.addView(eventLayout);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventRepository.close();
    }
}
