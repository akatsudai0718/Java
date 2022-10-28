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

import dao.UserDAO;
import etc.Token;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url;

		if (request.getParameter("message_error") != null) {
			String message_error = request.getParameter("message_error");
			request.setAttribute("message_error", message_error);
		}

		url = "/WEB-INF/jsp/login.jsp";
	    RequestDispatcher dispatcher = request.getRequestDispatcher(url);
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserDAO dao = new UserDAO();

		String message = "";
		String url;

		String mail = request.getParameter("mail");
		String password = request.getParameter("password");

		User user = dao.checkLoginUser(mail, password);

		if (user == null) {
			message = URLEncoder.encode("ログイン失敗：メールアドレスとパスワードを正しく入力してください。", "UTF-8");
			url = "LoginServlet?message_error=" + message;
			response.sendRedirect(url);
			return;
		}

		HttpSession session = request.getSession(true);
		session.setAttribute("user_id", user.getUserId());
		session.setAttribute("user_name", user.getUserName());
	    session.setAttribute("token", Token.getToken());

		url = "ThreadServlet";
    	response.sendRedirect(url);	}
}