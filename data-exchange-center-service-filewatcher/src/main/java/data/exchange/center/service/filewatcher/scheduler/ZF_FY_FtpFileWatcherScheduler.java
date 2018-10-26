package data.exchange.center.service.filewatcher.scheduler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.filewatcher.domain.FileInfo;
import data.exchange.center.service.filewatcher.domain.FtpInfo;
import data.exchange.center.service.filewatcher.mapper.FileWatcherMapper;

/**
 * 
 * Description:<公安到法院>定时器扫描ftp中间库
 * <p>Company: xinya </p>
 * <p>Date:2017年9月27日 上午10:09:07</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
//@Component
public class ZF_FY_FtpFileWatcherScheduler implements CommandLineRunner{
	
	private static Logger logger =   LoggerFactory.getLogger(ZF_FY_FtpFileWatcherScheduler.class);

	@Autowired
	private FileWatcherMapper fileWatcherMapper;
	@Autowired
	private RabbitTemplate    rabbitTemplate;
	@Value("${JCY_FY}")
	private String ZF_FY;
	
	@Value("${SGY_FTP}")
	private String SGY_FTP;
	
	private String dateNowStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
	
	/**
 	 * 定时任务执行器
 	 */
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月17日 上午11:09:43
	 */
    @Override
	public void run(String... args) throws Exception {
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
            	FTPClient ftpClient    = new FTPClient();
            	FTPClient sgyFtpClient = new FTPClient();
            	try {
            		logger.info("开始执行数据抽取定时任务----->扫描【检察院】");
            		FtpInfo ftpInfo = fileWatcherMapper.getFtpInfoByFtpAlias(ZF_FY);
            		ftpClient.connect(ftpInfo.getcIp(), Integer.valueOf(ftpInfo.getnPort()));
            		ftpClient.setControlEncoding(ftpInfo.getcEncoding());
        			ftpClient.login(ftpInfo.getcUsername(), ftpInfo.getcPassword());
        			/**
        			 * 被动模式
        			 */
        			ftpClient.enterLocalPassiveMode();
        			/**
                     * 230 表示登录成功
                     */
                    int reply = ftpClient.getReplyCode() ;
                    if (!FTPReply.isPositiveCompletion(reply)) {
                        logger.error(ftpInfo.getcFtpalias()+"ftp连接错误： "+ ftpClient.getReplyStrings().toString());
                        ftpClient.disconnect();
                    }
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    
                    /**
                     * 数据中心缓存FTP
                     */
                    FtpInfo SGYftpInfo = fileWatcherMapper.getFtpInfoByFtpAlias(SGY_FTP);
                    sgyFtpClient.connect(SGYftpInfo.getcIp(), Integer.valueOf(SGYftpInfo.getnPort()));
                    sgyFtpClient.setControlEncoding(SGYftpInfo.getcEncoding());
                    sgyFtpClient.login(SGYftpInfo.getcUsername(), SGYftpInfo.getcPassword());
        			/**
        			 * 被动模式
        			 */
                    sgyFtpClient.enterLocalPassiveMode();
        			/**
                     * 230 表示登录成功
                     */
                    if (!FTPReply.isPositiveCompletion(sgyFtpClient.getReplyCode())) {
                        logger.error(SGYftpInfo.getcFtpalias()+"ftp连接错误： "+sgyFtpClient.getReplyStrings().toString());
                        sgyFtpClient.disconnect();
                    }
                    sgyFtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    
                    
                    /**
                     * 切换到根目录
                     * 罗列出所有文件和文件夹准备遍历
                     */
                    ftpClient.changeWorkingDirectory("/");
                    sgyFtpClient.changeWorkingDirectory("/");
                    FTPFile[] ftpFiles = ftpClient.listFiles();
                    for(FTPFile ftpFile:ftpFiles) {
                    	sgyFtpClient.changeWorkingDirectory("/");
                    	if(ftpFile.isFile()) {
                    		if(!ftpFile.getName().contains(".zip")) {
                    			logger.error("该FTP文件不是zip包，文件名为："+ftpFile.getName());
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
                        		//String   fileMd5    = fileName.split("_")[3].substring(0, fileName.split("_")[3].lastIndexOf("."));
                        		/**
                        		 * 文件大小
                        		 */
                        		long     fileSize   = ftpFile.getSize();
                        		/**
                        		 * 文件时间戳
                        		 */
                        		Date     date       = ftpFile.getTimestamp().getTime();
                        		String   type       = "ZF";
                        		String   zip        = "zip";
                        		String   filePath   = "//zip//"+dateNowStr+"//"+fileName;
                        		
                        		ByteArrayOutputStream out = null;
                        		InputStream input = null;
                        		try {
                        			out = new ByteArrayOutputStream();
                        			/**
                                     * 从服务器检索命名文件并将其写入给定的OutputStream中
                                     */
                                    ftpClient.retrieveFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"), out);
                                    input = new ByteArrayInputStream(out.toByteArray());
                                   
                                    sgyFtpClient.makeDirectory(zip);
                                    sgyFtpClient.changeWorkingDirectory(zip);
                                    sgyFtpClient.makeDirectory(dateNowStr);
                                    sgyFtpClient.changeWorkingDirectory(dateNowStr);
                                    sgyFtpClient.storeFile(fileName, input);
                                    sgyFtpClient.changeWorkingDirectory("/");
                                    /**
                                     * MD5校验
                                     */
//                                    MessageDigest md = MessageDigest.getInstance("MD5");
//                                    byte[] buffer = new byte[1024];
//                                    int length = -1;
//                                    while ((length = input.read(buffer, 0, 1024)) != -1) {
//                                        md.update(buffer, 0, length);
//                                    }
//                                    String md5 = new BigInteger(1, md.digest()).toString(16);
//                                    System.out.println("文件md5值：" + md5);
//                                    if(!fileMd5.equalsIgnoreCase(md5)) {
//                                    	System.out.println("文件MD5值不匹配！！！！！！");
//                                    }
                        		}catch(Exception e) {
                        			logger.error("从FTP下载文件或者上传文件出错："+e.getMessage());
                        		}finally{
                        			out.close();
                        			input.close();
                        		}
                                
                                /**
                                 * 保存文件信息
                                 */
                                fileWatcherMapper.saveFtpFileInfo(new FileInfo(taskId, fileName, fileSize, date, type, filePath));
                                ftpClient.deleteFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"));
                                
                                /**
                                 * 收到文件包确认，等待处理
                                 */
                                
                                Map<String, Object> msgMap = new HashMap<String, Object>();
                                try {
                                	/**
                                	 * 任务id
                                	 */
                                	msgMap.put("taskId", taskId);
                                	/**
                                	 * 任务类型
                                	 */
                                	msgMap.put("type",   type);
                                	
                                	
                                	/**
                                	 * 根据消息类型进行转发
                                	 */
                                	rabbitTemplate.convertAndSend(
                                			RabbitmqConf.XTBAPT_EXCHANGE, 
                                			RabbitmqConf.XTBAPT_ROUTEKEY, 
                                			msgMap,
                                			new CorrelationData(UUID.randomUUID().toString()));
                                	logger.info("从前置机库获取JCY zip包成功，消息:"+msgMap.toString());
                                } catch (Exception e) {
                                	e.printStackTrace();
                                	logger.error(""+e.getMessage());
                                }
                    		}
                    	}
                    }
            	}catch(Exception e) {
            		logger.error(""+e.getMessage());
            	}finally {
            		try {
            			ftpClient.logout();
						sgyFtpClient.logout();
						ftpClient.disconnect();
						sgyFtpClient.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
						logger.error("断开ftp连接出错"+e.getMessage());
					}
            	}
            }
        }, TimeUtils.interval, TimeUtils.interval, TimeUnit.MILLISECONDS);
	}
}
