package com.example.demo.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Good;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.repository.TweetDao;

@Service
public class TweetService {

	private final TweetDao dao;

	@Autowired
	public TweetService(TweetDao dao) {
		this.dao = dao;
	}

	public List<Tweet> findAllTweet(Integer user_id) {
		List<Tweet> allTweetsList = dao.findAllTweet(user_id);
		allTweetsList.sort(Comparator.comparing(Tweet::getUpdate_at).reversed());

		return allTweetsList;
	}

	public Tweet findOneTweet(Integer tweet_id) {
		Tweet tweet = dao.findOneTweet(tweet_id);
		return tweet;
	}

	public User findUser(Integer user_id) {
		User user = dao.findUser(user_id);
		return user;
	}

	public List<Tweet> createTweet(Integer user_id, String content) {
		dao.createTweet(user_id, content);

		List<Tweet> allTweetsList = dao.findAllTweet(user_id);
		allTweetsList.sort(Comparator.comparing(Tweet::getUpdate_at).reversed());

		return allTweetsList;
	}

	public List<Tweet> createReply(Integer user_id, Integer tweet_id, String content) {
		dao.createReply(user_id, tweet_id, content);

		List<Tweet> allTweetsList = dao.findAllTweet(user_id);
		allTweetsList.sort(Comparator.comparing(Tweet::getUpdate_at).reversed());

		return allTweetsList;
	}

	public List<Tweet> updateRetweet(Integer user_id, Integer tweet_id) {
		dao.updateRetweet(user_id, tweet_id);

		List<Tweet> allTweetsList = dao.findAllTweet(user_id);
		allTweetsList.sort(Comparator.comparing(Tweet::getUpdate_at).reversed());

		return allTweetsList;
	}

	public Good pushGood(Integer user_id, Integer tweet_id) {
		Good good = dao.updateGood(user_id, tweet_id);

		return good;
	}

	public List<Tweet> deleteTweet(Integer user_id, Integer tweet_id) {
		dao.deleteTweet(tweet_id);

		List<Tweet> allTweetsList = dao.findAllTweet(user_id);
		allTweetsList.sort(Comparator.comparing(Tweet::getUpdate_at).reversed());

		return allTweetsList;
	}
}
