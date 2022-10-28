package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;

@Service
public class BookService {

	@Autowired
	BookDao bookDao;

	public Book findById(Integer id) {
		Book book = new Book();
		book.setId(id);
		return this.bookDao.findById(book);
	}

	public List<Book> getBookList() {
		return this.bookDao.findAll();
	}
}
