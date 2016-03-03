package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Customer;
import model.Movie;

public class RatingDB {
	
	private static String dbURL = "jdbc:postgresql://localhost:5432/webstore";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Connection connection;
	private static Statement stmt;
	
	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = ConnectionFactory.getInstance().getConnection();
		return connection;
	}
	
	public static void updateRating(int rating, Movie m, Customer c) {
		System.out.println(rating + " " + m + " " + c + " we out here");
		System.out.println(m.getProduct_id());
		System.out.println(c.getCustomer_id());
		String insert = "insert into ratings (user_id, product_id, rating, time_stamp) values (?,?,?, current_timestamp)";
		try {
			connection = getConnection();
			PreparedStatement stmt2 = null;
			stmt2 = connection.prepareStatement(insert);
			stmt2.setInt(1, c.getCustomer_id());
			stmt2.setInt(2, m.getProduct_id());
			stmt2.setInt(3, rating);
			stmt2.executeUpdate();
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
	
	public static int getRating(Movie m) {
		String query = "select rating from ratings where product_id = " + m.getProduct_id();
		int rating = 0;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int totalRating = 0, count = 0;
			while (rs.next()) {
				totalRating += rs.getInt("rating");
				count++;
			}
			rating = totalRating / count;
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
		return rating;
	}
}
