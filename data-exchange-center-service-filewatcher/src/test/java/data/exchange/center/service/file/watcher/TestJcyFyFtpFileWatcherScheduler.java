package data.exchange.center.service.file.watcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJcyFyFtpFileWatcherScheduler {

	private static Logger logger = LoggerFactory.getLogger(TestJcyFyFtpFileWatcherScheduler.class);

	/**
	 * 
	 * @function
	 * @author wenyuguang
	 * @creaetime 2017年10月17日 上午11:09:43
	 */
	public static void run() throws Exception {
		FTPClient ftpClient = new FTPClient();
		FTPClient sgyFtpClient = new FTPClient();
		try {
			ftpClient.connect("150.0.0.38", 21);
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.login("sf_fy", "sf_fy");
			/**
			 * 被动模式
			 */
			ftpClient.enterLocalPassiveMode();
			/**
			 * 230 表示登录成功
			 */
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.error("ftp连接错误： " + ftpClient.getReplyStrings().toString());
				ftpClient.disconnect();
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.setDataTimeout(0);
			ftpClient.setBufferSize(1024 * 1024);

			/**
			 * 数据中心缓存FTP
			 */
			sgyFtpClient.connect("150.0.2.164", 21);
			sgyFtpClient.setControlEncoding("UTF-8");
			sgyFtpClient.login("sgy", "sgy");
			/**
			 * 被动模式
			 */
			sgyFtpClient.enterLocalPassiveMode();
			/**
			 * 230 表示登录成功
			 */
			if (!FTPReply.isPositiveCompletion(sgyFtpClient.getReplyCode())) {
				logger.error("ftp连接错误： " + sgyFtpClient.getReplyStrings().toString());
				sgyFtpClient.disconnect();
			}
			sgyFtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

			/**
			 * 切换到根目录 罗列出所有文件和文件夹准备遍历
			 */
			ftpClient.changeWorkingDirectory("/");
			sgyFtpClient.changeWorkingDirectory("/");
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile ftpFile : ftpFiles) {
				sgyFtpClient.changeWorkingDirectory("/");
				if (ftpFile.isFile()) {
					if (!ftpFile.getName().endsWith(".zip")) {
						System.out.println("该FTP文件不是zip包，文件名为：" + ftpFile.getName());
					} else {
						/**
						 * 1608_1608A_5201_520111_4876945a6d6344c39eb45c0e1e4fe5fa_A722D14DC34C9E61B978940F8DF997AC.zip
						 * 
						 * ZIP包的名称是具有规则的，它的名称有 （1）发送单位编码--用于发送单位的编码，详见单位编码表 （2）接收单位编码--用于接收单位的编码，详见单位编码表
						 * （3）交互编号--本次交换的唯一编码，可用GUID （4）MD5校验码
						 * 
						 * 
						 */
						/**
						 * 文件名称
						 */
						String fileName = ftpFile.getName();
						/**
						 * MD5校验码
						 */
						String fileMd5 = fileName.split("_")[3].substring(0, fileName.split("_")[3].lastIndexOf("."));
						String zip = "zip";

						ByteArrayOutputStream out = null;
						InputStream input = null;
						InputStream inputMd5 = null;
						try {
							out = new ByteArrayOutputStream();
							/**
							 * 从服务器检索命名文件并将其写入给定的OutputStream中
							 */
							ftpClient.retrieveFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"), out);
							input = new ByteArrayInputStream(out.toByteArray());
							inputMd5 = new ByteArrayInputStream(out.toByteArray());

							/**
							 * MD5校验
							 */
							MessageDigest md = MessageDigest.getInstance("MD5");
							byte[] buffer = new byte[1024];
							int length = -1;
							while ((length = inputMd5.read(buffer, 0, 1024)) != -1) {
								md.update(buffer, 0, length);
							}
							String md5 = new BigInteger(1, md.digest()).toString(16);
							System.out.println("文件md5值：" + md5);

							if (fileMd5.toUpperCase().contains(md5.toUpperCase())) {

								sgyFtpClient.makeDirectory(zip);
								sgyFtpClient.changeWorkingDirectory(zip);
								sgyFtpClient.makeDirectory("/test");
								sgyFtpClient.changeWorkingDirectory("/test");
								sgyFtpClient.storeFile(fileName, input);
								sgyFtpClient.changeWorkingDirectory("/");

								try {
									logger.info("从前置机库获取JCY zip包成功，消息");
								} catch (Exception e) {
									e.printStackTrace();
									logger.error("" + e.getMessage());
								}
							} else {
								logger.error("文件名：" + fileName + " 的MD5值不匹配！！！！！！");
							}

						} catch (Exception e) {
							e.printStackTrace();
							logger.error("从FTP下载文件或者上传文件出错：" + e.getMessage());
						} finally {
							if (out != null) {
								out.close();
							}
							if (input != null) {
								input.close();
							}
							if (inputMd5 != null) {
								inputMd5.close();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("" + e.getMessage());
		} finally {
			try {
				ftpClient.logout();
				sgyFtpClient.logout();
				ftpClient.disconnect();
				sgyFtpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("断开ftp连接出错" + e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		run();
	}
}
