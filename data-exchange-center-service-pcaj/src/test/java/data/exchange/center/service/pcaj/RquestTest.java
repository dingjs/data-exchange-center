package data.exchange.center.service.pcaj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import data.exchange.center.service.pcaj.domain.ColMeta;
import data.exchange.center.service.pcaj.domain.TableMeta;
import data.exchange.center.service.pcaj.service.PcajService;
import data.exchange.center.service.pcaj.util.MappingDataType;

public class RquestTest {

	private static Logger logger = LoggerFactory.getLogger(RquestTest.class);

	private static PcajService pcajService;

	public static void main(String[] args) throws Exception {
		// StringBuilder requestUrl = new StringBuilder();
		// requestUrl.append("http://192.1.36.74:8080/drsp/services/resource/api/ac24e525a86f17ac3924957c8a688fbb.gxml"
		// + "?systemMark=true"
		// + "&ticket=b8a682e71a45a15f305e4a6a8fa3311c");
		//// requestUrl.append("&n_ajlx=25");
		// requestUrl.append("&d_rksj_start=");
		// requestUrl.append(URLEncoder.encode("2010-01-01 00:00:00", "UTF-8"));
		// requestUrl.append("&d_rksj_end=");
		// requestUrl.append(URLEncoder.encode(getYesterdayDate()+" 23:59:00",
		// "UTF-8"));
		//
		//
		// InputStream input = getRequest(requestUrl.toString());
		//
		// String resultXml = IOUtils.toString(input, "UTF-8");
		// System.out.println(resultXml);
		//
		// Map<String, Object> map = parseXmlContent(resultXml);
		// System.out.println(map);
		// List<String> pathList = (List<String>) map.get("path");
		// for(String path:pathList) {
		// String pathUrl =
		// "http://192.1.36.74:8080/drsp/services/resource/api/ac24e525a86f17ac3924957c8a688fbb.stream?systemMark=true&ticket=301e56eb156a86cd663c2ef8fee93b06";
		// pathUrl = pathUrl +"&path="+path;
		// InputStream zipInput = getRequest(pathUrl);
		// FileOutputStream fileout = new FileOutputStream(new
		// File("E://test//"+path.substring(path.lastIndexOf("/"), path.length())));
		// byte[] buff = new byte[100];
		// int rc = 0;
		// while ((rc = zipInput.read(buff, 0, 100)) > 0) {
		// fileout.write(buff, 0, rc);
		// }
		// fileout.flush();
		// fileout.close();
		//
		//
		// }
		String xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "\r\n"
				+ "<破产申请审查案件 xmlns=\"http://dataexchange.court.gov.cn/2009/data\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://dataexchange.court.gov.cn/2009/data 2B_破产申请审查.xsd\">\r\n"
				+ "    <案件标识>320097540000010</案件标识>\r\n" + "    <收案信息>\r\n" + "        <案号>2016川14民破2号</案号>\r\n"
				+ "        <收案来源>1</收案来源>\r\n" + "        <申请事由>1</申请事由>\r\n" + "        <收案日期>2016-07-21</收案日期>\r\n"
				+ "    </收案信息>\r\n" + "    <当事人>\r\n" + "        <R>\r\n" + "            <序号>1</序号>\r\n"
				+ "            <当事人>1</当事人>\r\n" + "            <当事人案件地位>2</当事人案件地位>\r\n" + "        </R>\r\n"
				+ "        <R>\r\n" + "            <序号>2</序号>\r\n" + "            <当事人>2</当事人>\r\n"
				+ "            <当事人案件地位>1</当事人案件地位>\r\n" + "            <申请人类型>2</申请人类型>\r\n" + "        </R>\r\n"
				+ "    </当事人>\r\n" + "    <与案实体名录>\r\n" + "        <R>\r\n" + "            <序号>1</序号>\r\n"
				+ "            <来源>2</来源>\r\n" + "            <标识>0</标识>\r\n" + "            <角色>BAA=</角色>\r\n"
				+ "            <类型>2</类型>\r\n" + "            <名称>四川省尼科国润新材料有限公司</名称>\r\n"
				+ "            <法定代表人>张芃</法定代表人>\r\n" + "            <地址>彭山县青龙镇永远村二组</地址>\r\n" + "        </R>\r\n"
				+ "        <R>\r\n" + "            <序号>2</序号>\r\n" + "            <来源>2</来源>\r\n"
				+ "            <标识>0</标识>\r\n" + "            <角色>BAA=</角色>\r\n" + "            <类型>2</类型>\r\n"
				+ "            <名称>成都华川进出口集团有限公司</名称>\r\n" + "            <法定代表人>陆恺</法定代表人>\r\n"
				+ "            <地址>四川省成都市高新区高发大厦五楼</地址>\r\n" + "        </R>\r\n" + "    </与案实体名录>\r\n"
				+ "</破产申请审查案件>\r\n" + "";
		Map<String, Object> resultMap = parseXml(xml1);
		System.out.println(resultMap);
		Set<String> set = resultMap.keySet();

		// 获取列
		String ajlx = set.toString().replace("案件", "").replace("[", "").replace("]", "");
		List<ColMeta> colMetaList = pcajService.getColMeta(ajlx);
		ColMeta colMeta = null;
		Map<String, Object> tableMap = new HashMap<String, Object>();
		List<ColMeta> tableColMetaList = new ArrayList<ColMeta>();
		for (int i = 0; i < colMetaList.size(); i++) {
			colMeta = colMetaList.get(i);
			if (i < colMetaList.size() - 1) {
				ColMeta colMetaLast = colMetaList.get(i + 1);
				if (!colMetaLast.getcEtbname().equals(colMeta.getcEtbname())) {
					tableColMetaList.add(colMeta);
					tableMap.put(colMeta.getcEtbname(), tableColMetaList);
				} else if (i == colMetaList.size() - 1) {
					tableColMetaList.add(colMeta);
					tableMap.put(colMeta.getcEtbname(), tableColMetaList);
				} else {
					tableColMetaList.add(colMeta);
				}
			}
		}
		// 封装成Map<"表名",List<ColMeta>>
		// 开始校验
		List<Map<String, Object>> xmlTableList = (List<Map<String, Object>>) resultMap
				.get(set.toString().replace("[", "").replace("]", ""));
		Map<String, Object> resultValidateStatus = ValidateXml(xmlTableList, tableColMetaList);
		if ((boolean) resultValidateStatus.get("status")) {
			// delete history data before insert data to table
			try {
				delete(resultMap);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 校验成功,入库操作
			Map<String, Object> sqlMap = null;
			try {
				sqlMap = saveXml(xmlTableList, tableColMetaList, xml1);
			} catch (RuntimeException e) {
			} catch (Exception e) {
			}
			if ((boolean) sqlMap.get("status")) {
				/**
				 * 处理成功提交事务
				 */
				// transactionManager.commit(transactionStatus);
				// 入库成功
				resultMap.put("status", true);
				resultMap.put("msg", "校验xml通过，入库成功！");
			} else {
				/**
				 * 处理失败回滚操作
				 */
//				transactionManager.rollback(transactionStatus);
//				return sqlMap;
			}
		} else {// 校验失败
				// 写日志记录ajbs等信息，记录失败原因
			resultMap.put("status", false);
			resultMap.put("msg", resultValidateStatus.get("msg"));
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> parseXmlContent(String xml) {
		Map<String, Object> returnMap = new HashMap<>();
		List<Object> pathList = new ArrayList<>();
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		List<Element> childElements = rootElement.elements();
		for (Element element : childElements) {
			if (element.getName().equalsIgnoreCase("BRIEF")) {// 数量
				List<Element> numElement = element.elements();
				returnMap.put("num", Integer.valueOf(numElement.get(0).getTextTrim()));
			} else {
				List<Element> numElement = element.elements();
				for (Element zipElement : numElement) {
					pathList.add(zipElement.getTextTrim());
				}
			}
		}
		returnMap.put("path", pathList);
		return returnMap;
	}

	public static String getYesterdayDate() throws ParseException {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(nowDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		Date endDate = dft.parse(dft.format(date.getTime()));
		return new SimpleDateFormat("yyyy-MM-dd").format(endDate);
	}

	public static InputStream getRequest(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		// 打印响应状态
		System.out.println(response.getStatusLine());
		System.out.println(response);
		System.out.println(entity);
		System.out.println(entity.getContentLength());
		System.out.println(entity.getContent());
		InputStream input = entity.getContent();
		return input;
	}

	public static final byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	private static Map<String, Object> parseXml(String xml) {
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		List<Element> childElements = rootElement.elements();
		List<Map<String, Object>> xmlContent = getXmlContent(childElements);
		Map<String, Object> xmlMap = new HashMap<String, Object>();
		xmlMap.put(rootElement.getName(), xmlContent);
		return xmlMap;
	}

	private static List<Map<String, Object>> getXmlContent(List<Element> childElements) {

		List<Map<String, Object>> tablesList = new ArrayList<Map<String, Object>>();
		for (Element element : childElements) {
			Map<String, Object> tableMap = new HashMap<String, Object>();
			if (element.elements().size() == 0) {// ajbs
				tableMap.put(element.getName(), element.getText());
			} else {
				tableMap.put(element.getName(), getTableContent(element.elements()));
			}
			tablesList.add(tableMap);
		}
		return tablesList;
	}

	private static List<Object> getTableContent(List<Element> elements) {
		List<Object> tableList = new ArrayList<Object>();
		Map<String, Object> colMap = new HashMap<String, Object>();// 存放没有R标签
		List<Object> list = new ArrayList<Object>();
		List<Object> listR = new ArrayList<Object>();// 存放多个R标签内容
		boolean addList = false;
		for (Element element : elements) {
			Map<String, Object> map_Rcol = new HashMap<String, Object>();
			if ("R".equalsIgnoreCase(element.getName())) {// xml中有一张表多条记录
				addList = true;
				@SuppressWarnings("unchecked")
				List<Element> list_R = element.elements();
				for (Element element_R : list_R) {// 所有R
					map_Rcol.put(element_R.getName(), element_R.getText());
				}
				listR.add(map_Rcol);
			} else {
				colMap.put(element.getName(), element.getText());
			}
		}
		if (addList) {
			tableList.add(listR);
		} else {
			list.add(colMap);
			tableList.add(list);
		}
		return tableList;
	}

	private static void delete(Map<String, Object> xmlMap) throws Exception {
		String param = xmlMap.keySet().toString().replace("案件", "").replace("[", "").replace("]", "").trim();
		List<TableMeta> tableMetaList = pcajService.getTableMeta(param);
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) xmlMap
				.get(xmlMap.keySet().toString().replace("[", "").replace("]", "").trim());
		String midSql = "";
		String ajbs = mapList.get(0).get(mapList.get(0).keySet().toString().replace("[", "").replace("]", "").trim())
				.toString();
		for (TableMeta tableMeta : tableMetaList) {
			midSql = midSql + " delete from " + tableMeta.getcEtbname() + " where ajbs = " + ajbs + "; ";
		}
		String deleteSql = "begin " + midSql + " end;";
		// patch sql
		logger.info("开始删除案件标识为：" + ajbs + "的记录，执行sql为：" + deleteSql);
		pcajService.deleteTableRecord(deleteSql);
	}

	private static Map<String, Object> ValidateXml(List<Map<String, Object>> xmlTableList,
			List<ColMeta> tableColMetaList) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (xmlTableList.get(0).containsKey("案件标识") && StringUtils.isEmpty(xmlTableList.get(0).get("案件标识"))) {
			logger.error("xml中[案件标识]值为空！");
			result.put("status", false);
			result.put("msg", "xml中[案件标识]值为空！");
			return result;
		} else if (!xmlTableList.get(0).containsKey("案件标识")) {
			logger.error("xml中没有[案件标识]标签！");
			result.put("status", false);
			result.put("msg", "xml中没有[案件标识]标签！");
			return result;
		}
		boolean status = true;
		StringBuffer strBuf = new StringBuffer();
		// 存取筛选出来的元数据表信息
		Map<String, Object> metaMap = new HashMap<String, Object>();
		for (Map<String, Object> xmlTable : xmlTableList) {
			String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
			// 遍历元数据的表名，获取到对应表的元数据
			List<ColMeta> colMetalist = Collections.synchronizedList(new ArrayList<ColMeta>());
			ColMeta tableColMeta = null;
			for (int i = 0; i < tableColMetaList.size(); i++) {
				tableColMeta = tableColMetaList.get(i);
				// 筛选出tableColMeta.getcCcolname()=tableName的 元数据
				if (tableName.equals(tableColMeta.getcCtbname())) {
					colMetalist.add(tableColMeta);
				}
			}
			metaMap.put(tableName, colMetalist);
		}
		// 校验pk,uk非空；字段名与元数据是否吻合
		for (Map<String, Object> xmlTable : xmlTableList) {
			if (xmlTable.keySet().size() > 1) {
				logger.error("some thing error!");
				strBuf.append("xml error!");
				status = false;
			} else if (!"案件标识".equals(xmlTable.keySet().toString().replace("[", "").replace("]", ""))) {
				String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
				List<Object> list = (List<Object>) xmlTable.get(tableName);
				// 是否包含字段名
				boolean isContain = false;
				for (Object obj : list) {
					List<Map<String, Object>> listM = (List<Map<String, Object>>) obj;
					for (Map<String, Object> objMap : listM) {
						// 遍历
						List<ColMeta> colMetaList = (List<ColMeta>) metaMap.get(tableName);
						for (ColMeta colMeta : colMetaList) {
							// 字段名校验
							for (Entry<String, Object> entry : objMap.entrySet()) {
								for (ColMeta colMet : colMetaList) {
									if (colMet.getcCcolname().equals(entry.getKey())) {
										isContain = true;
										break;
									}
								}
								if (!isContain) {
									logger.error("ERROR！元数据中没有表" + tableName + "的 " + entry.getKey() + "字段 ");
									strBuf.append("ERROR！元数据中没有表" + tableName + "的 " + entry.getKey() + "字段 \n ");
									status = false;
								}
								isContain = false;
							}
							// 主键校验
							if (colMeta.getcPucol().equalsIgnoreCase("YES") && !colMeta.getcCcolname().equals("案件标识")) {
								String cCcolname = colMeta.getcCcolname();
								for (Entry<String, Object> entry : objMap.entrySet()) {
									if (cCcolname.equals(entry.getKey())) {
										if (StringUtils.isEmpty(entry.getValue())) {
											logger.error("ERROR！xml中主键" + colMeta.getcPucol() + "值不能为空！\n ");
											strBuf.append("ERROR！xml中主键" + colMeta.getcPucol() + "值不能为空！\n ");
											status = false;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		result.put("status", status);
		if (status) {
			result.put("msg", "xml校验成功！");
		} else {
			result.put("msg", strBuf);
		}
		return result;
	}

	private static Map<String, Object> saveXml(List<Map<String, Object>> xmlTableList,
			List<ColMeta> tableColMetaList, String xml) {

		Map<String, Object> sqlMap = new HashMap<String, Object>();
		Map<String, Object> xmlTable;
		/**
		 * 如果插入sql出现异常，则设置为false，退出循环
		 */
		boolean isDo = true;
		for (int i = 1; i < xmlTableList.size(); i++) {// get xml tableName
			Map<String, Object> param = new HashMap<String, Object>();
			xmlTable = xmlTableList.get(i);
			String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
			// 需要插入的表 对应的元数据信息
			List<ColMeta> tableColMeta = Collections.synchronizedList(new ArrayList<ColMeta>());
			for (ColMeta tableColMet : tableColMetaList) {// get related meta table
				if (tableColMet.getcCtbname().equals(tableName)) {
					tableColMeta.add(tableColMet);
				}
			}
			// 组装插入sql，取的字段从元数据中取
			String column = "";
			// 封装#{参数，javaType=?}
			String val = "";
			for (int k = 0; k < tableColMeta.size(); k++) {
				if (k == tableColMeta.size() - 1) {
					column = column + tableColMeta.get(k).getcEcolname().toUpperCase();
					val = val + "#{params." + tableColMeta.get(k).getcEcolname().toUpperCase() + ", jdbcType="
							+ MappingDataType.dataType.get(tableColMeta.get(k).getcDatatype()).toUpperCase() + "}";
				} else {
					column = column + tableColMeta.get(k).getcEcolname().toUpperCase() + ", ";
					val = val + "#{params." + tableColMeta.get(k).getcEcolname().toUpperCase() + ", jdbcType="
							+ MappingDataType.dataType.get(tableColMeta.get(k).getcDatatype()).toUpperCase() + "}, ";
				}
			}
			// 如果tableColMeta.get(0)无数据，则说明此表对应的元数据为空
			String insertSql = "";
			if (tableColMeta.size() > 0) {
				insertSql = "insert into " + tableColMeta.get(0).getcEtbname() + "(" + column + ") values (" + val
						+ ")";
			} else {
				try {
					logger.error("缺少元数据信息");
					throw new Exception("缺少元数据信息");
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
			// AJBS, ID, REG_TIME, UPDATE_TIME, DEPT_CODE, APP_CODE,
			// AH, AJZLX, SDQQRQ, SARQ, AJJZJD, SALY, SAAJAH, DCQZSCAH, SPLCYGK
			param.put("AJBS", xmlTableList.get(0).get("案件标识").toString());
			param.put("REG_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			param.put("UPDATE_TIME", new Date(new java.util.Date().getTime()));
			param.put("APP_CODE", "002");
			param.put("DEPT_CODE", "111111");

			// 创建多条记录用的map参数
			for (Entry<String, Object> entry : xmlTable.entrySet()) {
				List<Object> obj = (List<Object>) entry.getValue();
				for (int j = 0; j < obj.size(); j++) {
					List<Object> list = (List<Object>) obj.get(j);
					for (int h = 0; h < list.size(); h++) {
						Map<String, Object> map = (Map<String, Object>) list.get(h);
						param.put("ID", UUID.randomUUID().toString().replace("-", ""));
						for (int k = 0; k < tableColMeta.size(); k++) {
							boolean isFound = false;
							for (Entry<String, Object> ent : map.entrySet()) {
								if (tableColMeta.get(k).getcCcolname().equals(ent.getKey())) {
									if (tableColMeta.get(k).getcDatatype().equalsIgnoreCase("DATE")) {
										// 字符串转date
										java.util.Date date = null;
										if (ent.getValue().toString().contains("T")) {
											SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");// 注意格式化的表达式
											try {
												date = format.parse(ent.getValue().toString());
											} catch (ParseException e) {
												e.printStackTrace();
											}
										} else {
											SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 注意格式化的表达式
											try {
												date = format.parse(ent.getValue().toString());
											} catch (ParseException e) {
												e.printStackTrace();
											}
										}
										param.put(tableColMeta.get(k).getcEcolname(), date);
									} else if (tableColMeta.get(k).getcDatatype().equalsIgnoreCase("BLOB")) {
										// 字符串转byte[]
										byte[] b = ent.getValue().toString().getBytes();
										param.put(tableColMeta.get(k).getcEcolname(), b);
									} else {
										param.put(tableColMeta.get(k).getcEcolname(), ent.getValue());
									}
									isFound = true;
								}
							}
							String colName = tableColMeta.get(k).getcEcolname();
							if (!isFound && !colName.equals("AJBS") && !colName.equals("ID")
									&& !colName.equals("REG_TIME") && !colName.equals("UPDATE_TIME")
									&& !colName.equals("APP_CODE") && !colName.equals("DEPT_CODE")) {
								param.put(tableColMeta.get(k).getcEcolname(), "");
							}
						}
						int result = 0;
						try {
							result = pcajService.saveCase(insertSql, param);
						} catch (Exception e) {
							logger.error(e.getMessage());
							sqlMap.put("status", false);
							sqlMap.put("msg", "unsuccess:" + e.getMessage());
							isDo = false;
						}
						if (result == 1) {
							logger.info("成功执行sql：" + insertSql);
						}
					}
				}
			}
			/**
			 * 抛异常退出循环
			 */
			if (!isDo) {
				break;
			}
		}
		if (!isDo) {
			return sqlMap;
		} else {
			sqlMap.put("status", true);
			sqlMap.put("msg", "成功咯");
			return sqlMap;
		}
	}
}
