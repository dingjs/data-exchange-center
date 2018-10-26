//package data.exchange.center.service.unstructured.node.task.push.thread.push;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.ApplicationContext;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.util.StringUtils;
//
//import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
//import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
//import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;
//import data.exchange.center.service.unstructured.node.util.Constant;
//import data.exchange.center.service.unstructured.node.util.SerializationUtil;
//import data.exchange.center.service.unstructured.node.util.SpringContextUtil;
//
///**
// * Description:
// * <p>Company: pelox </p>
// * <p>Date:2017年5月25日 上午9:37:39</p>
// * @author Wen.Yuguang
// * @version 1.0
// **/
//public class PushEajMlxxThread implements Runnable {
//
//    private static Logger logger = LoggerFactory.getLogger(PushEajMlxxThread.class);
//    
//    private Map<String, Object> msg;
//    
//    private RedisTemplate<Object, Object> redisTemplate;
//    private AgentGetDataMapper agentGetDataMapper;
//    private AgentPushDataMapper agentPushDataMapper;
//    
//    public PushEajMlxxThread(Map<String, Object> msg, RedisTemplate<Object, Object> redisTemplate, AgentGetDataMapper agentGetDataMapper, AgentPushDataMapper agentPushDataMapper){
//        this.msg = msg;
//        this.redisTemplate = redisTemplate;
//        this.agentGetDataMapper = agentGetDataMapper;
//        this.agentPushDataMapper = agentPushDataMapper;
//    }
//    /** (non-Javadoc)
//     * @see java.lang.Runnable#run()
//     */
//    @Override
//    public void run() {
//        try{
//            ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
//            RabbitTemplate template = (RabbitTemplate) applicationContext.getBean("readyAmqpTemplate");
//            List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
//            try {
//                List<TempEajMlxx> list = agentGetDataMapper.getEajMlxxFromTDH(msg.get("AJBS").toString());
//                /**
//                 * 向更新主表中添加更新记录
//                 */
//                if(list.size()>0){
//                    agentPushDataMapper.updateEajMlxxAJZT(msg.get("AJBS").toString());
//                }
//                for(TempEajMlxx tempEajMlxx:list){
//                    tempEajMlxx.setFYDM(msg.get("FYDM").toString());
//                    ConcurrentMap<String, Object> map = new ConcurrentHashMap<String,Object>();
//                    map.put("xh", tempEajMlxx.getXH());
//                    map.put("ajlx", tempEajMlxx.getAJLX());
//                    if(StringUtils.isEmpty(tempEajMlxx.getLASTUPDATE())){
//                        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//                    }else{
//                    	 map.put("date", tempEajMlxx.getLASTUPDATE());
//                    }
//                    listMap.add(map);
//                    int status = agentPushDataMapper.pushEajMlxxToSGY(tempEajMlxx);
//                    /**
//                     * 插入失败的记录下来
//                     */
//                    if(status != 1){
//                        final ConcurrentMap<String, Object> messageMap = new ConcurrentHashMap<String, Object>();
//                        messageMap.put(Constant.AJBS, tempEajMlxx.getLSH());
//                        messageMap.put("XH", tempEajMlxx.getXH());
//                        messageMap.put("OBJ", tempEajMlxx.toString());
//                        template.convertAndSend(messageMap);
//                    }
//                }
//                String key = msg.get("AJBS").toString() + "_EAJ_MLXX";
//                ValueOperations<Object, Object>  operations = redisTemplate.opsForValue();
//                operations.set(key, SerializationUtil.serialize(listMap), 1000, TimeUnit.SECONDS);
////                jedis.set(key.getBytes(), SerializationUtil.serialize(listMap));
//                logger.info("ajbs:"+msg.get("AJBS").toString()+" EAJ_MLXX处理成功!");
//            }
//            catch (Exception e) {
//                logger.error(e.getMessage());
//                e.printStackTrace();
//            }
//        }catch(Exception e){
//            logger.error(e.getMessage());
//            System.exit(-1);
//        }
//    }
//}
