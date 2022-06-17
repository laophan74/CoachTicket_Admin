package com.example.coachticket_admin.Model;

public class Voucher {
    private String id;
    private Boolean status;
    private int off;
    private String document;

    public Voucher(){}
    public Voucher(String id, Boolean status, int off, String document) {
        this.id = id;
        this.status = status;
        this.off = off;
        this.document = document;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getOff() {
        return off;
    }

    public void setOff(int off) {
        this.off = off;
    }
}
