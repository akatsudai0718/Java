package com.example.demo.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Book {
	private Integer id;
	private String bookName;
	private Integer volumeNum;
	private String authorName;
	private LocalDate publishedDate;
}
