package data.exchange.center.service.parse.ftpzip;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import data.exchange.center.service.parse.ftpzip.domain.Bsxx;
import data.exchange.center.service.parse.ftpzip.domain.Dsrxx;
import data.exchange.center.service.parse.ftpzip.domain.Gsxx;
import data.exchange.center.service.parse.ftpzip.domain.Jzxx;

public class TestMakeXml {

	public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
		 
		Bsxx bsxx = new Bsxx();
		Gsxx gsxx = new Gsxx();
		List<Dsrxx> dsrxxList = new ArrayList<>();
		List<Jzxx> JzxxList = new ArrayList<>();
		 Element root = DocumentHelper.createElement("root"); 
		 Document document = DocumentHelper.createDocument(root); 
		 Element record = root.addElement("RECORD");
		 //标识信息
		 Element bsxxEl = record.addElement("BSXX");
		 bsxxEl.addElement("CaseNo").addText(bsxx.getCASENO());
		 bsxxEl.addElement("FSDW").addText(bsxx.getFSDW());
		 bsxxEl.addElement("FSDWMC").addText(bsxx.getFSDWMC());
		 bsxxEl.addElement("JSDW").addText(bsxx.getJSDW());
		 bsxxEl.addElement("JSDWMC").addText(bsxx.getJSDWMC());
		 bsxxEl.addElement("LEIX").addText(bsxx.getLEIX());
		 bsxxEl.addElement("JHJD").addText("101");
		 Element gsxxEl = record.addElement("GSXX");
		 gsxxEl.addElement("GSZZM").addText(gsxx.getGSZZM());
		 gsxxEl.addElement("DSRC").addText(gsxx.getDSRC());
		 gsxxEl.addElement("GSSBH").addText(gsxx.getGSSBH());
		 gsxxEl.addElement("GSJG").addText(gsxx.getGSJG());
		 Element dsrxxEl = record.addElement("DSRXX");
		 for (int i = 0; i < dsrxxList.size(); i++) {
			 Dsrxx dsrxx = dsrxxList.get(i);
			 Element dsrxxRc = dsrxxEl.addElement("RECORD");
			 dsrxxRc.addElement("XM").addText(dsrxx.getXM());
			 dsrxxRc.addElement("XB").addText(dsrxx.getXB());
			 dsrxxRc.addElement("DZ").addText(dsrxx.getDZ());
			 dsrxxRc.addElement("MZ").addText(dsrxx.getMZ());
			 dsrxxRc.addElement("SF").addText(dsrxx.getSF());
			 dsrxxRc.addElement("ZY").addText(dsrxx.getZY());
			 dsrxxRc.addElement("GJ").addText(dsrxx.getGJ());
			 dsrxxRc.addElement("SFZHM").addText(dsrxx.getSFZHM());
			 dsrxxRc.addElement("FZNL").addText(dsrxx.getFZNL());
			 dsrxxRc.addElement("ZKZM").addText(dsrxx.getZKZM());
			 
		}
		 Element jzxxEl = record.addElement("JZXX");
		 for (int i = 0; i < JzxxList.size(); i++) {
			 Jzxx jzxx =  JzxxList.get(i);
			 Element jzxxElRc = jzxxEl.addElement("RECORD");
			 jzxxElRc.addElement("LX").addText(jzxx.getLX());
			 jzxxElRc.addElement("WSXH").addText(jzxx.getWSXH());
			 jzxxElRc.addElement("CLBT").addText(jzxx.getCLBT());
			 jzxxElRc.addElement("PATH").addText(jzxx.getPATH());
			 jzxxElRc.addElement("WSMC").addText(jzxx.getWSMC());
			 jzxxElRc.addElement("WJGS").addText(jzxx.getWJGS());
		 }
		 
		//把生成的xml文档存放在硬盘上  true代表是否换行  
        OutputFormat format = new OutputFormat("    ",true);  
        format.setEncoding("GBK");//设置编码格式  
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("Person.xml"),format);  
        xmlWriter.write(document);  
        xmlWriter.close();  
	}
}