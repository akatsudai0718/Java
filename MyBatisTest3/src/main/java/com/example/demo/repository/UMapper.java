package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.User;

@Mapper
public interface UMapper {

	public User findOne(String id);

	public List<User> find();

	public void insertOne(User u);

	public void updateOne(@Param("id") String id, @Param("name") String name, @Param("age") int age);

	public void deleteOne(User u);
}
