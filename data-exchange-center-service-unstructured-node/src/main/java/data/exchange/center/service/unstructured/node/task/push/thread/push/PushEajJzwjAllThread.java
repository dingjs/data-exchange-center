//package data.exchange.center.service.unstructured.node.task.push.thread.push;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.ApplicationContext;
//
//import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
//import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
//import data.exchange.center.service.unstructured.node.mapper.tdh.AgentGetDataMapper;
//import data.exchange.center.service.unstructured.node.util.Constant;
//import data.exchange.center.service.unstructured.node.util.SpringContextUtil;
//
///**
// * Description:   暂时不管这个类
// * <p>Company: pelox </p>
// * <p>Date:2017年5月25日 上午9:37:39</p>
// * @author Wen.Yuguang
// * @version 1.0
// **/
//public class PushEajJzwjAllThread implements Runnable {
//
//    private static Logger logger = LoggerFactory.getLogger(PushEajJzwjAllThread.class);
//    
//    private String ajbs;
//    
//    public PushEajJzwjAllThread(String ajbs){
//        this.ajbs = ajbs;
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
//            SqlSession sgySqlSession = ((SqlSessionFactory)SpringContextUtil
//                    .getApplicationContext()
//                    .getBean("sgySqlSessionFactory"))
//                    .openSession();
//            SqlSession tdhSqlSession = ((SqlSessionFactory)SpringContextUtil
//                    .getApplicationContext()
//                    .getBean("tdhSqlSessionFactory"))
//                    .openSession();
//            try {
//                AgentGetDataMapper agentGetDataMapper = tdhSqlSession.getMapper(AgentGetDataMapper.class);
//                AgentPushDataMapper agentPushDataMapper = sgySqlSession.getMapper(AgentPushDataMapper.class);
//                
//                List<TempEajJzwjAll> list = agentGetDataMapper.getEajJzWjAllFromTDH(ajbs);
//                for(TempEajJzwjAll tempEajJzwjAll:list){
////                    tempEajJzwjAll.setFYDM(msg.get("FYDM").toString());
//                    Map<String, Object> map = new HashMap<String,Object>();
//                    map.put("xh", tempEajJzwjAll.getXH());
//                    map.put("yxxh", tempEajJzwjAll.getYXXH());
//                    map.put("ajlx", tempEajJzwjAll.getAJLX());
//                    map.put("date", tempEajJzwjAll.getLASTUPDATE());
//                    listMap.add(map);
//                    int status = agentPushDataMapper.pushEajJzwjAllToSGY(tempEajJzwjAll);
//                    /**
//                     * 插入失败的记录下来
//                     */
//                    if(status != 1){
//                        final HashMap<String, Object> messageMap = new HashMap<String, Object>();
//                        messageMap.put(Constant.AJBS, tempEajJzwjAll.getAHDM());
//                        messageMap.put("XH", tempEajJzwjAll.getXH());
//                        messageMap.put("OBJ", tempEajJzwjAll.toString());
//                        template.convertAndSend(messageMap);
//                    }
//                }
//                //String key = ajbs + "_EAJ_JZWJ_ALL";
////                jedis.set(key.getBytes(), SerializationUtil.serialize(listMap));
//                logger.info("ajbs:"+ajbs+" 处理成功!");
//            }
//            catch (Exception e) {
//                logger.error(e.getMessage());
//                e.printStackTrace();
//            }finally{
//                sgySqlSession.close();
//                tdhSqlSession.close();
//            }
//        }catch(Exception e){
//            logger.error(e.getMessage());
//            System.exit(-1);
//        }
//    }
//}
