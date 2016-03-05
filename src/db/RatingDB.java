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
import model.Movie;
import model.Rating;

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
	
	public static boolean updateRating(int rating, Movie m, Customer c) {
		boolean retVal = false;
		String select = "select * from ratings where user_id = " + c.getCustomer_id() + " and product_id = " + m.getProduct_id() + " and rating = " + rating;
		String insert = "insert into ratings (user_id, product_id, rating, time_stamp) values (?,?,?, current_timestamp)";
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(select);
			if (rs.next()) {
				return retVal;
			} else {
				retVal = true;
			}
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
		return retVal;
	}
	
	public static List<Rating> getRatings(Customer c) {
		String query = "select * from ratings where user_id = " + c.getCustomer_id();
		Rating rating = null;
		List<Rating> ratings = new ArrayList<>();
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				rating = new Rating();
				rating.setProduct_id(rs.getInt("product_id"));
				rating.setRating(rs.getInt("rating"));
				rating.setTime_stamp(rs.getDate("time_stamp"));
				ratings.add(rating);
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
		return ratings;
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
	
	public static List<String> getMostFavoriteMoviesBiWeekly() {
		return null;
	}
}