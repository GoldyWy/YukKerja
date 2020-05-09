package com.example.goldy.yukkerja.model;

import java.util.List;

public class ResponseModelLowongan {
    private String status, message, daftar;
    private List<Lowongan> data;
    private Lowongan detail;

    public ResponseModelLowongan()
    {

    }

    public ResponseModelLowongan(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelLowongan(String status, String message, List<Lowongan> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseModelLowongan(String status, String message, Lowongan detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }

    public ResponseModelLowongan(String status, String message, String daftar, Lowongan detail) {
        this.status = status;
        this.message = message;
        this.daftar = daftar;
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

    public List<Lowongan> getData() {
        return data;
    }

    public void setData(List<Lowongan> data) {
        this.data = data;
    }

    public Lowongan getDetail() {
        return detail;
    }

    public void setDetail(Lowongan detail) {
        this.detail = detail;
    }

    public String getDaftar() {
        return daftar;
    }

    public void setDaftar(String daftar) {
        this.daftar = daftar;
    }
}
