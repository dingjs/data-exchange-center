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

import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.unstructured.node.conf.RabbitMqConfig;
import data.exchange.center.service.unstructured.node.domain.Ajxx;
import data.exchange.center.service.unstructured.node.domain.SubTask;
import data.exchange.center.service.unstructured.node.domain.TaskInfo;
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
public class PushAjbsToQueueRw implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(PushAjbsToQueueRw.class);
    
    private AgentPushDataMapper agentPushDataMapper;
    private TaskInfo taskInfo;
    private SubTask subTask;
    private RedisTemplate<Object, Object> redisTemplate;
    
    public PushAjbsToQueueRw(AgentGetDataMapper agentGetDataMapper,TaskInfo taskInfo,SubTask subTask,RedisTemplate<Object,Object> redisTemplate,AgentPushDataMapper agentPushDataMapper) {
    	this.taskInfo = taskInfo;
    	this.subTask = subTask;
    	this.redisTemplate = redisTemplate;
    	this.agentPushDataMapper = agentPushDataMapper;
    }
    /**
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringContextUtil.getApplicationContext().getBean("rabbitTemplate");
        Connection connection = rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);
        Map<String, Object> params = new HashMap<String, Object>();
         
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
            }else{
                try {
                	//获取需要查询数据的总量
                	params.put("cntsql", Constant.getSql(subTask.getCdetail(), "cntsql"));
                	System.err.println("cntsql"+Constant.getSql(subTask.getCdetail(), "cntsql"));
                	int count = agentPushDataMapper.getPageAllAjxxFromSgyRw(params);
                	int pageNum = (count  +  10000  - 1) / 10000;
                	/**
                	 * 循环遍历根据页码获取记录
                	 */
                	for(int i = 1; i <= pageNum; i++) {
                		//组装查询数据sql
                		String datasql = "SELECT * FROM ("+Constant.getSql(subTask.getCdetail(), "datasql")+ ")T  "
                				+ "WHERE 1=1 AND RN > " + ((i-1)*Constant.PAGE_NUM) + " AND RN <= " + i*Constant.PAGE_NUM;
                		params.put("datasql",datasql);
                		System.err.println("datasql"+datasql);
                		List<Ajxx> ajbsList = agentPushDataMapper.getAllAjxxFromSgyRw(params);
                		for(Ajxx ajxx:ajbsList){
                			try{
                				/**
                	    		 * 加锁 
                	    		 * key=value
                	    		 * 用于判断当前案件标识是否处于被锁状态
                	    		 */
                				String key = Constant.LOCK_NO.concat(ajxx.getAHDM());
                				if(!redisTemplate.hasKey(key)) {
                	    			Map<String, Object> map = new HashMap<String, Object>();
                	    			map.put("key", key);
                	    			map.put("level", taskInfo.getnTasklev());
                	    			RedisSerializer<?> stringSerializer = new StringRedisSerializer();
                            	    redisTemplate.setKeySerializer(stringSerializer);
                            	    redisTemplate.setHashKeySerializer(stringSerializer);
                	    			ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
                	    			operations.set(key, map, TimeUtils.THIRTY_DAY, TimeUnit.SECONDS);
                	    			Map<String, Object> messageMap = new HashMap<String, Object>();
                    				messageMap.put(Constant.AJBS, ajxx.getAHDM());
                    				messageMap.put(Constant.FYDM, ajxx.getFYDM());
                    				messageMap.put(Constant.AJLX, ajxx.getAJLX());
                    				messageMap.put(Constant.AJZT, ajxx.getAJZT());
                    				messageMap.put(Constant.LEVEL, taskInfo.getnTasklev());
                    				messageMap.put(Constant.AH, ajxx.getAH());
                    				messageMap.put(Constant.LARQ, ajxx.getLARQ());
                    				System.out.println(ajxx.getAHDM());
                    				rabbitTemplate.convertAndSend(
                    						RabbitMqConfig.agentExchange, 
                    						RabbitMqConfig.agentRoutingKey, 
                    						messageMap,
                    						new MessagePostProcessor() {
                    							@Override
    											public Message postProcessMessage(Message message) throws AmqpException {
    												message.getMessageProperties().setPriority(taskInfo.getnTasklev());
    												return message;
    											}
    										},
                    						new CorrelationData(UUID.randomUUID().toString())
                    				);
                	    		}
//                  				else{
//                	    			ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
//                	    			@SuppressWarnings("unchecked")
//									Map<String, Object> listRedis =(Map<String, Object>) operations.get(key);
//                	    			//如果当前级别大于已存在级别就删除与存在的，填装级别高的
//                	    			if(taskInfo.getnTasklev() > (Integer)listRedis.get("level")){
//                	    				redisTemplate.delete(key);
//                	    				//重新填装
//                	    				Map<String, Object> map = new HashMap<String, Object>();
//                    	    			map.put("key", key);
//                    	    			map.put("level", taskInfo.getnTasklev());
//                    	    			operations.set(key, map, 1000, TimeUnit.SECONDS);
//                    	    			Map<String, Object> messageMap = new HashMap<String, Object>();
//                        				messageMap.put(Constant.AJBS, ajxx.getAHDM());
//                        				messageMap.put(Constant.FYDM, ajxx.getFYDM());
//                        				messageMap.put(Constant.AJLX, ajxx.getAJLX());
//                        				messageMap.put(Constant.AJZT, Constant.getAjzt(ajxx.getAJZT()));
//                        				messageMap.put(Constant.LEVEL, taskInfo.getnTasklev());
//                        				messageMap.put(Constant.AH, ajxx.getAH());
//                        				messageMap.put(Constant.LARQ, ajxx.getLARQ());
//                        				System.out.println(ajxx.getAHDM());
//                        				rabbitTemplate.convertAndSend(
//                        						RabbitMqConfig.agentExchange, 
//                        						RabbitMqConfig.agentRoutingKey, 
//                        						messageMap,
//                        						new MessagePostProcessor() {
//                        							@Override
//        											public Message postProcessMessage(Message message) throws AmqpException {
//        												message.getMessageProperties().setPriority(taskInfo.getnTasklev());
//        												return message;
//        											}
//        										},
//                        						new CorrelationData(UUID.randomUUID().toString())
//                        				);
//                	    			}
//                	    		}
                			}catch(Exception e){
                				e.printStackTrace();
                				logger.error("抽取非结构化初始数据出错："+e.getMessage());
                			}
                		}
                	}
                	System.out.println("所有案件标识已经同步完成");
                }
                catch (Exception e) {
                    e.printStackTrace();
                    logger.error("抽取非结构化初始数据出错："+e.getMessage());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error("抽取非结构化初始数据出错："+e.getMessage());
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
