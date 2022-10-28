package com.example.demo.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Task;
import com.example.demo.model.TodoMapper;

@RestController
public class TodoRestController {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public Task getByName(String subject, LocalDate dead_line){
    	Task task = null;
        TodoMapper todoMapper = new TodoMapper();

        SqlParameterSource param = new MapSqlParameterSource().addValue("subject", subject).addValue("dead_line", dead_line);
        final String sql = "SELECT * FROM task WHERE subject = :subject AND dead_line = :dead_line";
        task = jdbcTemplate.queryForObject(sql, param, todoMapper);

        return task;
    }

    public Task getById(Integer id){
    	Task task = null;
        TodoMapper todoMapper = new TodoMapper();

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        final String sql = "SELECT * FROM task WHERE id = :id";
        task = jdbcTemplate.queryForObject(sql, param, todoMapper);

        return task;
    }
}
