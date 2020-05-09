package com.example.goldy.yukkerja.model;

import java.util.List;

public class ResponseModelKeahlian {
    private String status, message, size;
    private List<Keahlian> data;

    public ResponseModelKeahlian()
    {

    }



    public ResponseModelKeahlian(String status, String message, List<Keahlian> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseModelKeahlian(String size) {
        this.size = size;
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

    public List<Keahlian> getData() {
        return data;
    }

    public void setData(List<Keahlian> data) {
        this.data = data;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
