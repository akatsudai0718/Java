package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;

import model.Thread;

public class ThreadDAO {

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

 	public ArrayList<Thread> selectAllThreads () {
 		ArrayList<Thread> list = new ArrayList<Thread>();

 		String sql = "SELECT thread.id AS thread_id, thread.name AS thread_name, thread.user_id AS user_id, user.name AS user_name FROM thread INNER JOIN user ON thread.user_id = user.id ORDER BY thread_id desc";

 		try {
 			con = getConnection();
 			stmt = con.prepareStatement(sql);
 			rs = stmt.executeQuery();

 			while (rs.next()) {
 				Thread thread = new Thread();
 				thread.setUserId(rs.getInt("user_id"));
 				thread.setThreadId(rs.getInt("thread_id"));
 				thread.setUserName(StringEscapeUtils.escapeHtml4(rs.getString("user_name")));
 				thread.setThreadName(StringEscapeUtils.escapeHtml4(rs.getString("thread_name")));
 				list.add(thread);
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
 		return list;
 	}

 	public String selectThreadName (int thread_id) {
 		String thread_name;
 		String sql = "SELECT name FROM thread WHERE id = ?";

 		try {
 			con = getConnection();
 			stmt = con.prepareStatement(sql);

			stmt.setInt(1, thread_id);

 			rs = stmt.executeQuery();

 			thread_name = rs.getString("name");

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
 		return thread_name;
 	}

 	public int insertThread (int user_id, String thread_name) {
 		int i = 0;
 		String sql = "INSERT INTO thread (user_id, name) VALUES (?, ?)";

 		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, user_id);
	        stmt.setString(2, thread_name);

			i = stmt.executeUpdate();

		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		}

 		return i;
 	}

 	public int deleteThread(int thread_id, int user_id) {
 		int i = 0;
 		String sql = "DELETE thread, comment FROM thread LEFT JOIN comment ON thread.id = comment.thread_id WHERE thread.id = ? AND thread.user_id = ?";

 		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, thread_id);
	        stmt.setInt(2, user_id);

			i = stmt.executeUpdate();

		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		}

 		return i;
 	}
}