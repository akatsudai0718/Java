package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.entity.User;

public interface IUserAccountDao {

	Optional<User> findUser(String user_name);
}
