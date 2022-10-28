package etc;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Token {

	private static int TOKEN_LENGTH = 16;

	public static String getToken () {
		byte token[] = new byte[TOKEN_LENGTH];
		StringBuffer buf = new StringBuffer();
	    SecureRandom random = null;

	    try {
	    	random = SecureRandom.getInstance("SHA1PRNG");
	        random.nextBytes(token);

	        for (int i = 0; i < token.length; i++) {
	        	buf.append(String.format("%02x", token[i]));
	        }
	    } catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    }
	    return buf.toString();
	}

	public static void loginCheck (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((String)session.getAttribute("token") == null) {
	    	response.sendRedirect("login.jsp");
	    	return;
		}
	}

	public static boolean csrfCheck (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String csrf_token = request.getParameter("token");
	    String token = (String)session.getAttribute("token");

	    return token != null && token.equals(csrf_token);
	}
}