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
import java.util.Map;
import java.util.UUID;

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
import data.exchange.center.service.sfgk.domain.wzfwl.Wzfwl;
import data.exchange.center.service.sfgk.service.SfgkService;
import data.exchange.center.service.sfgk.service.TaskService;
import data.exchange.center.service.sfgk.util.DBUtil;
import data.exchange.center.service.sfgk.util.FileInfoUtil;
import data.exchange.center.service.sfgk.util.FileZipCompressUtil;
import data.exchange.center.service.sfgk.util.ParseUtil;
import data.exchange.center.service.sfgk.util.XsdName;
import data.exchange.center.service.sfgk.util.jdbc.JdbcFactory;
import data.exchange.center.service.sfgk.util.jdbc.WzfwlJdbc;
import data.exchange.center.service.sfgk.util.validate.Xmlvalidate;

@Service("wzfwlTaskService")
public class WzfwlTaskServiceImpl implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(WzfwlTaskServiceImpl.class);
	
	@Autowired
	private SfgkService sfgkService;

	@Override
	public void startTask() throws Exception {
		logger.info("开始生产 网站访问量 xml");
		String root = "D://SFGK//";
		String dir  = "wzfwl//";
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
		String sql = "SELECT 1 bh, 3000 fybh, val fwl," + 
				"  		       to_char(sysdate,'yyyy-MM-dd') tjfwlddyrq," + 
				"  		       to_char(sysdate,'yyyy-MM-dd hh:mm:ss') gxsj" + 
				"  		  FROM sfgk.tu_dm_kz WHERE dm = 'TOTAL_CNT'" + 
				"           AND fydm = '320100'";
		WzfwlJdbc dxJdbc = (WzfwlJdbc) JdbcFactory.getClass(WzfwlJdbc.class);
		List<Map<String, Object>> listMap = DBUtil.getSqlData(dxJdbc.wzfwlConnection(), sql);
		Wzfwl wzfwl = new Wzfwl();
		for(Map<String, Object> map:listMap) {
			wzfwl.setBh(map.get("bh").toString());
			wzfwl.setFwl(map.get("fwl").toString());
			wzfwl.setFybh(map.get("fybh").toString());
			wzfwl.setGxsj(map.get("gxsj").toString());
			wzfwl.setTjfwlddyrq(map.get("tjfwlddyrq").toString());
		}
		ByteArrayOutputStream outStreamXml = makeXml(wzfwl);
		String xmlfileName = root+String.format(FileInfoUtil.XML_NAME_WZFWL, 1);
		ParseUtil.saveXml(xmlfileName, outStreamXml);
		boolean retb = new Xmlvalidate().validateXml(XsdName.WZFWL, xmlfileName);
		if(!retb) {
			new File(xmlfileName).delete();
		}else {
			fileList.add(new File(xmlfileName));
			deleteList.add(new File(xmlfileName));
		}
		
		String zipname = FileInfoUtil.ZIP_LX_WZFWL
				+"_export_"
				+TimeUtils.getYesterdayDate()+"000000"
				+"_"+TimeUtils.getYesterdayDate()+"235959"
				+"_3000_"
				+TimeUtils.getNTime()+"_"+1+".zip";
		FileOutputStream fos2 = new FileOutputStream(new File(root + dir + zipname));
		FileZipCompressUtil.toZip(fileList, fos2);
		sfgkService.addWzfwlFile(root + dir + zipname);
		
		
		for(File deletefile:deleteList) {
			deletefile.delete();
		}
	}
	
	private ByteArrayOutputStream makeXml(Wzfwl wzfwl) {
		try {
			Document document = DocumentHelper.createDocument();  
		    Element root = document.addElement("网站访问","http://dataexchange.court.gov.cn/2009/data");  
		    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
		    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
			Element ktggxx = root.addElement("网站访问量");
			// 标识信息
			Element r = ktggxx.addElement("R");
			r.addElement("编号").addText(UUID.randomUUID().toString().replaceAll("-", ""));
			r.addElement("法院编号").addText(wzfwl.getFybh());
			r.addElement("访问量").addText(wzfwl.getFwl());
			r.addElement("统计访问量的对应日期").addText(ParseUtil.parseDate(wzfwl.getTjfwlddyrq()));
			r.addElement("更新时间").addText(ParseUtil.parseDateTime(wzfwl.getGxsj()));
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			XMLWriter xmlWriter = new XMLWriter(outputStream, format);
			xmlWriter.write(document);
			xmlWriter.close();
			return outputStream;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return null;
	}
}