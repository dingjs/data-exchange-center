package data.exchange.center.service.sfgk.util.jdbc;

import java.sql.Connection;

public interface JdbcConnection {

	public Connection dxConnection();
	public Connection wzfwlConnection();
}
