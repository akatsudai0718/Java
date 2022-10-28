package model;

import java.io.Serializable;

public class Thread implements Serializable{
	private int user_id;
	private int thread_id;
	private String user_name;
	private String thread_name;

//	public Thread () {};
//	public Thread (String user_name,String thread_name) {
//		this.user_name=user_name;
//		this.thread_name=thread_name;
//	}
	public int getUserId () {
		return user_id;
	}
	public void setUserId (int user_id) {
		this.user_id = user_id;
	}

	public int getThreadId () {
		return thread_id;
	}
	public void setThreadId (int thread_id) {
		this.thread_id = thread_id;
	}

	public String getUserName () {
		return user_name;
	}
	public void setUserName (String user_name) {
		this.user_name = user_name;
	}

	public String getThreadName () {
		return thread_name;
	}
	public void setThreadName (String thread_name) {
		this.thread_name = thread_name;
	}
}
