package data.exchange.center.service.unstructured.data.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.unstructured.data.domain.Count;
import data.exchange.center.service.unstructured.data.domain.FTPClientInfoConfigure;
import data.exchange.center.service.unstructured.data.domain.UrlInfo;
import data.exchange.center.service.unstructured.data.ftppool.FTPClientPool;
import data.exchange.center.service.unstructured.data.ftppool.FtpClientFactory;
import data.exchange.center.service.unstructured.data.mapper.FtpMapper;
import data.exchange.center.service.unstructured.data.service.FtpService;

@Service
public class FtpServiceImpl implements FtpService {

	private Logger logger = LoggerFactory.getLogger(FtpServiceImpl.class);
	
	@Autowired
	private FtpMapper ftpMapper;
	
	/**
     * utf-8
     */
    private final static String    UTF_8 = "UTF-8";
    /**
     * 法院ftp主机地址
     */
    private final static String    HIGHER_PEOPLES_COURT_FTP_HOST = "150.0.2.70";
    /**
     * 端口号
     */
    private final static int       HIGHER_PEOPLES_COURT_FTP_PORT = 21;
    /**
     * 账号
     */
    private final static String    HIGHER_PEOPLES_COURT_FTP_USERNAME = "dcbase";
    /**
     * 密码
     */
    private final static String    HIGHER_PEOPLES_COURT_FTP_PASSWORD = "dcbase";
    
    private final static int       HIGHER_PEOPLES_COURT_FTP_BUFFER_SIZE = 10240;
    private final static String    SPLIT_STRING = "/";
    
