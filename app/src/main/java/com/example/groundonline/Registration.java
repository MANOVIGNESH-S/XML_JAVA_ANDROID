package com.example.groundonline;

public class Registration {
    private int id;
    private int userId;
    private int eventId;
    private String registrationDate;

    public Registration(int id, int userId, int eventId, String registrationDate) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.registrationDate = registrationDate;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getEventId() { return eventId; }
    public String getRegistrationDate() { return registrationDate; }
}
