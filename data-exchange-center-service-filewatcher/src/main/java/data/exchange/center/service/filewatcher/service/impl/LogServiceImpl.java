package data.exchange.center.service.filewatcher.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.filewatcher.mapper.LogMapper;
import data.exchange.center.service.filewatcher.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper;
	
	@Override
	public void addLog(String taskId, String msg, String flag) {
		Map<String ,Object> param = new HashMap<>();
		param.put("taskId", taskId);
		if(msg.length()>4000) {
			param.put("msg", msg.substring(0, 3999));
		}else {
			param.put("msg", msg);
		}
		param.put("flag", flag);
		
		logMapper.addLog(param);
	}
}
