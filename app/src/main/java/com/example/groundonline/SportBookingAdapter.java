package com.example.groundonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SportBookingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SportBooking> sportBookings;

    public SportBookingAdapter(Context context, ArrayList<SportBooking> sportBookings) {
        this.context = context;
        this.sportBookings = sportBookings;
    }

    @Override
    public int getCount() {
        return sportBookings.size();
    }

    @Override
    public Object getItem(int position) {
        return sportBookings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sport_booking_item, parent, false);
        }

        // Bind data to the view elements
        SportBooking currentBooking = (SportBooking) getItem(position);

        TextView bookingName = convertView.findViewById(R.id.bookingName);
        TextView bookingDate = convertView.findViewById(R.id.bookingDate);
        TextView session = convertView.findViewById(R.id.session);
        TextView contactNumber = convertView.findViewById(R.id.contactNumber);
        TextView additionalInfo = convertView.findViewById(R.id.additionalInfo);

        bookingName.setText(currentBooking.getParticipantName());
        bookingDate.setText(currentBooking.getBookingDate());
        session.setText(currentBooking.getSession());
        contactNumber.setText(currentBooking.getContactNumber());
        additionalInfo.setText(currentBooking.getAdditionalInfo());

        return convertView;
    }
}
