package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;
import model.InvalidInputException;
import model.Item;
import model.bean.CartBean;

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
		if (request.getParameter("updateItems") != null){
			// get all the items in the request
			
			Map<String,String[]> paramMap = request.getParameterMap();
			
			Map<String,String> itemQuantity = getItemsFromParams(paramMap);
			Map<String,String> validItemQuantity;
			
			// give to the model to validate
			Cart cart = (Cart) this.getServletContext().getAttribute("cart");
			try {
				validItemQuantity = cart.process(itemQuantity);
			} catch (InvalidInputException e) {
				// return the cart page as is, with error
				String error = e.getMessage();
				request.setAttribute("error", error);
				request.getRequestDispatcher("/WEB-INF/cart.jspx")
						.forward(request, response);
				return;
			}
			
			updateSession(request, validItemQuantity);
						
		}
		
		try {
			
			// get all the cart related attributes
			HttpSession session = request.getSession();
			List<String> attrs = Collections.list(session.getAttributeNames());
			Map<String, String> results = new HashMap<String, String>();
			for (String attr : attrs){
				if (attr.startsWith("quantity_")){
					results.put(attr.split("_")[1], (String) session.getAttribute(attr));
				}
			}
			
			Cart cm = (Cart) this.getServletContext().getAttribute("cart");
			Item im = (Item) this.getServletContext().getAttribute("item");
			CartBean c = cm.createCart(results, im);
			
			request.setAttribute("cart", c);
			request.getRequestDispatcher("/cart.jspx")// FIXME should be under web-inf
					.forward(request, response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check if the cart needs to be updated, otherwise give back the cart page
		// test
		doGet(request, response);
	}

	private void updateSession(HttpServletRequest request,
			Map<String, String> newItemQuantities) {
		// modify the cart stored in the session
		HttpSession s = request.getSession();
		// delete all items in the session and replace them
		Enumeration<String> e = s.getAttributeNames();
		// need to save them into l because enumeration is not thread safe
		List<String> attrsToDelete = new ArrayList<String>();
		while (e.hasMoreElements() ){
			String cookieName = e.nextElement();
			if (cookieName.startsWith("quantity_")){
				attrsToDelete.add(cookieName);
			}
		}
		for (String name : attrsToDelete){
			s.removeAttribute(name);
		}
		for (String name : newItemQuantities.keySet()){
			s.setAttribute("quantity_" + name, newItemQuantities.get(name));
		}
	}
	
	private Map<String, String> getItemsFromParams(Map<String, String[]> paramMap){
		Map<String, String> itemQuantity = new HashMap<String, String>();
		for (String s : paramMap.keySet()){
			if (s.startsWith("quantity_")){
				// the ending part is the item number
				String itemNumber = s.split("_")[1];

				// get only the first value
				// there should only be one
				String quantity = paramMap.get(s)[0];
				itemQuantity.put(itemNumber, quantity);
			}
		}
		return itemQuantity;
	}
}
