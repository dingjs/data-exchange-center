package data.exchange.center.service.unstructured.node.task.contrast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;
import data.exchange.center.service.unstructured.node.mapper.sgy.AgentPushDataMapper;
import data.exchange.center.service.unstructured.node.service.ContrstService;
import data.exchange.center.service.unstructured.node.service.impl.LogService;

@Transactional
public class ContrastTheadNew {
    private static Logger logger = LoggerFactory.getLogger(ContrastTheadNew.class);

    // 案件标识
    private int level;
    private String ah;
    private Date larq;
    private String ajbs;
    private String ajlx;
    private String fydm;
    private String ajzt;
    private String serverName;
    private LogService logService;
    private ContrstService contrstService;
    private AgentPushDataMapper agentPushDataMapper;
    private RedisTemplate<Object, Object> redisTemplate;

    // 传递案件标识
    public ContrastTheadNew(String ajbs, String ajlx, String fydm, String ajzt,
            RedisTemplate<Object, Object> redisTemplate, ContrstService contrstService,
            AgentPushDataMapper agentPushDataMapper, Integer level, String ah, Date larq, String serverName,
            LogService logService) {
        this.ah = ah;
        this.ajbs = ajbs;
        this.ajlx = ajlx;
        this.fydm = fydm;
        this.ajzt = ajzt;
        this.larq = larq;
        this.level = level;
        this.serverName = serverName;
        this.logService = logService;
        this.redisTemplate = redisTemplate;
        this.contrstService = contrstService;
        this.agentPushDataMapper = agentPushDataMapper;
    }

