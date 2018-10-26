package data.exchange.center.service.parse.ftpzip;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import net.sf.json.JSONObject;

public class TestXmlToJson {

	public static String xmlToJSON(String xml, int jsonType) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			Map<?,?> map = iterateElement(root);
			obj.put(root.getName(), map);
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map iterateElement(Element root) {
		List childrenList = root.getChildren();
		Element element = null;
		Map map = new HashMap();
		List list = null;
		for (int i = 0; i < childrenList.size(); i++) {
			list = new ArrayList();
			element = (Element) childrenList.get(i);
			if (element.getChildren().size() > 0) {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(iterateElement(element));
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), iterateElement(element));
				}
			} else {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(element.getTextTrim());
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), element.getTextTrim());
				}
			}
		}

		return map;
	}

	public static void main(String[] args) throws ParseException {
		String xml = "<ROOT>" + "<Status>00</Status>" + "<ErrorMsg></ErrorMsg>" + "<Data>" + "<Row>"
				+ "<PERSONID>35020500200610000000000701355116</PERSONID>"
				+ "<XM>吴聪楠</XM><SFZH>350624198908052530</SFZH>" + "</Row>" + "<Row>"
				+ "<PERSONID>35020500200610000000000701355117</PERSONID>"
				+ "<XM>吴聪楠2</XM><SFZH>350624198908052531</SFZH>" + "</Row>" + "</Data>" + "</ROOT>";
		File file = new File("D:\\test\\0301_0301A_0001213370095_520111_a6d6344c394876945eb45c0e1e4fe5fa.xml");
		StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(result.toString());
		String str = TestXmlToJson.xmlToJSON(result.toString(), 1);
		System.out.println(str);
	}
}
