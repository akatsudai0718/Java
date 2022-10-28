package com.example.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Task;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Repository
@AllArgsConstructor
@NoArgsConstructor
public class TaskDaoJpql implements TaskDao<Task>{

	private EntityManager entityManager;

	@Override
	public List<Task> getAll() {
		Query query = entityManager.createNativeQuery("SELECT FROM task");
		@SuppressWarnings("unchecked")
		List<Task> list = query.getResultList();
		entityManager.close();
		return list;
	}

	@Override
	public List<Task> getWhereName(String subject) {
		@SuppressWarnings("unchecked")
		List<Task> list = entityManager
							.createNativeQuery("SELECT * FROM task WHERE subject = :subject")
							.setParameter("subject", subject)
							.getResultList();
		return list;
	}
}
