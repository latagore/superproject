package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;
import model.Item;
import model.items;

/**
 * Servlet implementation class CategoryController
 */
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		Category cm = (Category) this.getServletContext().getAttribute("category");
		Item im = (Item) this.getServletContext().getAttribute("item");
		List<String> allCategories = cm.getCategories();
		List<items> items;
		
		boolean isAllItemsSelected = true;
		// if path starts with /category, specific category is selected
		// otherwise, if path starts with /allItems, all items are selected
		if (path.startsWith("/category")){
			isAllItemsSelected = false;
			
			// get the information after "/category/" in the path			
			String categoryName = path.substring(
					path.substring(1) // removes the first slash
					.indexOf('/')); // removes everything before the second slash

			// check if category exists
			if (!cm.hasCategory(categoryName)){
				// TODO go to 404 page
				return;
			}

			// get all the items for this category
			items = im.getItemsByCategoryName(categoryName);
			request.setAttribute("selectedCategory", categoryName);
			
		} else if (path.matches("/allItems(/)?")){
			items = im.getAllItems();
		} else {
			// TODO log error in a better way
			throw new ServletException("Category controller was reached but path is not acceptable: path = " + path);
		}
		
		// redirect to category jsp
		request.setAttribute("categories", allCategories);
		request.setAttribute("items", items);
		request.setAttribute("isAllItemsSelected", isAllItemsSelected);
		request.getRequestDispatcher("/WEB-INF/Category.jspx")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
