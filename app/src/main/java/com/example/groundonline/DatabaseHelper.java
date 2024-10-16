package com.example.groundonline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "groundonline_db";
    private static final int DATABASE_VERSION = 13; // Incremented to trigger onUpgrade

    // Table names
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_USER = "user";
    public static final String TABLE_EVENTS = "events";
    public static final String TABLE_REGISTRATIONS = "registrations";
    public static final String TABLE_GROUNDS = "grounds";
    public static final String TABLE_GROUND_BOOKINGS = "ground_bookings";
    public static final String TABLE_SPORT_BOOKINGS = "sport_bookings";

    // Column names for admin table
    public static final String COLUMN_ADMIN_ID = "_id";
    public static final String COLUMN_ADMIN_NAME = "name";
    public static final String COLUMN_ADMIN_EMAIL = "email";
    public static final String COLUMN_ADMIN_MOBILE = "mobile";
    public static final String COLUMN_ADMIN_PASSWORD = "password";

    // Column names for user table
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_ADDRESS = "address";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_MOBILE = "mobile";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Column names for events table
    public static final String COLUMN_EVENT_ID = "_id";
    public static final String COLUMN_EVENT_DATE = "date";
    public static final String COLUMN_EVENT_TYPE = "type";
    public static final String COLUMN_EVENT_FEE = "fee";
    public static final String COLUMN_EVENT_PARTICIPATION_DETAILS = "participation_details";
    public static final String COLUMN_EVENT_LOCATION = "location"; // New column for event location
    public static final String COLUMN_EVENT_INCHARGE_NAME = "incharge_name"; // New column for in-charge name
    public static final String COLUMN_EVENT_INCHARGE_NUMBER = "incharge_number"; // New column for in-charge number

    // Column names for grounds table
    public static final String COLUMN_GROUND_ID = "_id";
    public static final String COLUMN_GROUND_NAME = "name";
    public static final String COLUMN_GROUND_LOCATION = "location";
    public static final String COLUMN_GROUND_CAPACITY = "capacity";
    public static final String COLUMN_GROUND_CHARGE = "charge"; // New column for charge
    public static final String COLUMN_GROUND_CONTACT = "contact_number"; // New column for contact number

    // Column names for registrations table
    public static final String COLUMN_REGISTRATION_ID = "_id";
    public static final String COLUMN_REGISTRATION_USER_ID = "user_id";
    public static final String COLUMN_REGISTRATION_EVENT_ID = "event_id";
    public static final String COLUMN_REGISTRATION_DATE = "registration_date";

    // Column names for ground bookings table
    public static final String COLUMN_BOOKING_ID = "_id";
    public static final String COLUMN_BOOKING_GROUND_ID = "ground_id";
    public static final String COLUMN_BOOKING_DATE = "booking_date";
    public static final String COLUMN_BOOKING_SESSION = "booking_session";
    public static final String COLUMN_BOOKING_PERSON_NAME = "booking_person_name";
    public static final String COLUMN_CONTACT_NUMBER = "contact_number";
    public static final String COLUMN_USER_INFO = "user_info";
    public static final String COLUMN_BOOKING_STATUS = "booking_status"; // New column for booking status

    // Column names for sport bookings table
    public static final String COLUMN_SPORT_BOOKING_ID = "_id"; // Renamed for clarity
    public static final String COLUMN_BOOKING_SPORT_ID = "sport_id";
    public static final String COLUMN_BOOKING_DATE_SPORT = "booking_date"; // Renamed for clarity
    public static final String COLUMN_BOOKING_SESSION_SPORT = "session";
    public static final String COLUMN_PARTICIPANT_NAME = "participant_name";
    public static final String COLUMN_ADDITIONAL_INFO = "additional_info";

    // SQL to create the admin table
    private static final String TABLE_CREATE_ADMIN =
            "CREATE TABLE " + TABLE_ADMIN + " (" +
                    COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ADMIN_NAME + " TEXT NOT NULL, " +
                    COLUMN_ADMIN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                    COLUMN_ADMIN_MOBILE + " TEXT NOT NULL, " +
                    COLUMN_ADMIN_PASSWORD + " TEXT NOT NULL" +
                    ");";

    // SQL to create the user table
    private static final String TABLE_CREATE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_NAME + " TEXT NOT NULL, " +
                    COLUMN_USER_ADDRESS + " TEXT NOT NULL, " +
                    COLUMN_USER_GENDER + " TEXT NOT NULL, " +
                    COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL, " +
                    COLUMN_USER_MOBILE + " TEXT NOT NULL, " +
                    COLUMN_USER_PASSWORD + " TEXT NOT NULL" +
                    ");";

    // SQL to create the events table
    private static final String TABLE_CREATE_EVENTS =
            "CREATE TABLE " + TABLE_EVENTS + " (" +
                    COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EVENT_DATE + " TEXT NOT NULL, " +
                    COLUMN_EVENT_TYPE + " TEXT NOT NULL, " +
                    COLUMN_EVENT_FEE + " TEXT NOT NULL, " +
                    COLUMN_EVENT_PARTICIPATION_DETAILS + " TEXT NOT NULL, " +
                    COLUMN_EVENT_LOCATION + " TEXT NOT NULL, " + // New column for event location
                    COLUMN_EVENT_INCHARGE_NAME + " TEXT NOT NULL, " + // New column for in-charge name
                    COLUMN_EVENT_INCHARGE_NUMBER + " TEXT NOT NULL" + // New column for in-charge number
                    ");";

    // SQL to create the grounds table with charge and contact number
    private static final String TABLE_CREATE_GROUNDS =
            "CREATE TABLE " + TABLE_GROUNDS + " (" +
                    COLUMN_GROUND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GROUND_NAME + " TEXT NOT NULL, " +
                    COLUMN_GROUND_LOCATION + " TEXT NOT NULL, " +
                    COLUMN_GROUND_CAPACITY + " INTEGER NOT NULL, " +
                    COLUMN_GROUND_CHARGE + " REAL NOT NULL, " + // New column for charge
                    COLUMN_GROUND_CONTACT + " TEXT NOT NULL" + // New column for contact number
                    ");";

    // SQL to create the registrations table
    private static final String TABLE_CREATE_REGISTRATIONS =
            "CREATE TABLE " + TABLE_REGISTRATIONS + " (" +
                    COLUMN_REGISTRATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_REGISTRATION_USER_ID + " INTEGER NOT NULL, " +
                    COLUMN_REGISTRATION_EVENT_ID + " INTEGER NOT NULL, " +
                    COLUMN_REGISTRATION_DATE + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + COLUMN_REGISTRATION_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_REGISTRATION_EVENT_ID + ") REFERENCES " + TABLE_EVENTS + "(" + COLUMN_EVENT_ID + ")" +
                    ");";

    // SQL to create the ground bookings table
    private static final String TABLE_CREATE_GROUND_BOOKINGS =
            "CREATE TABLE " + TABLE_GROUND_BOOKINGS + " (" +
                    COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BOOKING_GROUND_ID + " INTEGER NOT NULL, " +
                    COLUMN_BOOKING_DATE + " TEXT NOT NULL, " +
                    COLUMN_BOOKING_SESSION + " TEXT NOT NULL, " +
                    COLUMN_BOOKING_PERSON_NAME + " TEXT NOT NULL, " +
                    COLUMN_CONTACT_NUMBER + " TEXT NOT NULL, " +
                    COLUMN_USER_INFO + " TEXT NOT NULL, " +
                    COLUMN_BOOKING_STATUS + " TEXT NOT NULL, " + // Add the new booking status column
                    "FOREIGN KEY (" + COLUMN_BOOKING_GROUND_ID + ") REFERENCES " + TABLE_GROUNDS + "(" + COLUMN_GROUND_ID + ")" +
                    ");";

    // SQL to create the sport bookings table
    private static final String TABLE_CREATE_SPORT_BOOKINGS =
            "CREATE TABLE " + TABLE_SPORT_BOOKINGS + " (" +
                    COLUMN_SPORT_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BOOKING_SPORT_ID + " INTEGER NOT NULL, " +
                    COLUMN_BOOKING_DATE_SPORT + " TEXT NOT NULL, " +
                    COLUMN_BOOKING_SESSION_SPORT + " TEXT NOT NULL, " +
                    COLUMN_PARTICIPANT_NAME + " TEXT NOT NULL, " +
                    COLUMN_CONTACT_NUMBER + " TEXT NOT NULL, " +
                    COLUMN_ADDITIONAL_INFO + " TEXT NOT NULL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create all tables
        db.execSQL(TABLE_CREATE_ADMIN);
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(TABLE_CREATE_EVENTS);
        db.execSQL(TABLE_CREATE_REGISTRATIONS);
        db.execSQL(TABLE_CREATE_GROUNDS);
        db.execSQL(TABLE_CREATE_GROUND_BOOKINGS);
        db.execSQL(TABLE_CREATE_SPORT_BOOKINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUNDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUND_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPORT_BOOKINGS);

        // Create new tables
        onCreate(db);
    }
}
