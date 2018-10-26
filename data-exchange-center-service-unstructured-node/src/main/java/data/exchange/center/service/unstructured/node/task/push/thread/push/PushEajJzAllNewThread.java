//package data.exchange.center.service.unstructured.node.task.push.thread.push;
//
//import java.util.ArrayList;
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
//
//import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
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
//public class PushEajJzAllNewThread implements Runnable {
//    
//    private static Logger logger = LoggerFactory.getLogger(PushEajJzAllNewThread.class);
//    
//    private Map<String, Object> msg;
//    
//    private RedisTemplate<Object, Object> redisTemplate;
//    private AgentGetDataMapper agentGetDataMapper;
//    private AgentPushDataMapper agentPushDataMapper;
//    
//    public PushEajJzAllNewThread(Map<String, Object> msg, RedisTemplate<Object, Object> redisTemplate, AgentGetDataMapper agentGetDataMapper, AgentPushDataMapper agentPushDataMapper){
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
//            RabbitTemplate template = (RabbitTemplate) applicationContext.getBean("rabbitTemplate");
//            List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
//            try {
//                
//                List<TempEajJzwjAllNew> list = agentGetDataMapper.getEajJzwjAllNewFromTDH(msg.get("AJBS").toString());
//                /**
//                 * 向更新主表中添加信息
//                 */
//                if(list.size()>0){
//                    agentPushDataMapper.updateEajJzAllNewAJZT(msg.get("AJBS").toString());
//                }
//                for(TempEajJzwjAllNew tempEajJzwjAllNew:list){
//                    tempEajJzwjAllNew.setFYDM(msg.get("FYDM").toString());
//                    ConcurrentMap<String, Object> map = new ConcurrentHashMap<String,Object>();
//                    map.put("xh", tempEajJzwjAllNew.getXH());
//                    map.put("yxxh", tempEajJzwjAllNew.getYXXH());
//                    map.put("ajlx", tempEajJzwjAllNew.getAJLX());
//                    map.put("date", tempEajJzwjAllNew.getLASTUPDATE());
//                    listMap.add(map);
//                    int status = agentPushDataMapper.pushEajJzwjAllNewToSGY(tempEajJzwjAllNew);
//                    /**
//                     * 插入失败的记录下来
//                     */
//                    if(status != 1){
//                        final ConcurrentMap<String, Object> messageMap = new ConcurrentHashMap<String, Object>();
//                        messageMap.put(Constant.AJBS, tempEajJzwjAllNew.getAHDM());
//                        messageMap.put("XH", tempEajJzwjAllNew.getXH());
//                        messageMap.put("OBJ", tempEajJzwjAllNew.toString());
//                        template.convertAndSend(messageMap);
//                    }
//                }
//                String key = msg.get("AJBS").toString() + "_EAJ_JZWJ_ALL_NEW";
//                ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
//                operations.set(key, SerializationUtil.serialize(listMap), 1000, TimeUnit.SECONDS);
////                jedis.set(key.getBytes(), SerializationUtil.serialize(listMap));
//                logger.info("ajbs:"+msg.get("AJBS").toString()+" EAJ_JZWJ_ALL_NEW处理成功!");
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                logger.error(e.getMessage());
//            }
//        }catch(Exception e){
//            logger.error(e.getMessage());
//        }
//    }
//}
