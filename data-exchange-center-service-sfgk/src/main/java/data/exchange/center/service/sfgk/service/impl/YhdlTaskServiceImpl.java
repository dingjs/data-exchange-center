package data.exchange.center.service.sfgk.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.sfgk.domain.Ktgkxx;
import data.exchange.center.service.sfgk.service.SfgkService;
import data.exchange.center.service.sfgk.service.TaskService;
import data.exchange.center.service.sfgk.util.FileInfoUtil;
import data.exchange.center.service.sfgk.util.FileZipCompressUtil;
import data.exchange.center.service.sfgk.util.ParseUtil;
import data.exchange.center.service.sfgk.util.XsdName;
import data.exchange.center.service.sfgk.util.validate.Xmlvalidate;

@Service("yhdlTaskService")
public class YhdlTaskServiceImpl implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(YhdlTaskServiceImpl.class);
	
	@Autowired
	private SfgkService sfgkService;

	@Override
	public void startTask() throws Exception {
		logger.info("开始生产 用户登录 xml");
		String root = "D://SFGK//";
		String dir  = "yhdl//";
		Path path = Paths.get(root + dir);
		try {
	        Files.createDirectory(path);
	    } catch(FileAlreadyExistsException e){
	        // the directory already exists.
	    } catch (IOException e) {
	        //something else went wrong
	        e.printStackTrace();
	    }
		List<File> fileList = new ArrayList<>();
		List<File> deleteList = new ArrayList<>();
		ByteArrayOutputStream outStreamXml = makeXml(null);
		String xmlfileName = root+String.format(FileInfoUtil.XML_NAME_YHDL, 1);
		ParseUtil.saveXml(xmlfileName, outStreamXml);
		boolean retb = new Xmlvalidate().validateXml(XsdName.YHDL, xmlfileName);
		if(!retb) {
			new File(xmlfileName).delete();
		}else {
			fileList.add(new File(xmlfileName));
			deleteList.add(new File(xmlfileName));
		}
		
		String zipname = FileInfoUtil.ZIP_LX_YHDL
				+"_export_"
				+TimeUtils.getYesterdayDate()+"000000"
				+"_"+TimeUtils.getYesterdayDate()+"235959"
				+"_3000_"
				+TimeUtils.getNTime()+"_"+1+".zip";
		FileOutputStream fos2 = new FileOutputStream(new File(root + dir + zipname));
		FileZipCompressUtil.toZip(fileList, fos2);
		sfgkService.addYhdlFile(root + dir + zipname);
		
		
		for(File deletefile:deleteList) {
			deletefile.delete();
		}
	}

	/**
	 * 
	 * @function 生产xml
	 * @author Tony
	 * @param ktgkxxList
	 * @creaetime 2018年4月26日 下午3:17:24
	 * @return
	 */
	public ByteArrayOutputStream makeXml(Ktgkxx ttgkxx) {
		try {
		    Document document = DocumentHelper.createDocument();  
	        Element root = document.addElement("用户登录","http://dataexchange.court.gov.cn/2009/data");  
	        root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
	        root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
	        Element ktggxx = root.addElement("用户登录记录");
	        // 标识信息
	        Element r = ktggxx.addElement("R");
	        r.addElement("编号").addText("300200001000617");
	        r.addElement("法院编号").addText("3002");
	        r.addElement("登录用户").addText("罗童");
	        r.addElement("登录用户的类型代码").addText("1");
	        r.addElement("登录用户的类型名称").addText("被告人");
	        r.addElement("登录时间").addText("2018-06-26T09:42:32");
	        r.addElement("更新时间").addText(ParseUtil.parseDateTime(TimeUtils.getNowTime()));
			
			OutputFormat format = new OutputFormat("   ", true);
			format.setEncoding("UTF-8");// 设置编码格式
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return null;
	}


}