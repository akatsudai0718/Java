package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;

import model.Comment;

public class CommentDAO {

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

 	public ArrayList<Comment> selectAllComments (int thread_id) {
 		ArrayList<Comment> list = new ArrayList<Comment>();

 		String sql = "SELECT user.name AS user_name, comment.id AS comment_id, comment.user_id AS user_id, comment.thread_id AS thread_id, comment.created_at AS created_at, comment.updated_at AS updated_at, comment.content AS content, thread.name AS thread_name FROM comment INNER JOIN thread ON comment.thread_id = thread.id INNER JOIN user ON comment.user_id = user.id WHERE thread.id = ? ORDER BY comment_id desc";

 		try {
 			con = getConnection();
 			stmt = con.prepareStatement(sql);

			stmt.setInt(1, thread_id);

 			rs = stmt.executeQuery();

 			while (rs.next()) {
 				Comment comment = new Comment();
 				comment.setUserName(StringEscapeUtils.escapeHtml4(rs.getString("user_name")));
 				comment.setCommentId(rs.getInt("comment_id"));
 				comment.setUserId(rs.getInt("user_id"));
 				comment.setThreadId(rs.getInt("thread_id"));;
 				comment.setCreatedAt(StringEscapeUtils.escapeHtml4(rs.getString("created_at")));;
 				comment.setUpdatedAt(StringEscapeUtils.escapeHtml4(rs.getString("updated_at")));;
 				comment.setContent(StringEscapeUtils.escapeHtml4(rs.getString("content")));;
 				comment.setThreadName(StringEscapeUtils.escapeHtml4(rs.getString("thread_name")));;
 				list.add(comment);
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

 	public int insertComment (int user_id, int thread_id, String content) {
 		int i = 0;
 		String sql = "INSERT INTO comment (user_id, thread_id, content) VALUES (?, ?, ?)";

 		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, user_id);
			stmt.setInt(2, thread_id);
			stmt.setString(3, content);


			i = stmt.executeUpdate();

		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		}
 		return i;
 	}

 	public int updateComment (String content, int comment_id) {
 		int i = 0;
 		String sql = "UPDATE comment SET content = ? WHERE id = ?";

 		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setString(1, content);
			stmt.setInt(2, comment_id);

			i = stmt.executeUpdate();

		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		}
 		return i;
 	}

 	public int deleteComment (int comment_id, int user_id) {
 		int i = 0;
 		String sql = "DELETE FROM comment WHERE id = ? AND user_id = ?";

 		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, comment_id);
			stmt.setInt(2, user_id);

			i = stmt.executeUpdate();

		} catch (Exception e) {
 			throw new IllegalStateException(e);
 		}
 		return i;
 	}

 	public Comment selectCommentInfo (int comment_id) {
		Comment comment_info = new Comment();
 		String sql = "SELECT * FROM comment WHERE id = ?";

 		try {
 			con = getConnection();
 			stmt = con.prepareStatement(sql);

			stmt.setInt(1, comment_id);

 			rs = stmt.executeQuery();

 			if (!rs.next()) {
 				return null;
 			}

			comment_info.setCommentId(rs.getInt("id"));
			comment_info.setUserId(rs.getInt("user_id"));
			comment_info.setThreadId(rs.getInt("thread_id"));;
			comment_info.setContent(StringEscapeUtils.escapeHtml4(rs.getString("content")));;

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
 		return comment_info;
 	}

 	public String selectThreadInfo (int thread_id) {
 		String thread_name;
 		String sql = "SELECT name FROM thread WHERE id = ?";

 		try {
 			con = getConnection();
 			stmt = con.prepareStatement(sql);

			stmt.setInt(1, thread_id);

 			rs = stmt.executeQuery();

 			if (!rs.next()) {
 				return null;
 			}

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
}