package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ThreadDAO;
import etc.Token;
import etc.Validation;

/**
 * Servlet implementation class HomeThreadServlet
 */
@WebServlet("/InsertThreadServlet")
public class InsertThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertThreadServlet() {
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

		ThreadDAO dao = new ThreadDAO();

		int user_id = (int)session.getAttribute("user_id");
		String text = request.getParameter("thread_name");

		String thread_name = Validation.moldText(text);

		if (!Validation.getResult()) {
			message = URLEncoder.encode("スレッド名が入力されていません。", "UTF-8");
			url = "ThreadServlet?message_error=" + message;
		    response.sendRedirect(url);
		    return;
		}

		dao.insertThread(user_id, thread_name);
		message = URLEncoder.encode("スレッド「" + thread_name + "」を作成しました。", "UTF-8");
		url = "ThreadServlet?message_done=" + message;
	    response.sendRedirect(url);
	}
}