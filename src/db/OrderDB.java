package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import model.Movie;
import model.Order;
import model.Transaction;

public class OrderDB {
	private static String dbURL = "jdbc:postgresql://localhost:5432/webstore";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Connection connection;
	private static Statement stmt;
	
	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = ConnectionFactory.getInstance().getConnection();
		return connection;
	}
	
	public static void getOrdersAssociatedWithCustomer(Map<Movie, Order> movieOrderMap, Transaction transaction) {
		String query = "select * from orders where transaction_id = " + transaction.getTransaction_id();
		Order order = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				order = new Order();
				order.setOrder_date(rs.getDate("order_date"));
				order.setOrder_id(rs.getInt("order_id"));
				order.setPrice(rs.getDouble("price"));
				order.setProduct_id(rs.getInt("product_id"));
				order.setQuantity(rs.getInt("quantity"));
				order.setTransaction_id(rs.getInt("transaction_id"));
				//each order record should only have one movie record associated with it.
				String movieQuery = "select * from movies where product_id = " + order.getProduct_id();
				Movie movie = new Movie();
				stmt = connection.createStatement();
				ResultSet rs2 = stmt.executeQuery(movieQuery);
				if (rs2.next()) {
					movie.setActor(rs2.getString("actor"));
					movie.setActress(rs2.getString("actress"));
					movie.setDirector(rs2.getString("director"));
					movie.setGenre(rs2.getString("genre"));
					movie.setInventory(rs2.getInt("inventory"));
					movie.setLength(rs2.getInt("length"));
					movie.setPrice(rs2.getDouble("price"));
					movie.setProduct_id(rs2.getInt("product_id"));
					movie.setRelease_year(rs2.getInt("release_year"));
					movie.setTitle(rs2.getString("title"));
				}
				movieOrderMap.put(movie, order);				
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
	}

}
