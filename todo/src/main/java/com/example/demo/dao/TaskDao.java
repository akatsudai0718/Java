package com.example.demo.dao;

import java.io.Serializable;
import java.util.List;

public interface TaskDao <T> extends Serializable {

	public List<T> getAll();
	public List<T> getWhereName(String id);
}
