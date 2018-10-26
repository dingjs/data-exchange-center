package data.exchange.center.service.unstructured.node;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestJdba {
 // 定义连接所需的字符串
    // 192.168.0.X是本机地址(要改成自己的IP地址)，1521端口号，XE是精简版Oracle的默认数据库名
    private static String USERNAMR = "tj";
    private static String PASSWORD = "tj";
    private static String DRVIER = "oracle.jdbc.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@150.100.1.161:1521:oraapp22";

    // 创建一个数据库连接
    Connection connection = null;
    // 创建预编译语句对象，一般都是用这个而不用Statement
    PreparedStatement pstm = null;
    // 创建一个结果集对象
    ResultSet rs = null;
    public static void main(String[] args) {
        TestJdba tj = new TestJdba();
        tj.AddData();
    }

    public void AddData() {
        connection = getConnection();
        // String sql =
        // "insert into student values('1','王小军','1','17','北京市和平里七区30号楼7门102')";
        String sql = "  SELECT * FROM ecourt.EAJ_JZ1 WHERE AHDM = '300100001013441'";

        try {
            // 计算数据库student表中数据总数
            long startTime = System.currentTimeMillis();
            pstm = connection.prepareStatement(sql);
            long endTime = System.currentTimeMillis(); 
            rs = pstm.executeQuery();
            System.err.println("程序运行时间：" + (endTime - startTime) + "ms");
           
            while (rs.next()) {
                System.out.println("ajbs"+rs.getString("ahdm"));
                System.out.println("xh"+rs.getObject("nr"));
            }
         
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
    }
    /**
     * 释放资源
     */
    public void ReleaseResource() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 获取Connection对象
     * 
     * @return
     */
    public Connection getConnection() {
        try {
            Class.forName(DRVIER);
            connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
            System.out.println("成功连接数据库");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class not find !", e);
        } catch (SQLException e) {
            throw new RuntimeException("get connection error!", e);
        }

        return connection;
    }
    
}
