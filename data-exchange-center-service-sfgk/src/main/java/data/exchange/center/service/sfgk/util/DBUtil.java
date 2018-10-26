package data.exchange.center.service.sfgk.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
	

	/**
	 * 
	 * @function 获取SQL的结果集
	 * @author Tony
	 * @creaetime 2018年5月31日 上午11:43:50
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> getSqlData(Connection connection, String sql) {
		PreparedStatement pSatement = null;
		ResultSet rs = null;
		List<Map<String,Object>> retList = null;
		try {
			pSatement = connection.prepareStatement(sql);
	        rs = pSatement.executeQuery();
	        ResultSetMetaData rsMdata = rs.getMetaData();
	        retList = new ArrayList<Map<String,Object>>();
	        while(rs.next()){
	            Map<String, Object> map = new HashMap<String,Object>();
	            for (int i = 1; i <= rsMdata.getColumnCount(); i++) {
	                map.put(rsMdata.getColumnName(i).toLowerCase(), rs.getObject(i)==null?"":rs.getObject(i));
	            }
	            retList.add(map);
	        }
	        
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
	        try {
	        	if(rs != null) {
	        		rs.close();
	        	}
	        	if(pSatement != null) {
	        		pSatement.close();
	        	}
	        	if(connection != null ) {
	        		connection.close();
	        	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return retList;
	}
	
	/**
	 * 
	 * @function 获取过程的结果集
	 * @author Tony
	 * @creaetime 2018年5月31日 上午11:46:25
	 * @param sql 存储过程名
	 * @param args 参数
	 * @return
	 */
	public static List<Map<String,Object>> getCallData(Connection connection, String sql, String... args) {
		CallableStatement callSatement = null;
		ResultSet rs = null;
		List<Map<String,Object>> retList = null;
		try {
//			String curSql="{call dzjz.LOGMESSAGE(?,?,?,?,?)}";
			callSatement = connection.prepareCall(sql);
			int n = 0;
			for(String parm:args) {
				n = n + 1;
				callSatement.setString(n, parm);
			}
			callSatement.execute();
	        
	        rs = callSatement.executeQuery();
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
			// TODO: handle exception
		}finally {
	        try {
	        	rs.close();
	        	callSatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return retList;
	}
}
