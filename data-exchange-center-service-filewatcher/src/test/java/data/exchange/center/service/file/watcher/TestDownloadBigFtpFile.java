package data.exchange.center.service.file.watcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.exchange.center.common.time.TimeUtils;

/**
 * 
 * Description:<公安到法院>定时器扫描ftp中间库
 * <p>Company: xinya </p>
 * <p>Date:2017年9月27日 上午10:09:07</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class TestDownloadBigFtpFile{
	
	private static Logger logger =   LoggerFactory.getLogger(TestDownloadBigFtpFile.class);

	/**
 	 * 定时任务执行器
 	 */
    private final static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public static void test() {
    	scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
            	logger.info("开始执行数据抽取定时任务----->扫描【检察院】");
            }
        }, TimeUtils.interval, TimeUtils.interval, TimeUnit.MILLISECONDS);
    }
    public static void main(String[] args) {
    	String dateNowStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
    	FTPClient ftpClient    = new FTPClient();
    	try {
    		ftpClient.connect("150.0.0.38");
    		ftpClient.setControlEncoding("UTF-8");
			ftpClient.login("jcy_fy", "jcy_fy");
			/**
			 * 被动模式
			 */
			ftpClient.enterLocalPassiveMode();
			/**
             * 230 表示登录成功
             */
            int reply = ftpClient.getReplyCode() ;
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            
            /**
             * 切换到根目录
             * 罗列出所有文件和文件夹准备遍历
             */
            ftpClient.changeWorkingDirectory("/");
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile ftpFile:ftpFiles) {
            	if(ftpFile.isFile()) {
            		if(!ftpFile.getName().endsWith(".zip")) {
            			System.out.println("该FTP文件不是zip包，文件名为："+ftpFile.getName());
            		}else {
            			/**
                		 * 1608_1608A_5201_520111_4876945a6d6344c39eb45c0e1e4fe5fa_A722D14DC34C9E61B978940F8DF997AC.zip
                		 * 
                		 * ZIP包的名称是具有规则的，它的名称有
                		 * （1）发送单位编码--用于发送单位的编码，详见单位编码表
                                                                               （2）接收单位编码--用于接收单位的编码，详见单位编码表
                                                                               （3）交互编号--本次交换的唯一编码，可用GUID 
                                                                               （4）MD5校验码
                		 * 
                		 * 
                		 */
                		/**
                		 * 文件名称
                		 */
                		String   fileName   = ftpFile.getName();
                		/**
                		 * 任务id
                		 */
                		String   taskId     =  fileName.split("_")[2];
                		/**
                		 * MD5校验码
                		 */
                		String   fileMd5    = fileName.split("_")[3].substring(0, fileName.split("_")[3].lastIndexOf("."));
                		/**
                		 * 文件大小
                		 */
                		long     fileSize   = ftpFile.getSize();
                		/**
                		 * 文件时间戳
                		 */
                		Date     date       = ftpFile.getTimestamp().getTime();
                		String   type       = "JCY";
                		String   zip        = "zip";
                		String   filePath   = "//zip//"+dateNowStr+"//"+fileName;
                		
                		ByteArrayOutputStream out = null;
                		InputStream input = null;
                		InputStream inputMd5 = null;
                		try {
                			out = new ByteArrayOutputStream();
                			/**
                             * 从服务器检索命名文件并将其写入给定的OutputStream中
                             */
                            ftpClient.retrieveFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"), out);
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
                            
                            if(fileMd5.toUpperCase().contains(md5.toUpperCase())) {
                            	
                                try {
                                	/**
                                	 * 保存文件信息
                                	 */
                                	//ftpClient.deleteFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"));
                                }catch(Exception e) {
                                	System.out.println(fileName+e.getMessage());
                                }
                            }else {
                            	logger.error("文件名："+fileName+" 的MD5值不匹配！！！！！！");
                            }
                            
                		}catch(Exception e) {
                			e.printStackTrace();
                			logger.error("从FTP下载文件或者上传文件出错："+e.getMessage());
                		}finally{
                			if(out!= null) {
                				out.close();
                			}
                			if(input!= null) {
                				input.close();
                			}
                			if(inputMd5 !=null) {
                				inputMd5.close();
                			}
                		}
            		}
            	}
            }
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(""+e.getMessage());
    	}finally {
    		try {
    			ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("断开ftp连接出错"+e.getMessage());
			}
    	}
	}
}
