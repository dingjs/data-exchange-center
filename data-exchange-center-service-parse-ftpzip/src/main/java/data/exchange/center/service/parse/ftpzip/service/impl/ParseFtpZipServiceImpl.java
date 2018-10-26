package data.exchange.center.service.parse.ftpzip.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.common.zip.ZipCompress;
import data.exchange.center.service.parse.ftpzip.domain.Bsxx;
import data.exchange.center.service.parse.ftpzip.domain.CallBackInfo;
import data.exchange.center.service.parse.ftpzip.domain.Dsrxx;
import data.exchange.center.service.parse.ftpzip.domain.FileInfo;
import data.exchange.center.service.parse.ftpzip.domain.FtpInfo;
import data.exchange.center.service.parse.ftpzip.domain.Gsxx;
import data.exchange.center.service.parse.ftpzip.domain.Jzxx;
import data.exchange.center.service.parse.ftpzip.mapper.ParseFtpZipMapper;
import data.exchange.center.service.parse.ftpzip.service.ParseFtpZipService;
import data.exchange.center.service.parse.ftpzip.util.FileUtil;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月19日 下午6:33:07</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class ParseFtpZipServiceImpl implements ParseFtpZipService {

	private static Logger logger =   LoggerFactory.getLogger(ParseFtpZipServiceImpl.class);

	@Value("${SGY_FTP}")
	private String SGY_FTP;
	
	@Value("${FY_JCY_FTP}")
	private String FY_JCY_FTP;
	
	private static String TEMP_LOCAL_PATH = "D://FTP_TEMP//";
	
	private String taskids;
	
	@Autowired
	private ParseFtpZipMapper parseFtpZipMapper;

	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> unzipAndParse(String taskId) throws Exception {
        
		Map<String, Object> param = new HashMap<String, Object>();
		long start = System.currentTimeMillis();
		this.taskids = taskId;
		FTPClient ftpClient    = new FTPClient();
		OutputStream outputStream = null;
		InputStream inputStream = null;
		InputStream zipInputStream = null;
		String zipPath = null;
		String zipName = null;//准备压缩的名称
    	try {
    		logger.info("开始执行解析zip程序，任务号："+taskId);
    		FileInfo fileInfo      = parseFtpZipMapper.getFileInfo(taskId);
    		FtpInfo  ftpInfo       = parseFtpZipMapper.getFtpInfoByFtpAlias(SGY_FTP);
    		ftpClient.setConnectTimeout(Integer.valueOf(ftpInfo.getnTimeout()));
    		ftpClient.connect(ftpInfo.getcIp(), Integer.valueOf(ftpInfo.getnPort()));
    		ftpClient.setControlEncoding(ftpInfo.getcEncoding());
			ftpClient.login(ftpInfo.getcUsername(), ftpInfo.getcPassword());
			ftpClient.setDataTimeout(0);
			ftpClient.setDefaultTimeout(3600000);
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
             * 切换到根目录
             */
            ftpClient.changeWorkingDirectory("/");
            
            String ftpFilePath = fileInfo.getFilePath();
            String path = ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/"));
            String fileName = ftpFilePath.substring(ftpFilePath.lastIndexOf("/")+1, ftpFilePath.length()); 
            ftpClient.changeWorkingDirectory(path);
            /**
             * 下载文件
             */
            if(!new File(TEMP_LOCAL_PATH).exists()) {
            	new File(TEMP_LOCAL_PATH).mkdir();
            }
            zipPath = TEMP_LOCAL_PATH + fileName;
            File localFile = new File(zipPath);
            if(!localFile.exists()) {
            	localFile.createNewFile();
            }
            outputStream = new FileOutputStream(localFile);
			/**
             * 从服务器检索命名文件并将其写入给定的OutputStream中
             */
            ftpClient.retrieveFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"), outputStream);
            if(outputStream != null) {
    			outputStream.close();
    		}
            outputStream = null;
            /**
             * 获取xml
             */
            String xmlStr = FileUtil.getXmlFileCopyFromZip(zipPath);
            /**
             * 交换标识_发送方代码_接收方代码_业务节点_文件名称_时间 
             * 例如：
             * 		JCYTOJY_34000001_34000002_110_文件名称_20160110123312 
             * 标识：
             * 		FYTOJCY法院发往检察院，
             * 		FYTOJY法院发往监狱，
             * 		JYTOFY监狱发往法院，
             * 		JYTOJCY监狱发往检察院，
             * 		JCYTOFY检察院发往法院，
             * 		JCYTOJY检察院发往监狱。
             */
            String[] fileNames = fileName.split("_");
            if("JCY".equalsIgnoreCase(fileInfo.getType())) {
            	zipName = "JCYTOFY_"+fileNames[0]+"_"+fileNames[1]+"_101_"+taskId+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            }
            /**
             * 解析xml
             */
            Map<String, Object> result = parseXml(xmlStr);
            //添加事务
//            ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
//            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//            definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//            definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
//            DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
//            TransactionStatus transactionStatus = (TransactionStatus) transactionManager.getTransaction(definition);
            /**
             * 入库前清数据，避免错误
             */
            parseFtpZipMapper.deleteData(taskId);
            /**
             * 入库
             */
            Map<String, Object> handle = storage(result);
            if(CodeUtil.RETURN_SUCCESS.equals(handle.get(CodeUtil.RETURN_CODE))) {
            	//创建存放卷宗的文件夹
                String fjPath = TEMP_LOCAL_PATH+zipName+"/fj";
                File file = new File(TEMP_LOCAL_PATH+zipName);
                if(!file.exists()) {
                	file.mkdirs();
                	new File(fjPath).mkdirs();
                }
                //判断zip包中是否有文书和卷宗，如果缺一则业务错误
                if(FileUtil.hasJzAndWs(zipPath)) {
                	/**
                     * 复制卷宗和文书到fj目录
                     */
                    FileUtil.getJzFileCopyFromZip(zipPath, fjPath);
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("taskId", taskId);
                    parseFtpZipMapper.transferTcyToFy(params);
                    List<Bsxx>  bsxx       = (List<Bsxx>)  params.get("v_cursor1");
                    List<Gsxx>  gsxx       = (List<Gsxx>)  params.get("v_cursor2");
                    List<Dsrxx> dsrxxList  = (List<Dsrxx>) params.get("v_cursor3");
                    List<Jzxx>  jzxxList   = (List<Jzxx>)  params.get("v_cursor4");
                    
                    FileOutputStream fileOutputStream = null;
                    ByteArrayOutputStream outStream = makeXml(bsxx.get(0), gsxx.get(0), dsrxxList, jzxxList);
                    File xmlFile = new File(TEMP_LOCAL_PATH+zipName+"//"+zipName+".xml");
                    try {
                        fileOutputStream = new FileOutputStream(xmlFile);
                        fileOutputStream.write(outStream.toByteArray());
                        fileOutputStream.flush();
                    }catch(Exception e) {
                    	logger.error("生成xml出错："+e.getMessage());
                    	e.printStackTrace();
                    	param.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                    	param.put(CodeUtil.RETURN_MSG, e.getMessage());
                    	return param;
                    }finally {
                    	if(!StringUtils.isEmpty(fileOutputStream)) {
                    		fileOutputStream.close();
                    	}
                    }
                    
                    new ZipCompress(TEMP_LOCAL_PATH+zipName+".zip", TEMP_LOCAL_PATH+zipName).zip();
                    TimeUnit.MILLISECONDS.sleep(100);
                    
                    /**
                     * zip包上传到指定ftp
                     */
                    FTPClient tdhftpClient    = new FTPClient();
                	try {
                		tdhftpClient.connect("150.0.2.153", 21);
                		tdhftpClient.setConnectTimeout(60000);
                		tdhftpClient.setControlEncoding("UTF-8");
                		tdhftpClient.login("fjpt", "fjjhftp");
            			/**
            			 * 被动模式
            			 */
                		tdhftpClient.enterLocalPassiveMode();
            			/**
                         * 230 表示登录成功
                         */
                        int reply1 = tdhftpClient.getReplyCode() ;
                        if (!FTPReply.isPositiveCompletion(reply1)) {
                            logger.error("150.0.2.153"+"ftp连接错误： "+ tdhftpClient.getReplyStrings().toString());
                            tdhftpClient.disconnect();
                        }
                        tdhftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                        tdhftpClient.changeWorkingDirectory("/");
                        tdhftpClient.changeWorkingDirectory("/fjjhjcy");
                        /**
                         * 防止通达海删除目录后导致错误
                         */
                        tdhftpClient.makeDirectory("send");
                        tdhftpClient.makeDirectory("recv");
                        tdhftpClient.makeDirectory("control");
                        tdhftpClient.makeDirectory("err");
                        tdhftpClient.changeWorkingDirectory("control");
                        
                        inputStream = new FileInputStream(xmlFile); 
                        tdhftpClient.storeFile(zipName+".xml", inputStream);
                        tdhftpClient.changeWorkingDirectory("/");
                        tdhftpClient.changeWorkingDirectory("/fjjhjcy/send");
                        zipInputStream = new FileInputStream(new File(TEMP_LOCAL_PATH+zipName+".zip")); 
                        boolean rst = tdhftpClient.storeFile(zipName+".zip", zipInputStream);
                        logger.info(taskId + "任务完全，结果状态：" + rst + ",通达海FTP反馈信息：" + tdhftpClient.getReplyString());
                	}catch(Exception e) {
                		e.printStackTrace();
                		param.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                    	param.put(CodeUtil.RETURN_MSG, e.getMessage());
                    	return param;
                	}finally {
                		if(inputStream != null ) {
                			inputStream.close();
                		}
                		if(zipInputStream != null ) {
                			zipInputStream.close();
                		}
                		tdhftpClient.logout();
                		tdhftpClient.disconnect();
                	}
                }else {
                	param.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                	param.put(CodeUtil.RETURN_MSG,  "该案件缺少必要的文书或卷宗文件信息");
                	System.err.println("该案件缺少必要的文书或卷宗文件信息");
                	return param;
                }

            }else {
            	logger.error(taskId+"xml解析入库出错："+handle.get(CodeUtil.RETURN_MSG));
            	param.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            	param.put(CodeUtil.RETURN_MSG, "JZJBXX的部分JZMLWJ节点缺失");
            	return param;
            }
    	}catch(Exception e) {
    		e.printStackTrace();
    		param.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
        	param.put(CodeUtil.RETURN_MSG, e.toString());
        	return param;
    	}finally {
    		try {
    			if(inputStream != null ) {
        			inputStream.close();
        		}
        		if(ftpClient.isConnected()) {
        			ftpClient.logout();
            		ftpClient.disconnect();
        		}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
    		
    		try {
    			/**
                 * 清除下载的zip包
                 */
                new File(zipPath).delete();
                /**
                 * 清除生成的zip包
                 */
                new File(TEMP_LOCAL_PATH+zipName+".zip").delete();
                
                /**
                 * 清除本地缓存文件记录  需要提前关闭流
                 */
                File tempFile = new File(TEMP_LOCAL_PATH+zipName);
                File[] files = tempFile.listFiles();
                if(!StringUtils.isEmpty(files)) {
                	if(files.length > 0) {
                    	deleteDir(new File(TEMP_LOCAL_PATH+zipName));
                    }else {
                    	tempFile.delete();
                    }
                }
    		}catch(Exception e) {
    			
    		}
    	}
    	long end = System.currentTimeMillis();
    	System.err.println("已发送给通达海，耗时："+(end -start)+"ms");
    	
    	param.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
    	param.put(CodeUtil.RETURN_MSG, "耗时："+(end -start)+"ms");
    	return param;
	}


	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.service.parse.ftpzip.service.ParseFtpZipService#parseXml(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> parseXml(String xmlStr) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Document document = null;
		try{

			/**
			 * Map存放解析后XML对应表信息
			 */
			Map<String, Object> tableMap = new HashMap<String, Object>();
			List<Object> headList = new ArrayList<Object>();
			List<Object> gaList = new ArrayList<Object>();
			List<Object> jcyList = new ArrayList<Object>();
			List<Object> zfList = new ArrayList<Object>();
			List<Object> fyList = new ArrayList<Object>();
			List<Object> wsList = new ArrayList<Object>();
			
			List<Object> jzjbxxList = new ArrayList<Object>();
			List<Object> jzmljList = new ArrayList<Object>();
			List<Object> jzmlwjList = new ArrayList<Object>();
			List<Object> jzwjList = new ArrayList<Object>();
			List<Object> qzcsList = new ArrayList<Object>();
			List<Object> jbxxList = new ArrayList<Object>();
			List<Object> saxxList = new ArrayList<Object>();
			/**
			 * <ROOT>
	  			<HEAD></HEAD>
	  			<JZJBXX></JZJBXX>
	  			<WSS></WSS>
	  			<MSG></MSG>
			   </ROOT>
			 */
			document = DocumentHelper.parseText(xmlStr);
			Element rootElements = document.getRootElement();
			/**
			 * 对应的表名列表  1级
			 */
			List<Element> tableElements = rootElements.elements();
			for (Element element : tableElements) {
				
				/**
				 * 表名  二级
				 */
				List<Element> tableElement = element.elements();
				/**
				 * 单独处理JZJBXX卷宗基本信息
				 */
				if("JZJBXX".equalsIgnoreCase(element.getName())) {
					Map<String, Object> jzjbxxMap = new HashMap<String, Object>();
					/**
					 * 卷宗编号（主键）
					 */
					String JZBH = element.attributeValue("JZBH");
					/**
					 * 案件名称
					 */
					String AJMC = element.attributeValue("AJMC");
					/**
					 * 部门受案号（XML中暂时未提供）
					 */
					String BMSAH = element.attributeValue("BMSAH")==null?"":element.attributeValue("BMSAH");
					
					/**
					 * JZMLJ 卷信息
					 */
					for (Element elements : tableElement) {
						Map<String, Object> jzmljMap = new HashMap<String, Object>();
						/**
						 * 卷宗编号
						 */
						String J_JZBH = elements.attributeValue("JZBH");
						/**
						 * 目录编号
						 */
						String J_MLBH = elements.attributeValue("MLBH");
						/**
						 * 父目录编号
						 */
						String J_FMLBH = elements.attributeValue("FMLBH");
						/**
						 * 目录显示名称
						 */
						String J_MLXSMC = elements.attributeValue("MLXSMC");
						/**
						 * 目录信息
						 */
						String J_MLXX = elements.attributeValue("MLXX");
						/**
						 * 目录顺序号
						 */
						String J_MLSXH = elements.attributeValue("MLSXH");
						/**
						 * 目录类型
						 * 1：卷
						 * 3：文件
						 */
						String J_MLLX = elements.attributeValue("MLLX");
						/**
						 * 是否涉密（Y,N）
						 */
						String J_SFSM = elements.attributeValue("SFSM");
						
						
						List<Element> elementss = elements.elements();
						/**
						 * 遍历获取 JZMLWJ  目录
						 */
						for(Element el:elementss) {
							Map<String, Object> jzmlwjMap = new HashMap<String, Object>();
							String ML_JZBH	= el.attributeValue("JZBH");
							String ML_MLBH	= el.attributeValue("MLBH");
							String ML_FMLBH = el.attributeValue("FMLBH");
							String ML_MLXSMC = el.attributeValue("MLXSMC");
							String ML_MLXX = el.attributeValue("MLXX");
							String ML_MLSXH = el.attributeValue("MLSXH");
							String ML_MLLX = el.attributeValue("MLLX");
							String ML_SFSM	= el.attributeValue("SFSM");
							
							List<Element> elementsss = el.elements();
							
							/**
							 * 遍历文件（JZWJ）
							 */
							for(Element eleme : elementsss) {
								Map<String, Object> jzwjMap = new HashMap<String, Object>();
								/**
								 * 卷宗编号
								 */
								String WJ_JZBH	= eleme.attributeValue("JZBH");
								/**
								 * 目录编号
								 */
								String WJ_MLBH	= eleme.attributeValue("MLBH");
								/**
								 * 文件序号（主键）
								 */
								String WJ_WJXH = eleme.attributeValue("WJXH");
								/**
								 * 文件相对路径
								 */
								String WJ_WJLJ = eleme.attributeValue("WJLJ");
								/**
								 * 文件名称（物理文件名不含后缀）
								 */
								String WJ_WJMC = eleme.attributeValue("WJMC");
								/**
								 * 文件显示名称
								 */
								String WJ_WJXSMC = eleme.attributeValue("WJXSMC");
								/**
								 * 文件后缀
								 */
								String WJ_WJHZ = eleme.attributeValue("WJHZ");
								/**
								 * 文件顺序号
								 */
								String WJ_WJSXH = eleme.attributeValue("WJSXH");
								/**
								 * 文件大小（size,例：1.2M）
								 */
								String WJ_WJDX = eleme.attributeValue("WJDX");
								/**
								 * 文件类型：封面1，目录2，连续页3，漏码4，重码5，跳码6，备考表7，封底8
								 */
								String WJ_WJLX	= eleme.attributeValue("WJLX");
								/**
								 * 文件跳码次数
								 */
								String WJ_WJTM	= eleme.attributeValue("WJTM");
								/**
								 * 文件页码（主要用于记录跳码）
								 */
								String WJ_WJYM	= eleme.attributeValue("WJYM");
								
								jzwjMap.put("JZBH", WJ_JZBH);
								jzwjMap.put("MLBH", WJ_MLBH);
								jzwjMap.put("WJXH", WJ_WJXH);
								jzwjMap.put("WJLJ", WJ_WJLJ);
								jzwjMap.put("WJMC", WJ_WJMC);
								jzwjMap.put("WJXSMC", WJ_WJXSMC);
								jzwjMap.put("WJHZ", WJ_WJHZ);
								jzwjMap.put("WJSXH", WJ_WJSXH);
								jzwjMap.put("WJDX", WJ_WJDX);
								jzwjMap.put("WJLX", WJ_WJLX);
								jzwjMap.put("WJTM", WJ_WJTM);
								jzwjMap.put("WJYM", WJ_WJYM);
								jzwjList.add(jzwjMap);
							}
							jzmlwjMap.put("JZBH",   ML_JZBH);
							jzmlwjMap.put("MLBH",   ML_MLBH);
							jzmlwjMap.put("FMLBH",  ML_FMLBH);
							jzmlwjMap.put("MLXSMC", ML_MLXSMC);
							jzmlwjMap.put("MLXX",   ML_MLXX);
							jzmlwjMap.put("MLSXH",  ML_MLSXH);
							jzmlwjMap.put("MLLX",   ML_MLLX);
							jzmlwjMap.put("SFSM",   ML_SFSM);
							jzmlwjList.add(jzmlwjMap);
						}
						jzmljMap.put("JZBH",   J_JZBH);
						jzmljMap.put("MLBH",   J_MLBH);
						jzmljMap.put("FMLBH",  J_FMLBH);
						jzmljMap.put("MLXSMC", J_MLXSMC);
						jzmljMap.put("MLXX",   J_MLXX);
						jzmljMap.put("MLSXH",  J_MLSXH);
						jzmljMap.put("MLLX",   J_MLLX);
						jzmljMap.put("SFSM",   J_SFSM);
						jzmljList.add(jzmljMap);
					}
					jzjbxxMap.put("JZBH",  JZBH);
					jzjbxxMap.put("AJMC",  AJMC);
					jzjbxxMap.put("BMSAH", BMSAH);
					jzjbxxList.add(jzjbxxMap);
				}else if("XYRS".equalsIgnoreCase(element.getName())){
					for(Element elements : tableElement) {
						/**
						 * XYR
						 */
						List<Element> el = elements.elements();
						for(Element elementss : el) {
							List<Element> elem = elementss.elements();
							if("JBXX".equalsIgnoreCase(elementss.getName())) {
								Map<String, Object> jbxxMap = new HashMap<String, Object>();
								for(Element e: elem) {
									jbxxMap.put(e.getName(), e.getText());
								}
								jbxxList.add(jbxxMap);
							}else if("SAXX".equalsIgnoreCase(elementss.getName())) {
								Map<String, Object> saxxMap = new HashMap<String, Object>();
								for(Element e: elem) {
									saxxMap.put(e.getName(), e.getText());
								}
								saxxList.add(saxxMap);
							}else if("QZCSS".equalsIgnoreCase(elementss.getName())) {
								List<Element> qzcsElements = elementss.elements();
								for(Element qzcsElement : qzcsElements) {
									Map<String, Object> qzcsMap = new HashMap<String, Object>();
									List<Element> qzcsElemen = qzcsElement.elements();
									for(Element qzcsEleme : qzcsElemen) {
										qzcsMap.put(qzcsEleme.getName(), qzcsEleme.getText());
									}
									qzcsList.add(qzcsMap);
								}
							}
						}
					}
				}else {
					Map<String, Object> headMap = new HashMap<String, Object>();
					Map<String, Object> gaMap = new HashMap<String, Object>();
					Map<String, Object> jcyMap = new HashMap<String, Object>();
					Map<String, Object> zfMap = new HashMap<String, Object>();
					Map<String, Object> fyMap = new HashMap<String, Object>();
					for (Element elements : tableElement) {
						Map<String, Object> wsMap = new HashMap<String, Object>();
						if("GA".equalsIgnoreCase(elements.getName())){
							List<Element> ele = elements.elements();
							for(Element e : ele) {
								gaMap.put(e.getName(), e.getText());
							}
						} else if("ZF".equalsIgnoreCase(elements.getName())){
							List<Element> ele = elements.elements();
							for(Element e : ele) {
								zfMap.put(e.getName(), e.getText());
							}
						} else if("JCY".equalsIgnoreCase(elements.getName())){
							List<Element> ele = elements.elements();
							for(Element e : ele) {
								jcyMap.put(e.getName(), e.getText());
							}
						}  else if("FY".equalsIgnoreCase(elements.getName())){
							List<Element> ele = elements.elements();
							for(Element e : ele) {
								fyMap.put(e.getName(), e.getText());
							}
						} else if("WS".equalsIgnoreCase(elements.getName())){
							List<Element> wsElements = elements.elements();
							for(Element wsElement : wsElements) {
								wsMap.put(wsElement.getName(), wsElement.getText());
							}
						}else{
							/**
							 * HEAD信息 除开GA, JCY, ZF
							 */
							headMap.put(elements.getName(), elements.getText());
						}
						if(!wsMap.isEmpty()) {
							wsList.add(wsMap);
						}
					}
					if(!headMap.isEmpty()) {
						headList.add(headMap);
					}
					if(!gaMap.isEmpty()) {
						gaList.add(gaMap);
					}
					if(!jcyMap.isEmpty()) {
						jcyList.add(jcyMap);
					}
					if(!zfMap.isEmpty()) {
						zfList.add(zfMap);
					}
					if(!fyMap.isEmpty()) {
						fyList.add(fyMap);
					}
				}
			}
			tableMap.put("head",    headList);
			tableMap.put("gahead",  gaList);
			tableMap.put("jcyhead", jcyList);
			tableMap.put("zfhead",  zfList);
			tableMap.put("fyhead",  fyList);
			tableMap.put("ws",      wsList);
			tableMap.put("jzjbxx",  jzjbxxList);
			tableMap.put("jzmlj",   jzmljList);
			tableMap.put("jzmlwj",  jzmlwjList);
			tableMap.put("jzwj",    jzwjList);
			tableMap.put("qzcs",    qzcsList);
			tableMap.put("jbxx",    jbxxList);
			tableMap.put("saxx",    saxxList);
			
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			returnMap.put(CodeUtil.RETURN_MSG,  tableMap);
			return returnMap;
		}catch(Exception e) {
			logger.error(e.getMessage().toString());
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			returnMap.put(CodeUtil.RETURN_MSG, e.getMessage());
			return returnMap;
		}finally {
			document.clearContent();
		}
	}
	
    /**
     * 
     * @function 存库
     * @author wenyuguang
     * @creaetime 2017年10月23日 下午1:16:57
     * @param dataMap  数据map
     * @param JHBH     交换编号
     * @return
     */
	@SuppressWarnings("unchecked")
	private Map<String, Object> storage(Map<String, Object> xmlMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			Map<String, Object> dataMap = (Map<String, Object>) xmlMap.get(CodeUtil.RETURN_MSG);
			List<Object> headList       = (List<Object>) dataMap.get("head");
			save(headList,   "XTBAPT.INFY_HEAD");
			List<Object> gaList     = (List<Object>) dataMap.get("gahead");
			save(gaList,     "XTBAPT.INFY_HEAD_GA");
			List<Object> jcyList    = (List<Object>) dataMap.get("jcyhead");
			save(jcyList,    "XTBAPT.INFY_HEAD_JCY");
			List<Object> zfList     = (List<Object>) dataMap.get("zfhead");
			save(zfList,     "XTBAPT.INFY_HEAD_ZF");
			List<Object> fyList     = (List<Object>) dataMap.get("fyhead");
			save(fyList,     "XTBAPT.INFY_HEAD_FY");
			List<Object> wsList     = (List<Object>) dataMap.get("ws");
			save(wsList,     "XTBAPT.INFY_WS");
			List<Object> qzcsList   = (List<Object>) dataMap.get("qzcs");
			save(qzcsList,   "XTBAPT.INFY_XYR_QZCS");
			List<Object> jbxxList   = (List<Object>) dataMap.get("jbxx");
			save(jbxxList,   "XTBAPT.INFY_XYR_JBXX");
			List<Object> saxxList   = (List<Object>) dataMap.get("saxx");
			save(saxxList,   "XTBAPT.INFY_XYR_SAXX");
			List<Object> jzjbxxList = (List<Object>) dataMap.get("jzjbxx");
			save(jzjbxxList, "XTBAPT.INFY_JZJBXX");
			List<Object> jzmljList  = (List<Object>) dataMap.get("jzmlj");
			save(jzmljList,  "XTBAPT.INFY_JZJBXX_JZMLJ");
			List<Object> jzmlwjList = (List<Object>) dataMap.get("jzmlwj");
			save(jzmlwjList, "XTBAPT.INFY_JZJBXX_JZMLJ_JZMLWJ");
			List<Object> jzwjList   = (List<Object>) dataMap.get("jzwj");
			save(jzwjList,   "XTBAPT.INFY_JZJBXX_JZMLJ_JZMLWJ_JZWJ");
			
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			returnMap.put(CodeUtil.RETURN_MSG,  CodeUtil.RETURN_SUCCESS);
			return returnMap;
		}catch(Exception e) {
			logger.error(e.getMessage());
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			returnMap.put(CodeUtil.RETURN_MSG, e.getMessage().toString());
			return returnMap;
		}
	}
	
	/**
	 * 
	 * @function 入库操作
	 * @author wenyuguang
	 * @creaetime 2017年10月23日 下午2:46:03
	 * @param list 对应表list
	 * @param tableName 对应表名称
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void save(List<Object> list, String tableName) throws Exception{
		for(int i =0; i<list.size(); i++){
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			StringBuffer tableColKey = new StringBuffer();
			StringBuffer tableColValue = new StringBuffer();
			Map<String, Object> params = new HashMap<>();
			/**
			 * 主键信息
			 */
			params.put("JHBH", taskids);
			/**
			 * 插入时间字段
			 */
            Date nowTime = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ").parse(new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ").format(System.currentTimeMillis()));
			params.put("REG_TIME", nowTime);
			int k = 0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if(k == 0) {
					if("XTBAPT.INFY_HEAD".equalsIgnoreCase(tableName)) {
						tableColKey.append("REG_TIME, "+entry.getKey().toUpperCase());
						tableColValue.append("#{REG_TIME}, #{"+entry.getKey()+"}");
					}else {
						tableColKey.append("REG_TIME, JHBH, "+entry.getKey().toUpperCase());
						tableColValue.append("#{REG_TIME}, #{JHBH}, #{"+entry.getKey()+"}");
					}
				}else {
					tableColKey.append(", "+entry.getKey());
					tableColValue.append(", "+"#{"+entry.getKey()+"}");
				}
				/**
				 * 字符串转时间
				 */
				Date date = null;
				if("TIME".equalsIgnoreCase(entry.getKey())||"CJSJ".equalsIgnoreCase(entry.getKey())) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   	date = format.parse(entry.getValue().toString());
                    params.put(entry.getKey().toUpperCase(), date);
				}else {
					params.put(entry.getKey().toUpperCase(), entry.getValue());
				}
				k ++;
			}
			String sql = "insert into "+tableName+"("+tableColKey+")"
					+"values("+tableColValue+")";
			params.put("insertSql", sql);
			parseFtpZipMapper.saveXml(params);
		}
	}
	
	/**
	 * 
	 * @function 递归删除文件夹内所有文件
	 * @author wenyuguang
	 * @creaetime 2017年10月24日 上午10:17:39
	 * @param dir
	 * @return
	 */
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
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月24日 下午8:12:28
	 * @param dsr
	 * @param ywsj
	 * @param ptxg
	 * @param wsftpPathInfoList 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws IOException 
	 */
	private ByteArrayOutputStream makeXml(Bsxx bsxx, Gsxx gsxx, List<Dsrxx> dsrxxList, List<Jzxx> JzxxList) throws ParserConfigurationException, TransformerException, IOException {
		Element root = DocumentHelper.createElement("ROOT"); 
		Document document = DocumentHelper.createDocument(root); 
		Element record = root.addElement("RECORD");
		//标识信息
		Element bsxxEl = record.addElement("BSXX");
		bsxxEl.addElement("CaseNo").addText(bsxx.getCASENO()==null?"":bsxx.getCASENO());
		bsxxEl.addElement("FSDW").addText(bsxx.getFSDW()==null?"":bsxx.getFSDW());
		bsxxEl.addElement("FSDWMC").addText(bsxx.getFSDWMC()==null?"":bsxx.getFSDWMC());
		bsxxEl.addElement("JSDW").addText(bsxx.getJSDW()==null?"":bsxx.getJSDW());
		bsxxEl.addElement("JSDWMC").addText(bsxx.getJSDWMC()==null?"":bsxx.getJSDWMC());
		bsxxEl.addElement("LEIX").addText(bsxx.getLEIX()==null?"":bsxx.getLEIX());
		bsxxEl.addElement("JHJD").addText("101");
		Element gsxxEl = record.addElement("GSXX");
		gsxxEl.addElement("GSZZM").addText(gsxx.getGSZZM()==null?"":gsxx.getGSZZM());
		gsxxEl.addElement("DSRC").addText(gsxx.getDSRC()==null?"":gsxx.getDSRC());
		gsxxEl.addElement("GSSBH").addText(gsxx.getGSSBH()==null?"":gsxx.getGSSBH());
		gsxxEl.addElement("GSJG").addText(gsxx.getGSJG()==null?"":gsxx.getGSJG());
		/**
		 * 2017年11月9日16:40:44 公诉人
		 */
//		gsxxEl.addElement("GSR").addText("林开阳");
//		gsxxEl.addElement("FZSSYZJ").addText("乐山市人民检察院乐检诉刑诉[2017]30号诉被告人王新兵涉嫌故意伤害罪一案。");
		Element dsrxxEl = record.addElement("DSRXX");
		for (int i = 0; i < dsrxxList.size(); i++) {
			Dsrxx dsrxx = dsrxxList.get(i);
		 	Element dsrxxRc = dsrxxEl.addElement("RECORD");
		 	dsrxxRc.addElement("XM").addText(dsrxx.getXM()==null?"":dsrxx.getXM());
		 	dsrxxRc.addElement("XB").addText(dsrxx.getXB()==null?"":dsrxx.getXB());
		 	dsrxxRc.addElement("DZ").addText(dsrxx.getDZ()==null?"":dsrxx.getDZ());
		 	dsrxxRc.addElement("MZ").addText(dsrxx.getMZ()==null?"":dsrxx.getMZ());
		 	dsrxxRc.addElement("SF").addText(dsrxx.getSF()==null?"":dsrxx.getSF());
		 	dsrxxRc.addElement("ZY").addText(dsrxx.getZY()==null?"":dsrxx.getZY());
		 	dsrxxRc.addElement("GJ").addText(dsrxx.getGJ()==null?"":dsrxx.getGJ());
		 	dsrxxRc.addElement("SFZHM").addText(dsrxx.getSFZHM()==null?"":dsrxx.getSFZHM());
		 	dsrxxRc.addElement("FZNL").addText(dsrxx.getFZNL()==null?"":dsrxx.getFZNL());
		 	dsrxxRc.addElement("ZKZM").addText(dsrxx.getZKZM()==null?"":dsrxx.getZKZM());
		 
		}
		Element jzxxEl = record.addElement("JZXX");
		for (int i = 0; i < JzxxList.size(); i++) {
			Jzxx jzxx =  JzxxList.get(i);
		 	Element jzxxElRc = jzxxEl.addElement("RECORD");
		 	jzxxElRc.addElement("LX").addText(jzxx.getLX()==null?"":jzxx.getLX());
		 	jzxxElRc.addElement("WSXH").addText(jzxx.getWSXH()==null?"":jzxx.getWSXH());
		 	jzxxElRc.addElement("CLBT").addText(jzxx.getCLBT()==null?"":jzxx.getCLBT());
		 	jzxxElRc.addElement("PATH").addText(jzxx.getPATH()==null?"":jzxx.getPATH());
		 	jzxxElRc.addElement("WSMC").addText(jzxx.getWSMC()==null?"":jzxx.getWSMC());
		 	jzxxElRc.addElement("WJGS").addText(jzxx.getWJGS()==null?"":jzxx.getWJGS());
		}
		//把生成的xml文档存放在硬盘上  true代表是否换行  
		OutputFormat format = new OutputFormat("    ",true);
		format.setEncoding("UTF-8");//设置编码格式
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XMLWriter xmlWriter = new XMLWriter(outputStream,format);
		xmlWriter.write(document);
		xmlWriter.close();
		return outputStream;
	}


	@Override
	public Map<String, Object> callBack(String xtptbh, String lcjdbh, String lcslbh, 
			String rwh, String jsdwbm, String jsdwlx,
			String jsdwmc, String fsdwlx, String fsdwbm, 
			String fsdwmc, String jgzt, String ztms, String fhsj, 
			String ywlcbm, String jdbm) throws Exception {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> returnMap = new HashMap<>();
		
		try {
			params.put("xtptbh", xtptbh);
			params.put("lcjdbh", lcjdbh);
			params.put("lcslbh", lcslbh);
			params.put("rwh",    rwh);
			params.put("jsdwbm", jsdwbm);
			params.put("jsdwlx", jsdwlx);
			params.put("jsdwmc", jsdwmc);
			params.put("fsdwlx", fsdwlx);
			params.put("fsdwbm", fsdwbm);
			params.put("fsdwmc", fsdwmc);
			params.put("jgzt",   jgzt);
			params.put("ztms",   ztms);
			params.put("fhsj",   fhsj.replaceAll("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$1-$2-$3 $4:$5:$6"));
			params.put("ywlcbm", ywlcbm);
			params.put("jdbm",   jdbm);
			
			parseFtpZipMapper.callBack(params);
			Map<String, Object> handle = handleCallBack(rwh);
			if(handle.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)) {
				returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
	            returnMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
	            return returnMap;
			}else {
				returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
	            returnMap.put(CodeUtil.RETURN_MSG, handle.get(CodeUtil.RETURN_MSG));
	            return returnMap;
			}
			
		}catch(Exception e) {
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            returnMap.put(CodeUtil.RETURN_MSG, e.getMessage());
            return returnMap;
		}
	}
	
	@Override
	public Map<String, Object> handleCallBack(String rwh) throws Exception{
		Map<String ,Object> returnMap = new HashMap<>();
		
		FTPClient ftpClient    = new FTPClient();
		ByteArrayOutputStream outputStream = null;
		InputStream inputStream = null;
		InputStream inputMd5 = null;
    	try {
    		CallBackInfo callBackInfo = parseFtpZipMapper.getCallBackInfo(rwh);
    		
    		Element root = DocumentHelper.createElement("MESSAGE"); 
    		Document document = DocumentHelper.createDocument(root);
    		
    		root.addElement("XTPTBH").addText(callBackInfo.getXtptbh()==null?"":callBackInfo.getXtptbh());
    		root.addElement("LCJDBH").addText(callBackInfo.getLcjdbh()==null?"":callBackInfo.getLcjdbh());
    		root.addElement("LCSLBH").addText(callBackInfo.getLcslbh()==null?"":callBackInfo.getLcslbh());
    		root.addElement("RWH").addText(callBackInfo.getRwh()==null?"":callBackInfo.getRwh());
    		root.addElement("JSDWBM").addText(callBackInfo.getJsdwbm()==null?"":callBackInfo.getJsdwbm());
    		root.addElement("JSDWLX").addText(callBackInfo.getJsdwlx()==null?"":callBackInfo.getJsdwlx());
    		root.addElement("JSDWMC").addText(callBackInfo.getJsdwmc()==null?"":callBackInfo.getJsdwmc());
    		root.addElement("FSDWLX").addText(callBackInfo.getFsdwlx()==null?"":callBackInfo.getFsdwlx());
    		root.addElement("FSDWBM").addText(callBackInfo.getFsdwbm()==null?"":callBackInfo.getFsdwbm());
    		root.addElement("FSDWMC").addText(callBackInfo.getFsdwmc()==null?"":callBackInfo.getFsdwmc());
    		root.addElement("JGZT").addText(callBackInfo.getJgzt()==null?"":callBackInfo.getJgzt());
    		root.addElement("ZTMS").addText(callBackInfo.getZtms()==null?"":callBackInfo.getZtms());
    		root.addElement("FHSJ").addText(callBackInfo.getFhsj()==null?"":callBackInfo.getFhsj());
    		
    		//把生成的xml文档存放在硬盘上  true代表是否换行  
    		OutputFormat format = new OutputFormat("	",true);
    		format.setEncoding("UTF-8");//设置编码格式
    		outputStream = new ByteArrayOutputStream();
    		XMLWriter xmlWriter = new XMLWriter(outputStream,format);
    		xmlWriter.write(document);
    		xmlWriter.close();
    		inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    		inputMd5 = new ByteArrayInputStream(outputStream.toByteArray());
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
            if(md5.length()!=32) {
            	int val = 32 - md5.length();
            	String prex = "";
            	for(int i=0; i<val; i++) {
            		prex += "0";
            	}
            	md5 = prex + md5;
            }
            System.out.println("文件md5值：" + md5);
    		
    		
    		FtpInfo  ftpInfo = parseFtpZipMapper.getFtpInfoByFtpAlias(FY_JCY_FTP);
    		ftpClient.setConnectTimeout(Integer.valueOf(ftpInfo.getnTimeout()));
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
            ftpClient.changeWorkingDirectory("/");
            
            //反馈XML的名称由业务流程编码、节点编码、发送单位编码、接收单位、流程实例编码、MD5校验码组成
            String xmlName = callBackInfo.getYwlcbm()
            		+"_"+callBackInfo.getJdbm()
            		+"_"+callBackInfo.getFsdwbm()
            		+"_"+callBackInfo.getJsdwbm()
            		+"_"+callBackInfo.getLcslbh()
            		+"_"+(md5).toUpperCase()+".xml";
            ftpClient.storeFile(xmlName, inputStream);
            
            File dir = new File("d:\\back_to_jcy_xml");
            if(!dir.exists()) {
            	dir.mkdirs();
            }
            
            File file = new File("d:\\back_to_jcy_xml\\"+xmlName);
            FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(outputStream.toByteArray());
            fileOut.flush();
            fileOut.close();
            		
            
            returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            returnMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
            return returnMap;
    	}catch(Exception e) {
    		e.printStackTrace();
    		returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            returnMap.put(CodeUtil.RETURN_MSG, e.getMessage());
            return returnMap;
    	}finally {
    		if(ftpClient.isConnected()) {
    			ftpClient.logout();
    			ftpClient.disconnect();
    		}
    		if(outputStream != null) {
    			outputStream.close();
    		}
    		if(inputStream != null) {
    			inputStream.close();
    		}
    		if(inputMd5 != null) {
    			inputMd5.close();
    		}
    	}
	}

	@Override
	public Map<String, Object> saveErrMsg(String taskId) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			parseFtpZipMapper.saveErrMsg(taskId);
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			retMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
		}catch(Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
		}
		return retMap;
	}
}