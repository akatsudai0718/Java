package com.example.demo.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class User {

	private Long id;

	private String name;

	private String address;

	private String phone;

	private Date updateDate;

	private Date createDate;

	private Date deleteDate;
}
