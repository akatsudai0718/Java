package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.UserListDto;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserListDto userListDto;

	@ModelAttribute
	UserListDto setFormDto() {
		return new UserListDto();
	}

	@GetMapping(value = "/user/search")
	public String displaySearch(@ModelAttribute("doneMessage") String message, Model model) {
		model.addAttribute("userSearchRequest", new UserSearchRequest());
		model.addAttribute("userSearchRequests", new ArrayList<UserSearchRequest>());
		model.addAttribute("message", message);
		return "user/search";
	}

	@RequestMapping(value = "/user/id_search", method = RequestMethod.POST)
	public String search(@ModelAttribute UserSearchRequest userSearchRequest, Model model) {
		User user = userService.search(userSearchRequest);
		model.addAttribute("userinfo", user);
		return "user/search";
	}

//	うまくいかない
	@RequestMapping(value = "/user/id_s_search", method = RequestMethod.POST)
	public String searches(Model model) {
		System.out.println("kokomade");
//		Users users = userService.searches(userSearchRequests);
//		model.addAttribute("userinfos", users);
		return "user/search";
	}

	@RequestMapping(value = "/user/id_insert", method = RequestMethod.POST)
	public String insert(RedirectAttributes redirectAttributes, @ModelAttribute UserSearchRequest userSearchRequest, Model model) {
		userService.insert(userSearchRequest);
//		User user = userService.search(userSearchRequest);
//		model.addAttribute("userinfo", user);
		String doneMessage = "登録しました。";
//		model.addAttribute("doneMessage", doneMessage);
		redirectAttributes.addFlashAttribute("doneMessage", doneMessage);
		return "redirect:/user/search";
	}

	@RequestMapping(value = "/user/id_delete_id", method = RequestMethod.POST)
	public String deleteId(RedirectAttributes redirectAttributes, @ModelAttribute UserSearchRequest userSearchRequest, Model model) {
		userService.deleteId(userSearchRequest);
		String doneMessage = "指定IDのユーザーを削除しました。";
		redirectAttributes.addFlashAttribute("doneMessage", doneMessage);
		return "redirect:/user/search";
	}

	@RequestMapping(value = "/user/delete_all", method = RequestMethod.POST)
	public String deleteAll(RedirectAttributes redirectAttributes, Model model) {
		userService.deleteAll();
		String doneMessage = "ユーザーをすべて削除しました。";
		redirectAttributes.addFlashAttribute("doneMessage", doneMessage);
		return "redirect:/user/search";
	}

}
