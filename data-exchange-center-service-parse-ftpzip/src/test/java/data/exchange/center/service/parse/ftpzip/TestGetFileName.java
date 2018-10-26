package data.exchange.center.service.parse.ftpzip;

import java.io.File;

public class TestGetFileName {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\yuguang\\Desktop\\1111\\新建文件夹 (2)");
		File xmlFile[] = file.listFiles();
		for(File xml:xmlFile) {
			if(!xml.getName().equals("新建文件夹")) {
				System.out.println("'"+xml.getName().substring(26, 58)+"',");
			}
		}
	}
}
