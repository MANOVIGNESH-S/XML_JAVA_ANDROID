package com.example.groundonline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SportRegistrationRepository {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public SportRegistrationRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Open the database connection
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close the database connection
    public void close() {
        dbHelper.close();
    }

    // Method to insert a sport booking
    public boolean insertSportBooking(int sportId, String bookingDate, String session,
                                      String participantName, String contactNumber, String additionalInfo) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOKING_SPORT_ID, sportId);
        values.put(DatabaseHelper.COLUMN_BOOKING_DATE_SPORT, bookingDate);
        values.put(DatabaseHelper.COLUMN_BOOKING_SESSION_SPORT, session);
        values.put(DatabaseHelper.COLUMN_PARTICIPANT_NAME, participantName);
        values.put(DatabaseHelper.COLUMN_CONTACT_NUMBER, contactNumber);
        values.put(DatabaseHelper.COLUMN_ADDITIONAL_INFO, additionalInfo);

        long result = database.insert(DatabaseHelper.TABLE_SPORT_BOOKINGS, null, values);
        return result != -1;  // Return true if insert was successful
    }

    // Method to get all sport bookings
    public ArrayList<SportBooking> getAllSportBookings() {
        ArrayList<SportBooking> bookingsList = new ArrayList<>();

        String[] columns = {
                DatabaseHelper.COLUMN_SPORT_BOOKING_ID,
                DatabaseHelper.COLUMN_BOOKING_SPORT_ID,
                DatabaseHelper.COLUMN_BOOKING_DATE_SPORT,
                DatabaseHelper.COLUMN_BOOKING_SESSION_SPORT,
                DatabaseHelper.COLUMN_PARTICIPANT_NAME,
                DatabaseHelper.COLUMN_CONTACT_NUMBER,
                DatabaseHelper.COLUMN_ADDITIONAL_INFO
        };

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_SPORT_BOOKINGS,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int bookingId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SPORT_BOOKING_ID));
                int sportId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_SPORT_ID));
                String bookingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_DATE_SPORT));
                String session = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_SESSION_SPORT));
                String participantName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PARTICIPANT_NAME));
                String contactNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT_NUMBER));
                String additionalInfo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDITIONAL_INFO));

                SportBooking booking = new SportBooking(bookingId, sportId, bookingDate, session, participantName, contactNumber, additionalInfo);
                bookingsList.add(booking);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return bookingsList;
    }
}