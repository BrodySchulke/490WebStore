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
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "insert into customers (username, user_password, email, first_name, last_name, date_joined, customer_id) "+ 
					"values(?,?,?,?,?,current_timestamp,0)";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, customer.getUsername());
			stmt.setString(2, customer.getUser_password());
			stmt.setString(3, customer.getEmail());
			stmt.setString(4, customer.getFirst_name());
			stmt.setString(5, customer.getLast_name());
			
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
			String query = "select first_name, last_name, email, date_joined from customers where username = ?";
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

	public static int modifyCustomer(Customer c){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "update customer set user_password = ?, username= ?, email=? where customer_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, c.getUser_password());
			stmt.setString(2, c.getUsername());
			stmt.setString(3, c.getEmail());
			stmt.setInt(4, c.getCustomer_id());

			flag = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}
	
	public static int deleteCustomer(int id){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("delete from user where customer_id = ?");
			stmt.setInt(1, id);
			flag = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}
	
	public static int checkUserAvail(String username, String email){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		System.out.println(username);
		System.out.println(email);
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select * from customers where username = ? or email = ?");
			stmt.setString(1, username);
			stmt.setString(2, email);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			System.out.println(rs);
			if(rs.next()){
				flag = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
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

