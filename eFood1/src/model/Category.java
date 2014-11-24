package model;

import java.sql.SQLException;
import java.util.List;

import model.dao.CategoryDAO;

/**
 * Model for interacting with the categories in the store.
 */
public class Category {
	private CategoryDAO dao;
	
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
