package com.modul.praktikum1.Controllers;

import com.modul.praktikum1.Models.Mahasiswa;
import com.modul.praktikum1.Services.MahasiswaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MahasiswaController {
    @Autowired
    private MahasiswaService mahasiswaService;

    @GetMapping
    public String listMahasiswa(Model model) {
        model.addAttribute("mahasiswas", mahasiswaService.getAllMahasiswa());
        return "list";
    }

    @GetMapping("/tambah")
    public String showForm(Model model) {
        model.addAttribute("mahasiswa", new Mahasiswa());
        model.addAttribute("isEdit", false);
        return "form";
    }

    @PostMapping("/tambah")
    public String submitForm(@Valid @ModelAttribute Mahasiswa mahasiswa, BindingResult result , Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "form";
        }
        mahasiswaService.saveMahasiswa(mahasiswa);
        return "redirect:/";
    }

    @GetMapping("/edit/{nim}")
    public String showEditForm(@PathVariable String nim, Model model) {
        model.addAttribute("mahasiswa", mahasiswaService.getMahasiswaByNim(nim));
        model.addAttribute("isEdit", true);
        return "form";
    }

    @PostMapping("/edit/{nim}")
    public String updateMahasiswa(@PathVariable String nim, @Valid @ModelAttribute Mahasiswa mahasiswa, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "form";
        }
        mahasiswa.setNim(nim);
        mahasiswaService.saveMahasiswa(mahasiswa);
        return "redirect:/";
    }

    @GetMapping("/hapus/{nim}")
    public String deleteMahasiswa(@PathVariable String nim) {
        mahasiswaService.deleteMahasiswa(nim);
        return "redirect:/";
    }

    @GetMapping("/detail/{nim}")
    public String detailMahasiswa(@PathVariable String nim, Model model) {
        model.addAttribute("mahasiswa", mahasiswaService.getMahasiswaByNim(nim));
        return "detail";
    }

    @GetMapping("/cari")
    public String searchMahasiswa(@RequestParam String keyword, Model model) {
        model.addAttribute("mahasiswas", mahasiswaService.searchMahasiswa(keyword));
        return "list";
    }
}
