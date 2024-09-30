package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Controller

public class DemoApplication {
	private List<String> items = new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("items", items);
		return "index";
	}
	@PostMapping("/add")
	public String addItem(@RequestParam String item) {
		items.add(item);
		return "redirect:/";
	}
	@GetMapping("/edit/{index}")
	public String editItem(@PathVariable int index, Model model) {
		String item = items.get(index);
		model.addAttribute("index", index);
		model.addAttribute("item", item);
		return "edit";
	}
	@PostMapping("/update/{index}")
	public String updateItem(@PathVariable int index, @RequestParam String item) {
		items.set(index, item);
		return "redirect:/";
	}
	@GetMapping("/delete/{index}")
	public String deleteItem(@PathVariable int index) {
		items.remove(index);
		return "redirect:/";
	}
}