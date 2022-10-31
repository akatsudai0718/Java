package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemMapper;

@Service
public class ItemService {

	@Autowired
	ItemMapper itemMapper;

	public int create(Item item) {
		return itemMapper.create(item);
	}

	public int update(Item item) {
		return itemMapper.update(item);
	}

	public Item read(int id) {
		return itemMapper.read(id);
	}

	public int delete(int id) {
		return itemMapper.delete(id);
	}
}
