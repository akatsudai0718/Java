package com.example.demo.repository;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Task;

@PersistenceContext
@Repository
public interface TodoRepository extends JpaRepository<Task, Integer> {
}