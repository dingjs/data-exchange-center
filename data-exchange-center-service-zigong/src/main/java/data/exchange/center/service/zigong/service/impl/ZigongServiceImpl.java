package data.exchange.center.service.zigong.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.zigong.domain.AjbsList;
import data.exchange.center.service.zigong.domain.Tqtj;
import data.exchange.center.service.zigong.mapper.ZigongMapper;
import data.exchange.center.service.zigong.service.ZigongService;

@Service
public class ZigongServiceImpl implements ZigongService {

	@Autowired
	private ZigongMapper zigongMapper;

	@Override
	public Object getAjbsList(String startDateTime, String endDateTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(startDateTime) || StringUtils.isEmpty(endDateTime)) {
				map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				map.put(CodeUtil.RETURN_MSG, "参数startDateTime或者endDateTime不能为空");
				return map;
			}
			if (!patternDate(startDateTime, "udate")) {
				map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				map.put(CodeUtil.RETURN_MSG, "【开始时间】格式不正确,请用yyyyMMddHHmmss格式字符串类型日期");
				return map;
			}
			if (!patternDate(endDateTime, "udate")) {
				map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				map.put(CodeUtil.RETURN_MSG, "【结束时间】格式不正确,请用yyyyMMddHHmmss格式字符串类型日期");
				return map;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDate", startDateTime);
			params.put("endDate", endDateTime);
			List<AjbsList> ajbsList = zigongMapper.getAjbsList(params);
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			map.put(CodeUtil.RETURN_MSG, ajbsList == null ? "" : ajbsList);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG, e.getLocalizedMessage());
			return map;
		}
	}

	private static boolean patternDate(String dateStr, String type) {
		String eL = "(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])";
		String str = "^((?:19|20)\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0\\d|1\\d|2[0-3])(0\\d|[1-5]\\d)(0\\d|[1-5]\\d)$";// yyyyMMddHHmmss
		if (type.equalsIgnoreCase("date")) {
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(dateStr);
			return m.matches();
		} else {
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(dateStr);
			return m.matches();
		}
	}

	@Override
	public Object getTqtj(String ajbs, String ajlx) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(ajbs)) {
				map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				map.put(CodeUtil.RETURN_MSG, "参数ajbs不能为空");
				return map;
			}
			if (StringUtils.isEmpty(ajlx)) {
				map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				map.put(CodeUtil.RETURN_MSG, "参数ajlx不能为空");
				return map;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ajlx", ajlx);
			String tbAjlx = zigongMapper.getTbAjlx(param);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ajbs", ajbs);
			params.put("tbAjlx", tbAjlx);
			
			List<Tqtj> tqtjList = zigongMapper.getTqtj(params);
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			map.put(CodeUtil.RETURN_MSG, tqtjList == null ? "" : tqtjList);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG, e.getLocalizedMessage());
			return map;
		}
	}
}
