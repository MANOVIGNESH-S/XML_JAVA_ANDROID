package com.example.groundonline;

public class GroundBooking {
    private int bookingId;
    private int groundId;
    private String bookingDate;
    private String session;
    private String bookingPersonName;
    private String contactNumber;
    private String userInfo;
    private String bookingStatus; // Ensure this field exists

    // Constructor
    public GroundBooking(int bookingId, int groundId, String bookingDate, String session, String bookingPersonName, String contactNumber, String userInfo, String bookingStatus) {
        this.bookingId = bookingId;
        this.groundId = groundId;
        this.bookingDate = bookingDate;
        this.session = session;
        this.bookingPersonName = bookingPersonName;
        this.contactNumber = contactNumber;
        this.userInfo = userInfo;
        this.bookingStatus = bookingStatus;
    }

    // Getter for bookingStatus
    public String getBookingStatus() {
        return bookingStatus;
    }

    // Setter for bookingStatus
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    // Other getters
    public int getBookingId() {
        return bookingId;
    }

    public int getGroundId() {
        return groundId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getSession() {
        return session;
    }

    public String getBookingPersonName() {
        return bookingPersonName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getUserInfo() {
        return userInfo;
    }
}
