package _webstore;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class CustomerDB {
	private static String dbURL = "jdbc:postgresql://localhost:5432/webstore";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	
	public static boolean insertCustomer(Customer customer){
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/css490");
			conn = ds.getConnection();
			
			String query = "insert into customer(customer_id, username, user_password, email, first_name, last_name, date_joined)"+ 
					"values(?,?,?,?,?,?, current_timestamp)";
			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, customer.getCustomer_id());
			stmt.setString(2, customer.getUsername());
			stmt.setString(3, customer.getPassword());
			stmt.setString(4, customer.getEmail());
			stmt.setString(5, customer.getFirst_name());
			stmt.setString(6, customer.getLast_name());
			stmt.setDate(7, customer.getDate_joined());
			
			int i = stmt.executeUpdate();
			if(i > 0){
				result = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn);
		}
		
		return result;
	}
	
	public static ArrayList<Customer> showCustomers(){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Customer> customers = new ArrayList();
		try{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select username, email, first_name, last_name, date_joined from customers" +
						" order by username asc";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Customer c = new Customer();
				c.setUsername(rs.getString(1));
				c.setEmail(rs.getString(2));
				c.setFirst_name(rs.getString(3));
				c.setLast_name(rs.getString(4));
				c.setDate_joined(rs.getDate(5));
				customers.add(c);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return customers;
	}
	
	public static Customer showACustomer(String username){
		String first_name = "";
		String last_name = "";
		String email = "";
		String date_joined = ""; 
	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Customer c = new Customer();
		c.setUsername(username);
		try{
			
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select first_name, last_name, email, date_joined from user where username = ?";
			stmt = conn.prepareStatement(query);
	
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				c.setFirst_name(rs.getString("first_name"));
				c.setLast_name(rs.getString("last_name"));
				c.setEmail(rs.getString("email"));
				c.setDate_joined(rs.getDate("date_joined"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return c;
	}
	
	private static void closeAll(Statement stmt, Connection conn, ResultSet rs){
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException sqle){
			}
		}
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException sqle){
			}
		}
	}
	
	private static void closeAll(Statement stmt, Connection conn){
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException sqle){
			}
		}
	}
}		

