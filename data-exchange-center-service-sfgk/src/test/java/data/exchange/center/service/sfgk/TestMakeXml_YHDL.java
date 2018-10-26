package data.exchange.center.service.sfgk;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestMakeXml_YHDL {

	public static void main(String[] args) throws IOException {
		Document document = DocumentHelper.createDocument();  
	    Element root = document.addElement("用户登录","http://dataexchange.court.gov.cn/2009/data");  
	    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
	    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
		Element ktggxx = root.addElement("用户登录记录");
		// 标识信息
		Element r = ktggxx.addElement("R");
		r.addElement("编号").addText("123");
		r.addElement("法院编号").addText("123");
		r.addElement("登录用户").addText("123");
		r.addElement("登录用户的类型代码").addText("123");
		r.addElement("登录用户的类型名称").addText("2012-12-13T12:12:12");
		r.addElement("登录时间").addText("2012-12-13T12:12:12");
		r.addElement("登录ip").addText("2012-12-13T12:12:12");
		r.addElement("更新时间").addText("2012-12-13T12:12:12");
		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("D://YHDL.xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
}
