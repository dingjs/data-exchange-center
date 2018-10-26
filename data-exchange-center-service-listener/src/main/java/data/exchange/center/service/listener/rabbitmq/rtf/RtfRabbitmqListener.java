package data.exchange.center.service.listener.rabbitmq.rtf;

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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.listener.domain.Msg;
/**
 * 
 * Description: 对需要RTF转doc的进行RPC调用
 * <p>Company: xinya </p>
 * <p>Date:2018年5月18日 下午4:47:07</p>
 * @author Tony
 * @version 1.0
 *
 */
@Component
@RabbitListener(
		bindings = @QueueBinding(
				value = @Queue(value = RabbitmqConf.RTF_QUEUE, durable = "true"),
				exchange = @Exchange(value = RabbitmqConf.RTF_EXCHANGE, ignoreDeclarationExceptions = "true"),
				key = RabbitmqConf.RTF_ROUTEKEY)
		)
public class RtfRabbitmqListener {
	
	private static final Logger logger = LoggerFactory.getLogger(RtfRabbitmqListener.class);  
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
    @RabbitHandler
    public void process(
    		@Payload String msg,
    		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
    		Channel channel) throws Exception {
    	channel.basicQos(0, 1, false);
    	Gson gson = new Gson();
        Msg m = gson.fromJson(msg, Msg.class);
        String key = Constant.RTF_LK + m.getAjbs();
    	try {
    		rabbitTemplate.setReplyTimeout(1000*60*60);
    		rabbitTemplate.setReceiveTimeout(1000*60*60);
    		Object ret = rabbitTemplate.convertSendAndReceive(
    				RabbitmqConf.RPC_EXCHANGE, 
    				RabbitmqConf.RPC_ROUTEKEY, 
    				msg,
    				new MessagePostProcessor() {
    					@Override
    					public Message postProcessMessage(Message message) throws AmqpException {
    						message.getMessageProperties().setPriority(0);
    						return message;
    					}
    				},
    				new CorrelationData(UUID.randomUUID().toString())
    		);
    		System.out.println(String.format("案件：【%s】的处理结果为：%s", m.getAjbs(), new String((byte[])ret)));
    	} catch (Exception e) {
    		logger.error(String.format("【%s】远程调用出错：%s", m.getAjbs(), e.toString()));
			e.printStackTrace();
		}finally {
			channel.basicAck(deliveryTag, false);
			redisTemplate.delete(key);
		}
    }
} 