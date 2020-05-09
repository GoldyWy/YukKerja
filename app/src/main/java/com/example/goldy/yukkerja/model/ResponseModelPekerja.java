package com.example.goldy.yukkerja.model;

import java.util.List;

public class ResponseModelPekerja {
    private String status,message;
    private List<Pekerja> data;
    private Pekerja detail;

    public ResponseModelPekerja(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelPekerja(String status, String message, List<Pekerja> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseModelPekerja(String status, String message, Pekerja detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
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

    public List<Pekerja> getData() {
        return data;
    }

    public void setData(List<Pekerja> data) {
        this.data = data;
    }

    public Pekerja getDetail() {
        return detail;
    }

    public void setDetail(Pekerja detail) {
        this.detail = detail;
    }
}
