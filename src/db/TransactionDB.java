package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Order;
import model.Transaction;

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
	
	//TODO pass boolean to change trans status
	public static List<Transaction> getClosedTransactions(Customer customer) {
		List<Transaction> transactionsClosed = new ArrayList<>();
		String query = "select * from transactions where user_id = " + customer.getCustomer_id() + " and status = false";
		Transaction transaction = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				transaction = new Transaction();
				transaction.setClose_date(rs.getDate("close_date"));
				transaction.setStatus(rs.getBoolean("status"));
				transaction.setTotal_price(rs.getDouble("total_price"));
				transaction.setTransaction_id(rs.getInt("transaction_id"));
				transaction.setUser_id(rs.getInt("user_id"));
				transactionsClosed.add(transaction);
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
		return transactionsClosed;
	}
	
	public static void setUpTransactionForNewCustomer(Customer customer) {
		String insert = "insert into transactions (user_id, status, total_price) values(?,?,?)";
		try {
			connection = getConnection();
			PreparedStatement stmt2 = null;
			stmt2 = connection.prepareStatement(insert);
			String query = "select customer_id from customers where username = " + "\'" + customer.getUsername() + "\'";
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				stmt2.setInt(1, rs.getInt("customer_id"));
				stmt2.setBoolean(2, true);
				stmt2.setBigDecimal(3, new BigDecimal(0));
				stmt2.executeUpdate();
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
	
	public static Transaction initializeCart(Customer customer) {
		String query = "select * from transactions where user_id = " + customer.getCustomer_id() + " and status = true";
		Transaction transaction = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				transaction = new Transaction();
				transaction.setClose_date(rs.getDate("close_date"));
				transaction.setStatus(rs.getBoolean("status"));
				transaction.setTotal_price(rs.getDouble("total_price"));
				transaction.setTransaction_id(rs.getInt("transaction_id"));
				transaction.setUser_id(rs.getInt("user_id"));
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
		return transaction;
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
	
	public static void writeTransactionToDB(Transaction t) {
		String query = "update transactions set total_price = ? where user_id = ? and status = true";
		try {
			connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setBigDecimal(1, new BigDecimal(t.getTotal_price()));
			stmt.setInt(2, t.getUser_id());
			stmt.executeUpdate();
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
	
	public static void closeTransaction(Transaction transaction) {
		String query = "update transactions set status = false, close_date = current_timestamp where transaction_id = ?";
		try {
			connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, transaction.getTransaction_id());
			stmt.executeUpdate();
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
