package data.exchange.center.service.listener.scheduler;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.listener.domain.Msg;
import data.exchange.center.service.listener.mapper.RtfMapper;

@Component
public class RtfScheduler{
	
	private static Logger logger =   LoggerFactory.getLogger(RtfScheduler.class);
	
    @Autowired
    private RtfMapper rtfMapper;
    @Autowired
	private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
	private RabbitTemplate rabbitTemplate;
	/**
	 * 
	 * @function 每晚12点40执行一次
	 * @author wenyuguang
	 * @creaetime 2017年10月17日 上午11:09:43
	 */
    @Scheduled(cron="0 40 0 * * ?")
	public void run() throws Exception {
    	logger.info("开始查询需要RTF转DOC的案件");
    	try {
    		redisTemplate.setKeySerializer(new StringRedisSerializer());
        	redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    		List<Msg> msgList = rtfMapper.getRtfToDocAjbs();
    		for(Msg msg:msgList) {
    			String key = Constant.RTF_LK + msg.getAjbs();
    			
    			if(!redisTemplate.hasKey(key)) {
    				ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
    				operations.set(key, "1", TimeUtils.THIRTY_DAY, TimeUnit.SECONDS);
    				rabbitTemplate.convertAndSend(
    						RabbitmqConf.RTF_EXCHANGE, 
    						RabbitmqConf.RTF_ROUTEKEY, 
    						new Gson().toJson(msg),
    						new MessagePostProcessor() {
    							@Override
    							public Message postProcessMessage(Message message) throws AmqpException {
    								message.getMessageProperties().setPriority(0);
    								return message;
    							}
    						},
    						new CorrelationData(UUID.randomUUID().toString())
    				);
    			}
    			System.out.println(String.format("【%s】已入队列", msg.getAjbs()));
    		}
    	} catch (Exception e) {
			logger.error(String.format("查询RTF案件标识出错：%s",e.toString()));
			e.printStackTrace();
		}
	}
}
