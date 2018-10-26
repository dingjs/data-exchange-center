package data.exchange.center.service.unstructured.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDeleteOracle {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@150.0.2.12:1521:oraods2", "sgy", "sgy");
		Statement statement = connection.createStatement();
		String sql = "DELETE FROM DCADM.DC_FJGH_EXTOPER WHERE C_CZLX <> 'FTPINS'";
		int excEnd = statement.executeUpdate(sql);
		
	}
}
