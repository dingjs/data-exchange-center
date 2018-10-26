package data.exchange.center.service.huayu.xfbg.conf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import data.exchange.center.common.rabbitmq.RabbitmqConf;

/**
 * 
 * Description: 初始化交换器，路由器，队列
 * <p>Company: xinya </p>
 * <p>Date:2017年9月4日 下午5:41:32</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
public class RabbitMqConfig {
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
		 RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
	     Map<String, Object> arguments = new HashMap<String, Object>();
	     arguments.put("x-max-priority",10);
	     
	     /**
	      * 声明初始化完毕队列
	      */
	     TopicExchange BUSINESS_DATA_EXCHANGE = new TopicExchange(RabbitmqConf.UNDO_SYNC_DATA_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(BUSINESS_DATA_EXCHANGE);
	     Queue BUSINESS_DATA_QUEUE = new Queue(RabbitmqConf.UNDO_SYNC_DATA_QUEUE, true, false, false, arguments);
	     rabbitAdmin.declareQueue(BUSINESS_DATA_QUEUE);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(BUSINESS_DATA_QUEUE).to(BUSINESS_DATA_EXCHANGE).with(RabbitmqConf.UNDO_SYNC_DATA_ROUTEKEY));
	     return null;
	}
}