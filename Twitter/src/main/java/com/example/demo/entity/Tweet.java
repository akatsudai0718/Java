package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
// DBとリンクしている必要がるのかわからない
@Table(name = "tweet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Tweet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	Integer user_id;

	String user_name;

	String account_name;

	String content;

	Integer reply_tweet_id;

	Integer number_of_reply;

	Integer retweet_tweet_id;

	Integer number_of_retweet;

	boolean is_retweet;

	Integer number_of_good;

	boolean is_good;

//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Timestamp create_at;

//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Timestamp update_at;
}
