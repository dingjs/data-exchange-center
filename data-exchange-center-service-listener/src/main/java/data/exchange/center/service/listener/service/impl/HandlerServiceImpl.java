package data.exchange.center.service.listener.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.listener.domain.Msg;
import data.exchange.center.service.listener.mapper.HandlerMapper;
import data.exchange.center.service.listener.service.HandlerService;

@Service
public class HandlerServiceImpl implements HandlerService {

	@Autowired
	private HandlerMapper handlerMapper;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public Object handleFtp() throws Exception {
		List<Msg> list = handlerMapper.getAjbs_tfp();
		System.err.println(String.format("一共有%s个案件需要同步", list.size()));
    	Map<String, Object> messageMap = new HashMap<String, Object>();
    	for(Msg msg:list) {
    		messageMap.put(Constant.AJBS,  msg.getAjbs());
    		messageMap.put(Constant.FYDM,  msg.getFydm());
    		messageMap.put(Constant.AJLX,  msg.getAjlx());
    		messageMap.put(Constant.LEVEL, 1);
    		rabbitTemplate.convertAndSend(
    				RabbitmqConf.UNDO_SYNC_FTP_EXCHANGE, 
    				RabbitmqConf.UNDO_SYNC_FTP_ROUTEKEY, 
    				messageMap,
    				new MessagePostProcessor() {
    					@Override
    					public Message postProcessMessage(Message message) throws AmqpException {
    						message.getMessageProperties().setPriority(1);
    						return message;
    					}
    				},
    				new CorrelationData(UUID.randomUUID().toString())
    		);
    	}
		return "搞定";
	}
}
