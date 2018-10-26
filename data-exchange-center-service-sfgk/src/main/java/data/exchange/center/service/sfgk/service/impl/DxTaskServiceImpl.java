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
import data.exchange.center.service.sfgk.domain.dx.Dxtzjl;
import data.exchange.center.service.sfgk.service.SfgkService;
import data.exchange.center.service.sfgk.service.TaskService;
import data.exchange.center.service.sfgk.util.DBUtil;
import data.exchange.center.service.sfgk.util.FileInfoUtil;
import data.exchange.center.service.sfgk.util.FileZipCompressUtil;
import data.exchange.center.service.sfgk.util.ParseUtil;
import data.exchange.center.service.sfgk.util.XsdName;
import data.exchange.center.service.sfgk.util.jdbc.DxJdbc;
import data.exchange.center.service.sfgk.util.jdbc.JdbcFactory;
import data.exchange.center.service.sfgk.util.validate.Xmlvalidate;

@Service("dxTaskService")
public class DxTaskServiceImpl implements TaskService {

	private static Logger logger = LoggerFactory.getLogger(DxTaskServiceImpl.class);
	
	@Autowired
	private SfgkService sfgkService;

	@Override
	public void startTask() throws Exception {
		logger.info("开始生产 短信 xml");
		String root = "D://SFGK//";
		String dir  = "dx//";
		Path path = Paths.get(root + dir);
		try {
	        Files.createDirectory(path);
	    } catch(FileAlreadyExistsException e){
	        // the directory already exists.
	    } catch (IOException e) {
	        //something else went wrong
	        e.printStackTrace();
	    }
		String sql = "SELECT 1 bh, lsh ajbh, lsh ajbs, dsrxm jsr, sjhm jsrsjh, '1' jsrlxdm, '申请人' jsrlxmc, '3000'fybh, fsnr dxnr, fsrq fssj   \r\n" + 
				"FROM  COURT_DBA.dx_fxx " + 
				"WHERE ssdw IS NOT NULL" + 
				"      AND FSNR NOT LIKE '%111%' " + 
				"      AND FSNR IS NOT NULL" + 
				"      AND FSRQ IS NOT NULL";
		List<File> fileList = new ArrayList<>();
		List<File> deleteList = new ArrayList<>();
		DxJdbc dxJdbc = (DxJdbc) JdbcFactory.getClass(DxJdbc.class);
		List<Map<String, Object>> listMap = DBUtil.getSqlData(dxJdbc.dxConnection(), sql);
		Dxtzjl dxtzjl = new Dxtzjl();
		for(Map<String, Object> map:listMap) {
			dxtzjl.setAjbh(map.get("ajbh").toString());
			dxtzjl.setAjbs(map.get("ajbs").toString());
			dxtzjl.setBh(map.get("bh").toString());
			dxtzjl.setDxnr(map.get("dxnr").toString());
			dxtzjl.setFssj(map.get("fssj").toString());
			dxtzjl.setFybh(map.get("fybh").toString());
			dxtzjl.setJsr(map.get("jsr").toString());
			dxtzjl.setJsrlxdm(map.get("jsrlxdm").toString());
			dxtzjl.setJsrlxmc(map.get("jsrlxmc").toString());
			dxtzjl.setJsrsjh(map.get("jsrsjh").toString());
		}
		ByteArrayOutputStream outStreamXml = makeXml(dxtzjl);
		String xmlfileName = root+String.format(FileInfoUtil.XML_NAME_DX, 1);
		ParseUtil.saveXml(xmlfileName, outStreamXml);
		boolean retb = new Xmlvalidate().validateXml(XsdName.DX, xmlfileName);
		if(!retb) {
			new File(xmlfileName).delete();
		}else {
			fileList.add(new File(xmlfileName));
			deleteList.add(new File(xmlfileName));
		}
		
		String zipname = FileInfoUtil.ZIP_LX_DX
				+"_export_"
				+TimeUtils.getYesterdayDate()+"000000"
				+"_"+TimeUtils.getYesterdayDate()+"235959"
				+"_3000_"
				+TimeUtils.getNTime()+"_"+1+".zip";
		FileOutputStream fos2 = new FileOutputStream(new File(root + dir + zipname));
		FileZipCompressUtil.toZip(fileList, fos2);
		sfgkService.addDxFile(root + dir + zipname);
		
		
		for(File deletefile:deleteList) {
			deletefile.delete();
		}
	}
	
	private ByteArrayOutputStream makeXml(Dxtzjl dxtzjl) {
		try {
			Document document = DocumentHelper.createDocument();  
		    Element root = document.addElement("短信通知","http://dataexchange.court.gov.cn/2009/data");  
		    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
		    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
			Element ktggxx = root.addElement("短信通知记录");
			// 标识信息
			Element r = ktggxx.addElement("R");
			r.addElement("编号").addText(dxtzjl.getBh());
			r.addElement("案件编号").addText(dxtzjl.getAjbh());
			r.addElement("案件标识").addText(dxtzjl.getAjbs());
			r.addElement("接收人").addText(dxtzjl.getJsr());
			r.addElement("接收人手机号").addText(dxtzjl.getJsrsjh());
			r.addElement("接收人类型代码").addText(dxtzjl.getJsrlxdm());
			r.addElement("接收人类型名称").addText(dxtzjl.getJsrlxmc());
			r.addElement("法院编号").addText(dxtzjl.getFybh());
			r.addElement("短信内容").addText(dxtzjl.getDxnr());
			r.addElement("发送时间").addText(ParseUtil.parseDateTime(dxtzjl.getFssj()));
			
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