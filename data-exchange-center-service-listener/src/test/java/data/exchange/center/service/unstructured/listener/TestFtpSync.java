package data.exchange.center.service.unstructured.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestFtpSync {

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
    		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@150.0.2.15:1521:oraapp22", "lckd", "lckd");
    		Statement statement = connection.createStatement();
    		String sql = "SELECT * FROM FTPPATH ORDER BY FYDM, FTPNAME";
    		ResultSet resultset = statement.executeQuery(sql);
    		
    		ResultSetMetaData data = resultset.getMetaData();
            List<Map<String,Object>> ftpMapList = new ArrayList<Map<String,Object>>();
            while(resultset.next()){
                Map<String, Object> map = new HashMap<String,Object>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    map.put(data.getColumnName(i).toLowerCase(), resultset.getObject(i));
                }
                ftpMapList.add(map);
            }
            System.out.println(ftpMapList);
            
            for(Map<String,Object> map:ftpMapList) {
            	String fydm    =(String) map.get("fydm");
                String ftpname =(String) map.get("ftpname");
                String ipaddr  =(String) map.get("ipaddr");
                String ftpport =(String) map.get("ftpport");
                String passwd  =(String) map.get("passwd");
                String nowftp  =(String) map.get("nowftp");
                String root    =(String) map.get("root");
                String usrname =(String) map.get("usrname");
                String platform=(String) map.get("platform");
            }
            ThreadGroup group = Thread.currentThread().getThreadGroup();
            ThreadGroup topGroup = group;
            // 遍历线程组树，获取根线程组
            while (group != null) {
                topGroup = group;
                group = group.getParent();
            }
            // 激活的线程数再加一倍，防止枚举时有可能刚好有动态线程生成
            int slackSize = topGroup.activeCount() * 2;
            Thread[] slackThreads = new Thread[slackSize];
            // 获取根线程组下的所有线程，返回的actualSize便是最终的线程数
            int actualSize = topGroup.enumerate(slackThreads);
            Thread[] atualThreads = new Thread[actualSize];
            // 复制slackThreads中有效的值到atualThreads
            System.arraycopy(slackThreads, 0, atualThreads, 0, actualSize);
            System.out.println("Threads size is " + atualThreads.length);
            for (Thread thread : atualThreads) {
                System.out.println("Thread name : " + thread.getName());
            }
		} catch (Exception e) {

		}finally {
			
		}
	}
}
