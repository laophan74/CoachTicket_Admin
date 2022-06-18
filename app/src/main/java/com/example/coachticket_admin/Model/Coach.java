package com.example.coachticket_admin.Model;

import java.util.ArrayList;

public class Coach {
    private String plate;
    private String detail;
    private int price;
    private int speed;
    private int numSeat1;
    private int numSeat2;
    private ArrayList<Boolean> seat1;
    private ArrayList<Boolean> seat2;
    private String document;

    public Coach(){};

    public int getNumSeat1() {
        return numSeat1;
    }

    public void setNumSeat1(int numSeat1) {
        this.numSeat1 = numSeat1;
    }

    public int getNumSeat2() {
        return numSeat2;
    }

    public void setNumSeat2(int numSeat2) {
        this.numSeat2 = numSeat2;
    }

    public ArrayList<Boolean> getSeat1() {
        return seat1;
    }

    public void setSeat1(ArrayList<Boolean> seat1) {
        this.seat1 = seat1;
    }

    public ArrayList<Boolean> getSeat2() {
        return seat2;
    }

    public void setSeat2(ArrayList<Boolean> seat2) {
        this.seat2 = seat2;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
