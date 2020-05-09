package com.example.goldy.yukkerja.model;


import java.util.ArrayList;
import java.util.List;

public class ResponseModelPerusahaan {
    private String status, message,foto;
    private List<Perusahaan> data;

    public ResponseModelPerusahaan(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelPerusahaan(String status, String message, List<Perusahaan> data) {
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

    public List getData() {
        return data;
    }

    public void setData(List<Perusahaan> data) {
        this.data = data;
    }

}
