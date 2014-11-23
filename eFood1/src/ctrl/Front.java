package ctrl;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;
import model.Category;
import model.Item;
import model.Login;

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
    
	@Override
	public void init() throws ServletException {
		super.init();
		// initialize the model objects
		Category category = new Category();
		Item item = new Item();
		Cart cart = new Cart();
		Login login = new Login();
		
		// save them to the application scope
		ServletContext sc = this.getServletContext();
		sc.setAttribute("category", category);
		sc.setAttribute("item", item);
		sc.setAttribute("cart", cart);
		sc.setAttribute("login", login);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().print(request.getPathInfo());
		String path = request.getPathInfo();
		if (path.equals("/")){
			// redirect to home page controller
			this.getServletContext().getNamedDispatcher("HomePage")
				.forward(request, response);
		} else if (path.startsWith("/category/")){
			// redirect to category controller
			this.getServletContext().getNamedDispatcher("Category")
					.forward(request, response);
		} else if (path.matches("/cart(/)?")){
			// redirect to cart controller
			this.getServletContext().getNamedDispatcher("Cart")
					.forward(request, response);
		} else if (path.matches("/login(/)?")){
			// redirect to login controller
			this.getServletContext().getNamedDispatcher("Login")
					.forward(request, response);
		} else if (path.matches("/checkout(/)?")){
			// TODO redirect to checkout controller
		} else if (path.startsWith("/addItem")){
			// TODO redirect to add item controller
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
