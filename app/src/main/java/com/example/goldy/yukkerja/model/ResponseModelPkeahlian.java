package com.example.goldy.yukkerja.model;

import java.util.List;

public class ResponseModelPkeahlian {
    private String status, message;
    private List<Pkeahlian> data;
    private Pkeahlian detail;

    public ResponseModelPkeahlian() {
    }

    public ResponseModelPkeahlian(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelPkeahlian(String status, String message, List<Pkeahlian> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseModelPkeahlian(String status, String message, Pkeahlian detail) {
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

    public List<Pkeahlian> getData() {
        return data;
    }

    public void setData(List<Pkeahlian> data) {
        this.data = data;
    }

    public Pkeahlian getDetail() {
        return detail;
    }

    public void setDetail(Pkeahlian detail) {
        this.detail = detail;
    }
}
