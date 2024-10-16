package com.example.groundonline;

public class Event {
    private long id;
    private String date;
    private String type;
    private String fee;
    private String participationDetails;
    private String location;
    private String inChargeName;
    private String inChargeNumber;

    // Constructor
    public Event(long id, String date, String type, String fee, String participationDetails, String location, String inChargeName, String inChargeNumber) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.fee = fee;
        this.participationDetails = participationDetails;
        this.location = location;
        this.inChargeName = inChargeName;
        this.inChargeNumber = inChargeNumber;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getFee() {
        return fee;
    }

    public String getParticipationDetails() {
        return participationDetails;
    }

    public String getLocation() {
        return location;
    }

    public String getInChargeName() {
        return inChargeName;
    }

    public String getInChargeNumber() {
        return inChargeNumber;
    }

    // Setters (if needed)
    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setParticipationDetails(String participationDetails) {
        this.participationDetails = participationDetails;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInChargeName(String inChargeName) {
        this.inChargeName = inChargeName;
    }

    public void setInChargeNumber(String inChargeNumber) {
        this.inChargeNumber = inChargeNumber;
    }
}
