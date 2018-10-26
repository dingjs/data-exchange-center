package data.exchange.center.service.businessdata.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.common.log.LoggerService;
import data.exchange.center.common.rabbitmq.MessageLevel;
import data.exchange.center.common.rabbitmq.RabbitmqConf;
import data.exchange.center.service.businessdata.domain.DCMonXmlOutDel;
import data.exchange.center.service.businessdata.domain.DCMonXmlOutZzjg;
import data.exchange.center.service.businessdata.domain.DcMonXmlOutAll;
import data.exchange.center.service.businessdata.mapper.sgy.BusinessSgyDataMapper;
import data.exchange.center.service.businessdata.mapper.tdh.BusinessDataMapper;
import data.exchange.center.service.businessdata.service.XmlGetDataService;
import oracle.sql.BLOB;

/**
 * @ClassName: XmlGetDataServiceImpl
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: BaiMaojun
 * @Date: 2018年2月27日 上午11:27:28
 */
@Service
public class XmlGetDataServiceImpl implements XmlGetDataService {
    @Autowired
    private BusinessDataMapper businessDataMapper;
    @Autowired
    private BusinessSgyDataMapper businessSgyDataMapper;
    @Value("${spring.application.name}")
    private String serverName;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private LoggerService logService;
    Connection connection = null;
    Channel channel = null;

    private static Logger logger = LoggerFactory.getLogger(XmlGetDataServiceImpl.class);

