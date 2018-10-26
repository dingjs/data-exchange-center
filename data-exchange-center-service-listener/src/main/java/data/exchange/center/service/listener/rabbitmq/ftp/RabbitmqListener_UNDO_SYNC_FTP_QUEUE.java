package data.exchange.center.service.listener.rabbitmq.ftp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
import data.exchange.center.service.listener.mapper.FtpMapper;
import data.exchange.center.service.listener.service.FtpService;
import data.exchange.center.service.listener.service.Logservice;
/**
 * 
 * Description: FTP抽取
 * <p>Company: xinya </p>
 * <p>Date:2017年9月6日 下午4:13:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
@RabbitListener(
		bindings = @QueueBinding(
				value = @Queue(value = RabbitmqConf.UNDO_SYNC_FTP_QUEUE, durable = "true"),
				exchange = @Exchange(value = RabbitmqConf.UNDO_SYNC_FTP_EXCHANGE, ignoreDeclarationExceptions = "true"),
				key = RabbitmqConf.UNDO_SYNC_FTP_ROUTEKEY)
		)
public class RabbitmqListener_UNDO_SYNC_FTP_QUEUE {
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitmqListener_UNDO_SYNC_FTP_QUEUE.class);  
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	private FtpService ftpService;
	@Autowired
	private FtpMapper ftpMapper;
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
    	String uuid = String.valueOf(loggerService.getId());
    	String ajbs = rabbitmqMessage.get(Constant.AJBS).toString();
    	String fydm = rabbitmqMessage.get(Constant.FYDM).toString();
    	try {
			/**
			 * 提取ftp文件
			 */
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) ftpService.handleFtpFile(
					rabbitmqMessage.get(Constant.AJBS).toString(), 
					rabbitmqMessage.get(Constant.AJLX).toString(), 
					rabbitmqMessage.get(Constant.FYDM).toString());
			
			if(map.get(CodeUtil.RETURN_CODE).toString().equalsIgnoreCase(CodeUtil.RETURN_SUCCESS)) {
				System.out.println("*******"+rabbitmqMessage.get(Constant.AJBS).toString()+"文件同步完毕");
				if(rabbitmqMessage.containsKey(Constant.RECOVERY)) {
					Map<String, String> params = new HashMap<>();
					params.put("ajbs", ajbs);
					params.put("fydm", fydm);
					ftpMapper.addRecoveryAjbs(params);
				}
				/**
				 * 文件抽取完成后流程结束
				 * 解锁
				 */
				String key = Constant.LOCK_NO.concat(rabbitmqMessage.get(Constant.AJBS).toString());
				if(redisTemplate.hasKey(key)) {
					redisTemplate.delete(key);
					System.out.println("非结构化抽取完成，全局锁已清除："+rabbitmqMessage.get(Constant.AJBS).toString());
					
				}else {
					System.out.println("非结构化抽取完成，全局锁不存在："+rabbitmqMessage.get(Constant.AJBS).toString());
				}
			}else {
				//同步文件错误  抛弃全局锁队列中数据 解锁
				System.out.println("*******"+rabbitmqMessage.get(Constant.AJBS).toString()+"文件同步异常");
				/**
				 * 文件抽取完成后流程结束
				 * 解锁
				 */
				String key = Constant.LOCK_NO.concat(rabbitmqMessage.get(Constant.AJBS).toString());
				if(redisTemplate.hasKey(key)) {
					redisTemplate.delete(key);
					System.out.println("非结构化抽取异常，"+map.get(CodeUtil.RETURN_MSG).toString()+";全局锁已清除："+rabbitmqMessage.get(Constant.AJBS).toString());
					loggerService.logger(
							applicationName, 
							10, 
							uuid, 
							ajbs, 
							"handlePath", 
							fydm, 
							"003", 
							"处理路径信息异常，"+map.get(CodeUtil.RETURN_MSG).toString()+";全局锁已清除："+rabbitmqMessage.get(Constant.AJBS).toString());
				}else {
					System.out.println("非结构化抽取异常，"+map.get(CodeUtil.RETURN_MSG).toString()+";全局锁不存在："+rabbitmqMessage.get(Constant.AJBS).toString());
					loggerService.logger(
							applicationName, 
							10, 
							uuid, 
							ajbs, 
							"handlePath", 
							fydm, 
							"003", 
							"处理路径信息异常，"+map.get(CodeUtil.RETURN_MSG).toString()+";全局锁已清除："+rabbitmqMessage.get(Constant.AJBS).toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			loggerService.logger(
					applicationName, 
					10, 
					uuid, 
					ajbs, 
					"dequeue", 
					fydm, 
					"003", 
					"FTP队列出队异常："+e.toString());
			logger.error("FTP队列出队异常："+e.toString());
		}finally {
			channel.basicAck(deliveryTag, false);
		}
    }
} 