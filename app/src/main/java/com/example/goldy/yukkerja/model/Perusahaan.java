package com.example.goldy.yukkerja.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Perusahaan {
    private String id,email,nama,password,nomor_telp,alamat,foto,informasi,created_at,updated_at;

//    public Perusahaan(Map<String,String> simpleDetail){
//        this.id = simpleDetail.get("id");
//        this.email = simpleDetail
//    }

    public Perusahaan(){

    }

    public Perusahaan(String id, String email, String nama, String nomor_telp, String alamat, String foto, String informasi) {
        this.id = id;
        this.email = email;
        this.nama = nama;
        this.nomor_telp = nomor_telp;
        this.alamat = alamat;
        this.foto = foto;
        this.informasi = informasi;
    }

    public Perusahaan(String id, String email, String nama, String foto) {
        this.id = id;
        this.email = email;
        this.nama = nama;
        this.foto = foto;
    }

    public Perusahaan(String id, String email, String nama, String password, String nomor_telp, String alamat, String foto, String informasi, String created_at, String updated_at) {
        this.id = id;
        this.email = email;
        this.nama = nama;
        this.password = password;
        this.nomor_telp = nomor_telp;
        this.alamat = alamat;
        this.foto = foto;
        this.informasi = informasi;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
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
