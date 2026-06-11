package com.example.demo.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemNameController {

    private final ItemNameRepository itemNameRepository;

    public ItemNameController(ItemNameRepository itemNameRepository) {
        this.itemNameRepository = itemNameRepository;
    }

    @GetMapping("/item-names")
    public String list(Model model) {
        model.addAttribute("items", itemNameRepository.findAll());
        return "items/item-names";
    }
}
