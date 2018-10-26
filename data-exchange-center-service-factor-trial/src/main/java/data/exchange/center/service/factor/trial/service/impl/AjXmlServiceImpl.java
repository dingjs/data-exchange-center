package data.exchange.center.service.factor.trial.service.impl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.factor.trial.mapper.AjxxAllMapper;
import data.exchange.center.service.factor.trial.service.AjxmlService;
@Service("ajXmlServiceImpl")
public class AjXmlServiceImpl extends  Thread implements AjxmlService{

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	private AjxxAllMapper ajxxAllMapper;
	
	

	@Override
	public void getAjXml(Integer rownum,Integer endRownum) throws Exception {
		try {
			List<Map<String, Object>> listMap = new ArrayList<>();
			Map<String, Object> param=new  HashMap<>();
			Map<String, Object> map = new HashMap<>();
			param.put("rownum", rownum);
			param.put("endRownum", endRownum);
			listMap=ajxxAllMapper.getajbs(param);
			System.out.println(listMap.size());
			for (int i = 0; i < listMap.size(); i++) {
				map=listMap.get(i);
				getAjbsInfo(map.get("AJBS").toString(),map.get("AJLX").toString());
				ajxxAllMapper.insertAjbs(map.get("AJBS").toString()+"_0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getAjbsInfo(String ajbs, String ajlx) {
		List<Map<String, Object>> listMap = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> table = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		String tablename = null;
		String ajlxMc = null;
		try {
			listMap = ajxxAllMapper.getTablename(ajlx);
			ajlxMc = ajxxAllMapper.getAjlxMc(ajlx);
			param.put("ajbs", ajbs);
			param.put("ajlx", ajlx);
			data.put("ajlxmc", ajlxMc);
			Element root = DocumentHelper.createElement(ajlxMc);  
		    Document doucment = DocumentHelper.createDocument(root);
		    root.addAttribute("ACTION_TYPE","DELETE");
			for (int i = 0; i < listMap.size(); i++) {
				table = listMap.get(i);
				tablename = (String) table.get("C_ETBNAME");
				param.put("tableName", tablename);
				List<Map<String, Object>> ajxx = ajxxAllMapper.getAjxx(param);
				if (ajxx.size() > 0 && !tablename.contains("STWJ") && !tablename.contains("DAXX")
						&& !tablename.contains("MLXX")&& !tablename.contains("DZDA")&& !tablename.contains("WS")) {
					DOM4JCreateXML(getDelAjxx(ajxx,tablename), tablename, root);	
				}
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
		    format.setEncoding("utf-8");
		    FileOutputStream file = new FileOutputStream("D:/de/"+ajbs+"_2.xml");  
		    XMLWriter xmlwriter = new XMLWriter(file,format);  
		    xmlwriter.write(doucment);
		    xmlwriter.close();
		} catch (Exception e) {
			logger.debug("获取案件所有信息错误:ajbs"+ajbs+"案件类型"+ajlx+"错误详情：" + e.getMessage());
			e.printStackTrace();
		}
		data.put("date", map);
	}



	public List<Map<String, Object>> getAjxx(List<Map<String, Object>> ajxx,String tabName) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < ajxx.size(); i++) {
			Map<String, Object> data = new HashMap<>();
			map = ajxx.get(i);
			if(!tabName.equalsIgnoreCase("YASTML_YASTML")){
 			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue()))) {
					// 判断是否为blob字段
					if (entry.getValue() instanceof oracle.sql.BLOB) {
						
					} else {
						data.put(entry.getKey(), String.valueOf(entry.getValue()));
					}
				}
				
			}}else {
				
				for (Entry<String, Object> entry : map.entrySet()) {
					if (entry.getValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue()))) {
						// 判断是否为blob字段
						if (entry.getValue() instanceof oracle.sql.BLOB) {
						} else {

								if(entry.getKey().equalsIgnoreCase("dz")){
									data.put(entry.getKey(), "	xx市xx县XX村");
								}else if(entry.getKey().equalsIgnoreCase("mc")){
									String mc=String.valueOf(entry.getValue());
									if(mc.length()>1){
									data.put(entry.getKey(), mc.substring(0, 1)+"某某");
									}else{
										data.put(entry.getKey(),"李某某");
									}
								}
								else if(entry.getKey().equalsIgnoreCase("SFZHM")){
									data.put(entry.getKey(), "510XXXXXXXXXXXXXXX");
								}else if(entry.getKey().equalsIgnoreCase("LXDH")){
									if(entry.getValue().toString().length()>3){
									data.put(entry.getKey(), entry.getValue().toString().substring(0, 3)+"********");
									}else{
										data.put(entry.getKey(), "123********");
									}
								}else{
									data.put(entry.getKey(), entry.getValue());
								}
								
							}
							
						}
					}
		
			}
			list.add(data);
		}
		return list;
	}
	
	public List<Map<String, Object>> getDelAjxx(List<Map<String, Object>> ajxx,String tabName) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		if(ajxx.size()>0){
			map=ajxx.get(0);
			data.put("AJBS",map.get("AJBS"));
			list.add(data);
		}
		return list;
	}

	public void	DOM4JCreateXML(List<Map<String, Object>> listMap,String tabname,Element root){
		 Element element = root.addElement(tabname);
		 if(listMap.size()>1){
			 for (Map<String, Object> map : listMap) {
				 Element elementR = element.addElement("R");
				for (Map.Entry<String, Object> entry: map.entrySet()) {
					 Element elementkey = elementR.addElement(entry.getKey());
					 elementkey.addText(String.valueOf(entry.getValue()) );
				}
			}
		 }else{
			 for (Map<String, Object> map : listMap) {
				for (Map.Entry<String, Object> entry: map.entrySet()) {
					 Element elementkey = element.addElement(entry.getKey());
					 elementkey.addText(String.valueOf( entry.getValue()));
				}
			}
		 }
	}

	public void getMataXml() throws Exception {
		List<Map<String, Object>> listMap = new ArrayList<>();
		List<Map<String, Object>> colMap = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> table = new HashMap<>();
		String tableId=null;
		String eTablename = null;
		String cTablename = null;
		String ajlxMc = null;
		try {
			listMap = ajxxAllMapper.getMetaTablename();
			Element root = DocumentHelper.createElement("标准规范4.5审判业务分类");  
		    Document doucment = DocumentHelper.createDocument(root);
			for (int i = 0; i < listMap.size(); i++) {
				table = listMap.get(i);
				eTablename = (String) table.get("C_ETBNAME");
				cTablename = (String) table.get("C_CTBNAME");
				tableId=(String) table.get("C_TABLEID");
				if(table.get("C_AJLXCODE")!=null){
					ajlxMc=ajxxAllMapper.getAjlxMc((String)table.get("C_AJLXCODE"));
				}else{
					ajlxMc="公共应用表";
				}
				colMap=ajxxAllMapper.getMetaTabcol(tableId);
				Element element =root.addElement(ajlxMc);
				
				Element elementbab =element.addElement(eTablename);
				elementbab.addAttribute("name", cTablename);
				System.out.println(cTablename);
				DOM4JCreateXMLMeta(colMap, elementbab);
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
		    format.setEncoding("utf-8");
		    FileOutputStream file = new FileOutputStream("C:/Users/Administrator/Desktop/xml/标准规范4.5审判业务分类.xml");  
		    XMLWriter xmlwriter = new XMLWriter(file,format);  
		    xmlwriter.write(doucment);
		    xmlwriter.close();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
	}
	public void	DOM4JCreateXMLMeta(List<Map<String, Object>> listMap,Element element){
			 for (Map<String, Object> map : listMap) {
				 Element elementkey = element.addElement("FIEIDS");
				for (Map.Entry<String, Object> entry: map.entrySet()) {
						 elementkey.addAttribute(entry.getKey(), String.valueOf(entry.getValue()));
				}
			}
	}
}
