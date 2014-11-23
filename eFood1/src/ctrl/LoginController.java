package ctrl;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Login;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response); // if using HTTPS, entire request is still secure
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); // TODO right name?
		String password = request.getParameter("password");
		String redirectURL = request.getParameter("redirectURL");
		if (password != null){
			if (!request.isSecure()){
				// this is very bad, the user could have just lost their password to a man-in-the-middle
				// use a string builder just because it's faster 
				// than builtin string concatenation
				String error = new StringBuilder("This request was not securely transmitted (over HTTPS). ")
						.append("This is very bad. ")
						.append("You may have lost your password to bad people. ")
						.append("Please reset your password, especially if you logged in with your CSE account.")
						.toString();
				request.setAttribute("error", error);
				request.getRequestDispatcher("/WEB-INF/Login.jspx")
					.forward(request, response);
				return;
			} else if (username != null){
				Login login = (Login) this.getServletContext().getAttribute("login");
				
				try {
					String token = login.getSecurityToken(username, password);
					request.setAttribute("token", token);
				} catch (LoginException e) {
					// failed to login, go back to the login view
					String error = e.getMessage();
					request.setAttribute("error", error);
					request.getRequestDispatcher(request.getPathInfo())
							.forward(request, response);
					return;
				}
				
			}
			
			// all was successful, go to the redirectURL page
			// TODO should there be an intermediate page?
			if (redirectURL == null || redirectURL.equals("")){
				request.getRequestDispatcher("/")
						.forward(request, response);
			} else {
				request.getRequestDispatcher(redirectURL)
						.forward(request, response);
			}
		} else {
			request.getRequestDispatcher("/WEB-INF/Login.jspx")
					.forward(request, response);
		}
	}

}
