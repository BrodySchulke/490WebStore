package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
	
	public static List<Order> getOrders(Transaction t) {
		String query = "select * from orders where transaction_id = " + t.getTransaction_id();
		List<Order> orders = new ArrayList<>();
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
				orders.add(order);
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
		return orders;
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
	
	public static List<String> getCustomersPurchasingCategoryMoreThanTwoTimesAMonth() {
		String query = "select distinct genre from movies";
		List<String> customersTwice = new ArrayList<>();
		Connection conn = null;
		Statement stmt2 = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			String genre = null;
			while (rs.next()) {
				genre = rs.getString("genre");
				String query2 = "select tab.username from ( select username, count(username) as num from customerssOrders where genre = " + "'" + genre + "'" + "group by username) as tab where num > 1";
				try {
					conn = getConnection();
					stmt2 = conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery(query2);
					while (rs2.next()) {
						customersTwice.add(rs2.getString("username") + " " + genre);
					}
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				} finally {
					try {
						if (stmt2 != null) {
							stmt2.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
				}
			}
		} catch (SQLException sqle) {
			
		} catch (ClassNotFoundException cnfe) {
			
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
		return customersTwice;
	}
	
	public static List<String> getWeeklyBestSellersByCategory() {
		String query = "select distinct genre from movies";
		List<String> topFiveMostPurchasedByCategory = new ArrayList<>();
		Connection conn = null;
		Statement stmt2 = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			String genre = null;
			while (rs.next()) {
				genre = rs.getString("genre");
				String query2 = "select m.title, m.genre, avg(o.quantity) as average from movies m left join orders o on m.product_id = o.product_id where o.order_date is not null and o.order_date >= (now() - interval '1 weeks') and genre = " + "'" + genre + "'" + "group by m.product_id order by average desc limit 5";
				try {
					conn = getConnection();
					stmt2 = conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery(query2);
					if (rs2.next()) {
						topFiveMostPurchasedByCategory.add(genre + ": " + rs2.getString("title"));
					}
					while (rs2.next()) {
						topFiveMostPurchasedByCategory.add(rs2.getString("title"));
					}
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				} finally {
					try {
						if (stmt2 != null) {
							stmt2.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					}
				}
			}
		} catch (SQLException sqle) {
			
		} catch (ClassNotFoundException cnfe) {
			
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
		return topFiveMostPurchasedByCategory;
	}
	
	public static List<String> getWeeklyBestSellersByIndividuals() {
		String query = "select m.title, avg(o.quantity) as average from movies m left join orders o on m.product_id = o.product_id  where o.order_date is not null and o.order_date >= (now() - interval '1 weeks') group by m.product_id order by average desc limit 10";
		List<String> topTenMostPurchased = new ArrayList<>();
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				topTenMostPurchased.add("Movie: " + rs.getString("title") + " Average Purchased: " + rs.getDouble("average"));
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
		return topTenMostPurchased;
	}
	
}
