//package data.exchange.center.service.factor.trial.util;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//import org.apache.commons.net.ftp.FTPReply;
//
//public class FavFTPUtil {
//
//	/**
//	 * 上传文件（可供Action/Controller层使用）
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param fileName
//	 *            上传到FTP服务器后的文件名称
//	 * @param inputStream
//	 *            输入文件流
//	 * @return
//	 */
//	public static boolean uploadFile(String hostname, int port, String username, String password, String pathname,
//			String fileName, InputStream inputStream) {
//		boolean flag = false;
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.setControlEncoding("UTF-8");
//		try {
//			// 连接FTP服务器
//			ftpClient.connect(hostname, port);
//			// 登录FTP服务器
//			ftpClient.login(username, password);
//			// 是否成功登录FTP服务器
//			int replyCode = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(replyCode)) {
//				return flag;
//			}
//
//			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//			ftpClient.makeDirectory(pathname);
//			ftpClient.changeWorkingDirectory(pathname);
//			ftpClient.storeFile(fileName, inputStream);
//			inputStream.close();
//			ftpClient.logout();
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					ftpClient.disconnect();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return flag;
//	}
//
//	/**
//	 * 上传文件（可对文件进行重命名）
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param filename
//	 *            上传到FTP服务器后的文件名称
//	 * @param originfilename
//	 *            待上传文件的名称（绝对地址）
//	 * @return
//	 */
//	public static boolean uploadFileFromProduction(String hostname, int port, String username, String password,
//			String pathname, String filename, String originfilename) {
//		boolean flag = false;
//		try {
//			InputStream inputStream = new FileInputStream(new File(originfilename));
//			flag = uploadFile(hostname, port, username, password, pathname, filename, inputStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//
//	/**
//	 * 上传文件（不可以进行文件的重命名操作）
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param originfilename
//	 *            待上传文件的名称（绝对地址）
//	 * @return
//	 */
//	public static boolean uploadFileFromProduction(String hostname, int port, String username, String password,
//			String pathname, String originfilename) {
//		boolean flag = false;
//		try {
//			String fileName = new File(originfilename).getName();
//			InputStream inputStream = new FileInputStream(new File(originfilename));
//			flag = uploadFile(hostname, port, username, password, pathname, fileName, inputStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//
//	/**
//	 * 删除文件
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器保存目录
//	 * @param filename
//	 *            要删除的文件名称
//	 * @return
//	 */
//	public static boolean deleteFile(String hostname, int port, String username, String password, String pathname,
//			String filename) {
//		boolean flag = false;
//		FTPClient ftpClient = new FTPClient();
//		try {
//			// 连接FTP服务器
//			ftpClient.connect(hostname, port);
//			// 登录FTP服务器
//			ftpClient.login(username, password);
//			// 验证FTP服务器是否登录成功
//			int replyCode = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(replyCode)) {
//				return flag;
//			}
//			// 切换FTP目录
//			ftpClient.changeWorkingDirectory(pathname);
//			ftpClient.dele(filename);
//			ftpClient.logout();
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					ftpClient.logout();
//				} catch (IOException e) {
//
//				}
//			}
//		}
//		return flag;
//	}
//
//	public static void main(String[] args) {
//		String hostname = "150.0.2.70";
//		int port = 21;
//		String username = "dcbase";
//		String password = "dcbase";
//		String pathname = "/SGYZX/201601/511423/21/321100000000964/jz/1";
//		String filename = "20160419.215259.673_08.55.406-1_003.jpg";
//		String localpath = "d:\\11111";
//		downloadFile(null, null, hostname, port, username, password, pathname, filename, localpath);
//	}
//
//	/**
//	 * 下载文件
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器文件目录
//	 * @param filename
//	 *            文件名称
//	 * @param localpath
//	 *            下载后的文件路径
//	 * @return
//	 */
//	public static boolean downloadFile(HttpServletRequest request,  
//            HttpServletResponse response,String hostname, int port, String username, String password, String pathname,
//			String filename, String localpath) {
//		boolean flag = false;
//		FTPClient ftpClient = new FTPClient();
//		 BufferedInputStream bis = null;  
//	     BufferedOutputStream bos = null; 
//		try {
//			// 连接FTP服务器
//			ftpClient.connect(hostname, port);
//			// 登录FTP服务器
//			ftpClient.login(username, password);
//			ftpClient.setControlEncoding("gbk");
//			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//			//ftpClient.setBufferSize(BUFFER_SIZE);  
//			// 验证FTP服务器是否登录成功
//			int replyCode = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(replyCode)) {
//				return flag;
//			}
//			// 切换FTP目录
//			ftpClient.changeWorkingDirectory(pathname);
//            FTPFile[] files = ftpClient.listFiles(new String(filename.getBytes("GBK"),"iso-8859-1")); 
//            long fileLength=files[0].getSize();
//            //从服务器检索命名文件并将其写入给定的OutputStream中
//            InputStream in= ftpClient.retrieveFileStream(new String(filename.getBytes("GBK"),"ISO-8859-1"));
//         
//            response.setHeader("Content-disposition", "attachment; filename="  
//                    + new String(filename.getBytes("utf-8"), "ISO8859-1"));  
//            response.setHeader("Content-Length", String.valueOf(fileLength));
//            bos = new BufferedOutputStream(response.getOutputStream());
//            byte[] buff = new byte[2048];
//            int bytesRead;
//            while((bytesRead = in.read(buff))!= -1){   
//            	bos.write(buff,0,bytesRead);   
//            }
//			ftpClient.logout();
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					if(bis!=null){
//					bis.close();  
//					}
//					if(bos!=null){
//			        bos.close();  
//					}
//					ftpClient.logout();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return flag;
//	}
//	
//	/**
//	 * 下载文件
//	 * 
//	 * @param hostname
//	 *            FTP服务器地址
//	 * @param port
//	 *            FTP服务器端口号
//	 * @param username
//	 *            FTP登录帐号
//	 * @param password
//	 *            FTP登录密码
//	 * @param pathname
//	 *            FTP服务器文件目录
//	 * @param filename
//	 *            文件名称
//	 * @param localpath
//	 *            下载后的文件路径
//	 * @return
//	 */
//	public static boolean downloadFile1(HttpServletRequest request,  
//            HttpServletResponse response,String hostname, int port, String username, String password, String pathname,
//			String filename, String localpath) {
//		boolean flag = false;
//		int BUFFER_SIZE = 2048;  
//		FTPClient ftpClient = new FTPClient();
//		 BufferedInputStream bis = null;  
//	     BufferedOutputStream bos = null; 
//		try {
//			// 连接FTP服务器
//			ftpClient.connect(hostname, port);
//			// 登录FTP服务器
//			ftpClient.login(username, password);
//			ftpClient.setControlEncoding("gbk");
//			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//			//ftpClient.setBufferSize(BUFFER_SIZE);  
//			// 验证FTP服务器是否登录成功
//			int replyCode = ftpClient.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(replyCode)) {
//				return flag;
//			}
//			// 切换FTP目录
//			ftpClient.changeWorkingDirectory(pathname);
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            //从服务器检索命名文件并将其写入给定的OutputStream中
//            ftpClient.retrieveFile(new String(filename.getBytes("GBK"),"ISO-8859-1"), out);
//            long fileLength=out.toByteArray().length;
//            InputStream input = new ByteArrayInputStream(out.toByteArray());
//            response.setHeader("Content-disposition", "attachment; filename="  
//                    + new String(filename.getBytes("utf-8"), "ISO8859-1"));  
//            response.setHeader("Content-Length", String.valueOf(fileLength));
//            bis = new BufferedInputStream(input);  
//            bos = new BufferedOutputStream(response.getOutputStream());
//            byte[] buff = new byte[2048];  
//            int bytesRead;
//            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
//                bos.write(buff, 0, bytesRead);  
//            }  
//			ftpClient.logout();
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (ftpClient.isConnected()) {
//				try {
//					if(bis!=null){
//					bis.close();  
//					}
//					if(bos!=null){
//			        bos.close();  
//					}
//					ftpClient.logout();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return flag;
//	}
//	
//	public static void download(String path, HttpServletResponse response) throws Exception {   
//	    try {   path="E:\\www\\测试文件.rar";
//	            File file = new File(path);   
//	              if (true) {   
//	                 String filename = file.getName();   
//	                 InputStream fis = new BufferedInputStream(new FileInputStream(file));   
//	                  response.reset();   
//	                  response.setContentType("application/x-download");
//	                  response.addHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes(),"iso-8859-1"));
//	                  response.addHeader("Content-Length", "" + file.length());   
//	                 OutputStream toClient = new BufferedOutputStream(response.getOutputStream());   
//	                response.setContentType("application/octet-stream");   
//	                byte[] buffer = new byte[1024 * 1024 * 4];   
//	                  int i = -1;  
//	                  while ((i = fis.read(buffer)) != -1) {   
//	                      toClient.write(buffer, 0, i);  
//	                      
//	                  }   
//	                  fis.close();   
//	                  toClient.flush();   
//	                  toClient.close();   
//	                  try {
//	             response.wait();
//	            } catch (InterruptedException e) {
//	             // TODO Auto-generated catch block
//	             e.printStackTrace();
//	            }  
//	              } else {   
//	                 PrintWriter out = response.getWriter();   
//	                 out.print("<script>");   
//	                 out.print("alert(\"not find the file\")");   
//	                 out.print("</script>");   
//	              }   
//	          } catch (IOException ex) {   
//	        	  ex.printStackTrace();
//	             PrintWriter out = response.getWriter();   
//	                 out.print("<script>");   
//	                 out.print("alert(\"not find the file\")");   
//	                 out.print("</script>");   
//	          }   
//	     
//	      }
//
//}