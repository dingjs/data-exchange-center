package data.exchange.center.service.parse.ftpzip.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.parse.ftpzip.domain.RetInfo;
import data.exchange.center.service.parse.ftpzip.mapper.AjBindMapper;
import data.exchange.center.service.parse.ftpzip.service.AjBindService;
import data.exchange.center.service.parse.ftpzip.util.Code;
import data.exchange.center.service.parse.ftpzip.util.SerializationUtil;

@Service
public class AjBindServiceImpl implements AjBindService {

	private static final Logger logger = LoggerFactory.getLogger(AjBindServiceImpl.class);
	@Autowired
	private AjBindMapper ajBindMapper;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public Map<String, String> bindAjbs(String jhbh, String ptajbh, String ajbs, String flag) throws Exception {
		Map<String, String> rtMp = new HashMap<>();
		logger.info(jhbh + " " + ptajbh + " " + ajbs + " " + flag);
		try {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("jhbh", jhbh);
			paramMap.put("ptajbh", ptajbh);
			paramMap.put("ajbs", ajbs);
			paramMap.put("flag", flag);
			// 新增绑定案件前删除表中数据，因为过来数据可能为更新
			ajBindMapper.deleteBindAjbs(paramMap);
			ajBindMapper.insertBindAjbs(paramMap);
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			rtMp.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
			return rtMp;

		} catch (Exception e) {
			e.printStackTrace();
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMp.put(CodeUtil.RETURN_MSG, e.getMessage());
			return rtMp;
		}
	}

	@Override
	public Object queryJhkLog(String startDate, String endDate, String dsr, String ajbh, String wsbh) throws Exception {
		Map<String, Object> rtMp = new HashMap<>();
		try {
			if(StringUtils.isEmpty(startDate)
					&&StringUtils.isEmpty(endDate)
					&&StringUtils.isEmpty(dsr)
					&&StringUtils.isEmpty(ajbh)
					&&StringUtils.isEmpty(wsbh)) {
				if(redisTemplate.hasKey(Code.TOTAL_CASE)) {
					ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
					@SuppressWarnings("unchecked")
					Map<String, String> retMap = (Map<String, String>) SerializationUtil.deserialize((byte[]) operations.get(Code.TOTAL_CASE));
					return retMap;
				}
			}
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("startDate", startDate);
			paramMap.put("endDate", endDate);
			paramMap.put("dsr", dsr);
			paramMap.put("ajbh", ajbh);
			paramMap.put("wsbh", wsbh);
			List<RetInfo> list = ajBindMapper.queryJhkLog(paramMap);
			Map<String, Object> retMap = new HashMap<>();
			retMap.put("data", list);
			return retMap;
		} catch (Exception e) {
			e.printStackTrace();
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMp.put(CodeUtil.RETURN_MSG, "查询出错，请联系管理员：" + e.getMessage());
			return rtMp;
		}
	}

	@Override
	public Object queryCaseCount(String jsdw)throws Exception {
		Map<String, Object> rtMp = new HashMap<>();
		try {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("jsdw", jsdw);
			List<RetInfo> list = ajBindMapper.queryCaseCount(paramMap);
			Map<String, Object> retMap = new HashMap<>();
			retMap.put("data", list);
			return retMap;
		} catch (Exception e) {
			e.printStackTrace();
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMp.put(CodeUtil.RETURN_MSG, "查询出错，请联系管理员：" + e.getMessage());
			return rtMp;
		}
	}
}
