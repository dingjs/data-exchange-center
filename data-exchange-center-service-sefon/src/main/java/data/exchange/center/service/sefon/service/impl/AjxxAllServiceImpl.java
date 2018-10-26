package data.exchange.center.service.sefon.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.sefon.mapper.AjxxAllMapper;
import data.exchange.center.service.sefon.service.AjxxAllService;
import oracle.sql.BLOB;

@Service("ajxxAllService")
public class AjxxAllServiceImpl implements AjxxAllService {
	
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private AjxxAllMapper ajxxAllMapper;

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
			listMap = ajxxAllMapper.getTablename(ajlx);
			ajlxMc = ajxxAllMapper.getAjlxMc(ajlx);
			param.put("ajbs", ajbs);
			param.put("ajlx", ajlx);
			data.put("ajlxmc", ajlxMc);
			for (int i = 0; i < listMap.size(); i++) {
				table = listMap.get(i);
				tablename = (String) table.get("C_ETBNAME");
				param.put("tableName", tablename);
				List<Map<String, Object>> ajxx = ajxxAllMapper.getAjxx(param);
				if (ajxx.size() > 0 && !tablename.contains("STWJ") && !tablename.contains("DAXX")
						&& !tablename.contains("MLXX")&& !tablename.contains("DZDA")) {
					map.put(tablename, getAjxx(ajxx));
				}
			}
			jalxqz = tablename.substring(0, tablename.indexOf("_"));
			param.put("jalxqz", jalxqz);
		/*	map.put(jalxqz + "_DAJ", ajxxAllMapper.getAjDaj(param));
			map.put(jalxqz + "_Gcj", ajxxAllMapper.getAjGcj(param));*/
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
				if (entry.getValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue()))) {
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

}
