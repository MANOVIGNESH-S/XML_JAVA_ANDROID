package com.example.groundonline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private SQLiteDatabase db;
    private final DatabaseHelper dbHelper;

    // Constructor to initialize DatabaseHelper
    public EventRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Method to open the database
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    // Method to close the database
    public void close() {
        dbHelper.close();
    }

    // Method to insert an event into the database, including the new fields
    public long insertEvent(String date, String type, String fee, String participationDetails, String location, String inChargeName, String inChargeNumber) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EVENT_DATE, date);
        values.put(DatabaseHelper.COLUMN_EVENT_TYPE, type);
        values.put(DatabaseHelper.COLUMN_EVENT_FEE, fee);
        values.put(DatabaseHelper.COLUMN_EVENT_PARTICIPATION_DETAILS, participationDetails);
        values.put(DatabaseHelper.COLUMN_EVENT_LOCATION, location);
        values.put(DatabaseHelper.COLUMN_EVENT_INCHARGE_NAME, inChargeName);
        values.put(DatabaseHelper.COLUMN_EVENT_INCHARGE_NUMBER, inChargeNumber);

        return db.insert(DatabaseHelper.TABLE_EVENTS, null, values);
    }

    // Method to fetch all events from the database
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_EVENTS,
                    new String[]{
                            DatabaseHelper.COLUMN_EVENT_ID,
                            DatabaseHelper.COLUMN_EVENT_DATE,
                            DatabaseHelper.COLUMN_EVENT_TYPE,
                            DatabaseHelper.COLUMN_EVENT_FEE,
                            DatabaseHelper.COLUMN_EVENT_PARTICIPATION_DETAILS,
                            DatabaseHelper.COLUMN_EVENT_LOCATION,
                            DatabaseHelper.COLUMN_EVENT_INCHARGE_NAME,
                            DatabaseHelper.COLUMN_EVENT_INCHARGE_NUMBER
                    },
                    null, null, null, null, DatabaseHelper.COLUMN_EVENT_DATE + " ASC"); // Order by date

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_ID);
                int dateIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_DATE);
                int typeIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_TYPE);
                int feeIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_FEE);
                int participationDetailsIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_PARTICIPATION_DETAILS);
                int locationIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_LOCATION);
                int inChargeNameIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_INCHARGE_NAME);
                int inChargeNumberIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EVENT_INCHARGE_NUMBER);

                do {
                    long eventId = cursor.getLong(idIndex);
                    String eventDate = cursor.getString(dateIndex);
                    String eventType = cursor.getString(typeIndex);
                    String eventFee = cursor.getString(feeIndex);
                    String participationDetails = cursor.getString(participationDetailsIndex);
                    String eventLocation = cursor.getString(locationIndex);
                    String inChargeName = cursor.getString(inChargeNameIndex);
                    String inChargeNumber = cursor.getString(inChargeNumberIndex);

                    // Create an Event object and add it to the list
                    Event event = new Event(eventId, eventDate, eventType, eventFee, participationDetails, eventLocation, inChargeName, inChargeNumber);
                    events.add(event);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return events;
    }

    // Method to register a user for an event
    public long registerUserForEvent(long eventId, String userName, String userEmail) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EVENT_ID, eventId);
        values.put(DatabaseHelper.COLUMN_USER_NAME, userName);
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, userEmail);

        return db.insert(DatabaseHelper.TABLE_REGISTRATIONS, null, values);
    }

    // Method to get all registrations for a specific event
    public List<String> getRegistrationsForEvent(long eventId) {
        List<String> registrations = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_REGISTRATIONS,
                    new String[]{DatabaseHelper.COLUMN_USER_NAME, DatabaseHelper.COLUMN_USER_EMAIL},
                    DatabaseHelper.COLUMN_EVENT_ID + "=?", new String[]{String.valueOf(eventId)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME);
                int emailIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL);

                do {
                    String userName = cursor.getString(nameIndex);
                    String userEmail = cursor.getString(emailIndex);
                    String registrationDetails = "Name: " + userName + ", Email: " + userEmail;
                    registrations.add(registrationDetails);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return registrations;
    }

    // Method to count registrations for a specific event
    public int getRegistrationCountForEvent(long eventId) {
        int count = 0;
        Cursor cursor = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_REGISTRATIONS,
                    new String[]{"COUNT(*) AS count"},
                    DatabaseHelper.COLUMN_EVENT_ID + "=?",
                    new String[]{String.valueOf(eventId)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }

    // Method to delete an event by ID
    public boolean deleteEvent(long eventId) {
        return db.delete(DatabaseHelper.TABLE_EVENTS, DatabaseHelper.COLUMN_EVENT_ID + "=" + eventId, null) > 0;
    }

    // Method to update an event
    public int updateEvent(long eventId, String date, String type, String fee, String participationDetails, String location, String inChargeName, String inChargeNumber) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EVENT_DATE, date);
        values.put(DatabaseHelper.COLUMN_EVENT_TYPE, type);
        values.put(DatabaseHelper.COLUMN_EVENT_FEE, fee);
        values.put(DatabaseHelper.COLUMN_EVENT_PARTICIPATION_DETAILS, participationDetails);
        values.put(DatabaseHelper.COLUMN_EVENT_LOCATION, location);
        values.put(DatabaseHelper.COLUMN_EVENT_INCHARGE_NAME, inChargeName);
        values.put(DatabaseHelper.COLUMN_EVENT_INCHARGE_NUMBER, inChargeNumber);

        return db.update(DatabaseHelper.TABLE_EVENTS, values, DatabaseHelper.COLUMN_EVENT_ID + "=" + eventId, null);
    }
}
