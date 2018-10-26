//package data.exchange.center.service.unstructured.node.task.push.thread.push;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
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
//import data.exchange.center.service.unstructured.node.domain.TempEajJz;
//import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
//import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;
//import data.exchange.center.service.unstructured.node.util.Constant;
//import data.exchange.center.service.unstructured.node.util.DecodeUtil;
//import data.exchange.center.service.unstructured.node.util.SerializationUtil;
//import data.exchange.center.service.unstructured.node.util.SpringContextUtil;
//import oracle.sql.BLOB;
//
///**
// * Description:
// * <p>Company: pelox </p>
// * <p>Date:2017年5月25日 上午9:37:39</p>
// * @author Wen.Yuguang
// * @version 1.0
// **/
//public class PushEajJzThread implements Runnable {
//    
//    private static Logger logger = LoggerFactory.getLogger(PushEajJzThread.class);
//    
//    private Map<String, Object> msg;
//    
//    private RedisTemplate<Object, Object> redisTemplate;
//    private AgentGetDataMapper agentGetDataMapper;
//    private AgentPushDataMapper agentPushDataMapper;
//    
//    public PushEajJzThread(Map<String, Object> msg, RedisTemplate<Object, Object> redisTemplate, AgentGetDataMapper agentGetDataMapper, AgentPushDataMapper agentPushDataMapper){
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
//                String flag = msg.get("AJBS").toString().substring(14);
//                ConcurrentMap<String,String> paramMap = new ConcurrentHashMap<String,String>();
//                paramMap.put("ajbs", msg.get("AJBS").toString());
//                paramMap.put("flag", flag);
//                List<TempEajJz> list = agentGetDataMapper.getEajJzFromTDH(paramMap);
//                /**
//                 * 向更新主表中添加信息
//                 */
//                if(list.size()>0){
//                    agentPushDataMapper.updateEajJzAJZT(msg.get("AJBS").toString());
//                }
//                for(TempEajJz tempEajJz:list){
//                    tempEajJz.setFYDM(msg.get("FYDM").toString());
//                    /**
//                     * 如果ysbz=2，则这个blob字段是经过压缩的
//                     */
//                    if(!StringUtils.isEmpty(tempEajJz.getYSBZ())
//                            &&!StringUtils.isEmpty(tempEajJz.getNR())
//                            &&tempEajJz.getYSBZ().equals("2")){
//                        byte[] before = DecodeUtil.blobToBytes((BLOB)tempEajJz.getNR());
//                        InputStream in = new ByteArrayInputStream(before);
//                        byte[] blobByte = DecodeUtil.deCompress(in);
//                        tempEajJz.setNR(blobByte);
//                        tempEajJz.setWJDX(Float.valueOf(blobByte.length));
//                    }
//                    Map<String, Object> map = new HashMap<String,Object>();
//                    map.put("xh", tempEajJz.getXH());
//                    map.put("ajlx", tempEajJz.getAJLX());
//                    map.put("date", tempEajJz.getLASTUPDATE());
//                    listMap.add(map);
//                    int status=0;
//                    try{
//                        status = agentPushDataMapper.pushEajJzToSGY(tempEajJz);
//                    }catch(Exception e){
//                        final ConcurrentMap<String, Object> messageMap = new ConcurrentHashMap<String, Object>();
//                        messageMap.put(Constant.AJBS, tempEajJz.getAHDM());
//                        messageMap.put("XH", tempEajJz.getXH());
//                        messageMap.put("OBJ", tempEajJz.toString());
//                        template.convertAndSend(messageMap);
//                        break;
//                    }
//                    /**
//                     * 插入失败的记录下来
//                     */
//                    if(status != 1){
//                        final ConcurrentMap<String, Object> messageMap = new ConcurrentHashMap<String, Object>();
//                        messageMap.put(Constant.AJBS, tempEajJz.getAHDM());
//                        messageMap.put("XH", tempEajJz.getXH());
//                        messageMap.put("OBJ", tempEajJz.toString());
//                        template.convertAndSend(messageMap);
//                    }
//                }
//                String key = msg.get("AJBS").toString() + "_EAJ_JZ";
//                ValueOperations<Object, Object>  operations = redisTemplate.opsForValue();
//                operations.set(key, SerializationUtil.serialize(listMap), 1000, TimeUnit.SECONDS);
////                jedis.set(key.getBytes(), SerializationUtil.serialize(listMap));
//                logger.info("ajbs:"+msg.get("AJBS").toString()+" EAJ_JZ处理成功!");
////                sgySqlSession.commit();
////                tdhSqlSession.commit();
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                logger.error(e.getMessage());
//            }finally{
////                sgySqlSession.close();
////                tdhSqlSession.close();
//            }
//        }catch(Exception e){
//            logger.error(e.getMessage());
//        }
//    }
//}