    public int run() {
        try {
                int size = agentPushDataMapper.getAjbs(ajbs);// 判断库中是否存在数据;
                if (size == 0) {
                ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();
                ConcurrentMap<String, Object> eajMap = new ConcurrentHashMap<>();
                String cbrxm = contrstService.getAllAjxxCBR(ajbs);
                map.put("ah", ah);
                map.put("ahdm", ajbs);
                map.put("ajlx", ajlx);
                map.put("fydm", fydm);
                map.put("larq", larq);
                map.put("level", level);
                map.put("ajzt", Constant.getAjzt(ajzt));
                map.put("cbrxm", cbrxm == null ? "" : cbrxm);
                System.out.println("案件标识为:" + ajbs + "的案件开始处理");
                // 删除临时锁定表
                agentPushDataMapper.delTempAllAndInit(map);
                // 写入临时缓存表
                agentPushDataMapper.pushFjghInit(map);
                // 获取通达海数据
                eajMap.put("ajbs", ajbs);
                eajMap.put("bh", Integer.valueOf(ajbs.substring(14, 15)));
                List<TempEajJz> getTempEaj = null;
                try {
                    getTempEaj = contrstService.getEajJZ(eajMap);
                    System.setProperty(Constant.C_YWDSTATE, Constant.C_NORMAL_CONNECT);
                } catch (Exception e) {
                    System.setProperty(Constant.C_YWDSTATE, Constant.C_ERROR_CONNECT);
                    logService.logger(serverName, 15, String.valueOf(logService.getId()), ajbs, "ajbs", fydm, "003","通达海数据库出现错误"+e.getMessage());
                }
                
                List<TempEajSsjcyx> getTempEajSsjcyx = contrstService.getSsjcyx(ajbs);
                List<TempEajJzwjAllNew> getTempEajJzwjAllNew = contrstService.getEajJzwjAllNew(ajbs);
                // 获取需要更新的数据
                List<TempEajJzwjAllNew> listTempEajJzwjAllNew = HandleTdh2Redis.jzwjAllnewDate(getTempEajJzwjAllNew,
                        ajbs, redisTemplate);
                List<TempEajJz> listTempEajJz = HandleTdh2Redis.jzDate(getTempEaj, ajbs, redisTemplate);
                // eajJz和eajJzwj关联取数据，位置不可调换
                List<TempEajJzwjAllNew> pushTempEajJzwjAllNew = HandleTdh2Redis.jzToJzwjAllnew(getTempEajJzwjAllNew,
                        listTempEajJzwjAllNew, listTempEajJz, fydm);
                List<TempEajJz> pushTempEajJz = HandleTdh2Redis.JzwjAllnewToJz(getTempEaj, listTempEajJz,
                        listTempEajJzwjAllNew, fydm);
                // 获取诉讼卷宗影像数据
                List<TempEajSsjcyx> pushTempEajSsjcyx = HandleTdh2Redis.ssjcyxDate(getTempEajSsjcyx, ajbs, fydm,
                        redisTemplate);
                // 把每个缓存表是否有更新写入主表
                map.put(Constant.C_TEMP_EAJ_JZWJ_ALL_NEW, String.valueOf(pushTempEajJzwjAllNew.size() > 0));
                map.put(Constant.C_TEMP_EAJ_JZ, String.valueOf(pushTempEajJz.size() > 0));
                map.put(Constant.C_TEMP_EAJ_SSJCYX, String.valueOf(pushTempEajSsjcyx.size() > 0));
                map.put(Constant.C_TEMP_EAJ_MLXX_GC,
                        String.valueOf((pushTempEajJzwjAllNew.size() > 0 || pushTempEajJz.size() > 0)));
                map.put(Constant.C_TEMP_EAJ_MLXX, String.valueOf(pushTempEajSsjcyx.size() > 0));
                // 写入缓存数据
                for (int i = 0; i < pushTempEajJz.size(); i++) {
                    pushTempEajJz.get(i).setAJLX(ajlx);
                    agentPushDataMapper.pushEajJzToSGY(pushTempEajJz.get(i));
                }
                if (pushTempEajJzwjAllNew.size() > 0) {
                    List<TempEajJzwjAllNew> pushEajJzwjAllNew =  Collections.synchronizedList(new ArrayList<>());
                    // 预防出现大量数据
                    for (int i = 0; i < pushTempEajJzwjAllNew.size(); i++) {
                        pushTempEajJzwjAllNew.get(i).setAJLX(ajlx);
                        pushEajJzwjAllNew.add(pushTempEajJzwjAllNew.get(i));
                        if (i % 100 == 0 && pushTempEajJzwjAllNew.size() >= 100) {
                            agentPushDataMapper.pushEajJzwjAllNewToSGY(pushEajJzwjAllNew);
                            pushEajJzwjAllNew =  Collections.synchronizedList(new ArrayList<>());
                        }
                    }
                    if (pushTempEajJzwjAllNew.size() % 100 != 0 && pushEajJzwjAllNew.size() > 0) {
                        agentPushDataMapper.pushEajJzwjAllNewToSGY(pushEajJzwjAllNew);
                    }
                }

                if (pushTempEajSsjcyx.size() > 0) {
                    List<TempEajMlxx> getTempEajMlxx = contrstService.getEajMlxx(ajbs);
                    List<TempEajSsjcyx> pushEajSsjcyx =  Collections.synchronizedList(new ArrayList<>());
                    // 预防出现大量数据
                    for (int i = 0; i < pushTempEajSsjcyx.size(); i++) {
                        pushTempEajSsjcyx.get(i).setAJLX(ajlx);
                        pushEajSsjcyx.add(pushTempEajSsjcyx.get(i));
                        if (i % 100 == 0 && pushTempEajSsjcyx.size() >= 100) {
                            agentPushDataMapper.pushEajSsjcyxToSGY(pushEajSsjcyx);
                            pushEajSsjcyx =  Collections.synchronizedList(new ArrayList<>());
                        }
                    }
                    if (pushTempEajSsjcyx.size() % 100 != 0 && pushEajSsjcyx.size() > 0) {
                        agentPushDataMapper.pushEajSsjcyxToSGY(pushEajSsjcyx);
                    }
                    if (getTempEajMlxx.size() > 0) {
                        agentPushDataMapper.pushEajMlxxToSGY(getTempEajMlxx);
                    }
                }
                if (pushTempEajJzwjAllNew.size() > 0 || pushTempEajJz.size() > 0) {
                    List<TempEajMlxxGc> getTempEajMlxxGc = contrstService.getEajMlxxGc(ajbs);
                    if (getTempEajMlxxGc.size() > 0) {
                        agentPushDataMapper.pushEajMlxxGcToSGY(getTempEajMlxxGc);
                    }
                }
                // 写入主表
                if (pushTempEajSsjcyx.size() > 0 || pushTempEajJz.size() > 0 || pushTempEajJzwjAllNew.size() > 0) {
                    agentPushDataMapper.pushEajAll(map);
                }
                // 写入redis
                if (pushTempEajSsjcyx.size() > 0) {
                    HandleTdh2Redis.ssjcyxToRedis(getTempEajSsjcyx, redisTemplate, ajbs);
                }
                if (pushTempEajJz.size() > 0) {
                    HandleTdh2Redis.jzToRedis(getTempEaj, redisTemplate, ajbs);
                }
                if (pushTempEajJzwjAllNew.size() > 0) {
                    HandleTdh2Redis.jzwjAllNewToRedis(getTempEajJzwjAllNew, redisTemplate, ajbs);
                }
                // 删除临时锁定表
                agentPushDataMapper.delFjghInit(map);
                System.out.println("案件标识为:" + ajbs + "的案件处理完成");
                // 判断是否有更新
                if (pushTempEajSsjcyx.size() > 0 || pushTempEajJz.size() > 0 || pushTempEajJzwjAllNew.size() > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (Exception e) {
            try {
                logger.error("案件标识为:" + ajbs + "处理数据出现错误:" + e.getMessage());
                String errMsg = e.getMessage() == null ? "null" : e.getMessage();
                if(errMsg.length() > 450){
                    errMsg = errMsg.substring(0, 450);
                }
                logService.logger(serverName, 15, String.valueOf(logService.getId()), ajbs, "ajbs", fydm, "003",errMsg);
                e.printStackTrace();
                return -1;
            } catch (Exception e1) {
                logger.error("写入日志错误" + e1.getMessage());
                e.printStackTrace();
            }
        }
        return 0;

    }
}
