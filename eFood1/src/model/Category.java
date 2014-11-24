package model;

import java.sql.SQLException;
import java.util.List;

/**
 * Model for interacting with the categories in the store.
 */
public class Category {
	CategoryDAO dao;
	
	public Category() throws SQLException{
		dao = new CategoryDAO();
	}

	public List<String> getCategories() throws SQLException {
		return dao.getCategories();
	}

	public boolean hasCategory(String categoryName) throws SQLException {
		return dao.hasCategory(categoryName);
	}

}
