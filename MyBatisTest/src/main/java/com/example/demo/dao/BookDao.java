package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Book;

@Mapper
public interface BookDao {
	Book findById(Book book);

	List<Book>findAll();
}
