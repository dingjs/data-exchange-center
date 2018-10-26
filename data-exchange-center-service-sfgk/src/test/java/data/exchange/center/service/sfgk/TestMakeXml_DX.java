package data.exchange.center.service.sfgk;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestMakeXml_DX {

	public static void main(String[] args) throws IOException {
		Document document = DocumentHelper.createDocument();  
	    Element root = document.addElement("短信通知","http://dataexchange.court.gov.cn/2009/data");  
	    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
	    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
		Element ktggxx = root.addElement("短信通知记录");
		// 标识信息
		Element r = ktggxx.addElement("R");
		r.addElement("编号").addText("123");
		r.addElement("案件编号").addText("123");
		r.addElement("案件标识").addText("123");
		r.addElement("接收人").addText("123");
		r.addElement("接收人手机号").addText("123");
		r.addElement("接收人类型代码").addText("123");
		r.addElement("接收人类型名称").addText("2012-12-13T12:12:12");
		r.addElement("法院编号").addText("123");
		r.addElement("短信内容").addText("2012-12-13T12:12:12");
		r.addElement("发送时间").addText("2012-12-13T12:12:12");
		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("D://DX.xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
}
