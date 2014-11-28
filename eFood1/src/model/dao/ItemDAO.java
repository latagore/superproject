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

	
	public List<ItemBean> getItemsByCategoryName(int categoryID) throws SQLException {
		Connection con = this.ds.getConnection();
		PreparedStatement p = con
				.prepareStatement("select number, name, price, unit from roumani.item where catid=? order by number");
		p.setInt(1, categoryID);
		ResultSet r = p.executeQuery();
		List<ItemBean> l = new ArrayList<ItemBean>();
		
		while (r.next()){
			String id = r.getString("number");
			String name = r.getString("name");
			Double price = r.getDouble("price");
			String unit = r.getString("unit");
			l.add(new ItemBean(id, name, price, unit));
		}
		
		r.close();
		p.close();
		con.close();
		return l;
	}

	public List<ItemBean> getAllItems() throws SQLException {
		Connection con = this.ds.getConnection();
		PreparedStatement p = con
				.prepareStatement("select number, name, price, unit from roumani.item order by number");
		
		ResultSet r = p.executeQuery();
		List<ItemBean> l = new ArrayList<ItemBean>();
		
		while (r.next()){
			String id = r.getString("number");
			String name = r.getString("name");
			Double price = r.getDouble("price");
			String unit = r.getString("unit");
			l.add(new ItemBean(id, name, price,unit));
		}
		
		r.close();
		p.close();
		con.close();
		return l;
	}


	public ItemBean getItem(String itemNumber) throws SQLException{
		Connection con = this.ds.getConnection();
		PreparedStatement p = con
				.prepareStatement("select number, name, price, unit from roumani.item where number like ?");
		p.setString(1, itemNumber);
		ResultSet r = p.executeQuery();
		
		r.next();
		String id = r.getString("number");
		String name = r.getString("name");
		Double price = r.getDouble("price");
		String unit = r.getString("unit");
		ItemBean item = new ItemBean(id, name, price, unit);
		
		r.close();
		p.close();
		con.close();
		return item;
	}

}
