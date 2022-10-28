package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserSearchRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public User search(UserSearchRequest userSearchRequest) {
		return userMapper.search(userSearchRequest);
	}

	public List<User> searches(List<UserSearchRequest> userSearchRequests) {
		return userMapper.searches(userSearchRequests);
	}

	public void insert(UserSearchRequest userSearchRequest) {
		userMapper.insert(userSearchRequest);
	}

	public void deleteId(UserSearchRequest userSearchRequest) {
		userMapper.deleteId(userSearchRequest);
	}

	public void deleteAll() {
		userMapper.deleteAll();
	}

}