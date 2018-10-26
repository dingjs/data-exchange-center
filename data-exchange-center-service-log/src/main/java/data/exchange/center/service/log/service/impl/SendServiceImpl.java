package data.exchange.center.service.log.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.log.mapper.LogMapper;
import data.exchange.center.service.log.service.SendService;

@Service
public class SendServiceImpl implements SendService {

	private static final Logger logger = LoggerFactory.getLogger(SendServiceImpl.class);  
	
	@Autowired
	private LogMapper logMapper;
	
	@Override
	public void logger(String serviceName, int serviceId, String id, String ajbs, String lx, String fydm,
			String srccode, String msg) {
		
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("serviceName", serviceName);
			param.put("serviceId",   String.valueOf(serviceId));
			param.put("id",          id);
			param.put("ajbs",        ajbs);
			param.put("lx",          lx);
			param.put("fydm",        fydm);
			param.put("srccode",     srccode);
			if(msg.length()>450) {
				param.put("msg",     msg.substring(0, 450));
			}else {
				param.put("msg",     msg);
			}
			System.out.println("增加日志:"+param.toString());
			logMapper.logger(param);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("写日志出错："+e.getMessage());
		}
	}

	@Override
	public void deleteLog(String serviceName, String serviceId, String id, String ajbs, String lx, String fydm,
			String srccode, String msg) throws Exception {
		System.out.println("删除日志");
		try {
			if(!(StringUtils.isEmpty(serviceName)&&
					StringUtils.isEmpty(serviceId)&&
					StringUtils.isEmpty(id)&&
					StringUtils.isEmpty(ajbs)&&
					StringUtils.isEmpty(lx)&&
					StringUtils.isEmpty(fydm)&&
					StringUtils.isEmpty(srccode)&&
					StringUtils.isEmpty(msg))) {
				Map<String, String> param = new HashMap<>();
				param.put("serviceName", serviceName);
				param.put("serviceId",   serviceId);
				param.put("id",          id);
				param.put("ajbs",        ajbs);
				param.put("lx",          lx);
				param.put("fydm",        fydm);
				param.put("srccode",     srccode);
				param.put("msg",         msg);
				logMapper.deleteLog(param);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("删除日志出错："+e.getMessage());
		}
	}
}
