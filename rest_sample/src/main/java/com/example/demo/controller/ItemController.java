package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;

@RestController
@RequestMapping("item")
public class ItemController {

	@Autowired
	ItemService itemService;

	@PostMapping("")
	public Map create(@RequestBody Item item) {
		int number = itemService.create(item);

		Map<String, Object> res = new HashMap<>();
		res.put("data", number);
		return res;
	}

	@PutMapping("")
	public Map update(@RequestBody Item item) {
	    int number = itemService.update(item);

	    Map<String, Object> res = new HashMap<>();
	    res.put("data", number);
	    return res;
	}

	@GetMapping("/{id}")
	public Map read(@PathVariable("id") int id) {
	    Item item = itemService.read(id);

	    Map<String, Object> res = new HashMap<>();
	    res.put("data", item);
	    return res;
	}

	@DeleteMapping("/{id}")
	public Map delete(@PathVariable("id") int id) {
	    int number = itemService.delete(id);

	    Map<String, Object> res = new HashMap<>();
	    res.put("data", number);
	    return res;
	}
}
