package com.example.groundonline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GroundRepository {

    private static final String TABLE_GROUNDS = DatabaseHelper.TABLE_GROUNDS;
    private static final String TABLE_GROUND_BOOKINGS = "ground_bookings"; // Booking table

    // Column names for the grounds table
    public static final String COLUMN_GROUND_ID = DatabaseHelper.COLUMN_GROUND_ID;
    public static final String COLUMN_GROUND_NAME = DatabaseHelper.COLUMN_GROUND_NAME;
    public static final String COLUMN_GROUND_LOCATION = DatabaseHelper.COLUMN_GROUND_LOCATION;
    public static final String COLUMN_GROUND_CAPACITY = DatabaseHelper.COLUMN_GROUND_CAPACITY;
    public static final String COLUMN_GROUND_CHARGE = "charge";  // Column for charge
    public static final String COLUMN_GROUND_CONTACT = "contact_number";  // Column for contact number

    // Column names for the bookings table
    public static final String COLUMN_BOOKING_ID = "booking_id";
    public static final String COLUMN_BOOKING_GROUND_ID = "ground_id"; // Change ground_name to ground_id for correct relation
    public static final String COLUMN_BOOKING_DATE = "booking_date";
    public static final String COLUMN_BOOKING_SESSION = "booking_session";
    public static final String COLUMN_BOOKING_USER_INFO = "user_info";
    public static final String COLUMN_BOOKING_CONTACT_NUMBER = "contact_number"; // Added for booking contact

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    // Constructor to initialize the DatabaseHelper
    public GroundRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Open the database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close the database
    public void close() {
        dbHelper.close();
    }

    // Add a new ground with charge and contact number
    public long addGround(String name, String location, int capacity, double charge, String contactNumber) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_GROUND_NAME, name);
        values.put(COLUMN_GROUND_LOCATION, location);
        values.put(COLUMN_GROUND_CAPACITY, capacity);
        values.put(COLUMN_GROUND_CHARGE, charge);  // Add charge value
        values.put(COLUMN_GROUND_CONTACT, contactNumber);  // Add contact number value

        return database.insert(TABLE_GROUNDS, null, values);
    }

    // Insert a new ground booking
    public long insertGroundBooking(int groundId, String date, String session, String userInfo, String contactNumber) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOKING_GROUND_ID, groundId); // Store groundId in booking
        values.put(COLUMN_BOOKING_DATE, date);
        values.put(COLUMN_BOOKING_SESSION, session);
        values.put(COLUMN_BOOKING_USER_INFO, userInfo);
        values.put(COLUMN_BOOKING_CONTACT_NUMBER, contactNumber); // Store contact number

        return database.insert(TABLE_GROUND_BOOKINGS, null, values);
    }

    // Retrieve all ground details
    public ArrayList<Ground> getAllGrounds() {
        ArrayList<Ground> groundList = new ArrayList<>();
        String[] columns = {
                COLUMN_GROUND_ID,
                COLUMN_GROUND_NAME,
                COLUMN_GROUND_LOCATION,
                COLUMN_GROUND_CAPACITY,
                COLUMN_GROUND_CHARGE,  // Include new charge column
                COLUMN_GROUND_CONTACT  // Include new contact number column
        };

        Cursor cursor = database.query(TABLE_GROUNDS, columns, null, null, null, null, COLUMN_GROUND_NAME + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Ensure column indices are valid
                int idIndex = cursor.getColumnIndex(COLUMN_GROUND_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_GROUND_NAME);
                int locationIndex = cursor.getColumnIndex(COLUMN_GROUND_LOCATION);
                int capacityIndex = cursor.getColumnIndex(COLUMN_GROUND_CAPACITY);
                int chargeIndex = cursor.getColumnIndex(COLUMN_GROUND_CHARGE);  // Index for charge
                int contactIndex = cursor.getColumnIndex(COLUMN_GROUND_CONTACT);  // Index for contact number

                if (idIndex != -1 && nameIndex != -1 && locationIndex != -1 && capacityIndex != -1 && chargeIndex != -1 && contactIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String location = cursor.getString(locationIndex);
                    int capacity = cursor.getInt(capacityIndex);
                    double charge = cursor.getDouble(chargeIndex);
                    String contactNumber = cursor.getString(contactIndex);

                    Ground ground = new Ground(id, name, location, capacity, charge, contactNumber);
                    groundList.add(ground);
                }

            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return groundList;
    }

    // Retrieve a ground by ID
    public Ground getGroundById(int groundId) {
        Ground ground = null;
        String[] columns = {
                COLUMN_GROUND_ID,
                COLUMN_GROUND_NAME,
                COLUMN_GROUND_LOCATION,
                COLUMN_GROUND_CAPACITY,
                COLUMN_GROUND_CHARGE,
                COLUMN_GROUND_CONTACT
        };

        Cursor cursor = database.query(TABLE_GROUNDS, columns, COLUMN_GROUND_ID + "=?", new String[]{String.valueOf(groundId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_GROUND_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_GROUND_NAME);
            int locationIndex = cursor.getColumnIndex(COLUMN_GROUND_LOCATION);
            int capacityIndex = cursor.getColumnIndex(COLUMN_GROUND_CAPACITY);
            int chargeIndex = cursor.getColumnIndex(COLUMN_GROUND_CHARGE);
            int contactIndex = cursor.getColumnIndex(COLUMN_GROUND_CONTACT);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            String location = cursor.getString(locationIndex);
            int capacity = cursor.getInt(capacityIndex);
            double charge = cursor.getDouble(chargeIndex);
            String contactNumber = cursor.getString(contactIndex);

            ground = new Ground(id, name, location, capacity, charge, contactNumber);
            cursor.close();
        }

        return ground;
    }

    // Update ground details including charge and contact number
    public int updateGround(int id, String name, String location, int capacity, double charge, String contactNumber) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_GROUND_NAME, name);
        values.put(COLUMN_GROUND_LOCATION, location);
        values.put(COLUMN_GROUND_CAPACITY, capacity);
        values.put(COLUMN_GROUND_CHARGE, charge);
        values.put(COLUMN_GROUND_CONTACT, contactNumber);

        return database.update(TABLE_GROUNDS, values, COLUMN_GROUND_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Delete a ground by ID
    public int deleteGround(int id) {
        return database.delete(TABLE_GROUNDS, COLUMN_GROUND_ID + "=?", new String[]{String.valueOf(id)});
    }
}
