package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static ConnectionFactory instance = new ConnectionFactory();
	private static final String url = "jdbc:postgresql://localhost:5432/webstore";
	private static final String user = "css490";
	private static final String password = "css490pass";
	private static final String driver = "org.postgresql.Driver";
	
	private ConnectionFactory() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	public static ConnectionFactory getInstance() {
		return instance;
	}
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}
}
