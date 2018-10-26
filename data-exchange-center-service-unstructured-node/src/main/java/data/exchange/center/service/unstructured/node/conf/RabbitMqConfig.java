package data.exchange.center.service.unstructured.node.conf;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
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
	/**
	 * 用于同步非结构化数据
	 */
	@Value("${agentQueue}")
	public String queue;
	
	@Value("${agentExchange}")
	public String exchange;
    
    @Value("${agentRoutingKey}")
    public String routingKey;
    
	public static String agentQueue;
	public static String agentExchange;
	public static String agentRoutingKey;
	
	@PostConstruct  
	public void init() {  
		RabbitMqConfig.agentQueue      = queue;
		RabbitMqConfig.agentExchange   = exchange;
		RabbitMqConfig.agentRoutingKey = routingKey;
	} 
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
		 RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		 /**
		  * 申明任务队列
		  */
		 TopicExchange exchange = new TopicExchange(agentExchange, true, false);
	     rabbitAdmin.declareExchange(exchange);
	     Map<String, Object> arguments = new HashMap<String, Object>();
	     arguments.put("x-max-priority",10);
	     Queue queue = new Queue(agentQueue, true, false, false, arguments);
	     rabbitAdmin.declareQueue(queue);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(agentRoutingKey));
	     
	     /**
	      * 声明初始化完毕队列
	      */
	     TopicExchange UNDO_SYNC_EXCHANGE = new TopicExchange(RabbitmqConf.UNDO_SYNC_DATA_EXCHANGE, true, false);
	     rabbitAdmin.declareExchange(UNDO_SYNC_EXCHANGE);
	     Queue UNDO_SYNC_QUEUE = new Queue(RabbitmqConf.UNDO_SYNC_DATA_QUEUE, true, false, false, arguments);
	     rabbitAdmin.declareQueue(UNDO_SYNC_QUEUE);
	     rabbitAdmin.declareBinding(BindingBuilder.bind(UNDO_SYNC_QUEUE).to(UNDO_SYNC_EXCHANGE).with(RabbitmqConf.UNDO_SYNC_DATA_ROUTEKEY));
	     return null;
	}
}