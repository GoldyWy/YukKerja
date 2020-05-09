package com.example.goldy.yukkerja.model;

import java.util.List;

public class ResponseModelAdmin {
    private String status ,message, perusahaan, pekerja, pekerjaaktif, pekerjanonaktif, lowongan, lowonganaktif, lowongantutup, lowongansejarah;
    private List<Keahlian> keahlians;

    public ResponseModelAdmin(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelAdmin(String status, String message, List<Keahlian> keahlians) {
        this.status = status;
        this.message = message;
        this.keahlians = keahlians;
    }

    public ResponseModelAdmin(String status, String message, String perusahaan, String pekerja, String pekerjaaktif, String pekerjanonaktif, String lowongan, String lowonganaktif, String lowongantutup, String lowongansejarah) {
        this.status = status;
        this.message = message;
        this.perusahaan = perusahaan;
        this.pekerja = pekerja;
        this.pekerjaaktif = pekerjaaktif;
        this.pekerjanonaktif = pekerjanonaktif;
        this.lowongan = lowongan;
        this.lowonganaktif = lowonganaktif;
        this.lowongantutup = lowongantutup;
        this.lowongansejarah = lowongansejarah;
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

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getPekerja() {
        return pekerja;
    }

    public void setPekerja(String pekerja) {
        this.pekerja = pekerja;
    }

    public String getPekerjaaktif() {
        return pekerjaaktif;
    }

    public void setPekerjaaktif(String pekerjaaktif) {
        this.pekerjaaktif = pekerjaaktif;
    }

    public String getPekerjanonaktif() {
        return pekerjanonaktif;
    }

    public void setPekerjanonaktif(String pekerjanonaktif) {
        this.pekerjanonaktif = pekerjanonaktif;
    }

    public String getLowongan() {
        return lowongan;
    }

    public void setLowongan(String lowongan) {
        this.lowongan = lowongan;
    }

    public String getLowonganaktif() {
        return lowonganaktif;
    }

    public void setLowonganaktif(String lowonganaktif) {
        this.lowonganaktif = lowonganaktif;
    }

    public String getLowongantutup() {
        return lowongantutup;
    }

    public void setLowongantutup(String lowongantutup) {
        this.lowongantutup = lowongantutup;
    }

    public String getLowongansejarah() {
        return lowongansejarah;
    }

    public void setLowongansejarah(String lowongansejarah) {
        this.lowongansejarah = lowongansejarah;
    }

    public List<Keahlian> getKeahlians() {
        return keahlians;
    }

    public void setKeahlians(List<Keahlian> keahlians) {
        this.keahlians = keahlians;
    }
}
