package com.example.demo.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	String subject;

	@JsonProperty("dead-line")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate deadLine;

	@JsonProperty("done")
	Boolean hasDone;

//	public Task() {
//	}

	public Task(String subject, LocalDate deadLine, Boolean hasDone) {
		this.subject = subject;
		this.deadLine = deadLine;
		this.hasDone = hasDone;
	}

	public Task(Integer id, LocalDate deadLine, Boolean hasDone, String subject) {
		this.id = id;
		this.subject = subject;
		this.deadLine = deadLine;
		this.hasDone = hasDone;
	}
}
