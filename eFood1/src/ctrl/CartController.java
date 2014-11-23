package ctrl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;
import model.InvalidInputException;

/**
 * Servlet implementation class CartController
 */
@WebServlet("/CartController")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO always give plain cart page
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check if the cart needs to be updated, otherwise give back the cart page
		// test
		for (String s : request.getParameterMap().keySet()){
			System.out.println(s);
		}
		if (request.getParameter("updateItems") != null){
			// get all the items in the request
			Map<String, Integer> itemQuantity = new HashMap<String, Integer>();
			for (String s : request.getParameterMap().keySet()){
				if (s.startsWith("quantity_")){
					// the ending part is the item number
					String itemNumber = s.split("_")[1];
					Integer quantity = Integer.parseInt(request.getParameter(s));
					itemQuantity.put(itemNumber, quantity);
				}
			}

			// give to the model to validate
			Cart cart = (Cart) this.getServletContext().getAttribute("cart");
			try {
				cart.validate(itemQuantity);
			} catch (InvalidInputException e) {
				// return the cart page as is, with error
				String error = e.getMessage();
				request.setAttribute("error", error);
				request.getRequestDispatcher("/WEB-INF/cart.jspx")
						.forward(request, response);
				return;
			}
			
			// modify the cart stored in the session
			// TODO should we create a new session or not?
			HttpSession s = request.getSession();
			for (String item : itemQuantity.keySet()){
				int quantity = itemQuantity.get(item);
				// remove items with zero quantity
				// FIXME does this belong in the controller?
				if (quantity == 0) {
					s.removeAttribute(item);
				} else {
					s.setAttribute(item, quantity);
				}
			}
		}
	}

}
