package com.example.goldy.yukkerja.model;

import java.util.List;

public class Pekerja {
    private String id,email,nama_depan,nama_belakang,password,jk,nomor_telp,alamat,foto,gaji_harapan,lokasi_kerja,resume,resume_updated_at,status,deskripsi,token,created_at,updated_at;
    private List<Pkeahlian> pkeahlian;

    public Pekerja()
    {

    }

    public Pekerja(String id, String email, String nama_depan, String foto, String status, String jk) {
        this.id = id;
        this.email = email;
        this.nama_depan = nama_depan;
        this.foto = foto;
        this.status = status;
        this.jk = jk;
    }

    public Pekerja(String id, String email, String nama_depan, String nama_belakang, String password, String jk, String nomor_telp, String alamat, String foto, String gaji_harapan, String lokasi_kerja, String resume, String resume_updated_at, String status, String deskripsi, String token, String created_at, String updated_at, List<Pkeahlian> pkeahlian) {
        this.id = id;
        this.email = email;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.password = password;
        this.jk = jk;
        this.nomor_telp = nomor_telp;
        this.alamat = alamat;
        this.foto = foto;
        this.gaji_harapan = gaji_harapan;
        this.lokasi_kerja = lokasi_kerja;
        this.resume = resume;
        this.resume_updated_at = resume_updated_at;
        this.status = status;
        this.deskripsi = deskripsi;
        this.token = token;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.pkeahlian = pkeahlian;
    }

    public Pekerja(String id, String email, String nama_depan, String nama_belakang, String jk, String nomor_telp, String alamat, String foto, String gaji_harapan) {
        this.id = id;
        this.email = email;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.jk = jk;
        this.nomor_telp = nomor_telp;
        this.alamat = alamat;
        this.foto = foto;
        this.gaji_harapan = gaji_harapan;
    }

    public Pekerja(String id, String email, String nama_depan, String nama_belakang, String jk, String nomor_telp, String alamat, String foto) {
        this.id = id;
        this.email = email;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.jk = jk;
        this.nomor_telp = nomor_telp;
        this.alamat = alamat;
        this.foto = foto;
    }

    public Pekerja(String id, String email, String nama_depan, String foto) {
        this.id = id;
        this.email = email;
        this.nama_depan = nama_depan;
        this.foto = foto;
    }

    public Pekerja(String id, String email, String nama_depan, String nama_belakang, String password, String jk, String nomor_telp, String alamat, String foto, String gaji_harapan, String lokasi_kerja, String resume, String resume_updated_at, String status, String deskripsi, String token, String created_at, String updated_at) {
        this.id = id;
        this.email = email;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.password = password;
        this.jk = jk;
        this.nomor_telp = nomor_telp;
        this.alamat = alamat;
        this.foto = foto;
        this.gaji_harapan = gaji_harapan;
        this.lokasi_kerja = lokasi_kerja;
        this.resume = resume;
        this.resume_updated_at = resume_updated_at;
        this.status = status;
        this.deskripsi = deskripsi;
        this.token = token;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama_depan() {
        return nama_depan;
    }

    public void setNama_depan(String nama_depan) {
        this.nama_depan = nama_depan;
    }

    public String getNama_belakang() {
        return nama_belakang;
    }

    public void setNama_belakang(String nama_belakang) {
        this.nama_belakang = nama_belakang;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getNomor_telp() {
        return nomor_telp;
    }

    public void setNomor_telp(String nomor_telp) {
        this.nomor_telp = nomor_telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getGaji_harapan() {
        return gaji_harapan;
    }

    public void setGaji_harapan(String gaji_harapan) {
        this.gaji_harapan = gaji_harapan;
    }

    public String getLokasi_kerja() {
        return lokasi_kerja;
    }

    public void setLokasi_kerja(String lokasi_kerja) {
        this.lokasi_kerja = lokasi_kerja;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getResume_updated_at() {
        return resume_updated_at;
    }

    public void setResume_updated_at(String resume_updated_at) {
        this.resume_updated_at = resume_updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public List<Pkeahlian> getPkeahlian() {
        return pkeahlian;
    }

    public void setPkeahlian(List<Pkeahlian> pkeahlian) {
        this.pkeahlian = pkeahlian;
    }
}
