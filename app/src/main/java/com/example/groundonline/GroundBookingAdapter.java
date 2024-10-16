package com.example.groundonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GroundBookingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GroundBooking> groundBookings;
    private OnApproveClickListener onApproveClickListener;

    // Constructor
    public GroundBookingAdapter(Context context, ArrayList<GroundBooking> groundBookings, OnApproveClickListener listener) {
        this.context = context;
        this.groundBookings = groundBookings;
        this.onApproveClickListener = listener;
    }

    @Override
    public int getCount() {
        return groundBookings.size();
    }

    @Override
    public Object getItem(int position) {
        return groundBookings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ground_booking, parent, false);
        }

        GroundBooking currentBooking = (GroundBooking) getItem(position);

        // Find TextViews and Buttons in the layout
        TextView bookingInfo = convertView.findViewById(R.id.bookingInfo);
        TextView bookingStatus = convertView.findViewById(R.id.bookingStatus);
        Button approveButton = convertView.findViewById(R.id.approveButton);
        Button denyButton = convertView.findViewById(R.id.denyButton); // Reference the new Deny button

        // Create a string to display the booking details
        StringBuilder info = new StringBuilder();
        info.append("Booking ID: ").append(currentBooking.getBookingId()).append("\n")
                .append("Ground ID: ").append(currentBooking.getGroundId()).append("\n")
                .append("Booking Date: ").append(currentBooking.getBookingDate()).append("\n")
                .append("Session: ").append(currentBooking.getSession()).append("\n")
                .append("Booking Person Name: ").append(currentBooking.getBookingPersonName()).append("\n")
                .append("Contact Number: ").append(currentBooking.getContactNumber()).append("\n")
                .append("User Info: ").append(currentBooking.getUserInfo());

        // Set the text to the TextView
        bookingInfo.setText(info.toString());

        // Set the booking status
        bookingStatus.setText("Status: " + currentBooking.getBookingStatus());

        // Highlight status
        if ("Approved".equals(currentBooking.getBookingStatus())) {
            bookingStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
        } else {
            bookingStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
        }

        // Set up the Approve button click listener
        approveButton.setOnClickListener(v -> {
            if (onApproveClickListener != null) {
                onApproveClickListener.onApproveClick(currentBooking);
            }
        });

        // Set up the Deny button click listener
        denyButton.setOnClickListener(v -> {
            if (onApproveClickListener != null) {
                onApproveClickListener.onDenyClick(currentBooking);
            }
        });

        return convertView;
    }

    // Interface for button click listeners
    public interface OnApproveClickListener {
        void onApproveClick(GroundBooking booking);
        void onDenyClick(GroundBooking booking); // New method for Deny button click
    }
}
