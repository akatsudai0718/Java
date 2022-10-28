package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CommentDAO;
import etc.Validation;
import model.Comment;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
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

		if (session.getAttribute("token") == null) {
			url = "LoginServlet";
	    	response.sendRedirect(url);
	    	return;
		}

		if (!Validation.isNumeric(request.getParameter("thread_id"))) {
			message = URLEncoder.encode("閲覧するスレッドを選択してください。", "UTF-8");
			url = "ThreadServlet?message_error=" + message;
		    response.sendRedirect(url);
	        return;
		}

		CommentDAO dao = new CommentDAO();

		int thread_id = Integer.parseInt(request.getParameter("thread_id"));
		String thread_name = dao.selectThreadInfo(thread_id);

		if (thread_name == null) {
			message = URLEncoder.encode("お探しのスレッドは存在しません。", "UTF-8");
			url = "ThreadServlet?message_error=" + message;
		    response.sendRedirect(url);
	        return;
		}

		List<Comment> list = dao.selectAllComments(thread_id);
		request.setAttribute("thread_id", thread_id);
		request.setAttribute("thread_name", thread_name);
		request.setAttribute("list", list);

		if (request.getParameter("message_error") != null) {
			String message_error = request.getParameter("message_error");
			request.setAttribute("message_error", message_error);
		}

		if (request.getParameter("message_done") != null) {
			String message_done = request.getParameter("message_done");
			request.setAttribute("message_done", message_done);
		}

		url = "/WEB-INF/jsp/comment.jsp";
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}