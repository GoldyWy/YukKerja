package com.example.goldy.yukkerja.model;

public class Lkeahlian {
    private String id, lowongan_id, keahlian_id, keahlian_nama, created_at, updated_at;


    public Lkeahlian()
    {

    }

    public Lkeahlian(String id, String lowongan_id, String keahlian_id, String keahlian_nama, String created_at, String updated_at) {
        this.id = id;
        this.lowongan_id = lowongan_id;
        this.keahlian_id = keahlian_id;
        this.keahlian_nama = keahlian_nama;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLowongan_id() {
        return lowongan_id;
    }

    public void setLowongan_id(String lowongan_id) {
        this.lowongan_id = lowongan_id;
    }

    public String getKeahlian_id() {
        return keahlian_id;
    }

    public void setKeahlian_id(String keahlian_id) {
        this.keahlian_id = keahlian_id;
    }

    public String getKeahlian_nama() {
        return keahlian_nama;
    }

    public void setKeahlian_nama(String keahlian_nama) {
        this.keahlian_nama = keahlian_nama;
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
