package data.exchange.center.service.filewatcher.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.stereotype.Component;

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
@Component
public class JcyFyFtpFileWatcherScheduler implements CommandLineRunner{
	
	private static Logger logger =   LoggerFactory.getLogger(JcyFyFtpFileWatcherScheduler.class);

	@Autowired
	private FileWatcherMapper fileWatcherMapper;
	@Autowired
	private RabbitTemplate    rabbitTemplate;
	@Value("${JCY_FY}")
	private String JCY_FY;
	
	@Value("${SGY_FTP}")
	private String SGY_FTP;
	
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
            	String dateNowStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
            	FTPClient ftpClient    = new FTPClient();
            	FTPClient sgyFtpClient = new FTPClient();
            	try {
            		logger.info("开始执行数据抽取定时任务----->扫描【检察院】");
            		FtpInfo ftpInfo = fileWatcherMapper.getFtpInfoByFtpAlias(JCY_FY);
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
                    ftpClient.setDefaultTimeout(600000);
                    ftpClient.setDataTimeout(0);
                    ftpClient.setBufferSize(1024 * 1024);
                    
                    /**
                     * 数据中心缓存FTP
                     */
                    FtpInfo SGYftpInfo = fileWatcherMapper.getFtpInfoByFtpAlias(SGY_FTP);
                    sgyFtpClient.connect(SGYftpInfo.getcIp(), Integer.valueOf(SGYftpInfo.getnPort()));
                    sgyFtpClient.setControlEncoding(SGYftpInfo.getcEncoding());
                    sgyFtpClient.login(SGYftpInfo.getcUsername(), SGYftpInfo.getcPassword());
                    sgyFtpClient.setDefaultTimeout(600000);
                    sgyFtpClient.setDataTimeout(0);
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
                    		if(!ftpFile.getName().endsWith(".zip")) {
                    			logger.info("该FTP文件不是zip包，文件名为："+ftpFile.getName());
                    		}else{
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
                        		String   fileMd5    = fileName.split("_")[3].replace(".zip", "").replace(".ZIP", "");
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
                        		
                        		//下载文件前检查本地磁盘情况，磁盘小于20G删除旧数据
                        		File[] roots = File.listRoots();// 获取磁盘分区列表
                        		for (File disk : roots) {
                        			if(disk.getPath().toLowerCase().startsWith("d")) {
                        				logger.info("D盘剩余磁盘容量："+disk.getFreeSpace() / 1024 / 1024 / 1024+"G");
                        				if((disk.getFreeSpace() / 1024 / 1024 / 1024) < 20) {
                        					String ftpPath = "D:\\\\FYFTP\\\\sgy\\\\zip\\\\";
                        					File dir = new File(ftpPath);//FTP对应目录
                        					File[] files = dir.listFiles();
                        					List<Integer> nums = new ArrayList<Integer>();  
                        					for(File f:files) {
                        						nums.add(Integer.parseInt(f.getName()));
                        					}
                        			        Collections.sort(nums);
                        			        logger.info("磁盘不足20G，删除目录："+ftpPath+nums.get(0));
                        			        deleteDir(new File(ftpPath+nums.get(0)));
                        				}
                        			}
                        		}
                        		
                        		InputStream input = null;
                        		String tmpdir = "/filetmpdir/";
                        		try {
                                    try {
                                        Files.createDirectory(Paths.get(tmpdir));
                                    } catch(FileAlreadyExistsException e){
                                        // the directory already exists.
                                    } catch (IOException e) {
                                        //something else went wrong
                                        e.printStackTrace();
                                    }
                                    System.out.println("准备下载文件："+tmpdir+fileName);
                                    InputStream in = null;
                                    FileOutputStream fileOutputStream = null;
                                    try {
                                    	//进行断点续传，并记录状态   
                                        fileOutputStream = new FileOutputStream(tmpdir+fileName,true);   
                                        long localSize = 0;
                                        long lRemoteSize = ftpFile.getSize();
                                        ftpClient.setRestartOffset(localSize);   
                                        in = ftpClient.retrieveFileStream(new String(fileName.getBytes("GBK"),"iso-8859-1"));
                                        TimeUnit.SECONDS.sleep(2);
                                        byte[] bytes = new byte[1024];   
                                        long step = lRemoteSize /100;   
                                        long process=localSize /step;   
                                        int c;   
                                        while((c = in.read(bytes))!= -1){  
                                        	fileOutputStream.write(bytes,0,c);   
                                            localSize+=c;   
                                            long nowProcess = localSize /step;   
                                            if(nowProcess > process){   
                                                process = nowProcess;   
                                                if(process % 10 == 0)   
                                                	logger.info("下载进度："+process);   
                                            }   
                                        }
                                           
                                        boolean isDo = ftpClient.completePendingCommand();   
                                        logger.info("下载文件："+tmpdir+fileName+"结果："+ isDo);
									} finally {
										if(in != null) {
											in.close();
										}
										fileOutputStream.flush();
										if(fileOutputStream != null) {
											fileOutputStream.close();
										}
									}
                                    String md5 = getMD5Three(tmpdir+fileName);
                                    System.out.println("文件md5值：" + md5);
                                    
                                    if(fileMd5.toUpperCase().contains(md5.toUpperCase())) {
                                    	sgyFtpClient.makeDirectory(zip);
                                        sgyFtpClient.changeWorkingDirectory(zip);
                                        sgyFtpClient.makeDirectory(dateNowStr);
                                        sgyFtpClient.changeWorkingDirectory(dateNowStr);
                                        input = new FileInputStream(tmpdir+fileName);
                                        boolean b = sgyFtpClient.storeFile(fileName, input);
                                        System.out.println(tmpdir+fileName + "上传结果："+ b);
                                        sgyFtpClient.changeWorkingDirectory("/");
                                        
                                        //下载文件过程可能会被服务器关闭连接，原因暂时不明
                                        try {
                                        	if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                                        		logger.error(ftpInfo.getcFtpalias()+"ftp连接错误： "+ ftpClient.getReplyStrings().toString());
                                        		ftpClient.disconnect();
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
                                                if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                                                    logger.error(ftpInfo.getcFtpalias()+"ftp连接错误： "+ ftpClient.getReplyStrings().toString());
                                                    ftpClient.disconnect();
                                                }
                                        	}
                                        	/**
                                        	 * 保存文件信息，类似幂等操作，防止重入导致错误
                                        	 */
                                        	fileWatcherMapper.deleteFtpFileInfo(new FileInfo(taskId, fileName, fileSize, date, type, filePath));
                                        	fileWatcherMapper.saveFtpFileInfo(new FileInfo(taskId, fileName, fileSize, date, type, filePath));
                                        	boolean bb = ftpClient.deleteFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"));
                                        	logger.info("38ftp响应："+ftpClient.getReplyString()+"；删除38ftp文件结果:"+bb);
                                        }catch(Exception e) {
                                        	logger.info(fileName+e.toString());
                                        }
                                        
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
                                        	logger.error(e.toString());
                                        }
                                    }else {
                                    	logger.error("文件名："+fileName+" 的MD5值不匹配！！！！！！");
                                    }
                                    
                        		}catch(Exception e) {
                        			e.printStackTrace();
                        			logger.error("从FTP下载文件或者上传文件出错："+e.toString());
                        		}finally{
                        			if(input!= null) {
                        				input.close();
                        			}
                        			TimeUnit.SECONDS.sleep(2);
                        			new File(tmpdir+fileName).delete();//清除断点下载的本地临时文件
                        		}
                    		}
                    	}
                    }
            	}catch(Exception e) {
            		e.printStackTrace();
            		logger.error(e.toString());
            	}finally {
            		try {
            			ftpClient.logout();
						sgyFtpClient.logout();
						ftpClient.disconnect();
						sgyFtpClient.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
						logger.error("断开ftp连接出错"+e.toString());
					}
            	}
            }
        }, TimeUtils.interval, TimeUtils.interval, TimeUnit.MILLISECONDS);
	}
    
    
	public static String getMD5Three(String path) {
		BigInteger bi = null;
		try {
			byte[] buffer = new byte[8192];
			int len = 0;
			MessageDigest md = MessageDigest.getInstance("MD5");
			File f = new File(path);
			FileInputStream fis = new FileInputStream(f);
			while ((len = fis.read(buffer)) != -1) {
				md.update(buffer, 0, len);
			}
			fis.close();
			byte[] b = md.digest();
			bi = new BigInteger(1, b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi.toString(16);
	}
	
	private synchronized static void deleteDir(File dir) {
		try {
			if (dir.isDirectory()) {
				String[] children = dir.list();
				/**
				 * 递归删除目录中的子目录下
				 */
				for (int i = 0; i < children.length; i++) {
					deleteDir(new File(dir, children[i]));
				}
			}
			/**
			 * 目录此时为空，可以删除
			 */
			dir.delete();
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("清除本地缓存zip包出错："+e.getMessage());
		}
	}
}
