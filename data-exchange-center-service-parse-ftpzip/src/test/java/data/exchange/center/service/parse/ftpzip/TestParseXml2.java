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

public class TestParseXml2 {

	public static void main(String[] args) throws DocumentException {
		try{

			/**
			 * Map存放解析后XML对应表信息
			 */
			Map<String, Object> tableMap = new HashMap<String, Object>();
			List<Object> headList = new ArrayList<Object>();
			List<Object> gaList = new ArrayList<Object>();
			List<Object> jcyList = new ArrayList<Object>();
			List<Object> zfList = new ArrayList<Object>();
			List<Object> fyList = new ArrayList<Object>();
			List<Object> wsList = new ArrayList<Object>();
			
			List<Object> jzjbxxList = new ArrayList<Object>();
			List<Object> jzmljList = new ArrayList<Object>();
			List<Object> jzmlwjList = new ArrayList<Object>();
			List<Object> jzwjList = new ArrayList<Object>();
			List<Object> qzcsList = new ArrayList<Object>();
			List<Object> jbxxList = new ArrayList<Object>();
			List<Object> saxxList = new ArrayList<Object>();
			
			/**
			 * <ROOT>
	  			<HEAD></HEAD>
	  			<JZJBXX></JZJBXX>
	  			<WSS></WSS>
	  			<MSG></MSG>
			   </ROOT>
			 */
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File("C:\\Users\\yuguang\\Desktop\\510812_511102_c2ee113c67c54034a9a0997af82277d2_75CE620DBCB6E696C93447025A806181\\510812_511102_c2ee113c67c54034a9a0997af82277d2.xml"));
			Element rootElements = document.getRootElement();
			/**
			 * 对应的表名列表  1级
			 */
			List<Element> tableElements = rootElements.elements();
			for (Element element : tableElements) {
				
				/**
				 * 表名  二级
				 */
				List<Element> tableElement = element.elements();
				/**
				 * 单独处理JZJBXX卷宗基本信息
				 */
				if("JZJBXX".equalsIgnoreCase(element.getName())) {
					Map<String, Object> jzjbxxMap = new HashMap<String, Object>();
					/**
					 * 卷宗编号（主键）
					 */
					String JZBH = element.attributeValue("JZBH");
					/**
					 * 案件名称
					 */
					String AJMC = element.attributeValue("AJMC");
					/**
					 * 部门受案号（XML中暂时未提供）
					 */
					String BMSAH = element.attributeValue("BMSAH")==null?"":element.attributeValue("BMSAH");
					
					/**
					 * JZMLJ 卷信息
					 */
					for (Element elements : tableElement) {
						Map<String, Object> jzmljMap = new HashMap<String, Object>();
						/**
						 * 卷宗编号
						 */
						String J_JZBH = elements.attributeValue("JZBH");
						/**
						 * 目录编号
						 */
						String J_MLBH = elements.attributeValue("MLBH");
						/**
						 * 父目录编号
						 */
						String J_FMLBH = elements.attributeValue("FMLBH");
						/**
						 * 目录显示名称
						 */
						String J_MLXSMC = elements.attributeValue("MLXSMC");
						/**
						 * 目录信息
						 */
						String J_MLXX = elements.attributeValue("MLXX");
						/**
						 * 目录顺序号
						 */
						String J_MLSXH = elements.attributeValue("MLSXH");
						/**
						 * 目录类型
						 * 1：卷
						 * 3：文件
						 */
						String J_MLLX = elements.attributeValue("MLLX");
						/**
						 * 是否涉密（Y,N）
						 */
						String J_SFSM = elements.attributeValue("SFSM");
						
						
						List<Element> elementss = elements.elements();
						/**
						 * 遍历获取 JZMLWJ  目录
						 */
						for(Element el:elementss) {
							Map<String, Object> jzmlwjMap = new HashMap<String, Object>();
							String ML_JZBH	= el.attributeValue("JZBH");
							String ML_MLBH	= el.attributeValue("MLBH");
							String ML_FMLBH = el.attributeValue("FMLBH");
							String ML_MLXSMC = el.attributeValue("MLXSMC");
							String ML_MLXX = el.attributeValue("MLXX");
							String ML_MLSXH = el.attributeValue("MLSXH");
							String ML_MLLX = el.attributeValue("MLLX");
							String ML_SFSM	= el.attributeValue("SFSM");
							
							List<Element> elementsss = el.elements();
							
							/**
							 * 遍历文件（JZWJ）
							 */
							for(Element eleme : elementsss) {
								Map<String, Object> jzwjMap = new HashMap<String, Object>();
								/**
								 * 卷宗编号
								 */
								String WJ_JZBH	= eleme.attributeValue("JZBH");
								/**
								 * 目录编号
								 */
								String WJ_MLBH	= eleme.attributeValue("MLBH");
								/**
								 * 文件序号（主键）
								 */
								String WJ_WJXH = eleme.attributeValue("WJXH");
								/**
								 * 文件相对路径
								 */
								String WJ_WJLJ = eleme.attributeValue("WJLJ");
								/**
								 * 文件名称（物理文件名不含后缀）
								 */
								String WJ_WJMC = eleme.attributeValue("WJMC");
								/**
								 * 文件显示名称
								 */
								String WJ_WJXSMC = eleme.attributeValue("WJXSMC");
								/**
								 * 文件后缀
								 */
								String WJ_WJHZ = eleme.attributeValue("WJHZ");
								/**
								 * 文件顺序号
								 */
								String WJ_WJSXH = eleme.attributeValue("WJSXH");
								/**
								 * 文件大小（size,例：1.2M）
								 */
								String WJ_WJDX = eleme.attributeValue("WJDX");
								/**
								 * 文件类型：封面1，目录2，连续页3，漏码4，重码5，跳码6，备考表7，封底8
								 */
								String WJ_WJLX	= eleme.attributeValue("WJLX");
								/**
								 * 文件跳码次数
								 */
								String WJ_WJTM	= eleme.attributeValue("WJTM");
								/**
								 * 文件页码（主要用于记录跳码）
								 */
								String WJ_WJYM	= eleme.attributeValue("WJYM");
								
								jzwjMap.put("JZBH", WJ_JZBH);
								jzwjMap.put("MLBH", WJ_MLBH);
								jzwjMap.put("WJXH", WJ_WJXH);
								jzwjMap.put("WJLJ", WJ_WJLJ);
								jzwjMap.put("WJMC", WJ_WJMC);
								jzwjMap.put("WJXSMC", WJ_WJXSMC);
								jzwjMap.put("WJHZ", WJ_WJHZ);
								jzwjMap.put("WJSXH", WJ_WJSXH);
								jzwjMap.put("WJDX", WJ_WJDX);
								jzwjMap.put("WJLX", WJ_WJLX);
								jzwjMap.put("WJTM", WJ_WJTM);
								jzwjMap.put("WJYM", WJ_WJYM);
								jzwjList.add(jzwjMap);
							}
							jzmlwjMap.put("JZBH",   ML_JZBH);
							jzmlwjMap.put("MLBH",   ML_MLBH);
							jzmlwjMap.put("FMLBH",  ML_FMLBH);
							jzmlwjMap.put("MLXSMC", ML_MLXSMC);
							jzmlwjMap.put("MLXX",   ML_MLXX);
							jzmlwjMap.put("MLSXH",  ML_MLSXH);
							jzmlwjMap.put("MLLX",   ML_MLLX);
							jzmlwjMap.put("SFSM",   ML_SFSM);
							jzmlwjList.add(jzmlwjMap);
						}
						jzmljMap.put("JZBH",   J_JZBH);
						jzmljMap.put("MLBH",   J_MLBH);
						jzmljMap.put("FMLBH",  J_FMLBH);
						jzmljMap.put("MLXSMC", J_MLXSMC);
						jzmljMap.put("MLXX",   J_MLXX);
						jzmljMap.put("MLSXH",  J_MLSXH);
						jzmljMap.put("MLLX",   J_MLLX);
						jzmljMap.put("SFSM",   J_SFSM);
						jzmljList.add(jzmljMap);
					}
					jzjbxxMap.put("JZBH",  JZBH);
					jzjbxxMap.put("AJMC",  AJMC);
					jzjbxxMap.put("BMSAH", BMSAH);
					jzjbxxList.add(jzjbxxMap);
				}else if("XYRS".equalsIgnoreCase(element.getName())){
					for(Element elements : tableElement) {
						/**
						 * XYR
						 */
						List<Element> el = elements.elements();
						for(Element elementss : el) {
							List<Element> elem = elementss.elements();
							if("JBXX".equalsIgnoreCase(elementss.getName())) {
								Map<String, Object> jbxxMap = new HashMap<String, Object>();
								for(Element e: elem) {
									jbxxMap.put(e.getName(), e.getText());
								}
								jbxxList.add(jbxxMap);
							}else if("SAXX".equalsIgnoreCase(elementss.getName())) {
								Map<String, Object> saxxMap = new HashMap<String, Object>();
								for(Element e: elem) {
									saxxMap.put(e.getName(), e.getText());
								}
								saxxList.add(saxxMap);
							}else if("QZCSS".equalsIgnoreCase(elementss.getName())) {
								List<Element> qzcsElements = elementss.elements();
								for(Element qzcsElement : qzcsElements) {
									Map<String, Object> qzcsMap = new HashMap<String, Object>();
									List<Element> qzcsElemen = qzcsElement.elements();
									for(Element qzcsEleme : qzcsElemen) {
										qzcsMap.put(qzcsEleme.getName(), qzcsEleme.getText());
									}
									qzcsList.add(qzcsMap);
								}
							}
						}
					}
				}else {
					Map<String, Object> headMap = new HashMap<String, Object>();
					Map<String, Object> gaMap = new HashMap<String, Object>();
					Map<String, Object> jcyMap = new HashMap<String, Object>();
					Map<String, Object> zfMap = new HashMap<String, Object>();
					Map<String, Object> fyMap = new HashMap<String, Object>();
					for (Element elements : tableElement) {
						Map<String, Object> wsMap = new HashMap<String, Object>();
						if("GA".equalsIgnoreCase(elements.getName())){
							List<Element> ele = elements.elements();
							for(Element e : ele) {
								gaMap.put(e.getName(), e.getText());
							}
						} else if("ZF".equalsIgnoreCase(elements.getName())){
							List<Element> ele = elements.elements();
							for(Element e : ele) {
								zfMap.put(e.getName(), e.getText());
							}
						} else if("JCY".equalsIgnoreCase(elements.getName())){
							List<Element> ele = elements.elements();
							for(Element e : ele) {
								jcyMap.put(e.getName(), e.getText());
							}
						}  else if("FY".equalsIgnoreCase(elements.getName())){
							List<Element> ele = elements.elements();
							for(Element e : ele) {
								fyMap.put(e.getName(), e.getText());
							}
						} else if("WS".equalsIgnoreCase(elements.getName())){
							List<Element> wsElements = elements.elements();
							for(Element wsElement : wsElements) {
								wsMap.put(wsElement.getName(), wsElement.getText());
							}
						}else{
							/**
							 * HEAD信息 除开GA, JCY, ZF
							 */
							headMap.put(elements.getName(), elements.getText());
						}
						if(!wsMap.isEmpty()) {
							wsList.add(wsMap);
						}
					}
					if(!headMap.isEmpty()) {
						headList.add(headMap);
					}
					if(!gaMap.isEmpty()) {
						gaList.add(gaMap);
					}
					if(!jcyMap.isEmpty()) {
						jcyList.add(jcyMap);
					}
					if(!zfMap.isEmpty()) {
						zfList.add(zfMap);
					}
					if(!fyMap.isEmpty()) {
						fyList.add(fyMap);
					}
				}
			}
			tableMap.put("head",    headList);
			tableMap.put("gahead",  gaList);
			tableMap.put("jcyhead", jcyList);
			tableMap.put("zfhead",  zfList);
			tableMap.put("fyhead",  fyList);
			tableMap.put("ws",      wsList);
			tableMap.put("jzjbxx",  jzjbxxList);
			tableMap.put("jzmlj",   jzmljList);
			tableMap.put("jzmlwj",  jzmlwjList);
			tableMap.put("jzwj",    jzwjList);
			tableMap.put("qzcs",    qzcsList);
			tableMap.put("jbxx",    jbxxList);
			tableMap.put("saxx",    saxxList);
			System.out.println();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
