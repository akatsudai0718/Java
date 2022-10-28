package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import etc.Hash;
import model.User;

public class UserDAO {

	private static String driver = "com.mysql.jdbc.Driver";
 	private static String url = "jdbc:mysql://localhost/test";
 	private static String user = "root";
 	private static String pass = "";

	Connection con = null;
	PreparedStatement  stmt = null;
	ResultSet rs = null;

 	public static Connection getConnection () {
 		try {
 			Class.forName(driver);
 			Connection con = DriverManager.getConnection(url, user, pass);
 			return con;
 		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		}
 	}

 	public User checkLoginUser (String mail, String password) {
 		User user = new User();
 		String sql = "SELECT * FROM user WHERE mail = ? AND password = ?";

 		try {
 			con = getConnection();
 			stmt = con.prepareStatement(sql);

			stmt.setString(1, mail);
			stmt.setBytes(2, Hash.createHash(password));

 			rs = stmt.executeQuery();

 			if (!rs.next()) {
 				return null;
 			}

 			user.setUserName(rs.getString("name"));
 			user.setUserId(rs.getInt("id"));
 			user.setMail(rs.getString("mail"));
 			user.setPassword(rs.getBytes("password"));

 		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		} finally {
 			if (stmt != null) {
 				try {stmt.close();} catch (SQLException ignore) {}
 			}
 			if (con != null) {
 				try {con.close();} catch (SQLException ignore) {}
 			}
 		}
 		return user;
 	}

 	public boolean checkEntryUser (String user_name, String mail) {
 		String sql = "SELECT * FROM user WHERE name = ? OR mail = ?";

 		try {
 			con = getConnection();
 			stmt = con.prepareStatement(sql);

			stmt.setString(1, user_name);
			stmt.setString(2, mail);

 			rs = stmt.executeQuery();

 			if (!rs.next()) {
 				return true;
 			}

 		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		} finally {
 			if (stmt != null) {
 				try {stmt.close();} catch (SQLException ignore) {}
 			}
 			if (con != null) {
 				try {con.close();} catch (SQLException ignore) {}
 			}
 		}
 		return false;
 	}

 	public int insertUser (String user_name, String mail, byte[] password_hash) {
 		int i = 0;
 		String sql = "INSERT INTO user (name, mail, password) VALUES (?, ?, ?)";

 		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setString(1, user_name);
			stmt.setString(2, mail);
			stmt.setBytes(3, password_hash);

			i = stmt.executeUpdate();

		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		}

//		System.out.println("Insert直後のpassword_hashは"+password_hash);
//		System.out.println("Insert直後のpassword_hash_sは"+password_hash_s);

 		return i;
 	}
}