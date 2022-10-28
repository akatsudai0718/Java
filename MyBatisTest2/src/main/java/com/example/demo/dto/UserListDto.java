package com.example.demo.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

import lombok.Data;

@Data
@Component
public class UserListDto {

	private List<User> users;
}
