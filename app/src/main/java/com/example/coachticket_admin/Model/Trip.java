package com.example.coachticket_admin.Model;

import com.google.firebase.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Trip {
    private String start;
    private String finish;
    private Timestamp departure_time;
    private String coach;
    private String tripName;
    private String document;
    private boolean isDone;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Trip(){}

    public String GetDepartureDate(){
        Date d = departure_time.toDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String pickUp =
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " "
                        + calendar.get(Calendar.DAY_OF_MONTH) + "/"
                        +(calendar.get(Calendar.MONTH)+1) + "/"
                        + calendar.get(Calendar.YEAR);
        return pickUp;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Timestamp getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Timestamp departure_time) {
        this.departure_time = departure_time;
    }
}
