package com.example.demo.item;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class ItemService {

    private final ItemRepository itemRepo;

    public ItemService(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    public List<Item> findAll() {
        return itemRepo.findAll();
    }

    public Item findById(Long id) {
        return itemRepo.findById(id).orElseThrow();
    }

    public void save(Item item) {
        itemRepo.save(item);
    }

    public void delete(Long id) {
        itemRepo.deleteById(id);
    }
}
