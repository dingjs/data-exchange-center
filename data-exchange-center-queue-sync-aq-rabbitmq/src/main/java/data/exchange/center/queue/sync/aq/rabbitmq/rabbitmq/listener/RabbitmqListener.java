package data.exchange.center.queue.sync.aq.rabbitmq.rabbitmq.listener;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.rabbitmq.client.Channel;

import data.exchange.center.queue.sync.aq.rabbitmq.oraceaq.sender.SendMsgToOracleAQ;

/**
 * 
 * Description: 对注解@RabbitListener中的队列进行监听
 * <p>Company: xinya </p>
 * <p>Date:2017年9月6日 下午4:13:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
@RabbitListener(queues = "queueTest")
public class RabbitmqListener {
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener.class);  
	
    @RabbitHandler
    public void process(
    		@Payload HashMap<String, Object> rabbitmqMessage,
    		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
    		Channel channel) {
    	try {
    		System.out.println(rabbitmqMessage);
    		SendMsgToOracleAQ.enQueue(rabbitmqMessage);
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(String.format("rabbitmq 出队错误： %s", e.getMessage()));
		}
    }  
} 