package data.exchange.center.service.listener.rabbitmq.ftp;

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
@Component
@RabbitListener(
		bindings = @QueueBinding(
				value = @Queue(value = RabbitmqConf.UNDO_SYNC_DATA_QUEUE, durable = "true"),
				exchange = @Exchange(value = RabbitmqConf.UNDO_SYNC_DATA_EXCHANGE, ignoreDeclarationExceptions = "true"),
				key = RabbitmqConf.UNDO_SYNC_DATA_ROUTEKEY)
		)
public class RabbitmqListener_UNDO_SYNC_DATA_QUEUE {
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener_UNDO_SYNC_DATA_QUEUE.class);  
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	private Logservice loggerService;
	@Value("${spring.application.name}")
	private String applicationName;
	
    @RabbitHandler
    public void process(
    		@Payload Map<String, Object> rabbitmqMessage,
    		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
    		Channel channel) throws Exception {
    	channel.basicQos(0, 1, false);
    	redisTemplate.setKeySerializer(new StringRedisSerializer());
    	redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    	Map<String, Object> messageMap = new HashMap<String, Object>();
    	String uuid = String.valueOf(loggerService.getId());
    	String ajbs = rabbitmqMessage.get(Constant.AJBS).toString();
    	String ajlx = rabbitmqMessage.get(Constant.AJBS).toString();
    	String fydm = rabbitmqMessage.get(Constant.FYDM).toString();
    	try {
    		messageMap.put(Constant.AJBS,  rabbitmqMessage.get(Constant.AJBS).toString());
			messageMap.put(Constant.FYDM,  rabbitmqMessage.get(Constant.FYDM).toString());
			messageMap.put(Constant.AJLX,  rabbitmqMessage.get(Constant.AJLX).toString());
			messageMap.put(Constant.LEVEL, rabbitmqMessage.get(Constant.LEVEL));
			
			/**
			 * 对缓存到本地数据进行处理
			 */
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) 
				ftpService.handleTempUnstructureData(ajbs, ajlx, fydm, uuid);
			if(map.get(CodeUtil.RETURN_CODE).toString().equalsIgnoreCase(CodeUtil.RETURN_SUCCESS)){
				rabbitTemplate.convertAndSend(
						RabbitmqConf.UNDO_SYNC_PATH_EXCHANGE, 
						RabbitmqConf.UNDO_SYNC_PATH_ROUTEKEY, 
						messageMap,
						new MessagePostProcessor() {
							@Override
							public Message postProcessMessage(Message message) throws AmqpException {
								message.getMessageProperties().setPriority((Integer) rabbitmqMessage.get(Constant.LEVEL));
								return message;
							}
						},
						new CorrelationData(UUID.randomUUID().toString())
				);
			}else {
				//错误记录
				String key = Constant.LOCK_NO.concat(ajbs);
				if(redisTemplate.hasKey(key)) {
					redisTemplate.delete(key);
					logger.info("处理本地缓存表异常，"+map.get(CodeUtil.RETURN_MSG).toString()+";全局锁已清除："+rabbitmqMessage.get(Constant.AJBS).toString());
					loggerService.logger(
							applicationName, 
							10, 
							uuid, 
							ajbs, 
							"handleTempUnstructureData", 
							fydm, 
							"003", 
							"处理本地缓存表异常，"+map.get(CodeUtil.RETURN_MSG).toString()+";全局锁已清除："+rabbitmqMessage.get(Constant.AJBS).toString());
				}else {
					logger.info("处理本地缓存表异常，"+map.get(CodeUtil.RETURN_MSG).toString()+";全局锁不存在："+rabbitmqMessage.get(Constant.AJBS).toString());
					loggerService.logger(
							applicationName, 
							10, 
							uuid, 
							ajbs, 
							"FTP-AJBS", 
							fydm, 
							"003", 
							"处理本地缓存表异常，"+map.get(CodeUtil.RETURN_MSG).toString()+";全局锁不存在："+rabbitmqMessage.get(Constant.AJBS).toString());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			loggerService.logger(
					applicationName, 
					10, 
					uuid, 
					ajbs, 
					"FTP-AJBS", 
					fydm, 
					"003", 
					"DATA缓存表队列出队错误："+rabbitmqMessage+e.getMessage());
			logger.error("DATA缓存表队列出队错误："+rabbitmqMessage+e.getMessage());
		}finally {
			channel.basicAck(deliveryTag, false);
		}
    }
} 