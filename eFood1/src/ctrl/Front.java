package ctrl;

import java.io.IOException;
import java.sql.SQLException;

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
import model.admin;

/**
 * Servlet implementation class Front
 */
@WebServlet("/eFoods/*")
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
		try {
			admin model = (admin) this.getServletContext().getAttribute("model");
	    	if (model == null){
					model = new admin();
	    	}
	    	
			ServletContext sc = this.getServletContext();
			double freeShippingCutoff;
			double defaultShippingCost;
			try {
				freeShippingCutoff = Double.parseDouble(sc.getInitParameter("freeShippingCutoff"));
				defaultShippingCost = Double.parseDouble(sc.getInitParameter("defaultShippingCost"));
			} catch (NumberFormatException e){
				throw new ServletException("servlet is not configured properly", e);
			}
			
			Category category = new Category();
			Item item = new Item();
			Cart cart = new Cart(freeShippingCutoff, defaultShippingCost);
			Login login = new Login();

			// save them to the application scope
			this.getServletContext().setAttribute("model", model);
			sc.setAttribute("category", category);
			sc.setAttribute("item", item);
			sc.setAttribute("cart", cart);
			sc.setAttribute("login", login);
		} catch (SQLException e) {
			throw new ServletException("failed to initialize models", e);
		} 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		String itemID = request.getParameter("itemID");
		request.setAttribute("itemadded", false);
		if (itemID != null){
			int quantity = Integer.parseInt(request.getParameter(itemID+"_itemquantity"));
			Cart cart = (Cart) this.getServletContext().getAttribute("cart");
			cart.addItem(itemID, quantity);
			request.setAttribute("itemadded", true);
			System.out.println("item added");
		}
		if (path != null & path.equals("/")){
			// redirect to home page controller
			this.getServletContext().getNamedDispatcher("HomePage")
				.forward(request, response);
		} else if (path != null & path.startsWith("/category/")){
			// redirect to category controller
			this.getServletContext().getNamedDispatcher("Category")
					.forward(request, response);
		} else if (path != null & path.matches("/cart(/)?")){
			// redirect to cart controller
			this.getServletContext().getNamedDispatcher("Cart")
					.forward(request, response);
		} else if (path != null & path.matches("/login(/)?")){
			// redirect to login controller
			this.getServletContext().getNamedDispatcher("Login")
					.forward(request, response);
		} else if (path != null & path.matches("/checkout(/)?")){
			this.getServletContext().getNamedDispatcher("Checkout")
			.forward(request, response);
		} else if (path != null & path.equals("/order/")){
			// redirect to order controller
			this.getServletContext().getNamedDispatcher("Order")
				.forward(request, response);
		}
		else {
			request.getRequestDispatcher("/index.jspx").forward(request, response);
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
