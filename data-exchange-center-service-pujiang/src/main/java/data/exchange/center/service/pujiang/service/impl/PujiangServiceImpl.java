package data.exchange.center.service.pujiang.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.pujiang.domain.AjbsInfo;
import data.exchange.center.service.pujiang.domain.AjbsList;
import data.exchange.center.service.pujiang.domain.Ajcl;
import data.exchange.center.service.pujiang.domain.UserInfo;
import data.exchange.center.service.pujiang.mapper.PujiangMapper;
import data.exchange.center.service.pujiang.service.DownLoadService;
import data.exchange.center.service.pujiang.service.PujiangService;

@Service
public class PujiangServiceImpl implements PujiangService {

	@Autowired
	private PujiangMapper     pujiangMapper;
	@Autowired
	private DownLoadService downLoadService;

	@Override
	public Object getAjbsInfo(String ajbs, String fydm, String ajlx) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ajbs", ajbs);
			params.put("fydm", fydm);
			params.put("ajlx", ajlx);
			List<AjbsInfo> ajInfo = pujiangMapper.getAjbsInfo(params);
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			map.put(CodeUtil.RETURN_MSG,  ajInfo==null?"":ajInfo);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG,  e.getLocalizedMessage());
		}
		return map;
	}

	@Override
	public Object getAjcl(String ajbs, String xh) {
		byte[] bt = downLoadService.download(ajbs.concat("_").concat(xh));
		return bt;
	}

	@Override
	public Object getAjclList(String ajbs, String fydm, String ajlx) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ajbs", ajbs);
			params.put("fydm", fydm);
			params.put("ajlx", ajlx);
			List<Ajcl> ajInfoList = pujiangMapper.getAjclList(params);
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			map.put(CodeUtil.RETURN_MSG,  ajInfoList==null?"":ajInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG,  e.getLocalizedMessage());
		}
		return map;
	}

	@Override
	public Object getUserInfo(String fydm) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserInfo> ajInfoList = pujiangMapper.getUserInfo(fydm);
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			map.put(CodeUtil.RETURN_MSG,  ajInfoList==null?"":ajInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG,  e.getLocalizedMessage());
		}
		return map;
	}

	@Override
	public Object getBmInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> bmInfoList = pujiangMapper.getBmInfo();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			map.put(CodeUtil.RETURN_MSG,  bmInfoList==null?"":bmInfoList);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG,  e.getLocalizedMessage());
		}
		return map;
	}

	@Override
	public Object getAjbsList(String startDate, String endDate, String fydm) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(StringUtils.isEmpty(startDate)||StringUtils.isEmpty(endDate)){
				map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				map.put(CodeUtil.RETURN_MSG,  "参数startDate或者endDate不能为空");
				return map;
			}
			if(!patternDate(startDate, "udate")){
				map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				map.put(CodeUtil.RETURN_MSG,  "【开始时间】格式不正确,请用yyyyMMddHHmmss格式字符串类型日期");
				return map;
			}
			if(!patternDate(endDate, "udate")){
				map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
				map.put(CodeUtil.RETURN_MSG,  "【结束时间】格式不正确,请用yyyyMMddHHmmss格式字符串类型日期");
				return map;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startDate", startDate);
			params.put("endDate",   endDate);
			params.put("fydm",      fydm);
			List<AjbsList> ajbsList= pujiangMapper.getAjbsList(params);
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			map.put(CodeUtil.RETURN_MSG,  ajbsList==null?"":ajbsList);
			return map;
		}catch(Exception e) {
			e.printStackTrace();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG,  e.getLocalizedMessage());
			return map;
		}
	}
	
	
	private static boolean patternDate(String dateStr, String type){
		String eL = "(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])";
		String str = "^((?:19|20)\\d\\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0\\d|1\\d|2[0-3])(0\\d|[1-5]\\d)(0\\d|[1-5]\\d)$";//yyyyMMddHHmmss  
		if(type.equalsIgnoreCase("date")) {
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(dateStr);
			return m.matches();
		}else {
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(dateStr);
			return m.matches();
		}
	}
}