    private final static String    ROOT = "/";
	@Override
	public Object handleFtpFile(String ajbs, String ajlx, String fydm) {
		long start = System.currentTimeMillis();
		Map<String,Object> map = new HashMap<String, Object>();
        try{
            /**
             * 查询有几个ftp
             */
            List<Count> ftpNameList = ftpMapper.getFtpCount(ajbs);
            /**
             * 查询出中院中ftp的url
             */
            List<UrlInfo> urlInfoList = ftpMapper.getUrlInfo(ajbs);
            /**
             * 如果是文件清理或者文件夹清理的则先遍历处理掉
             */
            for(UrlInfo urlInfo:urlInfoList){
            	if(urlInfo.getCZLX().equals("FTPDEL")) {
            		deleteSgyFtpFile(urlInfo.getFILENAME());
            		Map<String, Object> params = new HashMap<>();
                	params.put("ajbs", ajbs);
                	params.put("xh", urlInfo.getXH());
        			ftpMapper.deleteDC_FJGH_EXTOPER(params);
            	}else if(urlInfo.getCZLX().equals("FTPCLS")) {
                	deleteSgyFtpDir(urlInfo.getFILENAME());
                	Map<String, Object> params = new HashMap<>();
                	params.put("ajbs", ajbs);
                	params.put("xh", urlInfo.getXH());
        			ftpMapper.deleteDC_FJGH_EXTOPER(params);
                }
            }
            
            for(Count Count:ftpNameList){//如果是文件清理ftpNameList可能为空，所以优先处理文件清理
                if(!StringUtils.isEmpty(Count)){
                	if(Count.getFtpName().equals("huayu")) {//处理华宇过来的减刑假释案件 2018年5月29日11:22:32
                		for(UrlInfo urlInfo:urlInfoList){
                        	//CZLX=FTPDEL为删除   CZLX=FTPINS为新增
                        	if(urlInfo.getCZLX().equals("FTPINS")) {
                        		String oldPath = urlInfo.getOLDPATH();
                                String hyPath = oldPath.split(":")[1];
                                String remotePath = hyPath.substring(0, hyPath.lastIndexOf("/"));
                                
                                //文件名称
                                String fileName = hyPath.substring(hyPath.lastIndexOf("/")+1, hyPath.length());
                                
                                //新建ftp路径
                                String storePath = urlInfo.getFILENAME().substring(0,urlInfo.getFILENAME().lastIndexOf('/')+1).replace("//", "/");
                                
                                
                        		String fileUrl = "http://150.0.2.155:9090/JxjsInterface/JxjsFtpServlet";
                                String jzpath = "?path="+remotePath;
                                String filename = "&name="+fileName;
                                String jzurl = fileUrl + jzpath + filename;
                        		InputStream input = null;
                                HttpURLConnection conn = null;
                                try {
                                    // 初始化连接
                                    URL url = new URL(jzurl);
                                    conn = (HttpURLConnection) url.openConnection();
                                    conn.setDoInput(true);
                                    conn.setDoOutput(true);
                                    // 读取数据
                                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                                    	input = conn.getInputStream();
                                    	boolean end= uploadFtpFile(input,storePath,fileName);
                                        if(!end){
                                            logger.error("文件上传ftp服务器失败："+remotePath+urlInfo.getFILENAME());
                                            Map<String, Object> params = new HashMap<>();
                                        	params.put("ajbs", ajbs);
                                        	params.put("xh", urlInfo.getXH());
                                        	params.put("ajlx", ajlx);
                                            ftpMapper.handleError(params);
                                        }else{
                                            System.out.println(">>>>"+remotePath+"/"+fileName+">>>> upload to >>>>"+urlInfo.getFILENAME()+">>>>upload success!");
                                        }
                                    }
                                }catch (Exception e) {
                                	logger.error("文件上传ftp服务器失败："+remotePath+urlInfo.getFILENAME());
                                    Map<String, Object> params = new HashMap<>();
                                	params.put("ajbs", ajbs);
                                	params.put("xh", urlInfo.getXH());
                                	params.put("ajlx", ajlx);
                                    ftpMapper.handleError(params);
                                    e.printStackTrace();
                                    continue;
                                }finally {
                                    try {
                                    	if(input != null){
                                    		input.close();
                                    	}
                                        conn.disconnect();
                                    }
                                    catch (IOException e) {
                                        e.printStackTrace();
                                        continue;
                                    }
                                }
                        	}
                        	//每一条操作记录遍历之后都要删掉，抽取ftp出错的记录则调用过程处理
                            Map<String, Object> params = new HashMap<>();
                        	params.put("ajbs", ajbs);
                        	params.put("xh", urlInfo.getXH());
                			ftpMapper.deleteDC_FJGH_EXTOPER(params);
                		}
                	}else {
                		Map<String, Object> param = new HashMap<>();
                    	param.put("fydm", fydm);
                    	param.put("ftpName", Count.getFtpName());
                        FTPClientInfoConfigure ftpClientInfoConfigure = ftpMapper.getFtpInfo(param);
                        FtpClientFactory clientFactory = new FtpClientFactory(ftpClientInfoConfigure);
                        FTPClientPool ftpClientPool = new FTPClientPool(clientFactory);
                        /**
                         * 拿ftp客户端
                         */
                        FTPClient ftpClient = ftpClientPool.borrowObject();
                        for(UrlInfo urlInfo:urlInfoList){
                        	//CZLX=FTPDEL为删除   CZLX=FTPINS为新增
                        	if(urlInfo.getCZLX().equals("FTPINS")) {
                                /**
                                 * 里面有垃圾旧数据
                                 * 如果没有“：”说明是老的数据
                                 */
                                if(urlInfo.getOLDPATH().contains(":")){
                                    String prex = urlInfo.getOLDPATH().substring(0, urlInfo.getOLDPATH().indexOf(":"));
                                    if(prex.equalsIgnoreCase(Count.getFtpName())){
                                        String oldPath = urlInfo.getOLDPATH();
                                        String[] path = oldPath.split(":");
//                                        String ftpName = path[0];
                                        String tdhPath = path[1];
                                        String remotePath1 = null;
                                        if(tdhPath.substring(0, 1).equals("/")){
                                            remotePath1 = ftpClientInfoConfigure.getROOT()+tdhPath.substring(1, tdhPath.length());
                                        }else{
                                            remotePath1 = ftpClientInfoConfigure.getROOT()+tdhPath;
                                        }
                                        String remotePath = remotePath1.substring(0, remotePath1.lastIndexOf("/"));
                                        
                                        //文件名称
                                        String fileName = tdhPath.substring(tdhPath.lastIndexOf("/")+1, tdhPath.length());
                                        
                                        //新建ftp路径
                                        String storePath = urlInfo.getFILENAME().substring(0,urlInfo.getFILENAME().lastIndexOf('/')+1).replace("//", "/");
                                        
                                        boolean insertDir = ftpClient.changeWorkingDirectory(remotePath);
                                        /**
                                         * 进入远程ftp目录失败
                                         */
                                        if(!insertDir){
                                            logger.error("进入远程目录失败! host:"+ftpClient.getRemoteAddress()+" remotePath:"+remotePath);
                                            /**
                                             * 错误直接抛出
                                             */
                                            Map<String, Object> params = new HashMap<>();
                                        	params.put("ajbs", ajbs);
                                        	params.put("xh", urlInfo.getXH());
                                        	params.put("ajlx", ajlx);
                                            ftpMapper.handleError(params);
                                        }else{
                                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                                            /**
                                             * 从服务器检索命名文件并将其写入给定的OutputStream中
                                             */
                                            boolean retrieveFile = ftpClient.retrieveFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"), out);
                                            if(retrieveFile){
                                                InputStream input = new ByteArrayInputStream(out.toByteArray());
                                                /**
                                                 * 上传文件
                                                 */
                                                boolean end= uploadFtpFile(input,storePath,fileName);
                                                if(!end){
                                                    logger.error("文件上传ftp服务器失败："+remotePath+urlInfo.getFILENAME());
                                                    Map<String, Object> params = new HashMap<>();
                                                	params.put("ajbs", ajbs);
                                                	params.put("xh", urlInfo.getXH());
                                                	params.put("ajlx", ajlx);
                                                    ftpMapper.handleError(params);
                                                }else{
                                                    System.out.println(">>>>"+remotePath+"/"+fileName+">>>> upload to >>>>"+urlInfo.getFILENAME()+">>>>upload success!");
                                                }
                                                out.close();
                                                input.close();
                                            }else{
                                                logger.error("没有在地址："+ftpClientInfoConfigure.getHOST()+"，用户名："
                                                +ftpClientInfoConfigure.getUSERNAME()+"，密码："
                                                +ftpClientInfoConfigure.getPASSWORD()
                                                +"，中找到 "+remotePath+fileName+" 文件");
                                                Map<String, Object> params = new HashMap<>();
                                            	params.put("ajbs", ajbs);
                                            	params.put("xh", urlInfo.getXH());
                                            	params.put("ajlx", ajlx);
                                                ftpMapper.handleError(params);
                                            }
                                        }
                                    }
                                }
                            }
                        	//每一条操作记录遍历之后都要删掉，抽取ftp出错的记录则调用过程处理
                            Map<String, Object> params = new HashMap<>();
                        	params.put("ajbs", ajbs);
                        	params.put("xh", urlInfo.getXH());
                			ftpMapper.deleteDC_FJGH_EXTOPER(params);
                        }
                        ftpClientPool.returnObject(ftpClient);
                	}
                }
            }
            long end = System.currentTimeMillis();
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            map.put(CodeUtil.RETURN_MSG, "耗时"+(end - start)/1000+"s");
            return map;
        }catch(Exception e){
        	e.printStackTrace();
            map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            map.put(CodeUtil.RETURN_MSG, e.toString());
            return map;
        }
	}

	/**
	 * 
	 * @function 删除省高院ftp数据 文件
	 * @author wenyuguang
	 * @creaetime 2018年1月9日 下午6:25:10
	 * @param filename
	 */
	private void deleteSgyFtpFile(String filename) {
		final FTPClient higherPeoplesCourtFtpClient = new FTPClient();
        int reply;
        try {
            /**
             * ftp服务器采用的UTF-8编码格式
             */
            higherPeoplesCourtFtpClient.setControlEncoding(UTF_8);
            higherPeoplesCourtFtpClient.connect(HIGHER_PEOPLES_COURT_FTP_HOST, HIGHER_PEOPLES_COURT_FTP_PORT);
            higherPeoplesCourtFtpClient.login(HIGHER_PEOPLES_COURT_FTP_USERNAME, HIGHER_PEOPLES_COURT_FTP_PASSWORD) ;
            /**
             * 被动模式
             */
            higherPeoplesCourtFtpClient.enterLocalPassiveMode();
            /**
             * 230 表示登录成功
             */
            reply = higherPeoplesCourtFtpClient.getReplyCode() ;
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.error("高院FTP连接失败:"+higherPeoplesCourtFtpClient.getReplyStrings());
                higherPeoplesCourtFtpClient.disconnect();
            }
            higherPeoplesCourtFtpClient.setBufferSize(HIGHER_PEOPLES_COURT_FTP_BUFFER_SIZE);
            higherPeoplesCourtFtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            higherPeoplesCourtFtpClient.changeWorkingDirectory(ROOT);
            higherPeoplesCourtFtpClient.deleteFile(new String(filename.getBytes("utf-8"), "iso-8859-1"));
            System.out.println(String.format("删除ftp文件：%s", filename));
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (higherPeoplesCourtFtpClient.isConnected()) {
                try {
                    higherPeoplesCourtFtpClient.logout();
                    higherPeoplesCourtFtpClient.disconnect();
                } catch (IOException ioe) {
                    logger.error("ftp dcbase download IO error:"+ioe.toString());
                }
            }
        }
	}
	
	/**
	 * 
	 * @function 删除ftp文件夹
	 * @author wenyuguang
	 * @creaetime 2018年1月19日 下午4:09:17
	 * @param filename
	 * @return
	 */
	private void deleteSgyFtpDir(String pathname) {
		FTPClient higherPeoplesCourtFtpClient = new FTPClient();
        int reply;
        try {
            /**
             * ftp服务器采用的UTF-8编码格式
             */
            higherPeoplesCourtFtpClient.setControlEncoding(UTF_8);
            higherPeoplesCourtFtpClient.connect(HIGHER_PEOPLES_COURT_FTP_HOST, HIGHER_PEOPLES_COURT_FTP_PORT);
            higherPeoplesCourtFtpClient.login(HIGHER_PEOPLES_COURT_FTP_USERNAME, HIGHER_PEOPLES_COURT_FTP_PASSWORD) ;
            higherPeoplesCourtFtpClient.setControlKeepAliveTimeout(60000);
            /**
             * 被动模式
             */
            higherPeoplesCourtFtpClient.enterLocalPassiveMode();
            /**
             * 230 表示登录成功
             */
            reply = higherPeoplesCourtFtpClient.getReplyCode() ;
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.error("高院FTP连接失败:"+higherPeoplesCourtFtpClient.getReplyStrings());
                higherPeoplesCourtFtpClient.disconnect();
            }
            higherPeoplesCourtFtpClient.setBufferSize(HIGHER_PEOPLES_COURT_FTP_BUFFER_SIZE);
            higherPeoplesCourtFtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            iterateDelete(higherPeoplesCourtFtpClient, pathname);
            System.out.println("*****>>>>>>>>delete complete："+pathname);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (higherPeoplesCourtFtpClient.isConnected()) {
                try {
                    higherPeoplesCourtFtpClient.logout();
                    higherPeoplesCourtFtpClient.disconnect();
                } catch (IOException ioe) {
                    logger.error("ftp dcbase download IO error:"+ioe.getMessage());
                }
            }
        }
	}

	private void iterateDelete(FTPClient ftpClient, String ftpPath) throws IOException {
		try {
			FTPFile[] files = ftpClient.listFiles(ftpPath);
			for (FTPFile f : files) {
				if(!f.getName().startsWith(".")) {
					String path = ftpPath + "/" + f.getName();
					if (f.isFile()) {
						// 是文件就删除文件
						ftpClient.deleteFile(new String(path.getBytes("utf-8"), "iso-8859-1"));
						System.out.println("delete file:"+path);
					} else if (f.isDirectory()) {
						iterateDelete(ftpClient, path);
					}
				}
			}
			// 每次删除文件夹以后就去查看该文件夹下面是否还有文件，没有就删除该空文件夹
			ftpClient.removeDirectory(ftpPath);
			System.out.println("delete dir:"+ftpPath);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	}

	@Value("${spring.application.name}")
	private String applicationName;
	
	@Override
	public Object handleTempUnstructureData(String ajbs, String ajlx, String fydm, String uuid) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("ajbs", ajbs);
			param.put("uuid", uuid);
			ftpMapper.handleTempUnstructureData(param);
			//flag 为0时正确返回
			int    flag = (int) param.get("v_cursor1");
			String msg  = (String) param.get("v_cursor2");
			if(flag == 1) {//警告
				logger.warn(msg);
				returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
				returnMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
				return returnMap;
			}else if(flag == -1) {
				returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				returnMap.put(CodeUtil.RETURN_MSG, msg);
				return returnMap;
			}else if(flag == 0) {
				returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
				returnMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
				return returnMap;
			}
			return null;
		}catch(Exception e) {
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			returnMap.put(CodeUtil.RETURN_MSG, e.getMessage());
			return returnMap;
		}
	}
	
	/**
     * 
     * @function 上传ftp文件到高院ftp服务器
     * @author wenyuguang
     * @creaetime 2017年5月17日 下午3:16:47
     * @param input
     * @param localPath
     * @param fileName
     * @return Boolean
     * @throws IllegalStateException
     * @throws IOException
     */
    private boolean uploadFtpFile(InputStream input, String localPath, String fileName){
        final FTPClient higherPeoplesCourtFtpClient = new FTPClient();
        boolean flag=false;
        int reply;
        try {
            /**
             * ftp服务器采用的UTF-8编码格式
             */
            higherPeoplesCourtFtpClient.setControlEncoding(UTF_8);
            higherPeoplesCourtFtpClient.connect(HIGHER_PEOPLES_COURT_FTP_HOST, HIGHER_PEOPLES_COURT_FTP_PORT);
            higherPeoplesCourtFtpClient.login(HIGHER_PEOPLES_COURT_FTP_USERNAME, HIGHER_PEOPLES_COURT_FTP_PASSWORD) ;
            /**
             * 被动模式
             */
            higherPeoplesCourtFtpClient.enterLocalPassiveMode();
            /**
             * 230 表示登录成功
             */
            reply = higherPeoplesCourtFtpClient.getReplyCode() ;
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.error("高院FTP连接失败:"+higherPeoplesCourtFtpClient.getReplyStrings());
                higherPeoplesCourtFtpClient.disconnect();
                return flag;
            }
            higherPeoplesCourtFtpClient.setBufferSize(HIGHER_PEOPLES_COURT_FTP_BUFFER_SIZE);
            higherPeoplesCourtFtpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            /**
             * 每一次都创建目录一次，避免因缺失目录导致文件上传失败
             */
            mkDirs(localPath, higherPeoplesCourtFtpClient);
            boolean changeTolocalPath = higherPeoplesCourtFtpClient.changeWorkingDirectory(localPath);
            if(changeTolocalPath){
                boolean uploadFile = higherPeoplesCourtFtpClient.storeFile(fileName, input);
                if(uploadFile){
                    flag=true;
                }else{
                    logger.error("upload ftp file to FTP_SERVER fail!");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (higherPeoplesCourtFtpClient.isConnected()) {
                try {
                    higherPeoplesCourtFtpClient.logout();
                    higherPeoplesCourtFtpClient.disconnect();
                } catch (IOException ioe) {
                    logger.error("ftp dcbase download IO error:"+ioe.getMessage());
                }
            }
        }
        return flag;
    }
    
    /**
     * 
     * @function 创建ftp对应后的路径
     * @author wenyuguang
     * @creaetime 2017年5月17日 下午3:06:43
     * @param path
     * @param ftpClient
     * @return
     */
    private boolean mkDirs(String path, FTPClient ftpClient){
        
        boolean result = true;
        try {
            /**
             * 设置编码格式，否则不能创建中文文件夹
             */
            ftpClient.setControlEncoding(UTF_8);
            /**
             * 切换到根目录
             */
            ftpClient.changeWorkingDirectory(ROOT);
            /**
             * 分割出目录
             */
            String dir = "";
            String[] strarray=path.split(SPLIT_STRING); 
            for (int i = 0; i < strarray.length; i++) {
                String temp = SPLIT_STRING+strarray[i];
                /**
                 * 分级目录
                 */
                dir = dir+temp;
                if (!ftpClient.changeWorkingDirectory(dir)) {
                    /**
                     * 创建目录
                     */
                    boolean mkDir = ftpClient.makeDirectory(dir);
                    if(mkDir){
                        /**
                         * 进入创建的目录
                         */
                        boolean cdDir = ftpClient.changeWorkingDirectory(dir);
                        if(!cdDir){
                            result=false;
                            break;
                        }
                    }else{
                        logger.error("创建单个文件夹失败："+dir+"****全路径为："+path);
                    }
                }else{
                    ftpClient.changeWorkingDirectory(dir);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

	@Override
	public Object handlePath(String ajbs, String ajlx, String fydm, String uuid) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("ajbs", ajbs);
			param.put("uuid", uuid);
			ftpMapper.handlePath(param);
			//flag 为0时正确返回
			int    flag = (int) param.get("v_cursor1");
			String msg  = (String) param.get("v_cursor2");
			if(flag == 1) {//警告
				returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
				returnMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
				return returnMap;
			}else if(flag == -1) {
				returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				returnMap.put(CodeUtil.RETURN_MSG, msg);
				return returnMap;
			}else if(flag == 0) {
				returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
				returnMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
				return returnMap;
			}
			return null;
		}catch(Exception e) {
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			returnMap.put(CodeUtil.RETURN_MSG, e.getMessage());
			return returnMap;
		}
	}
}
