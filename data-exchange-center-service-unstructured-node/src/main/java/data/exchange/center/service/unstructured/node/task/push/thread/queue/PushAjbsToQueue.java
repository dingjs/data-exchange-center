/**
 * data-governance-unstructured-agent-push
 * created by yuguang at 2017年5月24日
 */
package data.exchange.center.service.unstructured.node.task.push.thread.queue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.rabbitmq.client.Channel;

import data.exchange.center.common.log.LogUtil;
import data.exchange.center.common.rabbitmq.MessageLevel;
import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.unstructured.node.conf.RabbitMqConfig;
import data.exchange.center.service.unstructured.node.domain.Ajxx;
import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;
import data.exchange.center.service.unstructured.node.util.Constant;
import data.exchange.center.service.unstructured.node.util.SpringContextUtil;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年5月24日 下午5:58:29</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class PushAjbsToQueue{
	/**
	 * 用于同步非结构化数据
	 */

    private static Logger logger = LoggerFactory.getLogger(PushAjbsToQueue.class);
    
    private AgentPushDataMapper agentPushDataMapper;
	private RedisTemplate<Object, Object> redisTemplate;
    private String fydm;
    public PushAjbsToQueue(AgentGetDataMapper agentGetDataMapper, RedisTemplate<Object, Object> redisTemplate,AgentPushDataMapper agentPushDataMapper,String fydm) {
    	this.redisTemplate      = redisTemplate;
    	this.fydm = fydm.substring(0,4);
    	this.agentPushDataMapper = agentPushDataMapper;
    }
    /**
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringContextUtil.getApplicationContext().getBean("rabbitTemplate");
        Connection connection = rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);
        /**
         * 如果消息队列中还有剩余数据则跳过处理，下次执行任务的时候再次检查
         * 因为AGENT_2_QUEUE队列的出队是在一个listener监听中，异步出队不能确保完全出队完成
         * @see org.data.governance.unstructured.agent.push.task.push.rabbitmq.listener.AjbsMessageListener
         */
        try {
            /**
             * Returns the number of messages in a queue ready to be delivered to consumers. 
             * This method assumes the queue exists. If it doesn't, 
             * an exception will be closed with an exception.
             * 官方如此解释，所以如果计数大于0说明未完成全部数据出队，此次任务直接跳过
             */
            long msgCnt = channel.messageCount(RabbitMqConfig.agentQueue);
            if(msgCnt>0){
                /**
                 * 队列数据还未出队完成，跳过任务同步，否则可能会造成数据混乱
                 */
                logger.error(String.format(RabbitMqConfig.agentQueue +"%s", "队列非空，等待出对完成方可入队操作"));
            }else{
                try {
                	int count = agentPushDataMapper.getPageAllAjxxFromSgy(fydm);
                	int pageNum = (count  +  10000  - 1) / 10000;
                	/**
                	 * 循环遍历根据页码获取记录
                	 */
                	for(int i = 1; i <= pageNum; i++) {
                		Map<String, Object> params = new HashMap<String, Object>();
                 		params.put("min", (i-1)*Constant.PAGE_NUM);
                		params.put("max", i*Constant.PAGE_NUM);
                		params.put("fydm", fydm);
                		List<Ajxx> ajbsList = agentPushDataMapper.getAllAjxxFromSgy(params);
                		for(Ajxx ajxx:ajbsList){
                			try{
                				/**
                	    		 * 加锁 
                	    		 * key=value
                	    		 * 用于判断当前案件标识是否处于被锁状态
                	    		 * 如果当前案件还在redis中，说明处于锁定状态，流程未处理完
                	    		 * 再次进入流程系统则需要排除
                	    		 */
                				String key = Constant.LOCK_NO.concat(ajxx.getAHDM());
                	    		if(!redisTemplate.hasKey(key)) {
                	    			Map<String, Object> map = new HashMap<String, Object>();
                	    			map.put("key", key);
                	    			map.put("level", MessageLevel.LEVEL_0);
                	    			RedisSerializer<?> stringSerializer = new StringRedisSerializer();
                            	    redisTemplate.setKeySerializer(stringSerializer);
                            	    redisTemplate.setHashKeySerializer(stringSerializer);
                	    			ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
                	    			operations.set(key, map, TimeUtils.THIRTY_DAY, TimeUnit.SECONDS);
                	    		
                	    			Map<String, Object> messageMap = new HashMap<String, Object>();
                    				messageMap.put(Constant.AJBS, ajxx.getAHDM());
                    				messageMap.put(Constant.FYDM, ajxx.getFYDM());
                    				messageMap.put(Constant.AJLX, ajxx.getAJLX());
                    				messageMap.put(Constant.AJZT, Constant.getAjzt(ajxx.getAJZT()));
                    				messageMap.put(Constant.LEVEL, MessageLevel.LEVEL_0);
                    				messageMap.put(Constant.AH, ajxx.getAH());
                    				messageMap.put(Constant.LARQ, ajxx.getLARQ());
                    				rabbitTemplate.convertAndSend(
                    						RabbitMqConfig.agentExchange, 
                    						RabbitMqConfig.agentRoutingKey, 
                    						messageMap,
                    						new MessagePostProcessor() {
                    							@Override
    											public Message postProcessMessage(Message message) throws AmqpException {
    												message.getMessageProperties().setPriority(MessageLevel.LEVEL_0);
    												return message;
    											}
    										},
                    						new CorrelationData(UUID.randomUUID().toString())
                    				);
                	    		}
                			}catch(Exception e){
                				e.printStackTrace();
                				logger.error(LogUtil.LOG_PREFIX.concat("抽取非结构化初始数据出错：").concat(e.getMessage()));
                			}
                		}
                	}
                	System.out.println(LogUtil.LOG_PREFIX.concat("所有案件标识已经同步完成"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                    logger.error(LogUtil.LOG_PREFIX.concat("抽取非结构化初始数据出错：").concat(e.getMessage()));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error(LogUtil.LOG_PREFIX.concat("抽取非结构化初始数据出错：").concat(e.getMessage()));
        }finally{
            try {
                channel.close();
                connection.close();
            }
            catch (Exception e) {
            	  e.printStackTrace();
                  logger.error("关闭mq失败："+e.getMessage());
            }
        }
    }
}
