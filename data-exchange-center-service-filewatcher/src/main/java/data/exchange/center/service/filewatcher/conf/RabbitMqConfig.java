package data.exchange.center.service.filewatcher.conf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import data.exchange.center.common.rabbitmq.RabbitmqConf;

/**
 * 
 * Description:协同平台队列
 * <p>Company: xinya </p>
 * <p>Date:2017年10月25日 下午4:30:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
public class RabbitMqConfig {
	
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
		 RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		 /**
		  * 申明任务队列
		  */
		 TopicExchange exchange = new TopicExchange(RabbitmqConf.XTBAPT_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(exchange);
	     Map<String, Object> arguments = new HashMap<String, Object>();
	     arguments.put("x-max-priority",10);
	     Queue queue = new Queue(RabbitmqConf.XTBAPT_QUEUE, true, false, false, arguments);
	     rabbitAdmin.declareQueue(queue);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(RabbitmqConf.XTBAPT_ROUTEKEY));
	     
	     return null;
	}
}