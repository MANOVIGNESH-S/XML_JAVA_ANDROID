package com.example.groundonline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AdminRepository {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_INVALID_EMAIL = 1;
    public static final int LOGIN_INVALID_PASSWORD = 2;

    public AdminRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Insert a new admin into the database
    public long insertAdmin(String name, String email, String mobile, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ADMIN_NAME, name);
        values.put(DatabaseHelper.COLUMN_ADMIN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_ADMIN_MOBILE, mobile);
        values.put(DatabaseHelper.COLUMN_ADMIN_PASSWORD, password);

        return database.insert(DatabaseHelper.TABLE_ADMIN, null, values);
    }

    // Validate admin credentials (email and password)
    public int validateAdmin(String email, String password) {
        String[] columns = {DatabaseHelper.COLUMN_ADMIN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_ADMIN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = null;
        try {
            cursor = database.query(DatabaseHelper.TABLE_ADMIN, columns, selection, selectionArgs, null, null, null);

            // Check if the cursor has data
            if (cursor != null && cursor.moveToFirst()) {
                // Ensure the column index exists
                int passwordColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADMIN_PASSWORD);

                if (passwordColumnIndex != -1) {
                    String storedPassword = cursor.getString(passwordColumnIndex);
                    if (password.equals(storedPassword)) {
                        return LOGIN_SUCCESS;
                    } else {
                        return LOGIN_INVALID_PASSWORD;
                    }
                } else {
                    // Handle the case when the password column is missing
                    throw new SQLException("Password column not found in the database");
                }
            } else {
                // If no rows match the email, return invalid email
                return LOGIN_INVALID_EMAIL;
            }
        } catch (SQLException e) {
            // Handle any database errors
            e.printStackTrace();
            return LOGIN_INVALID_EMAIL; // Optionally handle this better depending on the error
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
