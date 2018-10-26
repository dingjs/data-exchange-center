package data.exchange.center.service.sfgk.util.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WzfwlJdbc implements JdbcConnection {
	
	private static final String driverClass = "oracle.jdbc.driver.OracleDriver";
	private static final String url         = "jdbc:oracle:thin:@150.0.2.179:1521/oraapp2";
	private static final String user        = "splcgk";
	private static final String password    = "splcgk";
	static {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Connection dxConnection() {
		return null;
		
	}
	@Override
	public Connection wzfwlConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

}
