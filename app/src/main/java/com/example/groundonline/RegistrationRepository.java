package com.example.groundonline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class RegistrationRepository {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public RegistrationRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Open the database connection
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close the database connection
    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Method to insert a ground booking
    public boolean insertGroundBooking(int groundId, String bookingDate, String session,
                                       String bookingPersonName, String contactNumber, String userInfo, String bookingStatus) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOKING_GROUND_ID, groundId);
        values.put(DatabaseHelper.COLUMN_BOOKING_DATE, bookingDate);
        values.put(DatabaseHelper.COLUMN_BOOKING_SESSION, session);
        values.put(DatabaseHelper.COLUMN_BOOKING_PERSON_NAME, bookingPersonName);
        values.put(DatabaseHelper.COLUMN_CONTACT_NUMBER, contactNumber);
        values.put(DatabaseHelper.COLUMN_USER_INFO, userInfo);
        values.put(DatabaseHelper.COLUMN_BOOKING_STATUS, bookingStatus);

        long result = database.insert(DatabaseHelper.TABLE_GROUND_BOOKINGS, null, values);
        return result != -1;  // Return true if insert was successful
    }

    // Method to get all registrations
    public ArrayList<Registration> getAllRegistrations() {
        ArrayList<Registration> registrationsList = new ArrayList<>();

        String[] columns = {
                DatabaseHelper.COLUMN_REGISTRATION_ID,
                DatabaseHelper.COLUMN_REGISTRATION_USER_ID,
                DatabaseHelper.COLUMN_REGISTRATION_EVENT_ID,
                DatabaseHelper.COLUMN_REGISTRATION_DATE
        };

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_REGISTRATIONS,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a new Registration object
                int registrationId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_REGISTRATION_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_REGISTRATION_USER_ID));
                int eventId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_REGISTRATION_EVENT_ID));
                String registrationDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REGISTRATION_DATE));

                Registration registration = new Registration(registrationId, userId, eventId, registrationDate);
                registrationsList.add(registration);
            } while (cursor.moveToNext());
            cursor.close(); // Close cursor here if it's not null
        }

        return registrationsList;
    }

    // Method to get all ground bookings
    public ArrayList<GroundBooking> getAllGroundBookings() {
        ArrayList<GroundBooking> bookingsList = new ArrayList<>();

        String[] columns = {
                DatabaseHelper.COLUMN_BOOKING_ID,
                DatabaseHelper.COLUMN_BOOKING_GROUND_ID,
                DatabaseHelper.COLUMN_BOOKING_DATE,
                DatabaseHelper.COLUMN_BOOKING_SESSION,
                DatabaseHelper.COLUMN_BOOKING_PERSON_NAME,
                DatabaseHelper.COLUMN_CONTACT_NUMBER,
                DatabaseHelper.COLUMN_USER_INFO,
                DatabaseHelper.COLUMN_BOOKING_STATUS
        };

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_GROUND_BOOKINGS,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a new GroundBooking object
                int bookingId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_ID));
                int groundId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_GROUND_ID));
                String bookingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_DATE));
                String session = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_SESSION));
                String bookingPersonName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_PERSON_NAME));
                String contactNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT_NUMBER));
                String userInfo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_INFO));
                String bookingStatus = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_STATUS));

                GroundBooking booking = new GroundBooking(bookingId, groundId, bookingDate, session, bookingPersonName, contactNumber, userInfo, bookingStatus);
                bookingsList.add(booking);
            } while (cursor.moveToNext());
            cursor.close(); // Close cursor here if it's not null
        }

        return bookingsList;
    }

    // Method to update booking status
    public boolean updateBookingStatus(int bookingId, String newStatus) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOKING_STATUS, newStatus);

        // Update the status based on bookingId
        return database.update(DatabaseHelper.TABLE_GROUND_BOOKINGS, values,
                DatabaseHelper.COLUMN_BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)}) > 0;
    }

    // Method to get all ground bookings for a specific user
    public ArrayList<GroundBooking> getBookingsForUser(int userId) {
        ArrayList<GroundBooking> bookingsList = new ArrayList<>();
        String selection = DatabaseHelper.COLUMN_USER_INFO + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(userId)};

        String[] columns = {
                DatabaseHelper.COLUMN_BOOKING_ID,
                DatabaseHelper.COLUMN_BOOKING_GROUND_ID,
                DatabaseHelper.COLUMN_BOOKING_DATE,
                DatabaseHelper.COLUMN_BOOKING_SESSION,
                DatabaseHelper.COLUMN_BOOKING_PERSON_NAME,
                DatabaseHelper.COLUMN_CONTACT_NUMBER,
                DatabaseHelper.COLUMN_USER_INFO,
                DatabaseHelper.COLUMN_BOOKING_STATUS
        };

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_GROUND_BOOKINGS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a new GroundBooking object
                int bookingId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_ID));
                int groundId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_GROUND_ID));
                String bookingDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_DATE));
                String session = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_SESSION));
                String bookingPersonName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_PERSON_NAME));
                String contactNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT_NUMBER));
                String userInfo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_INFO));
                String bookingStatus = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BOOKING_STATUS));

                GroundBooking booking = new GroundBooking(bookingId, groundId, bookingDate, session, bookingPersonName, contactNumber, userInfo, bookingStatus);
                bookingsList.add(booking);
            } while (cursor.moveToNext());
            cursor.close(); // Close cursor here if it's not null
        }

        return bookingsList;
    }
}
