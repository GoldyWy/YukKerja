package com.example.goldy.yukkerja.model;

public class Pendidikan {
    private String id, pekerja_id, institusi, bulan_wisuda, tahun_wisuda, kualifikasi, jurusan, nilai_akhir, created_at, updated_at;

    public Pendidikan() {
    }

    public Pendidikan(String id, String pekerja_id, String institusi, String bulan_wisuda, String tahun_wisuda, String kualifikasi, String jurusan, String nilai_akhir, String created_at, String updated_at) {
        this.id = id;
        this.pekerja_id = pekerja_id;
        this.institusi = institusi;
        this.bulan_wisuda = bulan_wisuda;
        this.tahun_wisuda = tahun_wisuda;
        this.kualifikasi = kualifikasi;
        this.jurusan = jurusan;
        this.nilai_akhir = nilai_akhir;
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

    public String getInstitusi() {
        return institusi;
    }

    public void setInstitusi(String institusi) {
        this.institusi = institusi;
    }

    public String getBulan_wisuda() {
        return bulan_wisuda;
    }

    public void setBulan_wisuda(String bulan_wisuda) {
        this.bulan_wisuda = bulan_wisuda;
    }

    public String getTahun_wisuda() {
        return tahun_wisuda;
    }

    public void setTahun_wisuda(String tahun_wisuda) {
        this.tahun_wisuda = tahun_wisuda;
    }

    public String getKualifikasi() {
        return kualifikasi;
    }

    public void setKualifikasi(String kualifikasi) {
        this.kualifikasi = kualifikasi;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getNilai_akhir() {
        return nilai_akhir;
    }

    public void setNilai_akhir(String nilai_akhir) {
        this.nilai_akhir = nilai_akhir;
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
