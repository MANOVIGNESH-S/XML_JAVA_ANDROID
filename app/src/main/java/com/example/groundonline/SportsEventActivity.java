package com.example.groundonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SportsEventActivity extends AppCompatActivity {

    private EventRepository eventRepository;
    private LinearLayout linearLayoutEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sports_event_activity);

        // Initialize EventRepository and LinearLayout
        eventRepository = new EventRepository(this);
        linearLayoutEvents = findViewById(R.id.linearLayoutEvents);

        // Open repository to access events
        eventRepository.open();

        // Retrieve list of events from the repository
        List<Event> eventList = eventRepository.getAllEvents();

        // Dynamically create views for each event
        if (eventList.isEmpty()) {
            TextView noEventsTextView = new TextView(this);
            noEventsTextView.setText("No events available at the moment.");
            noEventsTextView.setTextSize(18);
            noEventsTextView.setPadding(0, 20, 0, 20);
            linearLayoutEvents.addView(noEventsTextView);
        } else {
            for (Event event : eventList) {
                // Inflate the custom user sport event card layout
                View eventCard = LayoutInflater.from(this).inflate(R.layout.user_sport_event_card, linearLayoutEvents, false);

                // Find the TextViews and Button in the user sport event card layout
                TextView eventNameTextView = eventCard.findViewById(R.id.eventName);
                TextView eventDateTextView = eventCard.findViewById(R.id.eventDate);
                TextView eventTypeTextView = eventCard.findViewById(R.id.eventType);
                TextView eventFeeTextView = eventCard.findViewById(R.id.eventFee);
                TextView eventParticipationDetailsTextView = eventCard.findViewById(R.id.eventParticipationDetails);
                TextView eventLocationTextView = eventCard.findViewById(R.id.eventLocation);
                TextView eventInChargeTextView = eventCard.findViewById(R.id.eventInCharge);
                Button registerButton = eventCard.findViewById(R.id.registerButton);

                // Set event details
                eventNameTextView.setText(event.getType()); // Assuming 'type' is the name of the event
                eventDateTextView.setText("Date: " + event.getDate());
                eventTypeTextView.setText("Type: " + event.getType());
                eventFeeTextView.setText("Fee: " + event.getFee());
                eventParticipationDetailsTextView.setText("Participation: " + event.getParticipationDetails());
                eventLocationTextView.setText("Location: " + event.getLocation());
                eventInChargeTextView.setText("In-Charge: " + event.getInChargeName() + " (Contact: " + event.getInChargeNumber() + ")");

                // Set the click listener for the register button
                registerButton.setOnClickListener(v -> registerForEvent(event));

                // Add the event card to the main LinearLayout
                linearLayoutEvents.addView(eventCard);
            }
        }

        // Close repository when done
        eventRepository.close();
    }

    // Method to handle event registration
    private void registerForEvent(Event event) {
        Intent intent = new Intent(this, SportBookingActivity.class);

        // Pass the event details to SportBookingActivity
        intent.putExtra("SPORT_TYPE", event.getType()); // Assuming 'type' is the event name
        intent.putExtra("BOOKING_DATE", event.getDate()); // Pass the event date

        startActivity(intent);
    }
}
