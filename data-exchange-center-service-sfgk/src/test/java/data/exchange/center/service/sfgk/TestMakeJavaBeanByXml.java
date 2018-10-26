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

public class TestMakeJavaBeanByXml {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws DocumentException, IOException {
		String ajlx = "ajck";
		String fileName = "d://AJCK.xml";
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		Element root = document.getRootElement();
		List<Element> nodeList = root.elements();
		int xh = 1;
		//实体类生成
		for(Element element:nodeList) {
//			System.out.println("表名为："+element.getName());
			String tableName = PinYinUtil.getFirstSpell(element.getName());
			tableName = tableName.substring(0, 1).toUpperCase()+tableName.substring(1);
			StringBuffer str = new StringBuffer();
			str.append("package data.exchange.center.service.sfgk.domain."+ajlx+";");
			str.append("\n");
			str.append("\n");
			str.append("public class "+tableName+" {");
			str.append("\n");
			List<Element> colList = element.elements();
			for(Element col:colList) {
				if(col.getName().equals("R")) {
					List<Element> colLists = col.elements();
					for(Element cols:colLists) {
						String colName = PinYinUtil.getFirstSpell(cols.getName());
						str.append("\n");
						str.append("	/**");
						str.append("\n");
						str.append("	 * "+cols.getName());
						str.append("\n");
						str.append("	 */");
						str.append("\n");
						str.append("	private String "+colName+";");
					}
				}else {
					String colName = PinYinUtil.getFirstSpell(col.getName());
					str.append("\n");
					str.append("	/**");
					str.append("\n");
					str.append("	 * "+col.getName());
					str.append("\n");
					str.append("	 */");
					str.append("\n");
					str.append("	private String "+colName+";");
				}
			}
			//生成getter和setter
			for(Element col:colList) {
				if(col.getName().equals("R")) {
					List<Element> colLists = col.elements();
					for(Element cols:colLists) {
						String colName = PinYinUtil.getFirstSpell(cols.getName());
						String upperColName = colName.substring(0,1).toUpperCase()+colName.substring(1);
						str.append("\n");
						str.append("	public String get"+upperColName+"() {");
						str.append("\n");
						str.append("		return "+colName+" = "+colName+" == null?\"\":"+colName+".trim();");
						str.append("\n");
						str.append("	}");
						str.append("\n");
						str.append("	public void set"+upperColName+"(String "+colName+") {");
						str.append("\n");
						//appCode == null ? null : appCode.trim();
						str.append("		this."+colName+" = "+colName+";");
						str.append("\n");
						str.append("	}");
						
					}
				}else {
					String colName = PinYinUtil.getFirstSpell(col.getName());
					String upperColName = colName.substring(0,1).toUpperCase()+colName.substring(1);
					str.append("\n");
					str.append("	public String get"+upperColName+"() {");
					str.append("\n");
					str.append("		return "+colName+" = "+colName+" == null?\"\":"+colName+".trim();");
					str.append("\n");
					str.append("	}");
					str.append("\n");
					str.append("	public void set"+upperColName+"(String "+colName+") {");
					str.append("\n");
					str.append("		this."+colName+" = "+colName+";");
					str.append("\n");
					str.append("	}");
				}
			}
			xh = xh + 1;
			str.append("\n");
			str.append("}");
			FileOutputStream fileOut = new FileOutputStream(
					new File("src//main//java//data//exchange//center//service//sfgk//domain//"+ajlx+"//"+tableName+".java"));
			fileOut.write(str.toString().getBytes());
			fileOut.flush();
			fileOut.close();
		}
	}
}
