package model;

import java.sql.SQLException;
import java.util.List;

import model.bean.items;
import model.dao.ItemDAO;

public class Item {

	private ItemDAO dao;
	
	public Item() throws SQLException{
		dao = new ItemDAO();
	}
	
	public List<items> getItemsByCategoryName(String categoryName) throws SQLException {
		return dao.getItemsByCategoryName(categoryName);
	}

	public List<items> getAllItems() throws SQLException {
		return dao.getAllItems();
	}

}
