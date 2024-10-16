package com.example.groundonline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_INVALID_EMAIL = 1;
    public static final int LOGIN_INVALID_PASSWORD = 2;

    public UserRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Insert a new user into the database
    public long insertUser(String name, String address, String gender, String email, String mobile, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_NAME, name);
        values.put(DatabaseHelper.COLUMN_USER_ADDRESS, address);
        values.put(DatabaseHelper.COLUMN_USER_GENDER, gender);
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_USER_MOBILE, mobile);
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);

        return database.insert(DatabaseHelper.TABLE_USER, null, values);
    }

    // Validate user credentials (email and password)
    public int validateUser(String email, String password) {
        String[] columns = {DatabaseHelper.COLUMN_USER_PASSWORD}; // Only fetch the password column
        String selection = DatabaseHelper.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = null;
        try {
            cursor = database.query(
                    DatabaseHelper.TABLE_USER,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PASSWORD);
                if (passwordIndex != -1) {
                    String storedPassword = cursor.getString(passwordIndex);
                    if (password.equals(storedPassword)) {
                        return LOGIN_SUCCESS;
                    } else {
                        return LOGIN_INVALID_PASSWORD;
                    }
                }
            }
            return LOGIN_INVALID_EMAIL;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    // Fetch all user phone numbers for sending SMS
    public List<String> getAllUserPhones() {
        List<String> phoneNumbers = new ArrayList<>();
        String[] columns = {DatabaseHelper.COLUMN_USER_MOBILE}; // Assuming 'mobile' stores phone numbers

        Cursor cursor = null;
        try {
            cursor = database.query(
                    DatabaseHelper.TABLE_USER,
                    columns,
                    null, // No selection, fetch all rows
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int phoneIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_MOBILE);
                if (phoneIndex != -1) {
                    do {
                        String phoneNumber = cursor.getString(phoneIndex);
                        phoneNumbers.add(phoneNumber);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return phoneNumbers;
    }

    // Fetch all user email addresses for sending emails
    public List<String> getAllUserEmails() {
        List<String> emailAddresses = new ArrayList<>();
        String[] columns = {DatabaseHelper.COLUMN_USER_EMAIL}; // Assuming 'email' stores email addresses

        Cursor cursor = null;
        try {
            cursor = database.query(
                    DatabaseHelper.TABLE_USER,
                    columns,
                    null, // No selection, fetch all rows
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL);
                if (emailIndex != -1) {
                    do {
                        String email = cursor.getString(emailIndex);
                        emailAddresses.add(email);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return emailAddresses;
    }

    // Check if an email is already registered (for user registration)
    public boolean isEmailRegistered(String email) {
        String[] columns = {DatabaseHelper.COLUMN_USER_EMAIL};
        String selection = DatabaseHelper.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = null;
        try {
            cursor = database.query(
                    DatabaseHelper.TABLE_USER,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    // Update user information (name, address, etc.)
    public int updateUserDetails(String email, String newName, String newAddress, String newGender, String newMobile) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_NAME, newName);
        values.put(DatabaseHelper.COLUMN_USER_ADDRESS, newAddress);
        values.put(DatabaseHelper.COLUMN_USER_GENDER, newGender);
        values.put(DatabaseHelper.COLUMN_USER_MOBILE, newMobile);

        String selection = DatabaseHelper.COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        return database.update(DatabaseHelper.TABLE_USER, values, selection, selectionArgs);
    }
}
