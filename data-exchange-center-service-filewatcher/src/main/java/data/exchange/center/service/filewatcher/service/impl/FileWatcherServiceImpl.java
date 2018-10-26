package data.exchange.center.service.filewatcher.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.filewatcher.service.FileWatcherService;

@Service
public class FileWatcherServiceImpl implements FileWatcherService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public Object sendTask(String taskId) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			Map<String, Object> msgMap = new HashMap<String, Object>();
			msgMap.put("taskId", taskId);
			msgMap.put("type",   "JCY");
			
			rabbitTemplate.convertAndSend(
	    			RabbitmqConf.XTBAPT_EXCHANGE, 
	    			RabbitmqConf.XTBAPT_ROUTEKEY, 
	    			msgMap,
	    			new CorrelationData(UUID.randomUUID().toString()));
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
			retMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
		}catch(Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
		}
		return retMap;
	}
}
