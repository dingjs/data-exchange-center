package data.exchange.center.service.pcaj;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseXmlTest {

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				"\r\n" + 
				"<package>\r\n" + 
				"  <pub>\r\n" + 
				"    <txcode>91101</txcode>\r\n" + 
				"  </pub>\r\n" + 
				"  <req>\r\n" + 
				"    <checkSheetList>\r\n" + 
				"      <checkSheet>A|B|C|D|E|F|G|H|I|J|K|L|M|N</checkSheet>\r\n" + 
				"    </checkSheetList>\r\n" + 
				"    <operationTime>20171227105124</operationTime>\r\n" + 
				"    <siteName>33038100000000101</siteName>\r\n" + 
				"  </req>\r\n" + 
				"  <ans/>\r\n" + 
				"</package>\r\n" + 
				"";
		Map<Object, Object> map = new HashMap<>();
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		List<Element> childElements = rootElement.elements();
		for(Element element:childElements) {
			List<Element> elementList = element.elements();
			for(Element el:elementList) {
				if(el.getName().equalsIgnoreCase("checkSheetList")) {
					List<Element> checkSheetList = el.elements();
					for(Element e2:checkSheetList) {
						map.put(e2.getName(), e2.getTextTrim());
					}
				}else {
					map.put(el.getName(), el.getTextTrim());
				}
			}
		}
		System.out.println(map.toString());
	}
}
