package data.exchange.center.service.sfgk;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestMakeXml_fygkfs {

	public static void main(String[] args) throws IOException {
		Document document = DocumentHelper.createDocument();  
	    Element root = document.addElement("公开方式","http://dataexchange.court.gov.cn/2009/data");  
	    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
	    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
		Element ktggxx = root.addElement("法院与公开方式的关联关系");
		// 标识信息
		Element r = ktggxx.addElement("R");
		r.addElement("编号").addText("123");
		r.addElement("法院编号").addText("123");
		r.addElement("公开方式的编号").addText("123");
		r.addElement("开通时间").addText("2012-12-13T12:12:12");
		r.addElement("地址").addText("地址");
		r.addElement("更新时间").addText("2012-12-13T12:12:12");
		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("E://FYGKFS.xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
}
