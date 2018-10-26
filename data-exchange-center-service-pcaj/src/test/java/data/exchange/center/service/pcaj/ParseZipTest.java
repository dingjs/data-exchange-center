package data.exchange.center.service.pcaj;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ParseZipTest {

	public static void main(String[] args) throws IOException {

		ZipFile zf = null;
		InputStream in = null;
		ZipInputStream zin = null;
		ZipEntry ze = null;
		try {
			zf = new ZipFile("E:\\test\\JG_3000_20161208170606.zip");
			in = new BufferedInputStream(
					new FileInputStream("E:\\test\\JG_3000_20161208170606.zip"));
			zin = new ZipInputStream(in);
			int i=0;
			while ((ze = zin.getNextEntry()) != null) {
				
				i = i+1;
				if (ze.isDirectory()) {
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
					String line;
					StringBuffer strBuf= new StringBuffer();
					while ((line = br.readLine()) != null) {
						System.out.println(line);
						strBuf.append(line);
					}
					br.close();
					System.out.println(strBuf);
				}
				System.err.println(i);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			zin.closeEntry();
			zin.close();
			zf.close();
		}
		
	}
}
