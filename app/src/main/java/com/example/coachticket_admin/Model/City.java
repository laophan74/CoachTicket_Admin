package com.example.coachticket_admin.Model;

public class City implements Comparable<City> {
    private int distance;
    private String cname;
    private String station;
    private String document;
    public City(){}

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public int compareTo(City o) {
        int compare = o.getDistance();
        return this.distance - compare;
    }
}
