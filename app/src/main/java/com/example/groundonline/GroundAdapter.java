package com.example.groundonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GroundAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Ground> grounds;

    public GroundAdapter(Context context, ArrayList<Ground> grounds) {
        this.context = context;
        this.grounds = grounds;
    }

    @Override
    public int getCount() {
        return grounds.size();
    }

    @Override
    public Object getItem(int position) {
        return grounds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return grounds.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ground_list_item, parent, false);
        }

        // Get the ground object for this position
        Ground ground = grounds.get(position);

        // Populate the ground details into the list item
        TextView groundName = convertView.findViewById(R.id.name_text_view); // Updated ID
        TextView groundLocation = convertView.findViewById(R.id.location_text_view); // Updated ID
        TextView groundCapacity = convertView.findViewById(R.id.capacity_text_view); // Updated ID
        TextView groundCharge = convertView.findViewById(R.id.charge_text_view); // New TextView for charge
        TextView groundContact = convertView.findViewById(R.id.contact_text_view); // New TextView for contact number

        groundName.setText(ground.getName());
        groundLocation.setText(ground.getLocation());
        groundCapacity.setText("Capacity: " + ground.getCapacity());
        groundCharge.setText("Charge: " + ground.getCharge()); // Display charge
        groundContact.setText("Contact: " + ground.getContactNumber()); // Display contact number

        return convertView;
    }
}