    public void getDcMonXmlOutXmlJg() {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            Map<String, Object> messageMap = new HashMap<String, Object>();
            params.put("server", serverName);
            List<DCMonXmlOutZzjg> dCMonXmlOutZzjgList = businessDataMapper.getDcMonXmlOutJg(params);
            for (DCMonXmlOutZzjg dCMonXmlOutZzjg : dCMonXmlOutZzjgList) {
                try {
                    params.put("dm", dCMonXmlOutZzjg.getDm());
                    if (businessSgyDataMapper.getzzjgPcaj(params) == 0) {
                    } else {
                        continue;// 案件出现在排除表或缓存表
                    }
                    messageMap.put(Constant.AJBS, dCMonXmlOutZzjg.getDm());
                    messageMap.put(Constant.FYDM, dCMonXmlOutZzjg.getFydm());
                    messageMap.put(Constant.AJLX, dCMonXmlOutZzjg.getLx());
                    messageMap.put(Constant.AJSOURCE, "002");
                    messageMap.put(Constant.SJLX, Constant.C_BUZ_SJLX_SPZT);// 代表审判主题
                    businessSgyDataMapper.pushZzjg2xmlToSGY(dCMonXmlOutZzjg);
                    businessDataMapper.setJgFlag(params);
                    setRabbitMq(messageMap);

                } catch (Exception e) {
                    logService.logger(serverName, 16, String.valueOf(logService.getId()), dCMonXmlOutZzjg.getDm(),
                            "ZZJGDM", dCMonXmlOutZzjg.getFydm(), "002", e.getMessage());
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            logger.error("机构获取出现错误:" + e.getMessage());
            e.printStackTrace();
        }

    }

    public void getDcMonXmlOutXmldel(String serverTime) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            Map<String, Object> messageMap = new HashMap<String, Object>();
            params.put("serverTime", serverTime);
            params.put("server", serverName);
            List<DCMonXmlOutDel> dCMonXmlOutDelList = businessDataMapper.getDcMonXmlOutDel(params);
            for (DCMonXmlOutDel dCMonXmlOutDel : dCMonXmlOutDelList) {
                try {
                    params.put("ajbs", dCMonXmlOutDel.getAjbs());
                    if (businessSgyDataMapper.getDelPcaj(params) == 0) {

                    } else {
                        continue;// 案件出现在排除表或缓存表
                    }

                    messageMap.put(Constant.AJBS, dCMonXmlOutDel.getAjbs());
                    messageMap.put(Constant.FYDM, dCMonXmlOutDel.getFydm());
                    messageMap.put(Constant.AJLX, dCMonXmlOutDel.getAjlx());
                    messageMap.put(Constant.AJSOURCE, "002");
                    messageMap.put(Constant.SJLX, Constant.C_BUZ_SJLX_DELETE);// 代表案件删除
                    businessSgyDataMapper.pushAjsc2xmlToSGY(dCMonXmlOutDel);
                    businessDataMapper.setDelFlag(params);
                    setRabbitMq(messageMap);
                } catch (Exception e) {
                    logService.logger(serverName, 16, String.valueOf(logService.getId()), dCMonXmlOutDel.getAjbs(),
                            "ajbs", dCMonXmlOutDel.getFydm(), "002", e.getMessage());
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            logger.error("获取删除出现错误:" + e.getMessage());
            e.printStackTrace();
        }

    }

    public void getDcMonXmlOutXmlAJ(String serverTime) {
        try {
            int count = businessDataMapper.getPageDcMonXmlOut();
            Map<String, Object> messageMap = new HashMap<String, Object>();
            int pageNum = (count + Constant.PAGE_NUM - 1) / Constant.PAGE_NUM;
            for (int i = 1; i <= pageNum; i++) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("min", (i - 1) * Constant.PAGE_NUM);
                params.put("max", i * Constant.PAGE_NUM);
                params.put("serverTime", serverTime);
                List<DcMonXmlOutAll> dcMonXmlOutAllList = businessDataMapper.getDcMonXmlOut(params);
                for (DcMonXmlOutAll dcMonXmlOutAll : dcMonXmlOutAllList) {
                    try {
                        if (dcMonXmlOutAll.getAjbs().length() != 15) {
                            if (!data.exchange.center.service.businessdata.util.Constant
                                    .ajsbTo15(dcMonXmlOutAll.getAjbs()).equals("19")
                                    && !data.exchange.center.service.businessdata.util.Constant
                                            .ajsbTo15(dcMonXmlOutAll.getAjbs()).equals("18")) {
                            } else {
                                continue;// 案件标识出现错误
                            }
                            dcMonXmlOutAll.setAjbs(data.exchange.center.service.businessdata.util.Constant
                                    .ajsbTo15(dcMonXmlOutAll.getAjbs()));
                        }
                        params.put("ajbs", dcMonXmlOutAll.getAjbs());
                        params.put("ah", dcMonXmlOutAll.getAh());
                        params.put("server", serverName);
                        if (businessSgyDataMapper.getPcaj(params) == 0) {

                        } else {
                            continue;// 案件出现在排除表或缓存表
                        }
                        if (businessSgyDataMapper.getAhcf(params) == 0) {

                        } else {
                            logService.logger(serverName, 16, String.valueOf(logService.getId()),
                                    dcMonXmlOutAll.getAjbs(), "ajbs", dcMonXmlOutAll.getFydm(), "002", "案号重复");
                            continue;// 出现案号重复的情况
                        }

                        dcMonXmlOutAll.setXmlnr(data.exchange.center.service.businessdata.util.Constant
                                .blobToBytes((BLOB) dcMonXmlOutAll.getXmlnr()));
                        messageMap.put(Constant.AJBS, dcMonXmlOutAll.getAjbs());
                        messageMap.put(Constant.FYDM, dcMonXmlOutAll.getFydm());
                        messageMap.put(Constant.AJLX, dcMonXmlOutAll.getAjlx());
                        messageMap.put(Constant.AJSOURCE, "002");
                        messageMap.put(Constant.SJLX, Constant.C_BUZ_SJLX_INSERT);// 代表案件更新或新增
                        businessSgyDataMapper.pushExternal2xmlToSGY(dcMonXmlOutAll);
                        businessDataMapper.setOutFlag(params);
                        setRabbitMq(messageMap);

                    } catch (Exception e) {
                        logService.logger(serverName, 16, String.valueOf(logService.getId()), dcMonXmlOutAll.getAjbs(),
                                "AJBS", dcMonXmlOutAll.getFydm(), "002", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取数据出现错误:" + e.getMessage());
            e.printStackTrace();
        }

    }
    /**
     * @Title: getPushXmlAJ
     * @Description: 减刑假释案件导入
     * @param @param serverTime    参数
     * @return void    返回类型
     * @throws
     */
    public void getPushXmlAJ() {
        try {
            Map<String, Object> messageMap = new HashMap<String, Object>();
            Map<String, Object> params = new HashMap<String, Object>();
            List<DcMonXmlOutAll> dcMonXmlOutAllList = businessSgyDataMapper.getTemp(params);
            for (DcMonXmlOutAll dcMonXmlOutAll : dcMonXmlOutAllList) {
                try {
                    params.put("ajbs", dcMonXmlOutAll.getAjbs());
                    params.put("ah", dcMonXmlOutAll.getAh());
                    params.put("server", serverName);
                    if (dcMonXmlOutAll.getAh() != null) {
                        if (businessSgyDataMapper.getPcaj(params) == 0) {

                        } else {
                            continue;// 案件出现在排除表或缓存表
                        }
                        if (businessSgyDataMapper.getAhcf(params) == 0) {

                        } else {
                            logService.logger(serverName, 16, String.valueOf(logService.getId()),
                                    dcMonXmlOutAll.getAjbs(), "ajbs", dcMonXmlOutAll.getFydm(), "002", "案号重复");
                            continue;// 出现案号重复的情况
                        }
                    }
                    if (dcMonXmlOutAll.getXmlnr() != null) {
                        dcMonXmlOutAll.setXmlnr(data.exchange.center.service.businessdata.util.Constant
                                .blobToBytes((BLOB) dcMonXmlOutAll.getXmlnr()));
                        messageMap.put(Constant.AJBS, dcMonXmlOutAll.getAjbs());
                        messageMap.put(Constant.FYDM, dcMonXmlOutAll.getFydm());
                        messageMap.put(Constant.AJLX, dcMonXmlOutAll.getAjlx());
                        messageMap.put(Constant.AJSOURCE, "004");
                        messageMap.put(Constant.SJLX, Constant.C_BUZ_SJLX_INSERT);// 代表案件更新或新增
                        businessSgyDataMapper.pushExternal2xmlToSGY(dcMonXmlOutAll);
                    } else {
                        messageMap.put(Constant.AJBS, dcMonXmlOutAll.getAjbs());
                        messageMap.put(Constant.FYDM, dcMonXmlOutAll.getFydm());
                        messageMap.put(Constant.AJLX, dcMonXmlOutAll.getAjlx());
                        messageMap.put(Constant.AJSOURCE, "004");
                        messageMap.put(Constant.SJLX, Constant.C_BUZ_SJLX_DELETE);// 代表案件删除
                    }
                    businessSgyDataMapper.setTempFlag(params);
                    setRabbitMq(messageMap);
                } catch (Exception e) {
                    logService.logger(serverName, 16, String.valueOf(logService.getId()), dcMonXmlOutAll.getAjbs(),
                            "AJBS", dcMonXmlOutAll.getFydm(), "004", e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            logger.error("获取数据出现错误:" + e.getMessage());
            e.printStackTrace();
        }

    }

    public void setRabbitMq(Map<String, Object> messageMap) {
        rabbitTemplate.convertAndSend(RabbitmqConf.BUSINESS_DATA_EXCHANGE, RabbitmqConf.BUSINESS_DATA_ROUTEKEY,
                messageMap, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setPriority(MessageLevel.LEVEL_0);
                        return message;
                    }
                }, new CorrelationData(UUID.randomUUID().toString()));
    }

    @Override
    public Object getDcMonXmlOutXml() {
        try {
            String serverTime = data.exchange.center.service.businessdata.util.Constant.getServerTime();
            connection = rabbitTemplate.getConnectionFactory().createConnection();
            channel = connection.createChannel(false);
            getDcMonXmlOutXmlAJ(serverTime);
            getDcMonXmlOutXmldel(serverTime);
            getDcMonXmlOutXmlJg();
            getPushXmlAJ();
        } catch (Exception e) {
            logger.error("写入日志错误:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(channel != null){
                    channel.close();
                }
              if(connection!=null){
                  connection.close();
              }
               
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}
