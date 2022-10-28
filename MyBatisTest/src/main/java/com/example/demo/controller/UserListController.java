package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.mapper.UserMapper;

@Controller
public class UserListController {

	@Autowired
	UserMapper userMapper;

	@GetMapping("/")
	public ModelAndView listAll() {

		ModelAndView mav = new ModelAndView("index");

		mav.addObject("users", userMapper.selectAll());

		return mav;
	}
}
