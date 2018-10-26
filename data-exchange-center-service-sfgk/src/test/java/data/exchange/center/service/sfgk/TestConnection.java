package data.exchange.center.service.sfgk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.exchange.center.service.sfgk.util.jdbc.DxJdbc;
import data.exchange.center.service.sfgk.util.jdbc.JdbcFactory;

public class TestConnection {

	public static void main(String[] args) {
		DxJdbc dxJdbc = (DxJdbc) JdbcFactory.getClass(DxJdbc.class);
		Connection connection = dxJdbc.dxConnection();
		PreparedStatement pSatement = null;
		ResultSet rs = null;
		List<Map<String,Object>> retList = null;
		try {
			String sql = "SELECT * from  COURT_DBA.dx_fxx WHERE ssdw IS not NULL AND FSNR NOT LIKE '%111%' AND FSNR IS not null";
			pSatement = connection.prepareStatement(sql);
	        rs = pSatement.executeQuery();
	        ResultSetMetaData rsMdata = rs.getMetaData();
	        retList = new ArrayList<Map<String,Object>>();
	        while(rs.next()){
	            Map<String, Object> map = new HashMap<String,Object>();
	            for (int i = 1; i <= rsMdata.getColumnCount(); i++) {
	                map.put(rsMdata.getColumnName(i).toLowerCase(), rs.getObject(i));
	            }
	            retList.add(map);
	        }
	        
		} catch (Exception e) {
		}finally {
	        try {
	        	rs.close();
				pSatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(retList.toString());
	}
}
