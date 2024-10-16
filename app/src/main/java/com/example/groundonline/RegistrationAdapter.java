package com.example.groundonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class RegistrationAdapter extends ArrayAdapter<Registration> {
    public RegistrationAdapter(Context context, ArrayList<Registration> registrations) {
        super(context, 0, registrations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Registration registration = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_registration, parent, false);
        }

        TextView registrationId = convertView.findViewById(R.id.registration_id);
        TextView userId = convertView.findViewById(R.id.user_id);
        TextView eventId = convertView.findViewById(R.id.event_id);
        TextView registrationDate = convertView.findViewById(R.id.registration_date);

        registrationId.setText(String.valueOf(registration.getId()));
        userId.setText(String.valueOf(registration.getUserId()));
        eventId.setText(String.valueOf(registration.getEventId()));
        registrationDate.setText(registration.getRegistrationDate());

        return convertView;
    }
}
