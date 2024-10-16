package com.example.groundonline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SportsEventsAdapter extends RecyclerView.Adapter<SportsEventsAdapter.EventViewHolder> {

    private List<String> eventsList;
    private OnEventClickListener onEventClickListener;

    // Constructor for the adapter
    public SportsEventsAdapter(List<String> eventsList, OnEventClickListener onEventClickListener) {
        this.eventsList = eventsList;
        this.onEventClickListener = onEventClickListener;
    }

    // Define a ViewHolder for the RecyclerView
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView eventNameTextView, eventDateTextView, eventLocationTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            // Link the views from the item layout
            eventNameTextView = itemView.findViewById(R.id.textViewEventName);
            eventDateTextView = itemView.findViewById(R.id.textViewEventDate);
            eventLocationTextView = itemView.findViewById(R.id.textViewEventLocation);
        }
    }

    // Inflate the layout for each item in the RecyclerView
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the card layout (item_sports_event.xml)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sports_event, parent, false);
        return new EventViewHolder(view);
    }

    // Bind the data to each item
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        String event = eventsList.get(position);

        // Assuming the event data is a string formatted with name, date, and location
        // You may need to parse the event string or use a model class
        String[] eventDetails = event.split(";"); // Assuming ';' separates the event name, date, and location

        if (eventDetails.length >= 3) {
            holder.eventNameTextView.setText(eventDetails[0]); // Event name
            holder.eventDateTextView.setText(eventDetails[1]); // Event date
            holder.eventLocationTextView.setText(eventDetails[2]); // Event location
        } else {
            holder.eventNameTextView.setText("Unknown Event");
            holder.eventDateTextView.setText("Unknown Date");
            holder.eventLocationTextView.setText("Unknown Location");
        }

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            long eventId = extractEventId(event); // Extract event ID from the event string
            onEventClickListener.onEventClick(eventId);
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    // Interface to handle event clicks
    public interface OnEventClickListener {
        void onEventClick(long eventId);
    }

    // This method needs to be implemented to extract the event ID from the event string
    private long extractEventId(String eventDetails) {
        // Implement logic to extract event ID from event details
        // This may involve parsing the event string, for now, a placeholder value is returned
        return 1;
    }
}
