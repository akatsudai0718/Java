package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.UService;

@Controller
@RequestMapping("/users")
public class UController {

	@Autowired
	private UService service;

	@GetMapping("/list")
	public String getUserList(Model model) {
		List<User> userList = service.getList();
		model.addAttribute("users", userList);
		return "users/list";
	}

	@GetMapping("")
	public String top(Model model, @ModelAttribute User u) {
		model.addAttribute("users", service.getList());
		return "users/top";
	}

	@GetMapping("details/id={id}")
	public String details(@PathVariable("id") String id, Model model) {
		model.addAttribute("users", service.getUserOne(id));
		return "users/details";
	}

	@GetMapping("/register")
	public String registerUser(Model model, @ModelAttribute User u) {
		model.addAttribute("users", u);
		return "users/register";
	}

	@PostMapping("/register")
	public String CreatedFontTracker(@Validated @ModelAttribute User u, BindingResult result) {
		if (result.hasErrors()) {
			return "users/register";
		}
		service.insertOne(u);
		return "redirect:/users";
	}

	@GetMapping("change/id={id}")
	public String change(@PathVariable("id") String id, Model model) {
		model.addAttribute("users", service.getUserOne(id));
		return "users/change";
	}

	@PostMapping("change/id={id}")
	public String update(@ModelAttribute User u, Model model) {
		service.updateOne(u.getId(), u.getName(), u.getAge());
		return "redirect:/users";
	}

	@PostMapping("delete/id={id}")
	public String delete(@PathVariable String id, @ModelAttribute User u) {
		service.deleteOne(u);
		return "redirect:/users";
	}
}
