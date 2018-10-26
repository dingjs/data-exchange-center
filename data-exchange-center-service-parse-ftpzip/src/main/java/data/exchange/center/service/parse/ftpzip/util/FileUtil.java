package data.exchange.center.service.parse.ftpzip.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月2日 下午4:24:13</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class FileUtil {

	/**
	 * 解压文件到指定目录 解压后的文件名，和之前一致
	 * 
	 * @param zipFile
	 *            待解压的zip文件
	 * @param descDir
	 *            指定目录
	 */
	public static void unZipFiles(String dir, String descDir) throws IOException {

		ZipFile zip = new ZipFile(new File(dir), Charset.forName("GBK"));// 解决中文文件夹乱码
		String name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));

		File pathFile = new File(descDir + name);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}

		for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + "/" + zipEntryName).replaceAll("\\*", "/");

			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}

			FileOutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			if(in != null) {
				in.close();
			}
			if(out != null) {
				out.close();
			}
		}
		System.out.println("******************解压完毕********************");
		if(zip != null) {
			zip.close();
		}
		return;
	}

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月31日 下午4:42:55
	 * @param oldPath 老路径
	 * @param newPath 新路径
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @function 从zip包中获取卷宗文件
	 * @author wenyuguang
	 * @creaetime 2017年11月2日 下午4:22:31
	 * @param zipPath
	 * @param targetPath
	 * @throws IOException
	 */
	public static void getJzFileCopyFromZip(String zipPath, String targetPath) throws IOException {

		ZipFile zipFile = null;
		InputStream in  = null;
		ZipInputStream zipInputStream = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			zipFile = new ZipFile(zipPath);
			in = new BufferedInputStream(new FileInputStream(zipPath));
			zipInputStream = new ZipInputStream(in);

			ZipEntry ze;
			while ((ze = zipInputStream.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					// 为空的文件夹什么都不做
				} else {
					if (ze.getSize() > 0) {
						if (".xml".equalsIgnoreCase(
								ze.getName().substring(ze.getName().indexOf("."), ze.getName().length()))) {
							/**
							 * 对zip包中的xml不做处理
							 */
						} else {
							inputStream = zipFile.getInputStream(ze);
							String fileName = ze.getName().substring(ze.getName().lastIndexOf("/"), ze.getName().length());
							File file = new File(targetPath + "/" + fileName);
							if (!file.exists()) {
								file.createNewFile();
							}
							
							outputStream = new FileOutputStream(new File(targetPath + "/" + fileName));
							try {
								ByteArrayOutputStream output = new ByteArrayOutputStream();
							    byte[] buffer = new byte[4096];
							    int n = 0;
							    while (-1 != (n = inputStream.read(buffer))) {
							        output.write(buffer, 0, n);
							    }
							    outputStream.write(output.toByteArray());
							}catch(Exception e) {
								throw e;
							}finally {
								outputStream.close();
								inputStream.close();
							}
							
						}
					}
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
	}
	
	/**
	 * 
	 * @function 从zip包中获取xml
	 * @author wenyuguang
	 * @creaetime 2017年11月2日 下午4:21:45
	 * @param zipPath
	 * @param targetPath
	 * @throws IOException
	 */
	public static String getXmlFileCopyFromZip(String zipPath) throws IOException {

		ZipFile zipFile = null;
		InputStream in  = null;
		ZipInputStream zipInputStream = null;
		StringBuffer line = new StringBuffer();
		try {
			zipFile = new ZipFile(zipPath);
			in = new BufferedInputStream(new FileInputStream(zipPath));
			zipInputStream = new ZipInputStream(in);

			ZipEntry ze;
			while ((ze = zipInputStream.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					// 为空的文件夹什么都不做
				} else {
					if (ze.getSize() > 0) {
						if (".xml".equalsIgnoreCase(
								ze.getName().substring(ze.getName().indexOf("."), ze.getName().length()))) {
							BufferedReader reader;
							try {
								reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze), "utf-8"));
								line.append(reader.readLine());
								reader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
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
		return line.toString();
	}
	
	/**
	 * 
	 * @function 判断zip包中是否包含JZ和WS目录，即是否有卷宗和文书，目录存在则认为存在
	 * @author wenyuguang
	 * @creaetime 2018年2月1日 下午3:44:02
	 * @param zipPath
	 * @return
	 * @throws Exception
	 */
	public static boolean hasJzAndWs(String zipPath) throws Exception {
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
		if(dirs.containsKey("JZ")&&dirs.containsKey("WS")) {
			return true;
		}else {
			return false;
		}
	}
}