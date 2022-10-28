package com.example.demo.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private int id;
	private String userName;
	private String accountName;
	private String email;
	private String password;
	private Timestamp createAt;
	private int following;
	private int followers;
}
