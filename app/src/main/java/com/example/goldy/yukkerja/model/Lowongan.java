package com.example.goldy.yukkerja.model;

import java.util.List;

public class Lowongan {
    private String id, perusahaan_id, judul, deskripsi, requirement, waktu_kerja, range_gaji1, range_gaji2,lokasi,status, created_at, updated_at;
    private List<Lkeahlian> lkeahlian;
    private Perusahaan perusahaan;

    public Lowongan() {

    }

    public Lowongan(String id, String judul, String range_gaji1, String range_gaji2, String created_at, String updated_at) {
        this.id = id;
        this.judul = judul;
        this.range_gaji1 = range_gaji1;
        this.range_gaji2 = range_gaji2;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Lowongan(String id, String perusahaan_id, String judul, String deskripsi, String requirement, String waktu_kerja, String range_gaji1, String range_gaji2, String lokasi, String status, String created_at, String updated_at, List<Lkeahlian> lkeahlian, Perusahaan perusahaan) {
        this.id = id;
        this.perusahaan_id = perusahaan_id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.requirement = requirement;
        this.waktu_kerja = waktu_kerja;
        this.range_gaji1 = range_gaji1;
        this.range_gaji2 = range_gaji2;
        this.lokasi = lokasi;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.lkeahlian = lkeahlian;
        this.perusahaan = perusahaan;
    }

    public Lowongan(String id, String perusahaan_id, String judul, String deskripsi, String requirement, String waktu_kerja, String range_gaji1, String range_gaji2, String lokasi, String status, String created_at, String updated_at, List<Lkeahlian> lkeahlian) {
        this.id = id;
        this.perusahaan_id = perusahaan_id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.requirement = requirement;
        this.waktu_kerja = waktu_kerja;
        this.range_gaji1 = range_gaji1;
        this.range_gaji2 = range_gaji2;
        this.lokasi = lokasi;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.lkeahlian = lkeahlian;
    }

    public Lowongan(String id, String perusahaan_id, String judul, String deskripsi, String requirement, String waktu_kerja, String range_gaji1, String range_gaji2, String lokasi, String status, String created_at, String updated_at) {
        this.id = id;
        this.perusahaan_id = perusahaan_id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.requirement = requirement;
        this.waktu_kerja = waktu_kerja;
        this.range_gaji1 = range_gaji1;
        this.range_gaji2 = range_gaji2;
        this.lokasi = lokasi;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Lowongan(String id, String perusahaan_id, String judul, String deskripsi, String requirement, String waktu_kerja, String range_gaji1, String range_gaji2, String lokasi, String status, String created_at, String updated_at, Perusahaan perusahaan) {
        this.id = id;
        this.perusahaan_id = perusahaan_id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.requirement = requirement;
        this.waktu_kerja = waktu_kerja;
        this.range_gaji1 = range_gaji1;
        this.range_gaji2 = range_gaji2;
        this.lokasi = lokasi;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.perusahaan = perusahaan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPerusahaan_id() {
        return perusahaan_id;
    }

    public void setPerusahaan_id(String perusahaan_id) {
        this.perusahaan_id = perusahaan_id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getWaktu_kerja() {
        return waktu_kerja;
    }

    public void setWaktu_kerja(String waktu_kerja) {
        this.waktu_kerja = waktu_kerja;
    }

    public String getRange_gaji1() {
        return range_gaji1;
    }

    public void setRange_gaji1(String range_gaji1) {
        this.range_gaji1 = range_gaji1;
    }

    public String getRange_gaji2() {
        return range_gaji2;
    }

    public void setRange_gaji2(String range_gaji2) {
        this.range_gaji2 = range_gaji2;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Lkeahlian> getLkeahlian() {
        return lkeahlian;
    }

    public void setLkeahlian(List<Lkeahlian> lkeahlian) {
        this.lkeahlian = lkeahlian;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(Perusahaan perusahaan) {
        this.perusahaan = perusahaan;
    }
}