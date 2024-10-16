package com.example.groundonline;

public class Ground {

    private int id;                  // Unique identifier for the ground
    private String name;             // Name of the ground
    private String location;         // Location of the ground
    private int capacity;            // Capacity of the ground
    private double charge;           // Charge for the ground
    private String contactNumber;    // Contact number for the ground

    // Constructor with ID
    public Ground(int id, String name, String location, int capacity, double charge, String contactNumber) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.charge = charge;          // Initialize charge
        this.contactNumber = contactNumber; // Initialize contact number
    }

    // Constructor without ID
    public Ground(String name, String location, int capacity, double charge, String contactNumber) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.charge = charge;          // Initialize charge
        this.contactNumber = contactNumber; // Initialize contact number
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
