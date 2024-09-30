package com.modul.praktikum1.Repositories;

import com.modul.praktikum1.Models.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, String> {
    List<Mahasiswa> findByNamaContainingIgnoreCase(String nama);

    @Query("SELECT m FROM Mahasiswa m WHERE m.nim LIKE %:keyword% OR LOWER(m.nama) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Mahasiswa> search(String keyword);
}
