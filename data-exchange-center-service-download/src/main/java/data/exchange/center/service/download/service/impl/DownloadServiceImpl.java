package data.exchange.center.service.download.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import data.exchange.center.service.download.domain.FileBlob;
import data.exchange.center.service.download.domain.FilePath;
import data.exchange.center.service.download.mapper.DownloadMapper;
import data.exchange.center.service.download.service.DownloadService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年1月22日 下午4:51:04</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class DownloadServiceImpl implements DownloadService {

	private static final Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);
	
	@Autowired
	private DownloadMapper downloadMapper;

	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.service.download.service.DownloadService#download(java.lang.String)
	 */
	@Override
	public byte[] download(String key) throws Exception {
		FTPClient ftpClient = null;
		InputStream inStream = null;
		ByteArrayOutputStream outSteam = null;
		try {
			ftpClient = new FTPClient();
			/**
			 * 配置30分钟读取超时
			 */
			ftpClient.setDataTimeout(30*60*1000);
			ftpClient.setConnectTimeout(10000);
            ftpClient.connect("150.0.2.70", 21);
            int reply = ftpClient.getReplyCode();
            if ( !FTPReply.isPositiveCompletion(reply) ) {
                ftpClient.disconnect();
                throw new Exception("FTPServer refused connection: host:150.0.2.70,port:21");
            }
            boolean result = ftpClient.login("dcbase", "dcbase");
            if(!result){
                logger.error("ftpClient登陆失败! userName:" + "dcbase" );
            }
            ftpClient.setControlKeepAliveTimeout(60000*60);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("utf-8");
          	ftpClient.enterLocalPassiveMode();
          	
			FilePath filePath = downloadMapper.getFilePath(key.split("_")[0], key.split("_")[1]);
			
			/**
			 * 如果记录为空则返回 null
			 */
			if(StringUtils.isEmpty(filePath)) {
				logger.error(String.format("key:%s 下载出错：路径为空", key));
				return null;
			}
			/**
			 * 老路径为空则为blob
			 */
			if (StringUtils.isEmpty(filePath.getOldPath())) {
				Map<String,String> param = new HashMap<String,String>();
				param.put("ajbs", key.split("_")[0]);
				param.put("xh",   key.split("_")[1]);
				param.put("tb",   filePath.getcEpreFix());
				
				FileBlob fileBlob = downloadMapper.getBlob(param);
				if(StringUtils.isEmpty(fileBlob)) {
					logger.error(String.format("key:%s 下载出错：BLOB为空", key));
					return null;
				}
				return fileBlob.getWjnr();
			} else {
//				String path = filePath.getFileName().substring(0, filePath.getFileName().lastIndexOf("/"));
//				String name = filePath.getFileName().substring(filePath.getFileName().lastIndexOf("/")).replace("/", "");
//				ftpClient.changeWorkingDirectory(path);

				inStream = ftpClient.retrieveFileStream(new String(filePath.getFileName().getBytes("utf-8"), "iso-8859-1"));
				outSteam = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = inStream.read(buffer)) != -1) {
					outSteam.write(buffer, 0, len);
				}
				if(StringUtils.isEmpty(outSteam)) {
					logger.error(String.format("key:%s 下载出错：FTP文件为空", key));
					return null;
				}
				return outSteam.toByteArray();
			}
		} catch (Exception e) {
			logger.error(String.format("key:%s 下载出错：%s", key, e.toString()));
			return null;
		}finally {
			ftpClient.logout();
			if(outSteam!=null) {
				outSteam.close();
			}
			if(inStream!=null) {
				inStream.close();
			}
		}
	}

	@Override
	public String getFilePath(String key) throws Exception {
		
		FilePath filePath = downloadMapper.getFilePath(key.split("_")[0], key.split("_")[1]);
		/**
		 * 如果记录为空则返回 null
		 */
		if(StringUtils.isEmpty(filePath)) {
			logger.error(String.format("key:%s 下载出错：路径为空", key));
			return null;
		}
		return filePath.getFileName();
	}
}
