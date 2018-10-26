package data.exchange.center.service.unstructured.node.task.push.thread.queue;

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

import data.exchange.center.common.rabbitmq.MessageLevel;
import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.unstructured.node.conf.RabbitMqConfig;
import data.exchange.center.service.unstructured.node.domain.Ajxx;
import data.exchange.center.service.unstructured.node.domain.RcbAjbs;
import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;
import data.exchange.center.service.unstructured.node.service.impl.LogService;
import data.exchange.center.service.unstructured.node.util.Constant;
import data.exchange.center.service.unstructured.node.util.SpringContextUtil;

public class DleAjbsToRedis {
    /**
     * 用于根据回收池删除redis数据
     */
    private static Logger logger = LoggerFactory.getLogger(DleAjbsToRedis.class);

    public DleAjbsToRedis(AgentGetDataMapper agentGetDataMapper, RedisTemplate<Object, Object> redisTemplate,
            AgentPushDataMapper agentPushDataMapper, String fydm, RedisTemplate<Object, Object> redisTemplateLock,String serverName,LogService logService) {
        RabbitTemplate rabbitTemplate = (RabbitTemplate) SpringContextUtil.getApplicationContext()
                .getBean("rabbitTemplate");
        Connection connection = rabbitTemplate.getConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);
        try {
            List<RcbAjbs> rcbAjbsList = agentPushDataMapper.getDelRedis(fydm.substring(0, 4));
            for (int i = 0; i < rcbAjbsList.size(); i++) {
                RcbAjbs rcbAjbs = rcbAjbsList.get(i);
                try {
                    // 删除redis里面原有错误数据
                    redisTemplate.delete(rcbAjbs.getAJBS() + Constant.C_EAJ_JZ);
                    redisTemplate.delete(rcbAjbs.getAJBS() + Constant.C_EAJ_MLXX);
                    redisTemplate.delete(rcbAjbs.getAJBS() + Constant.C_EAJ_JZWJ_ALL_NEW);
                    redisTemplate.delete(rcbAjbs.getAJBS() + Constant.C_EAJ_JZWJ_ALL);
                    redisTemplate.delete(rcbAjbs.getAJBS() + Constant.C_EAJ_MLXX_GC);
                    redisTemplate.delete(rcbAjbs.getAJBS() + Constant.C_EAJ_SSJCYX);
                    //写入队列并加redis全局锁
                    Ajxx ajxx = agentPushDataMapper.getAllAjxxFromDelRedis(rcbAjbs.getAJBS());
                    if(ajxx != null){
                    String key = Constant.LOCK_NO.concat(ajxx.getAHDM());
                    if (!redisTemplate.hasKey(key)) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        // 写入redis锁定
                        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
                        redisTemplateLock.setKeySerializer(stringSerializer);
                        redisTemplateLock.setHashKeySerializer(stringSerializer);
                        ValueOperations<Object, Object> operations = redisTemplateLock.opsForValue();
                        map.put("key", key);
                        map.put("level", MessageLevel.LEVEL_9);
                        operations.set(key, map, TimeUtils.THIRTY_DAY, TimeUnit.SECONDS);
                        // 写入队列
                        Map<String, Object> messageMap = new HashMap<String, Object>();
                        messageMap.put(Constant.AJBS, ajxx.getAHDM());
                        messageMap.put(Constant.FYDM, ajxx.getFYDM());
                        messageMap.put(Constant.AJLX, ajxx.getAJLX());
                        messageMap.put(Constant.AJZT, ajxx.getAJZT());
                        messageMap.put(Constant.AH,   ajxx.getAH());
                        messageMap.put(Constant.LARQ, ajxx.getLARQ());
                        messageMap.put(Constant.LEVEL, MessageLevel.LEVEL_9);// 回收池的案件重新处理默认为最高级别
                        // 写入mq队列
                        rabbitTemplate.convertAndSend(RabbitMqConfig.agentExchange, RabbitMqConfig.agentRoutingKey,
                                messageMap, new MessagePostProcessor() {
                                    @Override
                                    public Message postProcessMessage(Message message) throws AmqpException {
                                        message.getMessageProperties().setPriority(MessageLevel.LEVEL_9);
                                        return message;
                                    }
                                }, new CorrelationData(UUID.randomUUID().toString()));
                    }
                    }
                  int delSize =  agentPushDataMapper.getDelRcbAjbs(rcbAjbs.getAJBS());
                  if(delSize >= 1){
                      System.out.println(rcbAjbs.getAJBS()+"回收成功并重新写入队列");
                  }
                } catch (Exception e) {
                    logService.logger(serverName, 15, String.valueOf(logService.getId()), rcbAjbs.getAJBS(), "ajbs", fydm, "003",e.getMessage() == null ? "null" : e.getMessage());
                }
              
            }
        } catch (Exception e) {
            logger.error("回收池案件删除失败" + e.getMessage());
        } finally {
            try {
                channel.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("关闭mq失败：" + e.getMessage());
            }

        }
    }
}
