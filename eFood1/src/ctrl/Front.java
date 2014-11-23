package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Front
 */
@WebServlet("/*")
public class Front extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Front() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().print(request.getPathInfo());
		String path = request.getPathInfo();
		if (path.equals("/")){
			// redirect to home page
		} else if (path.startsWith("/category/")){
			// redirect to category controller
			this.getServletContext().getNamedDispatcher("Category")
					.forward(request, response);
		} else if (path.matches("/cart(/)?")){
			// redirect to cart controller
			this.getServletContext().getNamedDispatcher("Cart")
					.forward(request, response);
		} else if (path.matches("/checkout(/)?")){
			// redirect to checkout controller
		} else if (path.startsWith("/addItem")){
			// redirect to add item controller
		} else {
			// TODO redirect to 404?
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path.matches("/cart(/)?")){
			// redirect to cart controller
			this.getServletContext().getNamedDispatcher("Cart")
					.forward(request, response);
		}
	}

}
