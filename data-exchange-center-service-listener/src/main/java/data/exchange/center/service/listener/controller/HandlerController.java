package data.exchange.center.service.listener.controller;

import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.listener.domain.Msg;
import data.exchange.center.service.listener.service.HandlerService;

@RestController
public class HandlerController {

	@Autowired
	private HandlerService handlerService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@RequestMapping(value = "/handlerMsg" ,method = RequestMethod.GET)
	public Object handlerMsg() throws Exception {
		return handlerService.handleFtp();
	}
	
	@RequestMapping(value = "/test" ,method = RequestMethod.GET)
	public Object test() throws Exception {
		Msg msg = new Msg();
		msg.setAjbs("300000000013321");
		msg.setAjlx("22");
		msg.setFydm("510000");
		String  json = new Gson().toJson(msg);
		rabbitTemplate.setReplyTimeout(1000*60*60);
		rabbitTemplate.setReceiveTimeout(1000*60*60);
		Object ret = rabbitTemplate.convertSendAndReceive(
				RabbitmqConf.RPC_EXCHANGE, 
				RabbitmqConf.RPC_ROUTEKEY, 
				json,
				new MessagePostProcessor() {
					@Override
					public Message postProcessMessage(Message message) throws AmqpException {
						message.getMessageProperties().setPriority(0);
						return message;
					}
				},
				new CorrelationData(UUID.randomUUID().toString())
		);
		return ret;
	}
}