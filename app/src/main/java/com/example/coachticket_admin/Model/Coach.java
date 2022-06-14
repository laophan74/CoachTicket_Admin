package com.example.coachticket_admin.Model;

import java.util.ArrayList;

public class Coach {
    private String plate;
    private String detail;
    private int price;
    private int speed;
    private int numseat1;
    private int numseat2;
    private ArrayList<Boolean> seat1;
    private ArrayList<Boolean> seat2;
    private String document;

    public Coach(String plate, String detail, int price, int speed, int numseat1, int numseat2, ArrayList<Boolean> seat1, ArrayList<Boolean> seat2, String document) {
        this.plate = plate;
        this.detail = detail;
        this.price = price;
        this.speed = speed;
        this.numseat1 = numseat1;
        this.numseat2 = numseat2;
        this.seat1 = seat1;
        this.seat2 = seat2;
        this.document = document;
    }

    public Coach(){};

    public int getNumseat1() {
        return numseat1;
    }

    public void setNumseat1(int numseat1) {
        this.numseat1 = numseat1;
    }

    public int getNumseat2() {
        return numseat2;
    }

    public void setNumseat2(int numseat2) {
        this.numseat2 = numseat2;
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
