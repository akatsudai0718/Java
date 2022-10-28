package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Good;
import com.example.demo.entity.MyUserDetails;
import com.example.demo.entity.Tweet;
import com.example.demo.entity.User;
import com.example.demo.service.MyUserService;
import com.example.demo.service.TweetService;

@Controller
public class TwitterWebController {

	@Autowired
	MyUserService myUserService;
	@Autowired
	TweetService tweetService;

	@RequestMapping(value="/loginForm")
	@ResponseBody
	public ModelAndView readLiginForm() {
		ModelAndView modelAndView = new ModelAndView("loginForm");

		return modelAndView;
	}

	@RequestMapping(value="/home")
	@ResponseBody
	public ModelAndView readHome(@AuthenticationPrincipal MyUserDetails user) {
		ModelAndView modelAndView = new ModelAndView("home");

		List<Tweet> tweets = tweetService.findAllTweet(user.getUserId());
		modelAndView.addObject("tweets", tweets);
		return modelAndView;
	}

	@RequestMapping(value="/tweet")
	@ResponseBody
	public List<Tweet> createTweet(String content, @AuthenticationPrincipal MyUserDetails user) {
		System.out.print("通過「createTweet」");
		System.out.print("contentの中身は" + content);

		int user_id = user.getUserId();
		List<Tweet> allTweet = tweetService.createTweet(user_id, content);

		return allTweet;
	}

	@RequestMapping(value= {"/replyForm","/retweetForm"})
	@ResponseBody
	public Tweet findOneTweet(int tweet_id) {
		Tweet tweet = tweetService.findOneTweet(tweet_id);
		return tweet;
	}

	@RequestMapping(value="/reply")
	@ResponseBody
	public List<Tweet> createReply(String content, int tweet_id, @AuthenticationPrincipal MyUserDetails user) {
		System.out.print("通過「createReply」");
		System.out.print("contentの中身は" + content);
		System.out.print("reply_tweet_idの中身は" + tweet_id);

		int user_id = user.getUserId();
		List<Tweet> allTweet = tweetService.createReply(user_id, tweet_id, content);

		return allTweet;
	}

	@RequestMapping(value="/retweet")
	@ResponseBody
	public List<Tweet> createRetweet(int tweet_id, @AuthenticationPrincipal MyUserDetails user) {
		System.out.print("通過「createRetweet」");
		System.out.print("retweet_tweet_idの中身は" + tweet_id);

		int user_id = user.getUserId();
		List<Tweet> allTweet = tweetService.updateRetweet(user_id, tweet_id);

		return allTweet;
	}

	@RequestMapping(value="/good")
	@ResponseBody
	public Good pushGood(Integer tweet_id, @AuthenticationPrincipal MyUserDetails user) {
//		System.out.println("通過「pushGood」");
//		System.out.println(tweet_id);

		int user_id = user.getUserId();
		Good good = tweetService.pushGood(user_id, tweet_id);

//		System.out.println(number_of_good);
		return good;
	}

	@RequestMapping(value="/delete")
	@ResponseBody
	public List<Tweet> deleteTweet(Integer tweet_id, @AuthenticationPrincipal MyUserDetails user) {
		int user_id = user.getUserId();
		List<Tweet> allTweet = tweetService.deleteTweet(user_id, tweet_id);

		return allTweet;
	}

	@GetMapping("/profile")
	public ModelAndView profileForm(@AuthenticationPrincipal MyUserDetails user) {
		ModelAndView modelAndView = new ModelAndView("profile");

		int user_id = user.getUserId();
		User ob_user = tweetService.findUser(user_id);
		modelAndView.addObject("user", ob_user);

		List<Tweet> tweets = tweetService.findAllTweet(user_id);
		modelAndView.addObject("tweets", tweets);
		return modelAndView;
	}


}