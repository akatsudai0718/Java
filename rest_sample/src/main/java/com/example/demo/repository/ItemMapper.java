package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Item;

@Mapper
public interface ItemMapper {

	int create(@Param("item") Item item);

	int update(@Param("item") Item item);

	Item read (@Param("id") int id);

	int delete(@Param("id") int id);
}
