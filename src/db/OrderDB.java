package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.postgresql.util.PGmoney;

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
	
	public static Order getOrder(Transaction t) {
		String query = "select * from orders where transaction_id = " + t.getTransaction_id();
		Order order = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				order = new Order();
				order.setOrder_date(rs.getDate("order_date"));
				order.setOrder_id(rs.getInt("order_id"));
				order.setPrice(rs.getDouble("price"));
				order.setProduct_id(rs.getInt("product_id"));
				order.setQuantity(rs.getInt("quantity"));
				order.setTransaction_id(rs.getInt("transaction_id"));	
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
		return order;
	}
	
	public static void clearUserOrders(Transaction transaction) {
		String orderRemover = "delete from orders where transaction_id = ?";
		PreparedStatement stmt = null;
		try {
			connection = getConnection();
//			stmt.execute(orderRemover);
			stmt = connection.prepareStatement(orderRemover);
			stmt.setInt(1, transaction.getTransaction_id());
			stmt.executeUpdate();
		} catch (SQLException sqle) {
			
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
	
	public static void writeBackUserOrders(Order writeBack) {
		String write = "";
		PreparedStatement stmt2 = null;
		try {
			connection = getConnection();
			write = "insert into orders (product_id, quantity, order_date, price, transaction_id) values (?,?,?,?,?)";
			stmt2 = connection.prepareStatement(write);
			stmt2.setInt(1, writeBack.getProduct_id());
			stmt2.setInt(2, writeBack.getQuantity());
			stmt2.setDate(3, (Date)writeBack.getOrder_date());
			stmt2.setBigDecimal(4, new BigDecimal(writeBack.getPrice()));
			stmt2.setInt(5,  writeBack.getTransaction_id());
			stmt2.executeUpdate();
		} catch (SQLException sqle) {
			
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} finally {
			try {
				if (stmt2 != null) {
					stmt2.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
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
	
	
	public static void purchaseCloseOrders(Map<Movie, Order> cart, Transaction transaction) {
		String ordersToClose = "select * from orders where transaction_id = ?";
		PreparedStatement stmt = null;
		try {
			connection = getConnection();
//			stmt.execute(orderRemover);
			stmt = connection.prepareStatement(ordersToClose);
			stmt.setInt(1, transaction.getTransaction_id());
			ResultSet rs = stmt.executeQuery();
			String updateMovieInventory = "update movies set inventory = inventory - ? where product_id = ?";
			String closeOrders = "update orders set order_date = current_timestamp";
			PreparedStatement stmt2, stmt3 = null;
			stmt2 = connection.prepareStatement(updateMovieInventory);
			stmt3 = connection.prepareStatement(closeOrders);
			while (rs.next()) {
				Order o = new Order();
//				o.setOrder_id(rs.getInt("order_id"));
//				o.setProduct_id(rs.getInt("product_id"));
				stmt2.setInt(1, rs.getInt("quantity"));
				stmt2.setInt(2, rs.getInt("product_id"));
				stmt2.executeUpdate();
				stmt3.executeUpdate();
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
	
	public static List<String> getMonthlyAggregateSales() {
		return null;
	}
	
	public static List<String> getWeeklyAggregateSales() {
		return null;
	}
	
	public static List<String> getMonthlyDifferenceAggregate() {
		return null;
	}
	
	public static List<String> getWeeklyBestSellersByCategory() {
		return null;
	}
	
	public static List<String> getWeeklyDifferenceAggregate() {
		return null;
	}
	
	public static List<String> getWeeklyBestSellersByIndividuals() {
		return null;
	}
	
	public static List<String> getMostFavoriteMoviesBiWeekly() {
		return null;
	}
	
}
