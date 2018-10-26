package data.exchange.center.service.unstructured.node.task.push.rabbitmqListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

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

import data.exchange.center.common.constant.Constant;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
import data.exchange.center.service.unstructured.node.service.ContrstService;
import data.exchange.center.service.unstructured.node.service.impl.LogService;
import data.exchange.center.service.unstructured.node.task.contrast.ContrastTheadNew;
import data.exchange.center.service.unstructured.node.util.SpringContextUtil;
import data.exchange.center.service.unstructured.node.util.VeDate;

/**
 * 
 * Description: 对注解@RabbitListener中的队列进行监听
 * <p>Company: xinya </p>
 * <p>Date:2017年9月6日 下午4:13:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
@RabbitListener(
		bindings = @QueueBinding(
				value = @Queue(value = "${agentQueue}", durable = "true"),
				exchange = @Exchange(value = "${agentExchange}", ignoreDeclarationExceptions = "true"),
				key = "${agentRoutingKey}")
		)
public class RabbitmqListener {
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener.class);  
	@Value("${spring.application.name}")
	public String serverName;
	@Autowired
	private LogService logService;
    @Autowired
    private ContrstService contrstService;
    @Autowired
    private AgentPushDataMapper agentPushDataMapper;
    //对比数据redis
    @Resource(name = "redisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;
	//锁定表redis
    @Resource(name = "sgyRedisTemplate")
	private RedisTemplate<Object, Object> sgyRedisTemplate;
    
    @RabbitHandler
    public void process(
    		@Payload Map<String, Object> rabbitmqMessage,
    		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, 
    		Channel channel) throws IOException {
    	Map<String, Object> messageMap = new HashMap<String, Object>();
    	channel.basicQos(0, 1, false);
    	redisTemplate.setKeySerializer(new StringRedisSerializer());
    	redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		sgyRedisTemplate.setKeySerializer(new StringRedisSerializer());
		sgyRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
		
    	try {
    		  RabbitTemplate rabbitTemplate = (RabbitTemplate)SpringContextUtil.getApplicationContext().getBean("rabbitTemplate");
			/**
			 * redis不存在则说明此案件从未入库 否则需要对比
			 */
    		/**
			 * 调用白开发的处理类 
			 */
			try {
				int contrastThead = new ContrastTheadNew(
						rabbitmqMessage.get(Constant.AJBS).toString(),
						rabbitmqMessage.get(Constant.AJLX).toString(), 
						rabbitmqMessage.get(Constant.FYDM).toString(),
						rabbitmqMessage.get(Constant.AJZT).toString(), 
						redisTemplate,
						contrstService,
						agentPushDataMapper, 
						Integer.valueOf(rabbitmqMessage.get(Constant.LEVEL).toString()),
						rabbitmqMessage.get(Constant.AH).toString(),
						VeDate.strToDateLongLarq(rabbitmqMessage.get(Constant.LARQ).toString()),
						serverName,
						logService
						).run();
			if(contrastThead == 1) {
				    try {
				        messageMap.put(Constant.AJBS,  rabbitmqMessage.get(Constant.AJBS).toString());
	                    messageMap.put(Constant.FYDM,  rabbitmqMessage.get(Constant.FYDM).toString());
	                    messageMap.put(Constant.AJLX,  rabbitmqMessage.get(Constant.AJLX).toString());
	                    messageMap.put(Constant.LEVEL, rabbitmqMessage.get(Constant.LEVEL));
	                    rabbitTemplate.convertAndSend(
	                            RabbitmqConf.UNDO_SYNC_DATA_EXCHANGE, 
	                            RabbitmqConf.UNDO_SYNC_DATA_ROUTEKEY, 
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
	                    System.setProperty(Constant.C_MQSTATE, Constant.C_NORMAL_CONNECT);
                    } catch (Exception e) {
                        System.setProperty(Constant.C_MQSTATE, Constant.C_ERROR_CONNECT);
                        logger.error("案件标识为:" + rabbitmqMessage.get(Constant.AJBS).toString() + "redis清除锁或mq入队出现错误:" + e.getMessage());
                        logService.logger(serverName, 15, String.valueOf(logService.getId()), rabbitmqMessage.get(Constant.AJBS).toString(), "ajbs", Constant.FYDM, "003",e.getMessage());
                    }
				}else{
					String key = Constant.LOCK_NO.concat(rabbitmqMessage.get(Constant.AJBS).toString());
					if(sgyRedisTemplate.hasKey(key)) {
						sgyRedisTemplate.delete(key);
					}
				}
			} catch (Exception e) {
                logger.error("案件标识为:" + Constant.AJBS + "redis清除锁或mq入队出现错误:" + e.getMessage());
                logService.logger(serverName, 15, String.valueOf(logService.getId()), rabbitmqMessage.get(Constant.AJBS).toString(), "ajbs", Constant.FYDM, "003",e.getMessage());
			}finally {
				channel.basicAck(deliveryTag, false);
				channel.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
    }
} 