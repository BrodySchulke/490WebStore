package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Customer;
import model.Movie;
import model.Order;

public class TransactionDB {
	private static String dbURL = "jdbc:postgresql://localhost:5432/webstore";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Connection connection;
	private static Statement stmt;
	
	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = ConnectionFactory.getInstance().getConnection();
		return connection;
	}
	
	public static ArrayList<Order> checkForOpenCart(Customer customer) {
		String query = "select transaction_id from transactions where user_id = " + customer.getCustomer_id() + " and status = true";
		ArrayList<Order> orderList = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				orderList = new ArrayList<>();
				String subquery = "select * from orders where transaction_id = " + rs.getInt("transaction_id");
				try {
					if (stmt != null) {
						stmt.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
				stmt = connection.createStatement();
				rs = stmt.executeQuery(subquery);
				while (rs.next()) {
					Order order = new Order();
					order.setProduct_id(rs.getInt("product_id"));
					order.setQuantity(rs.getInt("quantity"));
					order.setOrder_date(rs.getDate("order_date"));
					order.setPrice(rs.getDouble("price"));
					order.setTransaction_id(rs.getInt("transaction_id"));
					order.setOrder_id(rs.getInt("order_id"));
					orderList.add(order);
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return orderList;
	}

}
