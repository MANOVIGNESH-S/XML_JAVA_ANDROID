package com.example.groundonline;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RegistrationsActivity extends AppCompatActivity {

    private ListView registrationsListView;
    private EventRepository eventRepository;
    private long eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations);

        registrationsListView = findViewById(R.id.registrationsListView);

        eventRepository = new EventRepository(this);
        eventRepository.open();

        eventId = getIntent().getLongExtra("EVENT_ID", -1);

        List<String> registrations = eventRepository.getRegistrationsForEvent(eventId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registrations);
        registrationsListView.setAdapter(adapter);

        eventRepository.close();
    }
}
