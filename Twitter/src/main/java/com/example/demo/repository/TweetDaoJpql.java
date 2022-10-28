//package com.example.demo.repository;
//
//import com.example.demo.entity.Tweet;
//
//@Repository
//@AllArgsConstructor
//@NoArgsConstructor
//public class TweetDaoJpql implements TweetDao<Tweet>{
//
//	private EntityManager entityManager;
//
//	@Override
//	public List<Tweet> getAll() {
//		Query query = entityManager.createNativeQuery("SELECT FROM task");
//		@SuppressWarnings("unchecked")
//		List<Tweet> list = query.getResultList();
//		entityManager.close();
//		return list;
//	}
//
//	@Override
//	public List<Tweet> findAllTweet(int user_id) {
//		@SuppressWarnings("unchecked")
//		List<Tweet> list = entityManager
//							.createNativeQuery("SELECT * FROM tweet WHERE user_id = :user_id")
//							.setParameter("user_id", user_id)
//							.getResultList();
//		return list;
//	}
//
//}
