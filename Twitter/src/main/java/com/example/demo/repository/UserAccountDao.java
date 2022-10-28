package com.example.demo.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public class UserAccountDao implements IUserAccountDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserAccountDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUser(String user_name) {

        String sql = "SELECT id, user_name, account_name, email, password FROM user WHERE user_name = :user_name";

        Map<String, Object> param = new HashMap<>();
        param.put("user_name", user_name);

        User user = new User();

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, param);
            user.setId((int) result.get("id"));
            user.setUserName((String) result.get("user_name"));
            user.setEmail((String) result.get("email"));
            user.setPassword((String)result.get("password"));
        }catch(EmptyResultDataAccessException e){
            Optional <User> userOpl = Optional.ofNullable(user);
            return userOpl;
        }

        Optional <User> userOpl = Optional.ofNullable(user);
        return userOpl;
    }
}
