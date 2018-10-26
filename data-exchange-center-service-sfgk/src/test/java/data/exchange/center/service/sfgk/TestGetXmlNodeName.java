package data.exchange.center.service.sfgk;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import data.exchange.center.common.pinyin.PinYinUtil;
import data.exchange.center.service.sfgk.util.ParseUtil;

public class TestGetXmlNodeName {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws DocumentException, IOException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("d://XZ.xml"));
		Element root = document.getRootElement();
		List<Element> nodeList = root.elements();
		int xh = 1;
		//xml生成组装
		for(Element element:nodeList) {
//			System.out.println("表名为："+element.getName());
			System.out.println("Element node"+xh+" = root.addElement(\""+element.getName()+"\");");
			List<Element> colList = element.elements();
			for(Element col:colList) {
//				System.out.println("字段名为："+col.getName());
//				node1.addElement("").addText("");
				if(col.getName().equals("R")) {
					List<Element> colLists = col.elements();
					System.out.println("Element r"+xh+" = node"+xh+".addElement(\"R\");");
					for(Element cols:colLists) {
						String tableName = PinYinUtil.getFirstSpell(cols.getName());
//						System.out.println(tableName);
						System.out.println("ParseUtil.setNodeText(r"+xh+",\""+cols.getName()+"\",\"\");");
					}
				}else {
					System.out.println("ParseUtil.setNodeText(node"+xh+",\""+col.getName()+"\",\"\");");
//					String tableName = PinYinUtil.getFirstSpell(col.getName());
//					System.out.println(tableName);
				}
			}
			xh = xh + 1;
			System.out.println();
		}
	}
}
