package com.example.goldy.yukkerja.model;

import java.util.List;

public class ResponseModelNotifikasi {
    private String status,message;
    private List<Notifikasi> data;

    public ResponseModelNotifikasi() {
    }

    public ResponseModelNotifikasi(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelNotifikasi(String status, String message, List<Notifikasi> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Notifikasi> getData() {
        return data;
    }

    public void setData(List<Notifikasi> data) {
        this.data = data;
    }
}
