package com.example.coachticket_admin.Model;

public class Trip {
    private String start;
    private String finish;
    private String date;
    private String starttime;
    private String travelCar;
    private String tripName;

    private String document;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Trip(){}

    public Trip(String start, String finish, String date, String starttime, String travelCar, String tripName) {
        this.start = start;
        this.finish = finish;
        this.date = date;
        this.starttime = starttime;
        this.travelCar = travelCar;
        this.tripName = tripName;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTravelCar() {
        return travelCar;
    }

    public void setTravelCar(String travelCar) {
        this.travelCar = travelCar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStart() {
        return start;
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
