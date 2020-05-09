package com.example.goldy.yukkerja.model;

public class ResponseModel {
    private String status, message, role, id;



    public ResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    //Response Auth
    public ResponseModel(String status, String message, String role, String id) {
        this.status = status;
        this.message = message;
        this.role = role;
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
