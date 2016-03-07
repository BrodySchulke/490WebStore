package db;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;

import javax.sql.*;

import org.postgresql.util.PGmoney;

import model.Movie;
import model.Order;
import model.Rating;

import javax.naming.*;
import java.util.*;

public class MovieDB {
	private static String dbURL = "jdbc:postgresql://localhost:5432/webstore";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Connection connection;
	private static Statement stmt;
	private static final int numberOfRecords = 20;
	private static int offset = 0;
	
	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = ConnectionFactory.getInstance().getConnection();
		return connection;
	}
	
	public static int getOffset() {
		return offset;
	}
	
	public static Movie getMovie(Order o) {
		String query = "select * from movies where product_id = " + o.getProduct_id();
		Movie movie = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				movie = new Movie();
				movie.setActor(rs.getString("actor"));
				movie.setActress(rs.getString("actress"));
				movie.setDirector(rs.getString("director"));
				movie.setGenre(rs.getString("genre"));
				movie.setInventory(rs.getInt("inventory"));
				movie.setLength(rs.getInt("length"));
				movie.setPrice(rs.getDouble("price"));
				movie.setProduct_id(rs.getInt("product_id"));
				movie.setRelease_year(rs.getInt("release_year"));
				movie.setTitle(rs.getString("title"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
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
		return movie;
	}
	
	public static List<Movie> getMovies(Rating r) {
		String query = "select * from movies where product_id = " + r.getProduct_id();
		Movie movie = null;
		List<Movie> movies = new ArrayList<>();
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				movie = new Movie();
				movie.setActor(rs.getString("actor"));
				movie.setActress(rs.getString("actress"));
				movie.setDirector(rs.getString("director"));
				movie.setGenre(rs.getString("genre"));
				movie.setInventory(rs.getInt("inventory"));
				movie.setLength(rs.getInt("length"));
				movie.setPrice(rs.getDouble("price"));
				movie.setProduct_id(rs.getInt("product_id"));
				movie.setRelease_year(rs.getInt("release_year"));
				movie.setTitle(rs.getString("title"));
				movies.add(movie);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
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
		return movies;
	}
	
	//add value to form button for submission. if negative, go back 20, else forward 20
	public static ArrayList<Movie> viewMovies(String sort_value, int stepValue) {
		String query = null;
		if (sort_value.equals("")) {
			sort_value = "product_id";
			query = "select * from movies where inventory > 0 order by " + sort_value + " offset " + offset + " limit " + numberOfRecords;
		} else if (sort_value.equals("rating")){
			query = "select m.*, avg(r.rating) as average from movies m left join ratings r on m.product_id = r.product_id group by m.product_id order by average desc offset " + offset + " limit " + numberOfRecords;
		} else {
			query = "select * from movies where inventory > 0 order by " + sort_value + " offset " + offset + " limit " + numberOfRecords;
		}
		offset += stepValue;
		if (offset < 0) {
			offset = 0;
		}
		ArrayList<Movie> movieList = new ArrayList<>();
		Movie movie = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				movie = new Movie();
				movie.setActor(rs.getString("actor"));
				movie.setActress(rs.getString("actress"));
				movie.setDirector(rs.getString("director"));
				movie.setGenre(rs.getString("genre"));
				movie.setInventory(rs.getInt("inventory"));
				movie.setLength(rs.getInt("length"));
				movie.setPrice(rs.getDouble("price"));
				movie.setProduct_id(rs.getInt("product_id"));
				movie.setRelease_year(rs.getInt("release_year"));
				movie.setTitle(rs.getString("title"));
				movieList.add(movie);
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
		return movieList;
	}
	
	
	
	
	//add value to form button for submission. if negative, go back 20, else forward 20
	public static ArrayList<Movie> viewMoviesSearch(String search_value, int stepValue) {
		String query = null;
		StringBuilder queryBuilder = new StringBuilder();
		String[] searcher = search_value.split(" ");
		for (String s : searcher) {
			System.out.println(s);
			queryBuilder.append(".*").append(s).append(".*");
		}
		query = "select * from movies where title ~ " + "'" + queryBuilder.toString() + "'" + " or genre ~ " + "'" + queryBuilder.toString() + "'" + " or actor ~ " + "'" + queryBuilder.toString() + "'" + " or actress ~ " + "'" + queryBuilder.toString() + "'" + " or director ~ " + "'" + queryBuilder.toString() + "'" + " limit 20";
		offset += stepValue;
		if (offset < 0) {
			offset = 0;
		}
		ArrayList<Movie> movieList = new ArrayList<>();
		Movie movie = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				movie = new Movie();
				movie.setActor(rs.getString("actor"));
				movie.setActress(rs.getString("actress"));
				movie.setDirector(rs.getString("director"));
				movie.setGenre(rs.getString("genre"));
				movie.setInventory(rs.getInt("inventory"));
				movie.setLength(rs.getInt("length"));
				movie.setPrice(rs.getDouble("price"));
				movie.setProduct_id(rs.getInt("product_id"));
				movie.setRelease_year(rs.getInt("release_year"));
				movie.setTitle(rs.getString("title"));
				movieList.add(movie);
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
		return movieList;
	}
	
	
	public static ArrayList<Movie> viewMoviesFilter(String[] filter_list, int stepValue) {
		String query = null;
		if (filter_list[0].equals("genre")) {
			query = "select m.*, avg(r.rating) as average from movies m left join ratings r on m.product_id = r.product_id where m.genre = " + "'" + filter_list[1] + "'" + " group by m.product_id order by average desc offset " + offset + " limit " + numberOfRecords;
		} else if (filter_list[0].equals("years")) {
			String [] arr = filter_list[1].split(" ");
			int minYear = Integer.parseInt(arr[0]);
			int maxYear = Integer.parseInt(arr[1]);
			query = "select m.* from movies m where m.release_year >= " + minYear + " and m.release_year <= " + maxYear + " order by release_year desc offset " + offset + " limit " + numberOfRecords;
		} else if (filter_list[0].equals("price")) {
			String [] arr = filter_list[1].split(" ");
			double mydouble = Double.parseDouble(arr[0]);
			PGmoney minPrice = new PGmoney(Double.parseDouble(arr[0]));
			PGmoney maxPrice = new PGmoney(Double.parseDouble(arr[1]));
			query = "select m.* from movies m where m.price >= " + "'" + minPrice + "'" + " and m.price <= " + "'" + maxPrice + "'" + " order by price desc offset " + offset + " limit " + numberOfRecords; 
		}	
		else {
			double upperBound = Double.parseDouble(filter_list[1]) + 0.5;
			double lowerBound = Double.parseDouble(filter_list[1]) - 0.5;
			query = "select * from averages where average <= " + upperBound + " and average >= " + lowerBound + " order by average desc offset " + offset + " limit " + numberOfRecords;
		}
		offset += stepValue;
		if (offset < 0) {
			offset = 0;
		}
		ArrayList<Movie> movieList = new ArrayList<>();
		Movie movie = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				movie = new Movie();
				movie.setActor(rs.getString("actor"));
				movie.setActress(rs.getString("actress"));
				movie.setDirector(rs.getString("director"));
				movie.setGenre(rs.getString("genre"));
				movie.setInventory(rs.getInt("inventory"));
				movie.setLength(rs.getInt("length"));
				movie.setPrice(rs.getDouble("price"));
				movie.setProduct_id(rs.getInt("product_id"));
				movie.setRelease_year(rs.getInt("release_year"));
				movie.setTitle(rs.getString("title"));
				movieList.add(movie);
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
		return movieList;
	}
	
	
	public static Movie showAMovie(int movieId){
		String query = "select * from movies where product_id = " + movieId;
		Movie movie = null;
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				movie = new Movie();
				movie.setActor(rs.getString("actor"));
				movie.setActress(rs.getString("actress"));
				movie.setDirector(rs.getString("director"));
				movie.setGenre(rs.getString("genre"));
				movie.setInventory(rs.getInt("inventory"));
				movie.setLength(rs.getInt("length"));
				movie.setPrice(rs.getDouble("price"));
				movie.setProduct_id(rs.getInt("product_id"));
				movie.setRelease_year(rs.getInt("release_year"));
				movie.setTitle(rs.getString("title"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
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
		return movie;
	}
	
	public int getNumberOfRecords() {
		return numberOfRecords;
	}
	
	public static boolean insertMovie(Movie movie){
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/css490");
			conn = ds.getConnection();
			
			String query = "insert into movies(release_year, length, title, genre, actor, actress, director, inventory, price, product_id) "+ 
					"values(?,?,?,?,?,?,?,?,?, current_timestamp)";
			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, movie.getRelease_year());
			stmt.setInt(2, movie.getLength());
			stmt.setString(3, movie.getTitle());
			stmt.setString(4, movie.getGenre());
			stmt.setString(5, movie.getActor());
			stmt.setString(6, movie.getActress());
			stmt.setString(7, movie.getDirector());
			stmt.setInt(8, movie.getInventory());
			stmt.setDouble(9,  movie.getPrice());
			stmt.setInt(10, movie.getProduct_id());
			
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
	

	public static int modifyMovie(Movie m) {
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "update movie set inventory = ?, price = ? where product_id = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, m.getInventory());
			stmt.setDouble(2, m.getPrice());
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
			stmt = conn.prepareStatement("delete from movies where product_id = ?");
			stmt.setInt(1, id);
			flag = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}
	
	public static int checkMovieAvail(int product_id, String title){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select * from movies where product_id = ? or title = ?");
			stmt.setInt(1, product_id);
			stmt.setString(2, title);
			rs = stmt.executeQuery();
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

public static boolean createMovie(String product_id, String price, String inventory, String title, String genre, String year, String length, String actor, String actress, String director) {
	if (price == null || inventory == null || title == null || genre == null || year == null || length == null || actor == null || actress == null || director == null) {
		return false;
	}
	String check = "select * from movies where title = " + "'" + title + "'" + " and release_year = " + Integer.parseInt(year); 
	String query = "insert into movies(release_year, length, title, genre, actor, actress, director, inventory, price) "+ 
			"values(?,?,?,?,?,?,?,?,?)";
	try {
		connection = getConnection();
		Statement stmt2 = connection.createStatement();
		ResultSet rs = stmt2.executeQuery(check);
		if (rs.next()) {
			return false;
		}
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
		connection = getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, Integer.parseInt(year));
		stmt.setInt(2, Integer.parseInt(length));
		stmt.setString(3, title);
		stmt.setString(4, genre);
		stmt.setString(5, actor);
		stmt.setString(6, actress);
		stmt.setString(7, director);
		stmt.setInt(8, Integer.parseInt(inventory));
		stmt.setBigDecimal(9, new BigDecimal(price));
		stmt.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
		return false;
	}finally{
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
	return true;
}
	
	public static void updateMovie(String product_id, String price, String inventory) {
		String query = "update movies set price = ?, inventory = ? where product_id = ?";
		Movie movie = null;
		try {
			connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setBigDecimal(1, new BigDecimal(price));
			stmt.setInt(2, Integer.parseInt(inventory));
			stmt.setInt(3, Integer.parseInt(product_id));
			stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
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
	
	public static void setOffset(int value) {
		offset = value;
	}
	
}		

