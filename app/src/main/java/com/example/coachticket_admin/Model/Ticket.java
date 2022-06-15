package com.example.coachticket_admin.Model;

import java.util.ArrayList;

public class Ticket {
    private String start;
    private String finish;
    private String document;
    private int numChild;
    private int numAdult;
    private String userID;
    private String username;
    private String tripName;
    private ArrayList<String> seatNumber;
    //private int totalCost;
    private String trip;
    private String station;

    public Ticket(){};

    public Ticket(String start, String finish, String document, int numChild, int numAdult, String userID, String username, String tripName, ArrayList<String> seatNumber, String trip, String station) {
        this.start = start;
        this.finish = finish;
        this.document = document;
        this.numChild = numChild;
        this.numAdult = numAdult;
        this.userID = userID;
        this.username = username;
        this.tripName = tripName;
        this.seatNumber = seatNumber;
        this.trip = trip;
        this.station = station;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public ArrayList<String> getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(ArrayList<String> seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getStart() {
        return start;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public int getNumChild() {
        return numChild;
    }

    public void setNumChild(int numChild) {
        this.numChild = numChild;
    }

    public int getNumAdult() {
        return numAdult;
    }

    public void setNumAdult(int numAdult) {
        this.numAdult = numAdult;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}
