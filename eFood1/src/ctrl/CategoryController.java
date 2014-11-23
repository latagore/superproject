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
		
		// get the information after "/category/" in the path
		String categoryName = path.substring(
				path.substring(1) // removes the first slash
						.indexOf('/')); // removes everything before the second slash
						
		
		// check that the category exists
		Category category = (Category) this.getServletContext().getAttribute("category");
		List<String> categories = category.getCategories();
		if (!categories.contains(category)){
			// TODO redirect to 404 page
		}
		
		// find all the items names that have this category name
		Item item = (Item) this.getServletContext().getAttribute("item");
		List<items> l = item.getItemsByCategoryName(categoryName);
		
		// redirect to category jsp
		request.setAttribute("items", l);
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
