package data.exchange.center.service.huayu.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.huayu.mapper.HuayuMapper;
import data.exchange.center.service.huayu.service.HuayuService;
import oracle.sql.BLOB;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月15日 上午11:15:50</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class HuayuServiceImpl implements HuayuService {

	@Autowired
	private HuayuMapper huayuMapper;
	private static Logger logger = LoggerFactory.getLogger(HuayuServiceImpl.class); 
	@Override
	public List<Map<String, Object>> getAjlb(Map<String,Object> param) throws Exception {
		// TODO Auto-generated method stub
		try {
			return huayuMapper.getAjlb(param);
		} catch (Exception e) {
			logger.error("parameter:"+param+"错误日志"+e.getMessage());
		}
		return null;
	
	}
	@Override
	public Map<String, Object> getAjbsInfo(String ajbs, String ajlx) {

		List<Map<String, Object>> listMap = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> table = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		String tablename = null;
		String ajlxMc = null;
		String jalxqz = null;

		try {
			listMap = huayuMapper.getTablename(ajlx);
			ajlxMc = huayuMapper.getAjlxMc(ajlx);
			param.put("ajbs", ajbs);
			param.put("ajlx", ajlx);
			data.put("ajlxmc", ajlxMc);
			for (int i = 0; i < listMap.size(); i++) {
				table = listMap.get(i);
				tablename = (String) table.get("C_ETBNAME");
				param.put("tableName", tablename);
				List<Map<String, Object>> ajxx = huayuMapper.getAjxx(param);
				if (ajxx.size() > 0 && !tablename.contains("STWJ") && !tablename.contains("DAXX")
						&& !tablename.contains("MLXX")&& !tablename.contains("DZDA")&& !tablename.contains("_WS")) {
					map.put(tablename, getAjxx(ajxx));
				}
			}
			jalxqz = tablename.substring(0, tablename.indexOf("_"));
			param.put("jalxqz", jalxqz);
//			map.put(jalxqz + "_DAJ", ajxxAllMapper.getAjDaj(param));
//			map.put(jalxqz + "_Gcj", ajxxAllMapper.getAjGcj(param));
		} catch (Exception e) {
			logger.debug("获取案件所有信息错误:ajbs"+ajbs+"案件类型"+ajlx+"错误详情：" + e.getMessage());
			e.printStackTrace();
		}
		data.put("date", map);
		return data;
	}
	public List<Map<String, Object>> getAjxx(List<Map<String, Object>> ajxx) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < ajxx.size(); i++) {
			Map<String, Object> data = new HashMap<>();
			map = ajxx.get(i);
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue()))
					&& !entry.getKey().equals("APP_CODE")&& !entry.getKey().equals("DEPT_CODE")
					&& !entry.getKey().equals("REG_TIME")&& !entry.getKey().equals("UPDATE_TIME")
						) {
					// 判断是否为blob字段
					if (entry.getValue() instanceof oracle.sql.BLOB) {
						InputStream in = ((BLOB) entry.getValue()).getBinaryStream();
						data.put(entry.getKey(), getBlob(in));
					} else {
						data.put(entry.getKey(), String.valueOf(entry.getValue()));
					}
				}
				
			}
			list.add(data);
		}
		return list;
	}

	public static String getBlob(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[4096];
		int count = -1;
		while ((count = in.read(data, 0, 4096)) != -1)
			outStream.write(data, 0, count);

		data = null;
		String result = new String(outStream.toByteArray());
		return result;

	}
	@Override
	public Map<String, Object> getAjDaJz(String ajbs, String ajlx) throws Exception {
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		param.put("ajbs", ajbs);
		param.put("ajlx", ajlx);
		param.put("jalxqz", huayuMapper.getAjQz(ajlx));
		map.put("DAJ", huayuMapper.getAjDaj(param));
		map.put("Gcj", huayuMapper.getAjGcj(param));
		return map;
	}
	@Override
	public List<Map<String, Object>> getZzjg(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return huayuMapper.getZzjg(map);
	}
	@Override
	public List<Map<String, Object>> getZzry(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return huayuMapper.getZzry(map);
	}

}
