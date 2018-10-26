package data.exchange.center.service.parse.ftpzip;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class TestReadZipDir {

	public static void main(String[] args) throws Exception {
		String zipPath = "e:\\test\\510322_510322_b84e9eaf367348ee8b8430d6b84e2586_34C30C05DAC805F7E56552FF9DB9F7A8.zip";
		ZipFile zipFile = null;
		InputStream in  = null;
		ZipInputStream zipInputStream = null;
		Map<String, String> dirs = new HashMap<>();
		try {
			zipFile = new ZipFile(zipPath);
			in = new BufferedInputStream(new FileInputStream(zipPath));
			zipInputStream = new ZipInputStream(in);

			ZipEntry ze;
			
			while ((ze = zipInputStream.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					dirs.put(ze.getName().replaceAll("/", "").toUpperCase(), ze.getName().replaceAll("/", "").toUpperCase());
				}
			}
			
		}catch(Exception e) {
			throw e;
		}finally {
			if(zipInputStream != null) {
				zipInputStream.close();
			}
			if(zipFile != null) {
				zipFile.close();
			}
		}
		System.out.println(dirs);
		if(dirs.containsKey("JZ")&&dirs.containsKey("WS")) {
			System.out.println("包含卷宗和文书");
		}else {
			System.out.println("不包含卷宗和文书");
		}
	}
}
