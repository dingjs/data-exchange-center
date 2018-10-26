package service.sjsb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestMakeXml {

	public static void main(String[] args) throws IOException {
		List mlxxList = new ArrayList<>();
		
		//档案
		Document document = DocumentHelper.createDocument();  
	    Element root = document.addElement("开庭公告","http://dataexchange.court.gov.cn/2009/data");  
	    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
	    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data DA_档案信息.xsd"); 
		Element daxx = root.addElement("立案日期");
		daxx.addElement("立案日期").addText("");
		daxx.addElement("案件标识");
		daxx.addElement("承办人");
		daxx.addElement("案号");
		daxx.addElement("总页数");
		daxx.addElement("案件类别");
		daxx.addElement("案件类型");
		Element mlxx = daxx.addElement("目录信息");
		mlxx.addElement("R");

		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");// 设置编码格式
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		XMLWriter xmlWriter = new XMLWriter(outputStream, format);
//		xmlWriter.write(document);
//		xmlWriter.close();
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("E://Person.xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
}
