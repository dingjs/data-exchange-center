package data.exchange.center.service.pcaj.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.pcaj.domain.ColMeta;
import data.exchange.center.service.pcaj.domain.TableMeta;
import data.exchange.center.service.pcaj.mapper.PcajMapper;
import data.exchange.center.service.pcaj.service.PcajService;
import data.exchange.center.service.pcaj.util.MappingDataType;
import data.exchange.center.service.pcaj.util.SpringContextUtil;

@Service
public class PcajServiceImpl implements PcajService {

	private static Logger logger = LoggerFactory.getLogger(PcajServiceImpl.class);

	@Autowired
	private PcajMapper pcajMapper;
	@Autowired
	private PcajService pcajService;

	private String SOURCE_CODE = "007";

	@Override
	public List<ColMeta> getColMeta(String ajlx) throws Exception {
		return pcajMapper.getColMeta(ajlx);
	}

	@Override
	public void deleteTableRecord(String deleteSql) throws Exception {
		pcajMapper.deleteTableRecord(deleteSql);
	}

	@Override
	public List<TableMeta> getTableMeta(String ajlxChName) throws Exception {
		return pcajMapper.getTableMeta(ajlxChName);
	}

	@Override
	public int saveCase(String insertSql, Map<String, Object> params) throws Exception {
		params.put("insertSql", insertSql);
		return pcajMapper.saveCase(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> parseXml(String xml, String prex, String ajlxCode) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
	        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
	        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
	        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
	        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
	        TransactionStatus transactionStatus = (TransactionStatus) transactionManager.getTransaction(definition);
	        
	        Map<String, Object> resultMap = parseXmlContent(xml);
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
			if (resultValidateStatus.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)) {
				delete(resultMap);
				/**
				 * 入案件主表数据
				 */
				Map<String, Object> map = saveAjMainTableInfo(resultMap, prex, ajlxCode);
				if(map.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_FAIL)) {//主表入库错误直接返回数据
					transactionManager.rollback(transactionStatus);
					return map;
				}
				// 校验成功,入库操作
				Map<String, Object> sqlMap = null;
				try {
					sqlMap = saveXml(xmlTableList, tableColMetaList, prex);
				} catch (Exception e) {
					e.printStackTrace();
					params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
					params.put(CodeUtil.RETURN_MSG, e.getMessage());
					return params;
				}
				if (sqlMap.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)) {
					/**
					 * 处理成功提交事务
					 */
					 transactionManager.commit(transactionStatus);
					// 入库成功
					 params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
					 params.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
					 return params;
				} else {
					/**
					 * 处理失败回滚操作
					 */
					 transactionManager.rollback(transactionStatus);
					 return sqlMap;
				}
			} else {// 校验失败
					// 写日志记录ajbs等信息，记录失败原因
				resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				resultMap.put(CodeUtil.RETURN_MSG, resultValidateStatus.get(CodeUtil.RETURN_MSG));
				return resultMap;
			}
		}catch(Exception e) {
			e.printStackTrace();
			params.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			params.put(CodeUtil.RETURN_MSG, e.getMessage());
			return params;
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> parseXmlContent(String xml) {
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

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getXmlContent(List<Element> childElements) {

		List<Map<String, Object>> tablesList = new ArrayList<Map<String, Object>>();
		for (Element element : childElements) {
			Map<String, Object> tableMap = new HashMap<String, Object>();
			if (element.elements().size() == 0) {// ajbs
				tableMap.put(element.getName(), element.getText());
			} else {
				// 最高与省高院的表名中文名不同，需要做适配 2017-12-27 16:06:58
				if (element.getName().equals("立案移交和退案")) {
					tableMap.put("立案移交和退案情况", getTableContent(element.elements()));
				} else {
					tableMap.put(element.getName(), getTableContent(element.elements()));
				}
			}
			tablesList.add(tableMap);
		}
		return tablesList;
	}

	private List<Object> getTableContent(List<Element> elements) {
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

	@SuppressWarnings("unchecked")
	private void delete(Map<String, Object> xmlMap) throws Exception {
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
		String deleteSql = "begin delete from buf_eaj where ajbs = " + ajbs + ";" + midSql + " end;";
		// patch sql
		logger.info("开始删除案件标识为：" + ajbs + "的记录，执行sql为：" + deleteSql);
		pcajService.deleteTableRecord(deleteSql);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> ValidateXml(List<Map<String, Object>> xmlTableList, List<ColMeta> tableColMetaList) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (xmlTableList.get(0).containsKey("案件标识") && StringUtils.isEmpty(xmlTableList.get(0).get("案件标识"))) {
			logger.error("xml中[案件标识]值为空！");
			result.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			result.put(CodeUtil.RETURN_FAIL, "xml中[案件标识]值为空！");
			return result;
		} else if (!xmlTableList.get(0).containsKey("案件标识")) {
			logger.error("xml中没有[案件标识]标签！");
			result.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			result.put(CodeUtil.RETURN_FAIL, "xml中没有[案件标识]标签！");
			return result;
		}
		String status = CodeUtil.RETURN_SUCCESS;
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
				status = CodeUtil.RETURN_FAIL;
			} else if (!"案件标识".equals(xmlTable.keySet().toString().replace("[", "").replace("]", ""))) {
				String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
				List<Object> list = (List<Object>) xmlTable.get(tableName);
				// 是否包含字段名
				boolean isContain = false;
				boolean isD = true;
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
									logger.error("ERROR！元数据中没有表[" + tableName + "]的 [" + entry.getKey() + "]字段 ");
									strBuf.append("ERROR！元数据中没有表[" + tableName + "]的 [" + entry.getKey() + "]字段 \n ");
									status = CodeUtil.RETURN_FAIL;
									isD = false;
									break;
								}
								isContain = false;
								if(!isD) {
									break;
								}
							}
							// 主键校验
							if (colMeta.getcPucol().equalsIgnoreCase("YES") && !colMeta.getcCcolname().equals("案件标识")) {
								String cCcolname = colMeta.getcCcolname();
								for (Entry<String, Object> entry : objMap.entrySet()) {
									if (cCcolname.equals(entry.getKey())) {
										if (StringUtils.isEmpty(entry.getValue())) {
											logger.error("ERROR！xml中主键[" + colMeta.getcPucol() + "]值不能为空！\n ");
											strBuf.append("ERROR！xml中主键[" + colMeta.getcPucol() + "]值不能为空！\n ");
											status = CodeUtil.RETURN_FAIL;
											isD = false;
											break;
										}
									}
									if(!isD) {
										break;
									}
								}
								if(!isD) {
									break;
								}
							}
							if(!isD) {
								break;
							}
						}
						if(!isD) {
							break;
						}
					}
					if(!isD) {
						break;
					}
				}
			}
		}
		result.put(CodeUtil.RETURN_CODE, status);
		if (status.equals(CodeUtil.RETURN_SUCCESS)) {
			result.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
		} else {
			result.put(CodeUtil.RETURN_MSG, strBuf);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> saveXml(List<Map<String, Object>> xmlTableList, List<ColMeta> tableColMetaList,
			String prex) throws ParseException {

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
			List<ColMeta> tableColMeta = new ArrayList<ColMeta>();
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
							+ MappingDataType.dataType.get(tableColMeta.get(k).getcDatatype().toUpperCase()).toUpperCase() + "}";
				} else {
					column = column + tableColMeta.get(k).getcEcolname().toUpperCase() + ", ";
					val = val + "#{params." + tableColMeta.get(k).getcEcolname().toUpperCase() + ", jdbcType="
							+ MappingDataType.dataType.get(tableColMeta.get(k).getcDatatype().toUpperCase()).toUpperCase() + "}, ";
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
			Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			param.put("REG_TIME", time);
			param.put("UPDATE_TIME", time);
			param.put("APP_CODE", SOURCE_CODE);
			param.put("DEPT_CODE", MappingDataType.getDataType().get(prex));

			// 创建多条记录用的map参数
			for (Entry<String, Object> entry : xmlTable.entrySet()) {
				List<Object> obj = (List<Object>) entry.getValue();
				for (int j = 0; j < obj.size(); j++) {
					List<Object> list = (List<Object>) obj.get(j);
					//手动增加序号 破产申请审查案件
					int xh = 1;
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
						try {
							if(tableName.equals("文书")) {
								param.put("XH", xh);
								xh = xh + 1;
							}
							pcajService.saveCase(insertSql, param);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(e.getMessage());
							sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
							sqlMap.put(CodeUtil.RETURN_MSG, "unsuccess:" + e.getMessage());
							isDo = false;
						}
						if (!isDo) {
							break;
						}
					}
					if (!isDo) {
						break;
					}
				}
				if (!isDo) {
					break;
				}
			}
			if (!isDo) {
				break;
			}
		}
		if (!isDo) {
			return sqlMap;
		} else {
			sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			sqlMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
			return sqlMap;
		}
	}

	/**
	 * 
	 * @function
	 * @author wenyuguang
	 * @creaetime 2017年12月27日 下午5:56:45
	 * @param xmlMap
	 * @param prex
	 *            最高法院代码
	 * @param ajlx
	 *            案件类型 代码
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> saveAjMainTableInfo(Map<String, Object> xmlMap, String prex, String ajlx) throws ParseException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> mainTableParams = new HashMap<String, Object>();
		try {
			mainTableParams.put("SRCCODE", SOURCE_CODE);
			for (String key : xmlMap.keySet()) {
				List<Map<String, Object>> mapList = (List<Map<String, Object>>) xmlMap.get(key);
				for (Map<String, Object> map : mapList) {
					if (map.containsKey("案件标识")) {
						mainTableParams.put("AJBS", map.get("案件标识"));
					}
				}
			}

			mainTableParams.put("AJLX", ajlx);
			mainTableParams.put("DEPTCODE", MappingDataType.getDataType().get(prex));
			Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			mainTableParams.put("REGDATE", time);
			mainTableParams.put("LASTUPDATE", time);
			mainTableParams.put("CONTENTUPD", "");
			
			List<Map<String, Object>> mapList = (List<Map<String, Object>>) xmlMap.get(xmlMap.keySet().toString().replace("[", "").replace("]", ""));
			
			if (ajlx.equals("25")) {
				// 25民事破产案件
				// ah--收案和立案信息》》案号
				// ajjzjd--审理阶段》》案件进展阶段
				// larq--收案和立案信息》》立案日期
				// jarq--结案情况》》结案日期
				// laar--收案和立案信息》》立案案由
				// cbsptbs--案件审理情况》》承办审判庭标识
				// cbspt--案件审理情况》》承办审判庭
				// cbr--案件审理情况》》承办人
				for(Map<String, Object> map:mapList) {
					if(map.containsKey("收案和立案信息")) {
						if(map.keySet().toString().replace("[", "").replace("]", "").equals("收案和立案信息")) {
							Map<String, Object> param = (Map<String, Object>) ((List<Object>) ((List<Object>) map.get("收案和立案信息")).get(0)).get(0);
							mainTableParams.put("AH", param.get("案号") == null ? "" : param.get("案号"));
							mainTableParams.put("LARQ", param.get("立案日期") ==null?"":new SimpleDateFormat("yyyy-MM-dd").parse(param.get("立案日期").toString()));
							mainTableParams.put("LAAY", param.get("立案案由") == null ? "" : param.get("立案案由"));
							break;
						}
					}else {
						mainTableParams.put("AH", "");
						mainTableParams.put("LARQ", "");
						mainTableParams.put("LAAY", "");
					}
				}
				for(Map<String, Object> map:mapList) {
					if(map.containsKey("审理阶段")) {
						if(map.keySet().toString().replace("[", "").replace("]", "").equals("审理阶段")) {
							Map<String, Object> param = (Map<String, Object>) ((List<Object>) ((List<Object>) map.get("审理阶段")).get(0)).get(0);
							mainTableParams.put("AJJZJD", param.get("案件进展阶段") == null ? "" : param.get("案件进展阶段"));
							break;
						}
					}else {
						mainTableParams.put("AJJZJD", "");
					}
				}
				for(Map<String, Object> map:mapList) {
					if(map.containsKey("结案情况")) {
						if(map.keySet().toString().replace("[", "").replace("]", "").equals("结案情况")) {
							Map<String, Object> param = (Map<String, Object>) ((List<Object>) ((List<Object>) map.get("结案情况")).get(0)).get(0);
							mainTableParams.put("JARQ", param.get("结案日期")==null?"":new SimpleDateFormat("yyyy-MM-dd").parse(param.get("结案日期").toString()));
							break;
						}
					}else {
						mainTableParams.put("JARQ", "");
					}
				}
				for(Map<String, Object> map:mapList) {
					if(map.containsKey("案件审理情况")) {
						if(map.keySet().toString().replace("[", "").replace("]", "").equals("案件审理情况")) {
							Map<String, Object> param = (Map<String, Object>) ((List<Object>) ((List<Object>) map.get("案件审理情况")).get(0)).get(0);
							mainTableParams.put("CBSPTBS", param.get("承办审判庭标识") == null ? "" : param.get("承办审判庭标识"));
							mainTableParams.put("CBSPT", param.get("承办审判庭") == null ? "" : param.get("承办审判庭"));
							mainTableParams.put("CBR", param.get("承办人") == null ? "" : param.get("承办人"));
							break;
						}
					}else {
						mainTableParams.put("CBSPTBS", "");
						mainTableParams.put("CBSPT", "");
						mainTableParams.put("CBR", "");
					}
				}
			} else if (ajlx.equalsIgnoreCase("2B")) {
				// 2B破产申请审查案件
				// ah--收案信息》》案号
				// larq--收案信息》》收案日期
				// jarq--结案情况》》结案日期
				for(Map<String, Object> map:mapList) {
					if(map.containsKey("收案信息")) {
						if(map.keySet().toString().replace("[", "").replace("]", "").equals("收案信息")) {
							Map<String, Object> param = (Map<String, Object>) ((List<Object>) ((List<Object>) map.get("收案信息")).get(0)).get(0);
							mainTableParams.put("AH", param.get("案号") == null ? "" : param.get("案号"));
							mainTableParams.put("LARQ", param.get("收案日期")==null?"":new SimpleDateFormat("yyyy-MM-dd").parse(param.get("收案日期").toString()));
							break;
						}
					}else {
						mainTableParams.put("LARQ", "");
					}
				}
				for(Map<String, Object> map:mapList) {
					if(map.containsKey("结案情况")) {
						if(map.keySet().toString().replace("[", "").replace("]", "").equals("结案情况")) {
							Map<String, Object> param = (Map<String, Object>) ((List<Object>) ((List<Object>) map.get("结案情况")).get(0)).get(0);
							mainTableParams.put("JARQ", param.get("结案日期")==null?"":new SimpleDateFormat("yyyy-MM-dd").parse(param.get("结案日期").toString()));
							break;
						}
					}else {
						mainTableParams.put("JARQ", "");
					}
				}
				mainTableParams.put("AJJZJD", "");
				mainTableParams.put("LAAY", "");
				mainTableParams.put("CBSPTBS", "");
				mainTableParams.put("CBSPT", "");
				mainTableParams.put("CBR", "");
			}
			pcajMapper.insertTable_uf_eaj(mainTableParams);
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			returnMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
			return returnMap;
		}catch(Exception e) {
			e.printStackTrace();
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			returnMap.put(CodeUtil.RETURN_MSG, e.getMessage());
			return returnMap;
		}
	}
}
