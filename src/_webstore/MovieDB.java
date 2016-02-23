package _webstore;

import java.sql.*;
import java.sql.Date;

import javax.sql.*;

import org.postgresql.util.PGmoney;

import javax.naming.*;
import java.util.*;

public class MovieDB {
	private static String dbURL = "jdbc:postgresql://localhost:5432/webstore";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	
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
	
	public static ArrayList<Movie> showMovies(){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Movie> movies= new ArrayList();
		try{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select * from movies";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Movie m = new Movie();
				m.setRelease_year(rs.getInt(1));
				m.setLength(rs.getInt(2));
				m.setTitle(rs.getString(3));
				m.setGenre(rs.getString(4));
				m.setActor(rs.getString(5));
				m.setActress(rs.getString(6));
				m.setDirector(rs.getString(7));
				m.setInventory(rs.getInt(8));
				m.setPrice(rs.getDouble(9));
				m.setProduct_id(rs.getInt(10));
				movies.add(m);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return movies;
	}
	
	public static Movie showAMovie(int movieId){
		int release_year = -1;
		int length = -1;
		String title = "";
		String genre = "";
		String actor = "";
		String actress = "";
		String Director = "";
		int inventory = -1;
		double price = -1;
		int product_id = -1;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Movie m = new Movie();
		m.setProduct_id(movieId);;
		try{
			
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select * from movies where product_id = ?";
			stmt = conn.prepareStatement(query);
	
			stmt.setInt(1, movieId);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				m.setRelease_year(rs.getInt(1));
				m.setLength(rs.getInt(2));
				m.setTitle(rs.getString(3));
				m.setGenre(rs.getString(4));
				m.setActor(rs.getString(5));
				m.setActress(rs.getString(6));
				m.setDirector(rs.getString(7));
				m.setInventory(rs.getInt(8));
				m.setPrice(rs.getDouble(9));
				m.setProduct_id(rs.getInt(10));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return m;
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
}		

