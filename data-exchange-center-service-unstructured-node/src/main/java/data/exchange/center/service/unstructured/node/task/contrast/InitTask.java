package data.exchange.center.service.unstructured.node.task.contrast;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import data.exchange.center.service.unstructured.node.domain.FjghInit;
import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
import data.exchange.center.service.unstructured.node.util.Constant;
@Component
public class InitTask implements ApplicationListener<ContextRefreshedEvent> {
    @Value("${fydm}")
    private String fydm;
    //对比数据redis
    @Resource(name = "redisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private AgentPushDataMapper agentPushDataMapper;
    private static Logger logger = LoggerFactory.getLogger(InitTask.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
        if(System.getProperty("initEvent") == null){
        try {
            System.out.println("开始数据库及Redis初始化");
            System.setProperty("initEvent", "initEvent");
            List<FjghInit>  fjghInitList = agentPushDataMapper.getFjghInit(fydm.substring(0, 4));
            for (int i = 0; i < fjghInitList.size(); i++) {
                // 删除redis里面原有错误数据
                FjghInit fjghInit = fjghInitList.get(i);
                redisTemplate.delete(fjghInit.getC_AJBS() + Constant.C_EAJ_JZ);
                redisTemplate.delete(fjghInit.getC_AJBS() + Constant.C_EAJ_MLXX);
                redisTemplate.delete(fjghInit.getC_AJBS() + Constant.C_EAJ_JZWJ_ALL_NEW);
                redisTemplate.delete(fjghInit.getC_AJBS() + Constant.C_EAJ_JZWJ_ALL);
                redisTemplate.delete(fjghInit.getC_AJBS() + Constant.C_EAJ_MLXX_GC);
                redisTemplate.delete(fjghInit.getC_AJBS() + Constant.C_EAJ_SSJCYX);
            }
            agentPushDataMapper.delTempAllToInit(fydm.substring(0, 4));
            System.out.println("数据库及Redis初始化完毕");
        } catch (Exception e) {
            logger.error("数据库及Redis初始化失败，请检查后重新写入");
            e.printStackTrace();
        }
       
        }
    }
}
