package com.modul.praktikum1.Models;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa {
    @Id
    @NotBlank(message = "NIM tidak boleh kosong")
    @Size(min = 8, max = 12, message = "NIM harus antara 8 dan 12 karakter")
    private String nim;

    @NotBlank(message = "Nama tidak boleh kosong")
    @Size(max = 100, message = "Nama tidak boleh lebih dari 100 karakter")
    private String nama;

    @NotBlank(message = "Jurusan tidak boleh kosong")
    private String jurusan;

    @NotNull(message = "Tanggal lahir harus diisi")
    @Past(message = "Tanggal lahir harus di masa lalu")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tglLahir;

    public Mahasiswa(String nim, String nama, String jurusan, LocalDate tglLahir) {
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.tglLahir = tglLahir;
    }

    public Mahasiswa() {

    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public LocalDate getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(LocalDate tglLahir) {
        this.tglLahir = tglLahir;
    }
}
