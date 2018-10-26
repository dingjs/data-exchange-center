package data.exchange.center.service.parse.ftpzip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestParseXml {

	public static void main(String[] args) throws DocumentException {
		/**
		 * <ROOT>
  			<HEAD></HEAD>
  			<JZJBXX></JZJBXX>
  			<WSS></WSS>
  			<MSG></MSG>
		   </ROOT>
		 */
		SAXReader reader = new SAXReader();
		Document document = reader
				.read(new File("D:\\test\\00_协定后的.xml"));
		Element root = document.getRootElement();
		System.out.println(root);
		Element rootElements = document.getRootElement();
		/**
		 * 对应的表名列表
		 */
		List<Element> tableElements = rootElements.elements();
		System.out.println(tableElements);
		for (Element element : tableElements) {
			System.out.println(element.getName());
			
			/**
			 * 表名
			 */
			List<Element> tableElement = element.elements();
			for (Element elements : tableElement) {
				System.out.println(elements.getName());
			}
		}
		
		
		
		
		
		
		
		
		List<Object> tablesList = new ArrayList<Object>();
		for (Element element : tableElements) {
			Map<String, Object> tableMap = new HashMap<String, Object>();

			if (element.getName().equalsIgnoreCase("XYRS")) {
				/**
				 * XRY标签
				 */
				List<Element> listElements = element.elements();
				/**
				 * 遍历XRY标签
				 */
				for (Element el : listElements) {
					List<Element> listEl = el.elements();
					/**
					 * 遍历单个XRY中的表标签
					 */
					for (Element el1 : listEl) {
						Map<String, Object> map = new HashMap<String, Object>();
						/**
						 * 强制措施表有多条记录
						 */
						if ("QZCSS".equalsIgnoreCase(el1.getName())) {
							List<Object> lists = new ArrayList<Object>();
							List<Object> lists2 = new ArrayList<Object>();
							List<Element> listEl1 = el1.elements();
							for(Element el2 : listEl1) {
								lists.add(getTableContent(el2.elements()));
							}
							for(Object list:lists) {
								if(list instanceof List) {
									Map<String, Object> listMap = (Map<String, Object>) ((List) list).get(0);
									lists2.add(listMap);
								}
							}
							map.put(el.getName()+listEl1.get(0).getName(), lists2);
							tablesList.add(map);
						}else {
							map.put(el.getName()+el1.getName(), getTableContent(el1.elements()));
							tablesList.add(map);
						}
					}
				}
			} else if (element.getName().equalsIgnoreCase("WSS")) {
				List<Object> list1 = new ArrayList<Object>();
				/**
				 * XRY标签
				 */
				List<Element> listElements = element.elements();
				/**
				 * 遍历XRY标签
				 */
				for (Element el : listElements) {
					List<Element> listEl = el.elements();
					Map<String, Object> map = new HashMap<String, Object>();
					Map<String, Object> map1 = new HashMap<String, Object>();
					/**
					 * 遍历单个XRY中的表标签
					 */
					for (Element el1 : listEl) {
						/**
						 * 强制措施表有多条记录
						 */
						if ("JGSJ".equalsIgnoreCase(el1.getName())) {
							List<Object> list = new ArrayList<Object>();
							List<Element> listEl1 = el1.elements();
							for(Element el2 : listEl1) {
								list.add(getTableContent(el2.elements()));
							}
							map.put(el.getName()+listEl1.get(0).getName(), list);
//							tablesList.add(map);
						}else {
							if(!"JGSJ".equalsIgnoreCase(el1.getName())) {
								map.put(el1.getName(), el1.getText());
							}
						}
					}
					list1.add(map);
					map1.put(el.getName(), list1);
					tablesList.add(map1);
				}
			} else if(element.getName().equalsIgnoreCase("XTAJBH")){
				/**
				 * GA标签
				 */
				List<Element> listElements = element.elements();
				/**
				 * 遍历GA标签
				 */
				for (Element el : listElements) {
					List<Object> list = new ArrayList<Object>();
					Map<String, Object> gaTable = new HashMap<String, Object>();
					List<Element> listEl = el.elements();
					/**
					 * 遍历单个GA中的表标签
					 */
					for (Element el1 : listEl) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put(el1.getName(), el1.getText());
						list.add(map);
					}
					gaTable.put(element.getName()+el.getName(), list);
					tablesList.add(gaTable);
				}
			}else {
				tableMap.put(element.getName(), getTableContent(element.elements()));
				tablesList.add(tableMap);
			}
		}
		System.out.println(tablesList);
	}

	/**
	 * 
	 * @function 传入表名节点
	 * @author wenyuguang
	 * @creaetime 2017年10月21日 上午10:32:22
	 * @param elements
	 * @return
	 */
	private static List<Map<String, Object>> getTableContent(List<Element> elements) {
		List<Object> tableList = new ArrayList<Object>();
		/**
		 * 存放字段Map
		 */
		Map<String, Object> colMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Element element : elements) {
			colMap.put(element.getName(), element.getText());
		}
		list.add(colMap);
		tableList.add(list);
		return list;
	}
}