package com.example.demo.dto;

import lombok.Data;

@Data
public class UserSearchRequest {

	private Long id;

	private String name;

	private String address;

	private String phone;
}
