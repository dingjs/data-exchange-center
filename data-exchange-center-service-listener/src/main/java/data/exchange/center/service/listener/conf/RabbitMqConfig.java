package data.exchange.center.service.listener.conf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
		 /**
		  * 申明任务队列
		  */
		 TopicExchange exchange = new TopicExchange(RabbitmqConf.UNDO_SYNC_FTP_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(exchange);
	     Map<String, Object> arguments = new HashMap<String, Object>();
	     arguments.put("x-max-priority",10);
	     Queue queue = new Queue(RabbitmqConf.UNDO_SYNC_FTP_QUEUE, true, false, false, arguments);
	     rabbitAdmin.declareQueue(queue);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(RabbitmqConf.UNDO_SYNC_FTP_ROUTEKEY));
	     
	     /**
	      * 声明初始化完毕队列
	      */
	     TopicExchange UNDO_SYNC_PATH_EXCHANGE = new TopicExchange(RabbitmqConf.UNDO_SYNC_PATH_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(UNDO_SYNC_PATH_EXCHANGE);
	     Queue UNDO_SYNC_PATH_QUEUE = new Queue(RabbitmqConf.UNDO_SYNC_PATH_QUEUE, true, false, false, arguments);
	     rabbitAdmin.declareQueue(UNDO_SYNC_PATH_QUEUE);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(UNDO_SYNC_PATH_QUEUE).to(UNDO_SYNC_PATH_EXCHANGE).with(RabbitmqConf.UNDO_SYNC_PATH_ROUTEKEY));
	     /**
	      * 声明初始化完毕队列
	      */
	     TopicExchange UNDO_SYNC_EXCHANGE = new TopicExchange(RabbitmqConf.UNDO_SYNC_DATA_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(UNDO_SYNC_EXCHANGE);
	     Queue UNDO_SYNC_QUEUE = new Queue(RabbitmqConf.UNDO_SYNC_DATA_QUEUE, true, false, false, arguments);
	     rabbitAdmin.declareQueue(UNDO_SYNC_QUEUE);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(UNDO_SYNC_QUEUE).to(UNDO_SYNC_EXCHANGE).with(RabbitmqConf.UNDO_SYNC_DATA_ROUTEKEY));
	     
	     /**
	      * 声明初始化完毕队列
	      */
	     TopicExchange BUSINESS_DATA_EXCHANGE = new TopicExchange(RabbitmqConf.BUSINESS_DATA_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(BUSINESS_DATA_EXCHANGE);
	     Queue BUSINESS_DATA_QUEUE = new Queue(RabbitmqConf.BUSINESS_DATA_QUEUE, true, false, false, arguments);
	     rabbitAdmin.declareQueue(BUSINESS_DATA_QUEUE);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(BUSINESS_DATA_QUEUE).to(BUSINESS_DATA_EXCHANGE).with(RabbitmqConf.BUSINESS_DATA_ROUTEKEY));
	     
	     /**
	      * 声明初始化完毕队列
	      */
	     TopicExchange RTF_EXCHANGE = new TopicExchange(RabbitmqConf.RTF_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(RTF_EXCHANGE);
	     Queue RTF_QUEUE = new Queue(RabbitmqConf.RTF_QUEUE, true, false, false, arguments);
	     rabbitAdmin.declareQueue(RTF_QUEUE);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(RTF_QUEUE).to(RTF_EXCHANGE).with(RabbitmqConf.RTF_ROUTEKEY));
	     
	     /**
	      * RPC调用队列
	      */
	     DirectExchange rpcExchange  = new DirectExchange (RabbitmqConf.RPC_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(rpcExchange);
	     Queue rpcQueue = new Queue(RabbitmqConf.RPC_QUEUE, true, false, false);
	     rabbitAdmin.declareQueue(rpcQueue);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(rpcQueue).to(rpcExchange).with(RabbitmqConf.RPC_ROUTEKEY));
	     return null;
	}
}