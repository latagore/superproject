package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.bean.ItemBean;

import org.apache.tomcat.jdbc.pool.DataSource;

public class ItemDAO {

	private DataSource ds;
	
	public ItemDAO() throws SQLException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			throw new SQLException("Could not initialize database connection", e);
		}
	}

	
	public List<ItemBean> getItemsByCategoryName(String categoryName) throws SQLException {
		Connection con = this.ds.getConnection();
		PreparedStatement p = con
				.prepareStatement("select number, name, price from roumani.item where upper(name) like upper(?) order by number");
		// assume item ends in category name
		// sanitize name
		String sanitizedName = categoryName
				.replace("%", "[%]");
		p.setString(1, "%" + sanitizedName);
		ResultSet r = p.executeQuery();
		List<ItemBean> l = new ArrayList<ItemBean>();
		
		while (r.next()){
			String id = r.getString("number");
			String name = r.getString("name");
			Double price = r.getDouble("price");
			
			l.add(new ItemBean(id, name, price));
		}
		
		r.close();
		p.close();
		con.close();
		return l;
	}

	public List<ItemBean> getAllItems() throws SQLException {
		Connection con = this.ds.getConnection();
		PreparedStatement p = con
				.prepareStatement("select number, name, price from roumani.item where upper(name) like upper(?) order by number");
		
		ResultSet r = p.executeQuery();
		List<ItemBean> l = new ArrayList<ItemBean>();
		
		while (r.next()){
			String id = r.getString("number");
			String name = r.getString("name");
			Double price = r.getDouble("price");
			
			l.add(new ItemBean(id, name, price));
		}
		
		r.close();
		p.close();
		con.close();
		return l;
	}


	public ItemBean getItem(String itemNumber) throws SQLException{
		Connection con = this.ds.getConnection();
		PreparedStatement p = con
				.prepareStatement("select number, name, price from roumani.item where number like ?");
		p.setString(1, itemNumber);
		ResultSet r = p.executeQuery();
		
		r.next();
		String id = r.getString("number");
		String name = r.getString("name");
		Double price = r.getDouble("price");
		
		ItemBean item = new ItemBean(id, name, price);
		
		r.close();
		p.close();
		con.close();
		return item;
	}

}
