package com.example.coachticket_admin.Model;

import com.google.firebase.Timestamp;

public class Trip {
    private String start;
    private String finish;
    private com.google.firebase.Timestamp departure_time;
    private String coach;
    private String tripName;

    private String document;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Trip(){}

    public Trip(String start, String finish, Timestamp departure_time, String coach, String tripName, String document) {
        this.start = start;
        this.finish = finish;
        this.departure_time = departure_time;
        this.coach = coach;
        this.tripName = tripName;
        this.document = document;
    }

    public Timestamp getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Timestamp departure_time) {
        this.departure_time = departure_time;
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

    /*public Timestamp getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Timestamp departure_time) {
        this.departure_time = departure_time;
    }*/

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }
}
