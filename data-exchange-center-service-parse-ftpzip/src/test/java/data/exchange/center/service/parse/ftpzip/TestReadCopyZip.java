package data.exchange.center.service.parse.ftpzip;

import java.io.IOException;

import data.exchange.center.service.parse.ftpzip.util.FileUtil;

public class TestReadCopyZip {

	public static void main(String[] args) throws IOException {
		
		String zipPath = "F:\\511102_511100_f326b06ebff44e46adec930c16c0f392_30DAE98827A85C14F813598B764785B9.zip";
		String xml = FileUtil.getXmlFileCopyFromZip(zipPath);
		System.out.println(xml);
		FileUtil.getJzFileCopyFromZip(zipPath, "F:\\");
		
//		ZipFile zipFile = new ZipFile(path);
//		InputStream in = new BufferedInputStream(new FileInputStream(path));
//		ZipInputStream zipInputStream = new ZipInputStream(in);
//
//		ZipEntry ze;
//		while ((ze = zipInputStream.getNextEntry()) != null) {
//			if (ze.isDirectory()) {
//				// 为空的文件夹什么都不做
//			} else {
//				System.err.println("file:" + ze.getName() + "\nsize:" + ze.getSize() + "bytes");
//				if (ze.getSize() > 0) {
//					if (".xml".equalsIgnoreCase(
//							ze.getName().substring(ze.getName().indexOf("."), ze.getName().length()))) {
//						BufferedReader reader;
//						StringBuffer line = new StringBuffer();
//						try {
//							reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze), "utf-8"));
//							line.append(reader.readLine());
//							// while(reader.readLine()!=null){
//							// System.out.println(line);
//							// }
//							reader.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					} else {
//						InputStream inputStream = zipFile.getInputStream(ze);
//						String fileName = ze.getName().substring(ze.getName().lastIndexOf("/"), ze.getName().length());
//						File file = new File("F:\\" + fileName);
//						if (!file.exists()) {
//							file.createNewFile();
//						}
//						OutputStream outputStream = new FileOutputStream(new File("F://" + fileName));
//
//						ByteArrayOutputStream output = new ByteArrayOutputStream();
//					    byte[] buffer = new byte[4096];
//					    int n = 0;
//					    while (-1 != (n = inputStream.read(buffer))) {
//					        output.write(buffer, 0, n);
//					    }
//					    outputStream.write(output.toByteArray());
//						outputStream.close();
//					}
//				}
//			}
//		}
//		zipInputStream.close();
//		zipFile.close();
	}
}
