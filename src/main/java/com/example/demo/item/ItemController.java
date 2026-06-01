package com.example.demo.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
	
	private final ItemService itemService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("items", itemService.findAll());
		return "items/list";
	}
	
	@GetMapping("/new")
	public String newItem(Model model) {
		model.addAttribute("item", new Item());
		return "items/form";
	}
	
	@PostMapping
	public String create (@ModelAttribute Item item) {
		itemService.save(item);
		return "redirect:/items";
	}
	
	@GetMapping("/{id}/edit")
	public String edti(@PathVariable Long id, Model model) {
		model.addAttribute("item", itemService.findById(id));
		return "items/form";
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @ModelAttribute Item item) {
		item.setId(id);
		itemService.save(item);
		return "redirect:/items"; 
	}
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		itemService.delete(id);
		return "redirect:/items";
	}
}
