package com.example.demo.item;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

@RequestMapping("/items")
public class ItemController {
	
	private final ItemService itemService;
	
	public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
	
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
	
	@GetMapping("/edit/{id}")
	public String edti(@PathVariable Long id, Model model) {
		model.addAttribute("item", itemService.findById(id));
		return "items/form";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@PathVariable Long id, @ModelAttribute Item item) {
		item.setId(id);
		itemService.save(item);
		return "redirect:/items"; 
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes ra) {
	    try {
	        itemService.delete(id);
	        ra.addFlashAttribute("success", "削除しました");
	    } catch (DataIntegrityViolationException e) {
	        ra.addFlashAttribute("error", "この商品は注文に使用されているため削除できません");
	    }
	    return "redirect:/items";
	}

}
