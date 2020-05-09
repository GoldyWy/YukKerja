package com.example.goldy.yukkerja.model;

public class Pkeahlian {
    private String id, pekerja_id, keahlian_id, keahlian_nama, tingkat, created_at, updated_at;

    public Pkeahlian() {
    }

    public Pkeahlian(String id, String pekerja_id, String keahlian_id, String keahlian_nama, String tingkat, String created_at, String updated_at) {
        this.id = id;
        this.pekerja_id = pekerja_id;
        this.keahlian_id = keahlian_id;
        this.keahlian_nama = keahlian_nama;
        this.tingkat = tingkat;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPekerja_id() {
        return pekerja_id;
    }

    public void setPekerja_id(String pekerja_id) {
        this.pekerja_id = pekerja_id;
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

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
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
