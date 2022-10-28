package com.example.demo.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.TaskDaoJpql;
import com.example.demo.domain.Task;

@RestController
public class EntityManagerController {

	@PersistenceContext
	EntityManager entityManager;

	TaskDaoJpql dao;

	@PostConstruct
	public void init() {
		dao = new TaskDaoJpql(entityManager);
	}

	public List<Task> findWhereName(String subject) {
		List<Task> list = dao.getWhereName(subject);
		return list;
	}
}
