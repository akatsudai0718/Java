package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Book;
import com.example.demo.form.BookForm;
import com.example.demo.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;

//	@ModelAttribute
//	BookForm setUpForm() {
//		return new BookForm();
//	}

	@RequestMapping("/search")
	public String index(@ModelAttribute BookForm bookForm, String showList, Model model) {

		model.addAttribute("title", "本屋さん");

		if(bookForm.getId() != null) {
			Book book = bookService.findById(bookForm.getId());
			model.addAttribute("book", book);
		}

		if(showList != null) {
			List<Book> bookList = bookService.getBookList();
			model.addAttribute("bookList", bookList);
		}

		return "index2";
	}

//	@RequestMapping("/search2")
//	public String index2(@RequestParam(name="id") Integer id, @RequestParam(name="showList") String sl, Model model) {
//
//		System.out.println("IDは" + id);
//		System.out.println("showListは" + sl);
//
//		model.addAttribute("title", "本屋さん");
//
//		if(id != null) {
//			Book book = bookService.findById(id);
//			model.addAttribute("book", book);
//		}
//
//		if(sl != null) {
//			List<Book> bookList = bookService.getBookList();
//			model.addAttribute("bookList", bookList);
//		}
//
//		return "index2";
//	}
}
