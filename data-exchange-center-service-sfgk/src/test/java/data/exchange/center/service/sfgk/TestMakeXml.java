package data.exchange.center.service.sfgk;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestMakeXml {

	public static void main(String[] args) throws IOException {
	    Document document = DocumentHelper.createDocument();  
	    Element root = document.addElement("开庭公告","http://dataexchange.court.gov.cn/2009/data");  
	    root.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");  
	    root.addAttribute("xsi:schemaLocation","http://dataexchange.court.gov.cn/2009/data ktgg.xsd");  
		Element ktggxx = root.addElement("开庭公告信息");
		// 标识信息
		Element r = ktggxx.addElement("R");
		r.addElement("编号").addText("编号");
		r.addElement("案件编号").addText("案件编号");
		r.addElement("案件的唯一标识").addText("案件的唯一标识");
		r.addElement("案号").addText("案号");
		r.addElement("案件类别代码").addText("案件类别代码");
		r.addElement("案件类别名称").addText("案件类别名称");
		r.addElement("开庭日期").addText("2018-4-25");
		r.addElement("法庭名称").addText("法庭名称");
		r.addElement("案由编号").addText("案由编号");
		r.addElement("主审法官姓名").addText("主审法官姓名");
		r.addElement("原告或者上诉人名称").addText("原告或者上诉人名称");
		r.addElement("被告或者被上诉人名称").addText("被告或者被上诉人名称");
		r.addElement("公告类型").addText("公告类型");
		r.addElement("法院代码").addText("法院代码");
		r.addElement("法院名称").addText("法院名称");
		r.addElement("公告内容").addText("公告内容");
		r.addElement("公告标题").addText("公告标题");
		r.addElement("发布时间").addText("发布时间");
		r.addElement("更新时间").addText("更新时间");
		r.addElement("公告公开状态代码").addText("公告公开状态代码");
		r.addElement("公告公开状态名称").addText("公告公开状态名称");
		r.addElement("是否取消开庭代码").addText("是否取消开庭代码");
		r.addElement("是否取消开庭名称").addText("是否取消开庭名称");
		
		r = ktggxx.addElement("R");
		r.addElement("编号").addText("编号");
		r.addElement("案件编号").addText("案件编号");
		r.addElement("案件的唯一标识").addText("案件的唯一标识");
		r.addElement("案号").addText("案号");
		r.addElement("案件类别代码").addText("案件类别代码");
		r.addElement("案件类别名称").addText("案件类别名称");
		r.addElement("开庭日期").addText("2018-4-25");
		r.addElement("法庭名称").addText("法庭名称");
		r.addElement("案由编号").addText("案由编号");
		r.addElement("主审法官姓名").addText("主审法官姓名");
		r.addElement("原告或者上诉人名称").addText("原告或者上诉人名称");
		r.addElement("被告或者被上诉人名称").addText("被告或者被上诉人名称");
		r.addElement("公告类型").addText("公告类型");
		r.addElement("法院代码").addText("法院代码");
		r.addElement("法院名称").addText("法院名称");
		r.addElement("公告内容").addText("公告内容");
		r.addElement("公告标题").addText("公告标题");
		r.addElement("发布时间").addText("发布时间");
		r.addElement("更新时间").addText("更新时间");
		r.addElement("公告公开状态代码").addText("公告公开状态代码");
		r.addElement("公告公开状态名称").addText("公告公开状态名称");
		r.addElement("是否取消开庭代码").addText("是否取消开庭代码");
		r.addElement("是否取消开庭名称").addText("是否取消开庭名称");
		OutputFormat format = new OutputFormat("   ", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("E://Person.xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
}
