package model;

import java.io.Serializable;

public class User implements Serializable{
	private String user_name;
	private int user_id;
	private String mail;
	private byte[] password;

	public String getUserName () {
		return user_name;
	}
	public void setUserName (String user_name) {
		this.user_name = user_name;
	}

	public int getUserId () {
		return user_id;
	}
	public void setUserId (int user_id) {
		this.user_id = user_id;
	}

	public String getMail () {
		return mail;
	}
	public void setMail (String mail) {
		this.mail = mail;
	}

	public byte[] getPassword () {
		return password;
	}
	public void setPassword (byte[] password) {
		this.password = password;
	}
}
