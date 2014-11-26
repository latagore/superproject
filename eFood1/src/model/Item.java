package model;

import java.sql.SQLException;
import java.util.List;

import model.bean.ItemBean;
import model.dao.ItemDAO;

public class Item {

	private ItemDAO dao;
	
	public Item() throws SQLException{
		dao = new ItemDAO();
	}
	
	public List<ItemBean> getItemsByCategoryName(String categoryName) throws SQLException {
		return dao.getItemsByCategoryName(categoryName);
	}

	public List<ItemBean> getAllItems() throws SQLException {
		return dao.getAllItems();
	}

	public model.bean.ItemBean getItem(String itemNumber) throws SQLException {
		return dao.getItem(itemNumber);
	}

}
