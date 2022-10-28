package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ThreadDAO;
import model.Thread;

/**
 * Servlet implementation class home
 */
@WebServlet("/ThreadServlet")
public class ThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThreadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);

		String url;

		if (session.getAttribute("token") == null) {
			url = "LoginServlet";
	    	response.sendRedirect(url);
	    	return;
		}

		ThreadDAO dao = new ThreadDAO();

		List<Thread> list = dao.selectAllThreads();
		request.setAttribute("list", list);

		if (request.getParameter("message_error") != null) {
			String message_error = request.getParameter("message_error");
			request.setAttribute("message_error", message_error);
		}

		if (request.getParameter("message_done") != null) {
			String message_done = request.getParameter("message_done");
			request.setAttribute("message_done", message_done);
		}

		url = "/WEB-INF/jsp/thread.jsp";
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