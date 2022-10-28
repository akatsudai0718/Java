package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import etc.Hash;
import etc.Validation;

/**
 * Servlet implementation class EntryServlet
 */
@WebServlet("/EntryServlet")
public class EntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EntryServlet() {
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

		if (request.getParameter("message_done") != null) {
			String message_done = request.getParameter("message_done");
			request.setAttribute("message_done", message_done);
		}

		url = "/WEB-INF/jsp/entry.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		UserDAO dao = new UserDAO();

		String message = "";
		String url;
		byte[] password_hash = null;

		String text_user_name = request.getParameter("user_name");
		String text_mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");

		String user_name = Validation.moldText(text_user_name);
		if (!Validation.getResult()) {
			message = message + "ユーザ名が入力されていません。";
		}

		String mail = Validation.moldText(text_mail);
		if (!Validation.getResult()) {
			message = message + "ユーザ名が入力されていません。";
		} else if (!Validation.Mail(mail)) {
			message = message + "不正な形式のメールアドレスです。";
		}

		if (!Validation.Password(password)) {
			message = message + "パスワードは8文字以上24文字以下の半角英数字です。";
		} else if (!Validation.compareText(password, password2)) {
			message = message + "パスワードが一致しません。";
		}

		if (message.isEmpty() && !dao.checkEntryUser(user_name, mail)) {
			message = message + "同じ名前またはメールアドレスのユーザーが存在します。";
		}

		if (!message.isEmpty()) {
			message = URLEncoder.encode("登録失敗：" + message, "UTF-8");
			url = "EntryServlet?message_error=" + message;
		    response.sendRedirect(url);
		    return;
		}

		try {
			password_hash = Hash.createHash(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		dao.insertUser(user_name, mail, password_hash);
		message = URLEncoder.encode("登録完了：ユーザーの登録を行いました。", "UTF-8");
		url = "EntryServlet?message_done=" + message;
	    response.sendRedirect(url);
	}
}