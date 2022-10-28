package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.UserSearchRequest;
import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

	User search(UserSearchRequest user);

	List<User> searches(List<UserSearchRequest> users);

	void insert(UserSearchRequest user);

	void deleteId(UserSearchRequest user);

	void deleteAll();

}
