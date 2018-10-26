package data.exchange.center.service.guangan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreatPdf {
	public static Connection getOracleConnection()
			throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

		Class.forName("oracle.jdbc.driver.OracleDriver");

		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@150.0.2.15:1521/oraods", "sgy", "sgy");

		return connection;
	}

	public static void main(String[] args)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		Connection con = getOracleConnection();
		String selectPkSql = "select wjnr, wjmc from msys_stwj_gc where wjnr is not null and rownum <200";
		PreparedStatement stat = con.prepareStatement(selectPkSql);
		ResultSet Rs = stat.executeQuery();
		int k=0;
		while (Rs.next()) {
			Blob blob = Rs.getBlob("wjnr");
			String wjgs = Rs.getString("wjmc").substring(Rs.getString("wjmc").lastIndexOf("."));
//			if (!wjgs.contains("pdf")) {
				k=k+1;
				System.out.println("第几个："+k);
				byte[] temp = new byte[(int) blob.length()];
				InputStream in = blob.getBinaryStream();
				in.read(temp);
				String fileName = "F://test/test/" + Rs.getString("wjmc");
				File file = new File(fileName);
				FileOutputStream fout = new FileOutputStream(file);
				fout.write(temp);
				System.out.println(Rs.getString("wjmc"));
//				@SuppressWarnings("deprecation")
//				HttpClient httpClient = new DefaultHttpClient();
//				HttpGet httpGet = new HttpGet("http://localhost:52119/api/document?Source=" + fileName
//						+ "&Target=F://test//test//" + uuid + ".pdf");
//				HttpResponse response = null;
//				try {
//					response = httpClient.execute(httpGet);
//				} catch (Exception e) {
//				}
//
//				String temp1 = "";
//				try {
//					HttpEntity entity = response.getEntity();
//					temp1 = EntityUtils.toString(entity, "UTF-8");
//					System.out.println(temp1);
//				} catch (Exception e) {
//				}
				in.close();
				fout.close();
//				file.delete();
//			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
//				fixedThreadPool.execute(new Runnable() {
//					public void run() {
//						try {
//							
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				});
		}}
//	}
}
