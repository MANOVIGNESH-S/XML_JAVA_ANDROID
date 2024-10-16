package com.example.groundonline;

public class SportBooking {
    private int bookingId;
    private int sportId;
    private String bookingDate;
    private String session;
    private String participantName;
    private String contactNumber;
    private String additionalInfo;

    public SportBooking(int bookingId, int sportId, String bookingDate, String session,
                        String participantName, String contactNumber, String additionalInfo) {
        this.bookingId = bookingId;
        this.sportId = sportId;
        this.bookingDate = bookingDate;
        this.session = session;
        this.participantName = participantName;
        this.contactNumber = contactNumber;
        this.additionalInfo = additionalInfo;
    }

    // Getters
    public String getBookingDate() {
        return bookingDate;
    }

    public String getSession() {
        return session;
    }

    public String getParticipantName() {
        return participantName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
