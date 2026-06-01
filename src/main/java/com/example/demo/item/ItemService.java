package com.example.demo.item;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	public List<Item> findAll(){
		return itemRepository.findAll();
	}
	
	public Item findById(Long id) {
		return itemRepository.findById(id).orElse(null);
	}
	
	public Item save(Item item) {
		return itemRepository.save(item);
	}
	
	public void delete (Long id) {
		itemRepository.deleteById(id);
	}

}
