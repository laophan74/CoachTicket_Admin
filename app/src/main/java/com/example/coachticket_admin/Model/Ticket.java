package com.example.coachticket_admin.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Ticket {
    private String start;
    private String finish;
    private String document;
    private int numChild;
    private int numAdult;
    private String userID;
    private String username;
    private String tripName;
    private String pMethod;
    private String status;
    private ArrayList<String> seatNumber;
    private int totalCost;
    private String trip;
    private String station;
    private HashMap<String, Boolean> service;
    private Timestamp purchaseDate;

    public Ticket(){};

    public String GetDateToString(){
        Date d = purchaseDate.toDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String pickUp =
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " "
                        + calendar.get(Calendar.DAY_OF_MONTH) + "/"
                        +(calendar.get(Calendar.MONTH)+1) + "/"
                        + calendar.get(Calendar.YEAR);
        return pickUp;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public HashMap<String, Boolean> getService() {
        return service;
    }

    public void setService(HashMap<String, Boolean> service) {
        this.service = service;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
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

    public String getpMethod() {
        return pMethod;
    }

    public void setpMethod(String pMethod) {
        this.pMethod = pMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
