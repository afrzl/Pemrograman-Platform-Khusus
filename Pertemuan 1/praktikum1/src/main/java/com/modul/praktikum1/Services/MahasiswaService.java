package com.modul.praktikum1.Services;

import com.modul.praktikum1.Models.Mahasiswa;
import com.modul.praktikum1.Repositories.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MahasiswaService {
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public List<Mahasiswa> getAllMahasiswa() {
        return mahasiswaRepository.findAll();
    }

    public void saveMahasiswa(Mahasiswa mahasiswa) {
        mahasiswaRepository.save(mahasiswa);
    }

    public Mahasiswa getMahasiswaByNim(String nim) {
        return mahasiswaRepository.findById(nim)
                .orElseThrow(() -> new RuntimeException("Mahasiswa tidak ditemukan dengan NIM: " + nim));
    }

    public void deleteMahasiswa(String nim) {
        mahasiswaRepository.deleteById(nim);
    }

    public List<Mahasiswa> searchMahasiswa(String keyword) {
        return mahasiswaRepository.search(keyword);
    }
}
