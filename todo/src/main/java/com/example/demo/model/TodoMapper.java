package com.example.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.domain.Task;

public class TodoMapper implements RowMapper<Task>{
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Integer id = rs.getInt("id");
        LocalDate deadLine = rs.getDate("dead_line").toLocalDate();
        Boolean hasDone = rs.getBoolean("has_done");
        String subject = rs.getString("subject");

        return new Task(id, deadLine, hasDone, subject);
    }
}
