package com.example.goldy.yukkerja.model;

public class Keahlian {
    private String id, nama, created_at, updated_at;

    public Keahlian()
    {

    }

    public Keahlian(String id, String nama, String created_at, String updated_at) {
        this.id = id;
        this.nama = nama;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
