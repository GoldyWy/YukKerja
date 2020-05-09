package com.example.goldy.yukkerja.model;

import java.util.List;

public class Pendaftar {
    private String id,pekerja_id,perusahaan_id,lowongan_id,status,created_at,updated_at;
    private Pekerja pekerja;
    private Lowongan lowongan;
    private Perusahaan perusahaan;

    public Pendaftar(String id, String pekerja_id, String perusahaan_id, String lowongan_id, String status, String created_at, String updated_at, Pekerja pekerja, Lowongan lowongan, Perusahaan perusahaan) {
        this.id = id;
        this.pekerja_id = pekerja_id;
        this.perusahaan_id = perusahaan_id;
        this.lowongan_id = lowongan_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.pekerja = pekerja;
        this.lowongan = lowongan;
        this.perusahaan = perusahaan;
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

    public String getPerusahaan_id() {
        return perusahaan_id;
    }

    public void setPerusahaan_id(String perusahaan_id) {
        this.perusahaan_id = perusahaan_id;
    }

    public String getLowongan_id() {
        return lowongan_id;
    }

    public void setLowongan_id(String lowongan_id) {
        this.lowongan_id = lowongan_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Pekerja getPekerja() {
        return pekerja;
    }

    public void setPekerja(Pekerja pekerja) {
        this.pekerja = pekerja;
    }

    public Lowongan getLowongan() {
        return lowongan;
    }

    public void setLowongan(Lowongan lowongan) {
        this.lowongan = lowongan;
    }

    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(Perusahaan perusahaan) {
        this.perusahaan = perusahaan;
    }
}
