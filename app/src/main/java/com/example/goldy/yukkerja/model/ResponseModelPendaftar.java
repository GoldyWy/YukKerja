package com.example.goldy.yukkerja.model;

import java.util.List;

public class ResponseModelPendaftar {
    private String status, message;
    private List<Pendaftar> data;

    public ResponseModelPendaftar(){

    }

    public ResponseModelPendaftar(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelPendaftar(String status, String message, List<Pendaftar> data) {
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

    public List<Pendaftar> getData() {
        return data;
    }

    public void setData(List<Pendaftar> data) {
        this.data = data;
    }
}
