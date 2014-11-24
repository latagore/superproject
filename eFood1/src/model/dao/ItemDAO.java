package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.items;

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

	
	public List<items> getItemsByCategoryName(String categoryName) throws SQLException {
		Connection con = this.ds.getConnection();
		PreparedStatement p = con
				.prepareStatement("select number, name, price, qty from roumani.item where upper(name) like upper(?) order by number");
		// assume item ends in category name
		// sanitize name
		String sanitizedName = categoryName
				.replace("%", "[%]");
		p.setString(1, "%" + sanitizedName);
		ResultSet r = p.executeQuery();
		List<items> l = new ArrayList<items>();
		
		while (r.next()){
			int id = r.getInt("number");
			String name = r.getString("name");
			Double price = r.getDouble("price");
			int qty = r.getInt("qty");
			
			l.add(new items(name, id, price, qty));
		}
		
		r.close();
		p.close();
		con.close();
		return l;
	}

	public List<items> getAllItems() throws SQLException {
		Connection con = this.ds.getConnection();
		PreparedStatement p = con
				.prepareStatement("select number, name, price, qty from roumani.item where upper(name) like upper(?) order by number");
		
		ResultSet r = p.executeQuery();
		List<items> l = new ArrayList<items>();
		
		while (r.next()){
			int id = r.getInt("number");
			String name = r.getString("name");
			Double price = r.getDouble("price");
			int qty = r.getInt("qty");
			
			l.add(new items(name, id, price, qty));
		}
		
		r.close();
		p.close();
		con.close();
		return l;
	}

}
