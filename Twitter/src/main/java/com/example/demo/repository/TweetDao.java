package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Good;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;

@Repository
public class TweetDao {

	//	public List<T> getAll();
	//	public List<T> findAllTweet(int user_id);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//	public void setJdbcTemplete(JdbcTemplate jdbcTemplate) {
	//		this.jdbcTemplate = jdbcTemplate;
	//	}

	public ArrayList<Tweet> findAllTweet(int user_id) {
		ArrayList<Tweet> list = new ArrayList<Tweet>();

		System.out.println(user_id);

		String sql = "SELECT "
					+ "user.account_name, "
					+ "tweet.id, "
					+ "tweet.user_id, "
					+ "tweet.content, "
					+ "tweet.reply_tweet_id, "
					+ "tweet.retweet_tweet_id, "
					+ "tweet.update_at "
				+ "FROM "
					+ "tweet "
				+ "JOIN "
					+ "user "
				+ "ON "
					+ "tweet.user_id = user.id "
				+ "WHERE "
					+ "user_id = ? "
				+ "UNION "
				+ "SELECT "
					+ "user.account_name, "
					+ "tweet.id, "
					+ "tweet.user_id, "
					+ "tweet.content, "
					+ "tweet.reply_tweet_id, "
					+ "tweet.retweet_tweet_id, "
					+ "tweet.update_at "
				+ "FROM "
					+ "tweet "
				+ "JOIN "
					+ "follow "
				+ "ON "
					+ "tweet.user_id = follow.user_id "
				+ "JOIN "
					+ "user "
				+ "ON "
					+ "tweet.user_id = user.id "
				+ "WHERE "
					+ "follow.follow_user_id =?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, user_id, user_id);

		while (rs.next()) {
			Tweet tweet = new Tweet();
			int tweet_id = rs.getInt("id");

			boolean is_retweet = findRetweet(user_id, tweet_id);
			tweet.set_retweet(is_retweet);

			boolean is_good = findGood(user_id, tweet_id);
			tweet.set_good(is_good);

			int number_of_reply = countReply(tweet_id);
			tweet.setNumber_of_reply(number_of_reply);

			int number_of_retweet = countRetweet(tweet_id);
			tweet.setNumber_of_retweet(number_of_retweet);

			int number_of_good = countGood(tweet_id);
			tweet.setNumber_of_good(number_of_good);

			tweet.setId(tweet_id);
			tweet.setAccount_name(rs.getString("account_name"));
			tweet.setContent(rs.getString("content"));
			tweet.setUpdate_at(rs.getTimestamp("update_at"));

			list.add(tweet);
		}
		return list;
	}

	private int countReply(int tweet_id) {
		String sql = "SELECT COUNT(*) FROM tweet WHERE reply_tweet_id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, tweet_id);
		rs.next();
		return rs.getInt(1);
	}

	private int countRetweet(int tweet_id) {
		String sql = "SELECT COUNT(*) FROM tweet WHERE retweet_tweet_id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, tweet_id);
		rs.next();
		return rs.getInt(1);
	}

	private int countGood(int tweet_id) {
		String sql = "SELECT COUNT(*) FROM good WHERE tweet_id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, tweet_id);
		rs.next();
		return rs.getInt(1);
	}

	public Tweet findOneTweet(Integer tweet_id) {
		Tweet tweet = new Tweet();

		String sql = "SELECT user.user_name, tweet.content, tweet.update_at FROM tweet JOIN user ON tweet.user_id = user.id WHERE tweet.id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, tweet_id);

		rs.next();
		tweet.setUser_name(rs.getString("user_name"));
		tweet.setContent(rs.getString("content"));
		tweet.setUpdate_at(rs.getTimestamp("update_at"));

		return tweet;
	}

	public User findUser(int user_id) {
		User user = new User();

		String sql = "SELECT "
					+ "user.user_name, "
					+ "user.account_name, "
					+ "user.email, "
					+ "user.create_at, "
				+ "(SELECT "
					+ "COUNT(*) "
				+ "FROM "
					+ "follow "
				+ "WHERE "
					+ "user_id = ? "
				+ ")AS followers, "
				+ "(SELECT "
					+ "COUNT(*) "
				+ "FROM "
					+ "follow "
				+ "WHERE "
					+ "follow_user_id = ? "
				+ ")AS following "
				+ "FROM "
					+ "user "
				+ "WHERE "
					+ "id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, user_id, user_id, user_id);
		rs.next();
		user.setUserName(rs.getString("user_name"));
		user.setAccountName(rs.getString("account_name"));
		user.setEmail(rs.getString("email"));
		user.setCreateAt(rs.getTimestamp("create_at"));
		user.setFollowing(rs.getInt("following"));
		user.setFollowers(rs.getInt("followers"));

		return user;
	}

	public void createTweet(Integer user_id, String content) {
		String sql = "INSERT INTO tweet (user_id, content) VALUES (?, ?)";
		jdbcTemplate.update(sql, user_id, content);
	}

	public void createReply(Integer user_id, Integer tweet_id, String content) {
		String sql = "INSERT INTO tweet (user_id, reply_tweet_id, content) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, user_id, tweet_id, content);
	}

	public void updateRetweet(Integer user_id, Integer tweet_id) {
		if(findRetweet(user_id, tweet_id)) {
			deleteRetweet(user_id, tweet_id);
			return;
		}

		String sql = "INSERT INTO tweet (user_id, retweet_tweet_id, content) VALUES (?, ?, ?)";
		String content = "これはリツイートです。";
		jdbcTemplate.update(sql, user_id, tweet_id, content);
	}

	public void deleteRetweet(Integer user_id, Integer tweet_id) {
		String sql = "DELETE FROM retweet WHERE user_id = ? AND tweet_id = ?";
		jdbcTemplate.update(sql, user_id, tweet_id);

		String sql2 = "DELETE FROM tweet WHERE user_id = ? AND retweet_tweet_id = ?";
		jdbcTemplate.update(sql2, user_id, tweet_id);
	}

	public Good updateGood(int user_id, int tweet_id) {
		Good good = new Good();

		if(findGood(user_id, tweet_id)) {
			good.set_good(false);
			deleteGood(user_id, tweet_id);
			good.setNumber_of_good(countGood(tweet_id));
			return good;
		}
		String sql = "INSERT INTO good (user_id, tweet_id) VALUES (? ,?)";
		jdbcTemplate.update(sql, user_id, tweet_id);

		good.set_good(true);
		good.setNumber_of_good(countGood(tweet_id));
		return good;
	}

	private boolean findRetweet(int user_id, int tweet_id) {
		String sql = "SELECT id FROM tweet WHERE user_id = ? AND retweet_tweet_id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, user_id, tweet_id);

		if(!rs.next()) {
			return false;
		}
		return true;
	}

	private boolean findGood(int user_id, int tweet_id) {
		String sql = "SELECT id FROM good WHERE user_id = ? AND tweet_id = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, user_id, tweet_id);

		if(!rs.next()) {
			return false;
		}
		return true;
	}

	private void deleteGood(int user_id, int tweet_id) {
		String sql = "DELETE FROM good WHERE user_id = ? AND tweet_id = ?";
		jdbcTemplate.update(sql, user_id, tweet_id);
	}

	public void deleteTweet(Integer tweet_id) {
		String sql = "DELETE FROM tweet WHERE id = ?";
		jdbcTemplate.update(sql, tweet_id);
	}
}