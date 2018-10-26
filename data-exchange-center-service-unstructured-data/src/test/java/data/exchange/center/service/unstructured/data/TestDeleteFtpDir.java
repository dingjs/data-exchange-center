package data.exchange.center.service.unstructured.data;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class TestDeleteFtpDir {


	public static void main(String[] args) {
		for(int i=510700; i<513444; i++) {
			deleteDir("100001A000000001/TDH1/"+i+"/");
		}
	}

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年1月22日 上午9:49:18
	 * @return
	 * @throws IOException
	 */
//	public static FTPClient newInstance() throws IOException {
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.connect("150.0.2.70");
//		boolean flag = ftpClient.login("dcbase", "dcbase");
//		if (flag) {
//			ftpClient.setControlKeepAliveTimeout(60000);
//			return ftpClient;
//		} else {
//			return null;
//		}
//	}

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年1月22日 上午9:46:39
	 * @param ftpPath
	 * @return
	 */
	public static void deleteDir(String ftpPath) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect("150.0.2.70");
			ftpClient.login("dcbase", "dcbase");
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.setControlKeepAliveTimeout(6000000);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			iterateDelete(ftpClient, ftpPath);
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年1月22日 上午9:46:35
	 * @param ftpClient
	 * @param ftpPath
	 * @return
	 * @throws IOException
	 */
	public static void iterateDelete(FTPClient ftpClient, String ftpPath) throws IOException {
		FTPFile[] files = ftpClient.listFiles(ftpPath);
		for (FTPFile f : files) {
			if(!f.getName().startsWith(".")) {
				String path = ftpPath + "/" + f.getName();
				if (f.isFile()) {
					// 是文件就删除文件
					boolean rst = ftpClient.deleteFile(new String(path.getBytes("utf-8"), "iso-8859-1"));
					System.err.print(rst+"    ");System.out.println("delete file:"+path);
				} else if (f.isDirectory()) {
					iterateDelete(ftpClient, path);
				}
			}
		}
		// 每次删除文件夹以后就去查看该文件夹下面是否还有文件，没有就删除该空文件夹
		ftpClient.removeDirectory(ftpPath);
		System.out.println("delete dir:"+ftpPath);
	}
}
