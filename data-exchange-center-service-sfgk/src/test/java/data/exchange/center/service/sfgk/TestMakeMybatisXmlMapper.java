package data.exchange.center.service.sfgk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import data.exchange.center.common.pinyin.PinYinUtil;

public class TestMakeMybatisXmlMapper {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws DocumentException, IOException {
		String ajlx = "dx";
		String fileName = "d://DX.xml";
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		Element root = document.getRootElement();
		List<Element> nodeList = root.elements();
		int xh = 1;
		StringBuffer str = new StringBuffer();
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n" + 
				"<mapper namespace=\"data.exchange.center.service.sfgk.mapper."+document.getName()+"Mapper\">");
		str.append("\n");
		//实体类生成
		for(Element element:nodeList) {
//			System.out.println("表名为："+element.getName());
			String tableName = PinYinUtil.getFirstSpell(element.getName());
			String upperTableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1);
			str.append("\n");
			str.append("	<resultMap id=\"outMapXz"+upperTableName+"\" type=\"data.exchange.center.service.sfgk.domain."+ajlx+"."+upperTableName+"\">");
			List<Element> colList = element.elements();
			for(Element col:colList) {
				if(col.getName().equals("R")) {
					List<Element> colLists = col.elements();
					for(Element cols:colLists) {
						String colName = PinYinUtil.getFirstSpell(cols.getName());
						str.append("\n");
						str.append("		<result column=\""+colName+"\"    property=\""+colName+"\"    javaType=\"string\"    jdbcType=\"VARCHAR\" />");
					}
				}else {
					String colName = PinYinUtil.getFirstSpell(col.getName());
					str.append("\n");
					str.append("		<result column=\""+colName+"\"    property=\""+colName+"\"    javaType=\"string\"    jdbcType=\"VARCHAR\" />");
				}
			}
			xh = xh + 1;
			str.append("\n");
			str.append("  	</resultMap>");
		}
		str.append("\n");
		str.append("</mapper>");
		FileOutputStream fileOut = new FileOutputStream(
				new File("src\\main\\resources\\mybatis\\mapper//"+document.getText()+"Mapper.xml"));
		fileOut.write(str.toString().getBytes());
		fileOut.flush();
		fileOut.close();
	}
}
