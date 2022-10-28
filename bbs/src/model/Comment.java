package model;

import java.io.Serializable;

public class Comment implements Serializable{
	private String user_name;
	private int comment_id;
	private int user_id;
	private int thread_id;
	private String created_at;
	private String updated_at;
	private String content;
	private String thread_name;

	public String getUserName () {
		return user_name;
	}
	public void setUserName (String user_name) {
		this.user_name = user_name;
	}

	public int getCommentId () {
		return comment_id;
	}
	public void setCommentId (int comment_id) {
		this.comment_id = comment_id;
	}

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

	public String getCreatedAt () {
		return created_at;
	}
	public void setCreatedAt (String created_at) {
		this.created_at = created_at;
	}

	public String getUpdatedAt () {
		return updated_at;
	}
	public void setUpdatedAt (String updated_at) {
		this.updated_at = updated_at;
	}

	public String getContent () {
		return content;
	}
	public void setContent (String content) {
		this.content = content;
	}

	public String getThreadName () {
		return thread_name;
	}
	public void setThreadName (String thread_name) {
		this.thread_name = thread_name;
	}
}
