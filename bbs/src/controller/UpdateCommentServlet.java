package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CommentDAO;
import etc.Token;
import etc.Validation;
import model.Comment;

/**
 * Servlet implementation class UpdateCommentServlet
 */
@WebServlet("/UpdateCommentServlet")
public class UpdateCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);

		String message = "";
		String url;

		if ((String)session.getAttribute("token") == null) {
			url = "LoginServlet";
	    	response.sendRedirect(url);
	    	return;
		}

		if (!Validation.isNumeric(request.getParameter("comment_id"))) {
			message = URLEncoder.encode("編集するコメントを選択してください。", "UTF-8");
			url = "CommentServlet?thread_id=" + request.getParameter("thread_id") + "&message_error=" + message;
			response.sendRedirect(url);
			return;
		}

		CommentDAO dao = new CommentDAO();

		int comment_id = Integer.parseInt(request.getParameter("comment_id"));
		Comment comment_info = dao.selectCommentInfo(comment_id);

		if (comment_info == null) {
			message = URLEncoder.encode("お探しのコメントは存在しません。", "UTF-8");
			url = "CommentServlet?thread_id=" + request.getParameter("thread_id") + "&message_error=" + message;
			response.sendRedirect(url);
			return;
		}

		request.setAttribute("comment_info", comment_info);

		if (request.getParameter("message_error") != null) {
			String message_error = request.getParameter("message_error");
			request.setAttribute("message_error", message_error);
		}

		url = "/WEB-INF/jsp/comment_edit.jsp";
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
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

		int comment_id = Integer.parseInt(request.getParameter("comment_id"));
		int thread_id = Integer.parseInt(request.getParameter("thread_id"));
		String text = request.getParameter("content");
		String text_old = request.getParameter("content_old");

		String content = Validation.moldText(text);

		if (!Validation.getResult()) {
			message = URLEncoder.encode("コメントが入力されていません。", "UTF-8");
			url = "UpdateCommentServlet?comment_id=" + comment_id + "&message_error=" + message;
		    response.sendRedirect(url);
		    return;
		}

		if (Validation.compareText(text, text_old)) {
			url = "CommentServlet?thread_id=" + thread_id;
		    response.sendRedirect(url);
		    return;
		}

		dao.updateComment(content, comment_id);
		message = URLEncoder.encode("コメントを編集しました。", "UTF-8");
		url = "CommentServlet?thread_id=" + thread_id + "&message_done=" + message;
	    response.sendRedirect(url);
	}
}