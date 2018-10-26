package data.exchange.center.service.listener.rabbitmq.rpctest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.common.constant.Constant;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.listener.service.FtpService;
import data.exchange.center.service.listener.service.Logservice;
/**
 * 
 * Description: 对缓存表中的数据进行处理
 * <p>Company: xinya </p>
 * <p>Date:2017年9月6日 下午4:13:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
//@Component
//@RabbitListener(
//		bindings = @QueueBinding(
//				value = @Queue(value = RabbitmqConf.RPC_QUEUE, durable = "true"),
//				exchange = @Exchange(value = RabbitmqConf.RPC_EXCHANGE, ignoreDeclarationExceptions = "true"),
//				key = RabbitmqConf.RPC_ROUTEKEY)
//		)
public class RpcRabbitmqListener {
	
	private static final Logger logger = LoggerFactory.getLogger(RpcRabbitmqListener.class);  
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
    @RabbitHandler
    public String process(
    		@Payload String msg,
    		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
    		Channel channel) throws Exception {
    	channel.basicQos(0, 1, false);
    	try {
    		System.out.println("收到消息："+msg);
			return "收到消息咯。处理完咯"+msg;
    	} catch (Exception e) {
			e.printStackTrace();
		}finally {
			channel.basicAck(deliveryTag, false);
		}
    	return null;
    }
} 