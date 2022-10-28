package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CommentDAO;
import etc.Token;
import etc.Validation;

/**
 * Servlet implementation class InsertCommentServlet
 */
@WebServlet("/InsertCommentServlet")
public class InsertCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(false);

	    String url;

		if (!Token.csrfCheck(session, request, response)) {
			url = "ThreadServlet";
	    	response.sendRedirect(url);
	    	return;
		}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession(false);

		String message;
		String url;

		if (!Token.csrfCheck(session, request, response)) {
			url = "ThreadServlet";
	    	response.sendRedirect(url);
	    	return;
		}

		CommentDAO dao = new CommentDAO();

		int user_id = (int)session.getAttribute("user_id");
		int thread_id = Integer.parseInt(request.getParameter("thread_id"));
		String text = request.getParameter("content");

		String content = Validation.moldText(text);

		if (!Validation.getResult()) {
			message = URLEncoder.encode("コメントが入力されていません。", "UTF-8");
			url = "CommentServlet?thread_id=" + thread_id + "&message_error=" + message;
		    response.sendRedirect(url);
		    return;
		}

		dao.insertComment(user_id, thread_id, content);
		message = URLEncoder.encode("コメントを追加しました。", "UTF-8");
		url = "CommentServlet?thread_id=" + thread_id + "&message_done=" + message;
	    response.sendRedirect(url);
	}
}