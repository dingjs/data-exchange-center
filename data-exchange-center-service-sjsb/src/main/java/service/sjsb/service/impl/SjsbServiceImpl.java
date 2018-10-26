package service.sjsb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.code.CodeUtil;
import service.sjsb.domain.AjxxInfo;
import service.sjsb.mapper.SjsbMapper;
import service.sjsb.service.SjsbService;

@Service
public class SjsbServiceImpl implements SjsbService {

	private static final Logger logger = LoggerFactory.getLogger(SjsbServiceImpl.class);
	
	@Autowired
	private SjsbMapper sjsbMapper;

	@Override
	public List<AjxxInfo> getAjbs() {
		return sjsbMapper.getAjbs();
	}

	@Override
	public Map<String, Object> getAjData(String ajbs, String ajlx, String fydm) {
		Map<String, Object> retMap = new HashMap<>();
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("ajbs", ajbs);
			params.put("ajlx", ajlx);
			params.put("fydm", fydm);
			sjsbMapper.getAjData(params);
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			retMap.put(CodeUtil.RETURN_MSG, params);
			return retMap;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			retMap.put(CodeUtil.RETURN_MSG, e.toString());
			return retMap;
		}
	}

	@Override
	public void addSjsbFile(String zippath) {
		Map<String, String> params = new HashMap<>();
		params.put("uuid", UUID.randomUUID().toString().replace("-", ""));
		params.put("zippath", zippath);
		sjsbMapper.addSjsbFile(params);
	}

	@Override
	public List<String> getSjsbFile() {
		return sjsbMapper.getSjsbFile();
	}

	@Override
	public List<String> getDeleteSjsbFile() {
		return sjsbMapper.getDeleteSjsbFile();
	}
}
