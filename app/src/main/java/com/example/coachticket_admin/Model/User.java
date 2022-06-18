package com.example.coachticket_admin.Model;

import java.util.Date;

public class User {
    private String username;
    private String name;
    private String cmnd;
    private String dob;
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return cmnd;
    }

    public void setId(String id) {
        this.cmnd = id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
