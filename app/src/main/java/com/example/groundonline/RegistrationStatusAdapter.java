package com.example.groundonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RegistrationStatusAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GroundBooking> groundBookings;

    public RegistrationStatusAdapter(Context context, ArrayList<GroundBooking> groundBookings) {
        this.context = context;
        this.groundBookings = groundBookings;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_ground_booking, parent, false);
        }

        GroundBooking currentBooking = (GroundBooking) getItem(position);

        // Find TextViews in the layout
        TextView bookingInfo = convertView.findViewById(R.id.bookingInfo);
        TextView bookingStatus = convertView.findViewById(R.id.bookingStatus);

        // Set the booking information
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

        return convertView;
    }
}
