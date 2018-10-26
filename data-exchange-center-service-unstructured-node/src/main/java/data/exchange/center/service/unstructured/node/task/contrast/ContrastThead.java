//package data.exchange.center.service.unstructured.node.task.contrast;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import data.exchange.center.service.unstructured.node.domain.TempEajJz;
//import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
//import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
//import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
//import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
//import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;
//import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
//import data.exchange.center.service.unstructured.node.service.ContrstService;
//import data.exchange.center.service.unstructured.node.util.Constant;
//import data.exchange.center.service.unstructured.node.util.DecodeUtil;
//import data.exchange.center.service.unstructured.node.util.ReflectUtils;
//import data.exchange.center.service.unstructured.node.util.VeDate;
//import oracle.sql.BLOB;
////@Transactional
//public class ContrastThead {
//	private static Logger logger = LoggerFactory.getLogger(ContrastThead.class);
//
//	// 案件标识
//	private String ajbs;
//	private String ajlx;
//	private String fydm;
//	private String ajzt;
//	private int level;
//	private String ah;
//	private Date larq;
//	private RedisTemplate<Object, Object> redisTemplate;
//	private ContrstService contrstService;
//	private AgentPushDataMapper agentPushDataMapper;
//	private List<TempEajJz> tempEajJzList;
//	private List<TempEajJzwjAllNew> tempEajJzwjAllNewList;
//	boolean BJz = false;
//	List<String> listXh = new ArrayList<>();
//	List<String> tempEajJzwjAllNewXh = new ArrayList<>();
//
//	// 传递案件标识
//	public ContrastThead(String ajbs, String ajlx, String fydm, String ajzt,
//			RedisTemplate<Object, Object> redisTemplate, ContrstService contrstService,
//			AgentPushDataMapper agentPushDataMapper, Integer level, String ah, Date larq) {
//		this.ajbs = ajbs;
//		this.ajlx = ajlx;
//		this.fydm = fydm;
//		this.ajzt = ajzt;
//		this.level = level;
//		this.ah = ah;
//		this.larq = larq;
//		this.redisTemplate = redisTemplate;
//		this.contrstService = contrstService;
//		this.agentPushDataMapper = agentPushDataMapper;
//
//	}
//
//	*//**
//	 * 写入数据到中心缓存库
//	 * 
//	 * @param data
//	 * @param tableName
//	 *//*
//	public void pushSgyTempTable(ConcurrentMap<String, Object> data, String tableName) throws Exception {
//
//		if (tableName.equalsIgnoreCase(Constant.C_EAJ_JZ)) {
//			TempEajJz tempEajJz = ReflectUtils.getBean(data, TempEajJz.class);
//			byte[] b = null;
//			if (tempEajJz.getNR() == null) {
//				b = null;
//			} else {
//				b = DecodeUtil.blobToBytes((BLOB) tempEajJz.getNR());
//			}
//
//			if (!org.springframework.util.StringUtils.isEmpty(tempEajJz.getYSBZ())
//					&& ("2").equalsIgnoreCase(tempEajJz.getYSBZ()) && (BLOB) tempEajJz.getNR() != null) {
//				byte[] before = DecodeUtil.blobToBytes((BLOB) tempEajJz.getNR());
//				InputStream in = new ByteArrayInputStream(before);
//				byte[] blobByte = DecodeUtil.deCompress(in);
//				tempEajJz.setWJDX(Float.valueOf(blobByte.length));
//				tempEajJz.setNR(blobByte);
//			} else {
//				tempEajJz.setNR(b);
//			}
//			tempEajJz.setFYDM(fydm);
//			agentPushDataMapper.pushEajJzToSGY(tempEajJz);
//
//		} else if (tableName.equalsIgnoreCase(Constant.C_EAJ_JZWJ_ALL_NEW)) {
//			TempEajJzwjAllNew tempEajJzwjAllNew = ReflectUtils.getBean(data, TempEajJzwjAllNew.class);
//			tempEajJzwjAllNewXh.add(tempEajJzwjAllNew.getXH());
//			for (int i = 0; i < tempEajJzwjAllNewList.size(); i++) {
//				TempEajJzwjAllNew eajJzwjAllNew = tempEajJzwjAllNewList.get(i);
//				if (eajJzwjAllNew.getXH().equals(tempEajJzwjAllNew.getXH())) {
//					if (JzwjAllNewToJz(tempEajJzwjAllNew)) {
//						eajJzwjAllNew.setFYDM(fydm);
//						eajJzwjAllNew.setACTIONTYPE(Constant.C_TYPE_INSERT);
//						agentPushDataMapper.pushEajJzwjAllNewToSGY(eajJzwjAllNew);
//					}
//				}
//
//			}
//
//		} else if (tableName.equalsIgnoreCase(Constant.C_TEMP_EAJ_MLXX)) {
//			TempEajMlxx tempEajMlxx = ReflectUtils.getBean(data, TempEajMlxx.class);
//			tempEajMlxx.setFYDM(fydm);
//			agentPushDataMapper.pushEajMlxxToSGY(tempEajMlxx);
//
//		} else if (tableName.equalsIgnoreCase(Constant.C_TEMP_EAJ_MLXX_GC)) {
//			TempEajMlxxGc tempEajMlxxGc = ReflectUtils.getBean(data, TempEajMlxxGc.class);
//			tempEajMlxxGc.setFYDM(fydm);
//			agentPushDataMapper.pushEajMlxxGcToSGY(tempEajMlxxGc);
//
//		} else if (tableName.equalsIgnoreCase(Constant.C_EAJ_SSJCYX)) {
//			TempEajSsjcyx tempEajMlxxGc = ReflectUtils.getBean(data, TempEajSsjcyx.class);
//			tempEajMlxxGc.setFYDM(fydm);
//			agentPushDataMapper.pushEajSsjcyxToSGY(tempEajMlxxGc);
//
//		}
//	}
//
//	// 如果eaj_JzwjAllNew有更新，需要把对应的eaj_jz文件更新
//	@SuppressWarnings("unchecked")
//	public boolean JzToJzwjAllNew(TempEajJz tempEajJz) throws Exception {
//		List<ConcurrentMap<String, Object>> listRedis = Collections.synchronizedList(new ArrayList<>());
//		ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
//		String bh = ajbs + Constant.C_EAJ_JZWJ_ALL_NEW;// 组装序号
//		ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
//		boolean inJz = true;
//		// 从redis中获取数据
//		if (redisTemplate.hasKey(bh)) {
//			listRedis = (List<ConcurrentMap<String, Object>>) operations.get(bh);
//		}
//
//		try {
//			for (int i = 0; i < tempEajJzwjAllNewXh.size(); i++) {
//				if (tempEajJzwjAllNewXh.get(i).equals(tempEajJz.getXH())) {
//					inJz = false;
//				}
//			}
//			if (inJz) {
//
//				for (int i = 0; i < listRedis.size(); i++) {
//					Map<String, Object> redisMap = listRedis.get(i);
//					if (redisMap.get("xh").equals(tempEajJz.getXH())) {
//						listRedis.remove(i);
//					}
//				}
//				for (int i = 0; i < tempEajJzwjAllNewList.size(); i++) {
//					TempEajJzwjAllNew tempEajJzwjAllNew = tempEajJzwjAllNewList.get(i);
//					if (tempEajJz.getXH().equals(tempEajJzwjAllNew.getXH())) {
//						listXh.add(tempEajJzwjAllNew.getXH());
//						BJz = true;
//						tempEajJzwjAllNew.setFYDM(fydm);
//						tempEajJzwjAllNew.setACTIONTYPE(Constant.C_TYPE_INSERT);
//						agentPushDataMapper.pushEajJzwjAllNewToSGY(tempEajJzwjAllNew);
//						map = new ConcurrentHashMap<>();
//						// 准备更新redis的数据
//						map.put("xh", tempEajJz.getXH());
//						map.put("ajlx", ajlx);
//						map.put("date", tempEajJz.getLASTUPDATE());
//						listRedis.add(map);
//					}
//				}
//			}
//			operations.set(bh, listRedis);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
//
//	// 如果eaj_JzwjAllNew有更新，需要把对应的eaj_jz文件更新
//	@SuppressWarnings("unchecked")
//	public boolean JzwjAllNewToJz(TempEajJzwjAllNew tempEajJzwjAllNew) throws Exception {
//		List<ConcurrentMap<String, Object>> listRedis = Collections.synchronizedList(new ArrayList<>());
//		ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
//		String bh = ajbs + Constant.C_EAJ_JZ;// 组装序号
//		ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
//		boolean inJz = true;
//		// 从redis中获取数据
//		if (redisTemplate.hasKey(bh)) {
//			listRedis = (List<ConcurrentMap<String, Object>>) operations.get(bh);
//		}
//		try {
//			for (int i = 0; i < listXh.size(); i++) {
//				if (listXh.get(i).equals(tempEajJzwjAllNew.getXH())) {
//					inJz = false;
//				}
//			}
//			if (inJz) {
//				for (int i = 0; i < tempEajJzList.size(); i++) {
//					TempEajJz tempEajJz = tempEajJzList.get(i);
//					if (tempEajJz.getXH().equals(tempEajJzwjAllNew.getXH())) {
//						if (tempEajJz.getNR() != null) {
//							return false;
//						} else {
//							listXh.add(tempEajJzwjAllNew.getXH());
//							BJz = true;
//							tempEajJz.setFYDM(fydm);
//							tempEajJz.setACTIONTYPE(tempEajJzwjAllNew.getACTIONTYPE());
//							agentPushDataMapper.pushEajJzToSGY(tempEajJz);
//							map = new ConcurrentHashMap<>();
//							// 准备更新redis的数据
//							map.put("xh", tempEajJz.getXH());
//							map.put("ajlx", ajlx);
//							map.put("date", tempEajJz.getLASTUPDATE());
//							listRedis.add(map);
//						}
//					}
//				}
//			}
//
//			operations.set(bh, listRedis);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
//
//	@SuppressWarnings({ "unchecked" })
//	public boolean handleDate(List<ConcurrentMap<String, Object>> listMapTempEaj, String tableName) throws Exception {
//
//		List<ConcurrentMap<String, Object>> list = Collections.synchronizedList(new ArrayList<>());
//		ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
//		List<ConcurrentMap<String, Object>> listRedis = Collections.synchronizedList(new ArrayList<>());
//		ConcurrentMap<String, Object> data = new ConcurrentHashMap<String, Object>();
//		ConcurrentMap<String, Object> redisData = new ConcurrentHashMap<String, Object>();
//		ConcurrentMap<String, Object> redisDataDelete = new ConcurrentHashMap<String, Object>();
//
//		boolean delBoolean;
//		boolean updateBoolean = false;
//		String xh = "";
//		String yxxh = "";
//		String ajlx = "";
//		String redisxh = "";
//		String redisyxxh = "";
//		String lastUpdate = null;// 业务库更新时间
//		String bh = ajbs + tableName;// 组装序号
//		String redisLastUpdate = "";// redis库中更新时间
//		ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
//		try {
//			// 从redis中获取数据
//			if (redisTemplate.hasKey(bh)) {
//				listRedis = (List<ConcurrentMap<String, Object>>) operations.get(bh);
//			}
//
//			// 重redis中遍历出需要删除的数据
//			for (int i = 0; i < listRedis.size(); i++) {
//				data = listRedis.get(i);
//				redisxh = (String) data.get("xh");
//				redisyxxh = (String) data.get("yxxh");
//				delBoolean = false;
//				// 判断redis里面是否存在业务库中没有的数据
//				for (int j = 0; j < listMapTempEaj.size(); j++) {
//					xh = String.valueOf(listMapTempEaj.get(j).get("XH"));
//					yxxh = (listMapTempEaj.get(j).get("YXXH") == null ? "null"
//							: String.valueOf(listMapTempEaj.get(j).get("YXXH")));
//					redisyxxh = redisyxxh == null ? "null" : redisyxxh;
//					if (redisxh.equals(xh) && redisyxxh.equals(yxxh)) {
//						delBoolean = true;
//						break;
//					}
//				}
//				if (!delBoolean) {
//					redisDataDelete.put("AHDM", ajbs);
//					redisDataDelete.put("XH", redisxh == null ? "" : redisxh);
//					redisDataDelete.put("AJLX", data.get("ajlx") == null ? "" : data.get("ajlx"));
//					redisDataDelete.put("ACTIONTYPE", Constant.C_TYPE_DELETE);
//					redisDataDelete.put("YXXH", redisyxxh);
//					pushSgyTempTable(redisDataDelete, tableName);
//					updateBoolean = true;
//				}
//
//			}
//			// 遍历出需要更新的案件
//			for (int i = 0; i < listMapTempEaj.size(); i++) {
//				data = listMapTempEaj.get(i);
//				// 零时判断，如果更新时间为空就不处理
//				if (!StringUtils.isEmpty(data.get("LASTUPDATE"))) {
//					lastUpdate = VeDate.strDateToDate(String.valueOf(data.get("LASTUPDATE")));
//					xh = String.valueOf(data.get("XH"));
//					ajlx = String.valueOf(data.get("AJLX"));
//					yxxh = String.valueOf(data.get("YXXH"));
//					map = new ConcurrentHashMap<>();
//					// 准备更新redis的数据
//					map.put("xh", xh);
//					map.put("yxxh", yxxh == null ? "" : yxxh);
//					map.put("ajlx", ajlx);
//					map.put("date", data.get("LASTUPDATE"));
//					redisLastUpdate = null;
//					list.add(map);
//					// 遍历数据
//					for (int j = 0; j < listRedis.size(); j++) {
//						redisData = listRedis.get(j);
//						redisxh = (String) redisData.get("xh");
//						redisyxxh = (String) redisData.get("yxxh") == null ? "null" : (String) redisData.get("yxxh");
//						if (xh.equalsIgnoreCase(redisxh) && yxxh.equalsIgnoreCase(redisyxxh)) {
//							redisLastUpdate = VeDate.strDateToDate(String.valueOf(redisData.get("date")));
//							break;
//						}
//					}
//					// 如果在redis中没有获取到数据，就是新增
//					if (StringUtils.isEmpty(redisLastUpdate)) {
//						data.put("ACTIONTYPE", Constant.C_TYPE_INSERT);
//						pushSgyTempTable(data, tableName);
//						updateBoolean = true;
//						// 如果时间不相等就更新
//					} else if (redisLastUpdate != "" && !redisLastUpdate.equalsIgnoreCase(lastUpdate)) {
//						data.put("ACTIONTYPE", Constant.C_TYPE_UPDATE);
//						pushSgyTempTable(data, tableName);
//						updateBoolean = true;
//					}
//				}
//
//			}
//			if (updateBoolean) {
//				operations.set(bh, list);
//			}
//			return updateBoolean;
//		} catch (Exception e) {
//			logger.error("对比数据入中心库出现错误：" + e.getMessage() + "ajbs" + ajbs);
//			e.printStackTrace();
//		}
//		return updateBoolean;
//
//	}
//
//	*//**
//	 * 创建线程去获取数据
//	 *//*
//	@SuppressWarnings({ "unchecked" })
//	public boolean jzwjAllnewDate(List<ConcurrentMap<String, Object>> listMapTempEaj, String tableName)throws Exception  {
//
//		List<ConcurrentMap<String, Object>> list = Collections.synchronizedList(new ArrayList<>());
//		ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
//		List<ConcurrentMap<String, Object>> listRedis = Collections.synchronizedList(new ArrayList<>());
//		ConcurrentMap<String, Object> data = new ConcurrentHashMap<String, Object>();
//		ConcurrentMap<String, Object> redisData = new ConcurrentHashMap<String, Object>();
//		ConcurrentMap<String, Object> redisDataDelete = new ConcurrentHashMap<String, Object>();
//		List<String> xhList = new ArrayList<>();
//		boolean delBoolean;
//		boolean updateBoolean = false;
//		boolean jzwjBoolean = false;
//		String xh = "";
//		String yxxh = "";
//		String ajlx = "";
//		String redisxh = "";
//		String redisyxxh = "";
//		String lastUpdate = null;// 业务库更新时间
//		String bh = ajbs + tableName;// 组装序号
//		String redisLastUpdate = "";// redis库中更新时间
//		ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
//		try {
//			// 从redis中获取数据
//			if (redisTemplate.hasKey(bh)) {
//				listRedis = (List<ConcurrentMap<String, Object>>) operations.get(bh);
//			}
//
//			// 重redis中遍历出需要删除的数据
//			for (int i = 0; i < listRedis.size(); i++) {
//				data = listRedis.get(i);
//				redisxh = (String) data.get("xh");
//				redisyxxh = (String) data.get("yxxh");
//				delBoolean = false;
//				// 判断redis里面是否存在业务库中没有的数据
//				for (int j = 0; j < listMapTempEaj.size(); j++) {
//					xh = String.valueOf(listMapTempEaj.get(j).get("XH"));
//					yxxh = (listMapTempEaj.get(j).get("YXXH") == null ? "null"
//							: String.valueOf(listMapTempEaj.get(j).get("YXXH")));
//					redisyxxh = redisyxxh == null ? "null" : redisyxxh;
//					if (redisxh.equals(xh) && redisyxxh.equals(yxxh)) {
//						delBoolean = true;
//						break;
//					}
//				}
//				if (!delBoolean) {
//					redisDataDelete.put("AHDM", ajbs);
//					redisDataDelete.put("XH", redisxh == null ? "" : redisxh);
//					redisDataDelete.put("AJLX", data.get("ajlx") == null ? "" : data.get("ajlx"));
//					redisDataDelete.put("ACTIONTYPE", Constant.C_TYPE_DELETE);
//					redisDataDelete.put("YXXH", redisyxxh);
//					pushSgyTempTable(redisDataDelete, tableName);
//					updateBoolean = true;
//				}
//
//			}
//			// 遍历出需要更新的案件
//			for (int i = 0; i < listMapTempEaj.size(); i++) {
//				data = listMapTempEaj.get(i);
//				xh = String.valueOf(data.get("XH"));
//				ajlx = String.valueOf(data.get("AJLX"));
//				yxxh = String.valueOf(data.get("YXXH"));
//				jzwjBoolean = true;
//				for (int j = 0; j < listXh.size(); j++) {
//					if (xh.equals(listXh.get(j))) {
//						jzwjBoolean = false;
//					}
//				}
//				if (!jzwjBoolean) {
//					continue;
//				}
//				// 零时判断，如果更新时间为空就不处理
//				if (!StringUtils.isEmpty(data.get("LASTUPDATE"))) {
//					lastUpdate = VeDate.strDateToDate(String.valueOf(data.get("LASTUPDATE")));
//					map = new ConcurrentHashMap<>();
//					// 准备更新redis的数据
//					map.put("xh", xh);
//					map.put("yxxh", yxxh == null ? "" : yxxh);
//					map.put("ajlx", ajlx);
//					map.put("date", data.get("LASTUPDATE"));
//					redisLastUpdate = null;
//					list.add(map);
//					// 遍历数据
//					for (int j = 0; j < listRedis.size(); j++) {
//						redisData = listRedis.get(j);
//						redisxh = (String) redisData.get("xh");
//						redisyxxh = (String) redisData.get("yxxh") == null ? "null" : (String) redisData.get("yxxh");
//						if (xh.equalsIgnoreCase(redisxh) && yxxh.equalsIgnoreCase(redisyxxh)) {
//							redisLastUpdate = VeDate.strDateToDate(String.valueOf(redisData.get("date")));
//							break;
//						}
//					}
//					// 如果在redis中没有获取到数据，就是新增
//					if (StringUtils.isEmpty(redisLastUpdate)) {
//						data.put("ACTIONTYPE", Constant.C_TYPE_INSERT);
//						pushSgyTempTable(data, tableName);
//						updateBoolean = true;
//						// 如果时间不相等就更新
//					} else if (redisLastUpdate != "" && !redisLastUpdate.equalsIgnoreCase(lastUpdate)) {
//						data.put("ACTIONTYPE", Constant.C_TYPE_UPDATE);
//						pushSgyTempTable(data, tableName);
//						updateBoolean = true;
//					}
//					if (StringUtils.isEmpty(redisLastUpdate)
//							|| (redisLastUpdate != "" && !redisLastUpdate.equalsIgnoreCase(lastUpdate))) {
//						
//						xhList.add(xh);
//					}
//				}
//
//			}
//			if (updateBoolean) {
//				operations.set(bh, list);
//			}
//			return updateBoolean;
//		} catch (Exception e) {
//			logger.error("对比数据入中心库出现错误：" + e.getMessage() + "ajbs" + ajbs);
//			e.printStackTrace();
//		}
//		return updateBoolean;
//
//	}
//
//	// @SuppressWarnings("unchecked")
//	// public boolean handleMlxx(List<ConcurrentMap<String, Object>>
//	// listMapTempEaj, String tableName){
//	// List<ConcurrentMap<String, Object>> list =
//	// Collections.synchronizedList(new ArrayList<>());
//	// ConcurrentMap<String, Object> map = new
//	// ConcurrentHashMap<String,Object>();
//	// List<ConcurrentMap<String, Object>> listRedis =
//	// Collections.synchronizedList(new ArrayList<>());
//	// ConcurrentMap<String, Object> data = new ConcurrentHashMap<String,
//	// Object>();
//	// ConcurrentMap<String, Object> redisData = new ConcurrentHashMap<String,
//	// Object>();
//	// ConcurrentMap<String, Object> redisDataDelete = new
//	// ConcurrentHashMap<String, Object>();
//	//
//	// boolean delBoolean;
//	// int mlxxBoolean = 0;
//	// boolean updateBoolean=false;
//	// String xh = "";
//	// String yxxh = "";
//	// String ajlx = "";
//	// String redisxh = "";
//	// String redisyxxh = "";
//	// String bh = ajbs + tableName;// 组装序号
//	// ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
//	// try {
//	// // 从redis中获取数据
//	// if (redisTemplate.hasKey(bh)) {
//	// listRedis = (List<ConcurrentMap<String, Object>>) operations.get(bh);
//	// }
//	//
//	// // 重redis中遍历出需要删除的数据
//	// for (int i = 0; i < listRedis.size(); i++) {
//	//
//	// data = listRedis.get(i);
//	// redisxh = (String) data.get("xh");
//	// redisyxxh = (String) data.get("yxxh");
//	// delBoolean = false;
//	// // 判断redis里面是否存在业务库中没有的数据
//	// for (int j = 0; j < listMapTempEaj.size(); j++) {
//	// xh = String.valueOf(listMapTempEaj.get(j).get("XH"));
//	// yxxh
//	// =(listMapTempEaj.get(j).get("YXXH")==null?"null":String.valueOf(listMapTempEaj.get(j).get("YXXH")));
//	// if (redisxh.equals(xh) && redisyxxh.equals(yxxh)) {
//	// delBoolean = true;
//	// break;
//	// }
//	// }
//	// if (!delBoolean) {
//	// redisDataDelete.put("AHDM", ajbs);
//	// redisDataDelete.put("XH", redisxh ==null?"":redisxh);
//	// redisDataDelete.put("AJLX", data.get("ajlx") ==
//	// null?"":data.get("ajlx"));
//	// redisDataDelete.put("ACTIONTYPE", Constant.C_TYPE_DELETE);
//	// pushSgyTempTable(redisDataDelete, tableName);
//	// updateBoolean = true;
//	// }
//	//
//	// }
//	// // 遍历出需要更新的案件
//	// for (int i = 0; i < listMapTempEaj.size(); i++) {
//	// data = listMapTempEaj.get(i);
//	// xh = String.valueOf(data.get("XH"));
//	// ajlx = String.valueOf(data.get("AJLX"));
//	// yxxh = String.valueOf(data.get("YXXH"));
//	// mlxxBoolean = 0;
//	// map=new ConcurrentHashMap<>();
//	// //准备更新redis的数据
//	// map.put("xh", xh);
//	// map.put("yxxh", yxxh);
//	// map.put("ajlx", ajlx);
//	// map.put("JZLB", data.get("JZLB")==null?"":data.get("JZLB"));
//	// map.put("PXH", data.get("PXH")==null?"":data.get("PXH"));
//	// map.put("CLBT", data.get("CLBT")==null?"":data.get("CLBT"));
//	// map.put("P1", data.get("P1")==null?"":data.get("P1"));
//	// map.put("P2", data.get("P2")==null?"":data.get("P2"));
//	// map.put("CFLAGH", data.get("FLAG")==null?"":data.get("FLAG"));
//	// map.put("JMBH", data.get("JMBH")==null?"":data.get("JMBH"));
//	// map.put("CH", data.get("CH")==null?"":data.get("CH"));
//	// list.add(map);
//	// // 遍历数据
//	// for (int j = 0; j < listRedis.size(); j++) {
//	// mlxxBoolean = 1;
//	// redisData = listRedis.get(j);
//	// redisxh = (String) redisData.get("xh");
//	// redisyxxh = (String) redisData.get("yxxh")==null?"null":(String)
//	// redisData.get("yxxh");
//	// if (xh.equalsIgnoreCase(redisxh) && yxxh.equalsIgnoreCase(redisyxxh)) {
//	// //代表有数据没更新
//	// mlxxBoolean = 2;
//	// //由于目录信息没有更新时间所以只有判断每个字段是否有变化
//	// if(!String.valueOf(redisData.get("JZLB")).equals(String.valueOf(data.get("JZLB")))
//	// ||!String.valueOf(redisData.get("PXH")).equals(String.valueOf(data.get("PXH")))
//	// ||!String.valueOf(redisData.get("CLBT")).equals(String.valueOf(data.get("CLBT")))
//	// ||!String.valueOf(redisData.get("P1")).equals(String.valueOf(data.get("P1")))
//	// ||!String.valueOf(redisData.get("P2")).equals(String.valueOf(data.get("P2")))
//	// ||!String.valueOf(redisData.get("CFLAGH")).equals(String.valueOf(data.get("CFLAGH")))
//	// ||!String.valueOf(redisData.get("JMBH")).equals(String.valueOf(data.get("JMBH")))
//	// ||!String.valueOf(redisData.get("CH")).equals(String.valueOf(data.get("CH")))){
//	// //代表是更新
//	// mlxxBoolean = 3;
//	// }
//	// break;
//	// }
//	// }
//	// if(mlxxBoolean==3){
//	// data.put("ACTIONTYPE", Constant.C_TYPE_UPDATE);
//	// data.put("LSH", ajbs);
//	// pushSgyTempTable(data, tableName);
//	// updateBoolean=true;
//	// }else if(mlxxBoolean == 0 || mlxxBoolean == 1){
//	// data.put("ACTIONTYPE", Constant.C_TYPE_INSERT);
//	// data.put("LSH", ajbs);
//	// pushSgyTempTable(data, tableName);
//	// updateBoolean=true;
//	// }
//	// }
//	//
//	// if (updateBoolean) {
//	// operations.set(bh ,list);
//	// }
//	// return updateBoolean;
//	// } catch (Exception e) {
//	// logger.error("对比数据入中心库出现错误：" + e.getMessage()+"ajbs："+ajbs);
//	// e.printStackTrace();
//	// }
//	// return updateBoolean;
//	//
//	// }
//	*//**
//	 * 数据对比
//	 * 
//	 * @param ajbs
//	 *//*
//	@SuppressWarnings({ "unchecked" })
//	public boolean getEajJZ(String ajbs) throws Exception {
//		List<ConcurrentMap<String, Object>> lisTempEaj = Collections.synchronizedList(new ArrayList<>());
//		ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
//		map.put("ajbs", ajbs);
//		map.put("bh", Integer.valueOf(ajbs.substring(14, 15)));
//		long startTime = System.currentTimeMillis();
//		List<TempEajJz> getTempEaj = contrstService.getEajJZ(map);
//		  long endTime = System.currentTimeMillis(); 
//	     System.err.println(" C_EAJ_JZ 程序运行时间：" + (endTime - startTime) + "ms");
//		// 转bean为map
//		for (int i = 0; i < getTempEaj.size(); i++) {
//			lisTempEaj.add(Constant.toMap(getTempEaj.get(i)));
//		}
//		long endTimec = System.currentTimeMillis(); 
//	    System.err.println("Constant.C_EAJ_JZ 转换时间：" + (endTimec - endTime) + "ms");
//		boolean YNUpdate = handleDate(lisTempEaj, Constant.C_EAJ_JZ);
//		long endTimed = System.currentTimeMillis(); 
//	    System.err.println("Constant.C_EAJ_JZ对比及入库时间：" + (endTimed - endTimec) + "ms");
//		return YNUpdate;
//	}
//
//	@SuppressWarnings("unchecked")
//	public boolean getEajJzwjAll(String ajbs) throws Exception {
//		List<ConcurrentMap<String, Object>> lisTempEaj = Collections.synchronizedList(new ArrayList<>());
//		 long startTime = System.currentTimeMillis();
//		List<TempEajJzwjAll> getTempEaj = contrstService.getEajJzwjAll(ajbs);
//		 long endTime = System.currentTimeMillis(); 
//	     System.err.println(" C_EAJ_JZWJ_ALL程序运行时间：" + (endTime - startTime) + "ms");
//		// 转bean为map
//		for (int i = 0; i < getTempEaj.size(); i++) {
//			lisTempEaj.add(Constant.toMap(getTempEaj.get(i)));
//		}
//		 long endTimec = System.currentTimeMillis(); 
//	        System.err.println("Constant.C_EAJ_JZWJ_ALL 转换时间：" + (endTimec - endTime) + "ms");
//		boolean YNUpdate = handleDate(lisTempEaj, Constant.C_EAJ_JZWJ_ALL);
//		long endTimed = System.currentTimeMillis(); 
//        System.err.println("Constant.C_EAJ_JZWJ_ALL对比及入库时间：" + (endTimed - endTimec) + "ms");
//		return YNUpdate;
//	}
//
//	@SuppressWarnings("unchecked")
//	public boolean getEajJzwjAllNew(String ajbs) throws Exception {
//		ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
//		map.put("ajbs", ajbs);
//		map.put("bh", Integer.valueOf(ajbs.substring(14, 15)));
//		
//		this.tempEajJzList = contrstService.getEajJZ(map);
//		
//		this.tempEajJzwjAllNewList = contrstService.getEajJzwjAllNew(ajbs);
//
//		List<ConcurrentMap<String, Object>> lisTempEaj = Collections.synchronizedList(new ArrayList<>());
//		long startTime = System.currentTimeMillis();
//		List<TempEajJzwjAllNew> getTempEaj = contrstService.getEajJzwjAllNew(ajbs);
//		long endTime = System.currentTimeMillis(); 
//		System.err.println(" Constant.C_EAJ_JZWJ_ALL_NEW 程序运行时间：" + (endTime - startTime) + "ms");
//		// 转bean为map
//		for (int i = 0; i < getTempEaj.size(); i++) {
//			lisTempEaj.add(Constant.toMap(getTempEaj.get(i)));
//		}
//		long endTimec = System.currentTimeMillis(); 
//		System.err.println("Constant.C_EAJ_JZWJ_ALL_NEW 转换时间：" + (endTimec - endTime) + "ms");
//		boolean YNUpdate = jzwjAllnewDate(lisTempEaj, Constant.C_EAJ_JZWJ_ALL_NEW);
//		long endTimed = System.currentTimeMillis(); 
//		System.err.println("Constant.C_EAJ_JZWJ_ALL_NEW对比及入库时间：" + (endTimed - endTimec) + "ms");
//		return YNUpdate;
//	}
//
//	public void getEajMlxx(String ajbs) throws Exception {
//		List<TempEajMlxx> getTempEaj = contrstService.getEajMlxx(ajbs);
//		// 转bean为map
//		for (int i = 0; i < getTempEaj.size(); i++) {
//			TempEajMlxx tempEajMlxx = getTempEaj.get(i);
//			tempEajMlxx.setFYDM(fydm);
//			agentPushDataMapper.pushEajMlxxToSGY(tempEajMlxx);
//		}
//	}
//
//	public void getEajMlxxGc(String ajbs) throws Exception {
//		List<TempEajMlxxGc> getTempEaj = contrstService.getEajMlxxGc(ajbs);
//		// 转bean为map
//		for (int i = 0; i < getTempEaj.size(); i++) {
//			TempEajMlxxGc tempEajMlxxGc = getTempEaj.get(i);
//			tempEajMlxxGc.setFYDM(fydm);
//			agentPushDataMapper.pushEajMlxxGcToSGY(tempEajMlxxGc);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public boolean getSsjcyx(String ajbs) throws Exception {
//		List<ConcurrentMap<String, Object>> lisTempEaj = Collections.synchronizedList(new ArrayList<>());
//		 long startTime = System.currentTimeMillis();
//		List<TempEajSsjcyx> getTempEaj = contrstService.getSsjcyx(ajbs);
//		long endTime = System.currentTimeMillis(); 
//        System.err.println(" Constant.C_EAJ_SSJCYX 程序运行时间：" + (endTime - startTime) + "ms");
//		// 转bean为map
//		for (int i = 0; i < getTempEaj.size(); i++) {
//			lisTempEaj.add(Constant.toMap(getTempEaj.get(i)));
//		}
//		long endTimec = System.currentTimeMillis(); 
//	    System.err.println("Constant.C_EAJ_SSJCYX 转换时间：" + (endTimec - endTime) + "ms");
//		boolean YNUpdate = handleDate(lisTempEaj, Constant.C_EAJ_SSJCYX);
//		long endTimed = System.currentTimeMillis(); 
//		System.err.println("Constant.C_EAJ_SSJCYX对比及入库时间：" + (endTimed - endTimec) + "ms");
//		return YNUpdate;
//	}
//	*//**
//	 * 
//	 * @function
//	 * @author wenyuguang
//	 * @creaetime 2017年10月13日 下午3:21:27
//	 *//*
//	public int run() {
//		int updTable = 0;// 0为无更新，1为有更新，-1为错误
//		try {
//		    long endTimega = System.currentTimeMillis(); 
//			int size = agentPushDataMapper.getAjbs(ajbs);// 判断库中是否存在数据
//			 long endTimegb = System.currentTimeMillis(); 
//			 System.err.println(ajbs);
//	           System.err.println("判断库中是否存在数据" + (  endTimegb-endTimega) + "ms");
//			if (size == 0) {
//				logger.info("案件标识:" + ajbs);
//				String cbrxm = contrstService.getAllAjxxCBR(ajbs);
//				ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();
//				map.put("ahdm", ajbs);
//				map.put("ajlx", ajlx);
//				map.put("fydm", fydm);
//				map.put("level", level);
//				map.put("ah", ah);
//				map.put("larq", larq);
//				map.put("cbrxm", cbrxm == null ? "" : cbrxm);
//				map.put("ajzt", Constant.getAjzt(ajzt));
//				 long startTime = System.currentTimeMillis();
//				map.put(Constant.C_TEMP_EAJ_JZWJ_ALL_NEW, String.valueOf(getEajJzwjAllNew(ajbs)));
//			    long endTime = System.currentTimeMillis(); 
//		        System.err.println("C_TEMP_EAJ_JZWJ_ALL_NEW：" + (endTime - startTime) + "ms");
//				// C_TEMP_EAJ_JZWJ_ALL_NEW和C_TEMP_EAJ_JZ顺序不能变（两表有关联处理）
//				if (BJz == true) {
//					getEajJZ(ajbs);
//					map.put(Constant.C_TEMP_EAJ_JZ, "true");
//				} else {
//					map.put(Constant.C_TEMP_EAJ_JZ, String.valueOf(getEajJZ(ajbs)));
//				}
//				  long endTimec = System.currentTimeMillis(); 
//	              System.err.println(" C_TEMP_EAJ_JZ：" + (endTimec - endTime) + "ms");
//				// map.put(Constant.C_TEMP_EAJ_JZWJ_ALL,getEajJzwjAll(ajbs));
//				map.put(Constant.C_TEMP_EAJ_MLXX, "fasle");
//				long endTimed = System.currentTimeMillis(); 
//                System.err.println("C_TEMP_EAJ_MLXX：" + (endTimed - endTimec) + "ms");
//				map.put(Constant.C_TEMP_EAJ_MLXX_GC, "fasle");
//				long endTimee = System.currentTimeMillis(); 
//                System.err.println(" C_TEMP_EAJ_MLXX_GC：" + (endTimee - endTimed) + "ms");
//				// map.put(Constant.C_TEMP_EAJ_MLXX_GC, "false");
//				map.put(Constant.C_TEMP_EAJ_SSJCYX, String.valueOf(getSsjcyx(ajbs)));
//			    long endTimef = System.currentTimeMillis(); 
//                System.err.println(" C_TEMP_EAJ_MLXX_GC：" + (endTimef - endTimee) + "ms");
//				if (map.get(Constant.C_TEMP_EAJ_JZ).equals("true")
//						|| map.get(Constant.C_TEMP_EAJ_JZWJ_ALL_NEW).equals("true")
//						|| map.get(Constant.C_TEMP_EAJ_SSJCYX).equals("true")) {
//					if (map.get(Constant.C_TEMP_EAJ_JZ).equals("true")
//							|| map.get(Constant.C_TEMP_EAJ_JZWJ_ALL_NEW).equals("true")) {
//						getEajMlxxGc(ajbs);
//						map.put(Constant.C_TEMP_EAJ_MLXX_GC, "true");
//					}
//					if (map.get(Constant.C_TEMP_EAJ_SSJCYX).equals("true")) {
//						getEajMlxx(ajbs);
//						map.put(Constant.C_TEMP_EAJ_MLXX, "true");
//					}
//					agentPushDataMapper.pushEajAll(map);
//					 long endTimeg = System.currentTimeMillis(); 
//		                System.err.println("upd" + (endTimeg - endTimef) + "ms");
//					updTable = 1;
//				}
//			}
//			logger.info("案件标识为:" + ajbs + "的案件非结构化数据对应的结构化数据抽取处理完成");
//			 long endTimege = System.currentTimeMillis(); 
//             System.err.println("总时间" + (endTimege - endTimega) + "ms");
//			return updTable;
//		} catch (Exception e) {
//			updTable = -1;
//			e.printStackTrace();
//			return updTable;
//		}
//	}
//
//}