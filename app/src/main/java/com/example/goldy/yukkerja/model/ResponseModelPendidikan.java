package com.example.goldy.yukkerja.model;

import java.util.List;

public class ResponseModelPendidikan {
    private String status, message;
    private List<Pendidikan> data;

    public ResponseModelPendidikan() {
    }



    public ResponseModelPendidikan(String status, String message, List<Pendidikan> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseModelPendidikan(String status, String message) {
        this.status = status;
        this.message = message;
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

    public List<Pendidikan> getData() {
        return data;
    }

    public void setData(List<Pendidikan> data) {
        this.data = data;
    }
}
