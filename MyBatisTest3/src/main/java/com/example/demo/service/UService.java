package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UMapper;

@Service
public class UService {

	@Autowired
	UMapper mapper;

	public User getUserOne(String id) {
		return mapper.findOne(id);
	}

	public List<User> getList() {
		return mapper.find();
	}

	public void insertOne(User u) {
		mapper.insertOne(u);
	}

	public void updateOne(String id, String name, int age) {
		mapper.updateOne(id, name, age);
	}

	public void deleteOne(User u) {
		mapper.deleteOne(u);
	}
}
