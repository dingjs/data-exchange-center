package data.exchange.center.service.businessdata.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import data.exchange.center.common.log.LoggerService;
import data.exchange.center.common.time.VeDate;
import data.exchange.center.service.businessdata.domain.DCMonXmlOutDel;
import data.exchange.center.service.businessdata.domain.DcMonXmlOutAll;
import data.exchange.center.service.businessdata.mapper.sgy.BusinessSgyDataMapper;
import data.exchange.center.service.businessdata.service.HyZipDowloadServer;
import data.exchange.center.service.businessdata.util.FTPListAllFiles;
import data.exchange.center.service.businessdata.util.FtpDowload;

@Service
public class HyZipDowloadServerImpl implements HyZipDowloadServer {
    @Value("${ftp.user}")
    private String user;
    @Value("${ftp.ip}")
    private String ip;
    @Value("${ftp.port}")
    private String port;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.loadpath}")
    private String loadpath;
    @Value("${ftp.path}")
    private String ftpPath;
    @Autowired
    private LoggerService logService;
    @Autowired
    private BusinessSgyDataMapper businessSgyDataMapper;
    @Value("${spring.application.name}")
    private String serverName;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void zipAnalysis() throws Exception {
        zipZipDowAls();

    }

    public void zipZipDowAls() throws IOException {
        for (int i = 0; i < 9; i++) {
        
        if (StringUtils.isBlank(ftpPath)) {
            ftpPath = "/" + VeDate.getNextDay(VeDate.dateToStr(VeDate.getNowDateShort()), '-'+String.valueOf(9-i)) + "/";
        }
        Iterator<Map<String, String>> it = FTPListAllFiles.getZipPath(ip, Integer.valueOf(port), user, password,
                ftpPath);
        while (it.hasNext()) {
            Map<String, String> map = it.next();
            String remotedPath = map.get("path");
            String remoteFileName = map.get("fileName");
            boolean success = FtpDowload.zipDowload(ip, Integer.valueOf(port), user, password, loadpath, remotedPath,
                    remoteFileName);
            if (success) {
                if (remoteFileName.substring(0, 2).equals("AJ")) {
                    List<DcMonXmlOutAll> dcMonXmlOutAllList = xmlInAnalysis(loadpath, remoteFileName);
                    pushExternal2Xml(dcMonXmlOutAllList);
                }
                if (remoteFileName.substring(0, 2).equals("DL")) {
                    List<DCMonXmlOutDel> dCMonXmlOutDelList = xmlDLAnalysis(loadpath, remoteFileName);
                    PushAjsc(dCMonXmlOutDelList);
                }
                File file = new File(loadpath + remoteFileName);
                if (file.exists()) {
                    file.delete();
                }
            }
        }}
        System.out.println("处理完毕");
    }

    public void pushExternal2Xml(List<DcMonXmlOutAll> dcMonXmlOutAllList) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("server", serverName);
        for (DcMonXmlOutAll dcMonXmlOutAll : dcMonXmlOutAllList) {
            try {
                params.put("ajbs", dcMonXmlOutAll.getAjbs());
                params.put("ah", dcMonXmlOutAll.getAh());
                params.put("server", serverName);
                businessSgyDataMapper.dlTempExternal2xmlToSGY(dcMonXmlOutAll.getAjbs());
                businessSgyDataMapper.pushTempExternal2xmlToSGY(dcMonXmlOutAll);
            } catch (Exception e) {
                try {
                    logService.logger(serverName, 16, String.valueOf(logService.getId()), dcMonXmlOutAll.getAjbs(),
                            "AJBS",  dcMonXmlOutAll.getFydm(),"004", "写入缓存表错误"+e.getMessage());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    public void PushAjsc(List<DCMonXmlOutDel> dCMonXmlOutDelList) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            Map<String, Object> messageMap = new HashMap<String, Object>();
            params.put("server", serverName);
            for (DCMonXmlOutDel dCMonXmlOutDel : dCMonXmlOutDelList) {
                try {
                    DcMonXmlOutAll dcMonXmlOutAll = new DcMonXmlOutAll();
                    dcMonXmlOutAll.setAjsource("004");
                    dcMonXmlOutAll.setInputsrc("JAVA");
                    dcMonXmlOutAll.setDestschema("SGY");
                    dcMonXmlOutAll.setXmltype("1");
                    dcMonXmlOutAll.setAjbs(dCMonXmlOutDel.getAjbs());
                    dcMonXmlOutAll.setFydm(dCMonXmlOutDel.getFydm());
                    dcMonXmlOutAll.setAjlx(dCMonXmlOutDel.getAjlx());
                    businessSgyDataMapper.dlTempExternal2xmlToSGY(dcMonXmlOutAll.getAjbs());
                    businessSgyDataMapper.pushTempExternal2xmlToSGY(dcMonXmlOutAll);
                } catch (Exception e) {
//                    logService.logger(serverName, 16, String.valueOf(logService.getId()), dCMonXmlOutDel.getAjbs(),
//                            "ajbs", "", dCMonXmlOutDel.getFydm(), e.getMessage());
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public void pushExternal2Xml(List<DcMonXmlOutAll> dcMonXmlOutAllList) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("server", serverName);
//        for (DcMonXmlOutAll dcMonXmlOutAll : dcMonXmlOutAllList) {
//            Map<String, Object> messageMap = new HashMap<String, Object>();
//            try {
//                params.put("ajbs", dcMonXmlOutAll.getAjbs());
//                params.put("ah", dcMonXmlOutAll.getAh());
//                params.put("server", serverName);
//                if (businessSgyDataMapper.getPcaj(params) == 0) {
//
//                } else {
//                    continue;// 案件出现在排除表或缓存表
//                }
//                if (businessSgyDataMapper.getAhcf(params) == 0) {
//
//                } else {
//                    logService.logger(serverName, 16, String.valueOf(logService.getId()), dcMonXmlOutAll.getAjbs(),
//                            "ajbs", dcMonXmlOutAll.getFydm(), "004", "案号重复");
//                    continue;// 出现案号重复的情况
//                }
//
//                dcMonXmlOutAll.setXmlnr(dcMonXmlOutAll.getXmlnr());
//                messageMap.put(Constant.AJBS, dcMonXmlOutAll.getAjbs());
//                messageMap.put(Constant.FYDM, dcMonXmlOutAll.getFydm());
//                messageMap.put(Constant.AJLX, dcMonXmlOutAll.getAjlx());
//                messageMap.put(Constant.AJSOURCE, "004");
//                messageMap.put(Constant.SJLX, Constant.C_BUZ_SJLX_INSERT);// 代表案件更新或新增
//                businessSgyDataMapper.pushExternal2xmlToSGY(dcMonXmlOutAll);
//                setRabbitMq(messageMap);
//            } catch (Exception e) {
//                try {
//                    logService.logger(serverName, 16, String.valueOf(logService.getId()), dcMonXmlOutAll.getAjbs(),
//                            "AJBS", "", dcMonXmlOutAll.getFydm(), e.getMessage());
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//                e.printStackTrace();
//            }
//        }
//    }

//    public void PushAjsc(List<DCMonXmlOutDel> dCMonXmlOutDelList) {
//        try {
//            Map<String, Object> params = new HashMap<String, Object>();
//            Map<String, Object> messageMap = new HashMap<String, Object>();
//            params.put("server", serverName);
//            for (DCMonXmlOutDel dCMonXmlOutDel : dCMonXmlOutDelList) {
//                try {
//
//                    params.put("ajbs", dCMonXmlOutDel.getAjbs());
//                    if (businessSgyDataMapper.getDelPcaj(params) == 0) {
//
//                    } else {
//                        continue;// 案件出现在排除表或缓存表
//                    }
//                    messageMap.put(Constant.AJBS, dCMonXmlOutDel.getAjbs());
//                    messageMap.put(Constant.FYDM, dCMonXmlOutDel.getFydm());
//                    messageMap.put(Constant.AJLX, dCMonXmlOutDel.getAjlx());
//                    messageMap.put(Constant.AJSOURCE, "004");
//                    messageMap.put(Constant.SJLX, Constant.C_BUZ_SJLX_DELETE);// 代表案件删除
//                    setRabbitMq(messageMap);
//                } catch (Exception e) {
//                    logService.logger(serverName, 16, String.valueOf(logService.getId()), dCMonXmlOutDel.getAjbs(),
//                            "ajbs", "", dCMonXmlOutDel.getFydm(), e.getMessage());
//                    e.printStackTrace();
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    @SuppressWarnings("resource")
    public List<DcMonXmlOutAll> xmlInAnalysis(String loadpath, String remoteFileName) {
        File file = new File(loadpath + remoteFileName);
        List<DcMonXmlOutAll> dcMonXmlOutAllList = new ArrayList<DcMonXmlOutAll>();
        String fydm = businessSgyDataMapper.getFydm(remoteFileName.substring(3, 7));
        try {
            ZipFile zipFile = new ZipFile(file);
            for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements();) {
                ZipEntry zipEntry = entries.nextElement();
                // 从zipFile中获取zipEntry的输入流，即xml文件的输入流
                InputStream inputStream = zipFile.getInputStream(zipEntry);
                // 以下使用dom4j解析xml文件
                SAXReader reader = new SAXReader();
                try {
                    Document document = reader.read(inputStream);
                    Element node = document.getRootElement();
                    DcMonXmlOutAll dcMonXmlOutAll = new DcMonXmlOutAll();
                    dcMonXmlOutAll.setAjbs(node.element("案件标识").getText());
                    Element sljd = node.element("审理阶段");
                    dcMonXmlOutAll.setAjzt(sljd.element("案件进展阶段").getText());
                    Element salaxx = node.element("收案和立案信息");
                    dcMonXmlOutAll.setAh(salaxx.element("案号").getText());
                    dcMonXmlOutAll.setLarq(VeDate.strToDateLongLarq(salaxx.element("立案日期").getText()));
                    Element jaxx = node.element("结案情况");
                    if (jaxx != null && jaxx.element("结案日期")!= null) {
                        dcMonXmlOutAll.setJarq(VeDate.strToDateLongLarq(jaxx.element("结案日期").getText()));
                    }
                    dcMonXmlOutAll.setAjlx("15");
                    dcMonXmlOutAll.setXmlnr(document.asXML().getBytes("UTF-8"));
                    dcMonXmlOutAll.setCreatetime(VeDate.getNowDate());
                    dcMonXmlOutAll.setLastupdate(VeDate.getNowDate());
                    dcMonXmlOutAll.setType("insert");
                    dcMonXmlOutAll.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
                    dcMonXmlOutAll.setInputsrc("JAVA");
                    dcMonXmlOutAll.setDestschema("SGY");
                    dcMonXmlOutAll.setAjsource("004");
                    dcMonXmlOutAll.setXmltype("1");
                    dcMonXmlOutAll.setFydm(fydm);
                    dcMonXmlOutAllList.add(dcMonXmlOutAll);
                } catch (Exception e) {
                    logService.logger(serverName, 16, String.valueOf(logService.getId()), zipEntry.getName(),
                            "JXJSWJMC", "", businessSgyDataMapper.getFydm(zipEntry.getName().substring(3, 7)),
                            "包名:" + file.getName() + "错误:" + e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
            return dcMonXmlOutAllList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("resource")
    public List<DCMonXmlOutDel> xmlDLAnalysis(String loadpath, String remoteFileName) {
        File file = new File(loadpath + remoteFileName);
        List<DCMonXmlOutDel> dCMonXmlOutDelList = new ArrayList<DCMonXmlOutDel>();
        try {
            ZipFile zipFile = new ZipFile(file);
            for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements();) {
                ZipEntry zipEntry = entries.nextElement();
                // 从zipFile中获取zipEntry的输入流，即xml文件的输入流
                InputStream inputStream = zipFile.getInputStream(zipEntry);
                // 以下使用dom4j解析xml文件
                SAXReader reader = new SAXReader();
                try {
                    DCMonXmlOutDel dCMonXmlOutDel = new DCMonXmlOutDel();
                    Document document = reader.read(inputStream);
                    Element node = document.getRootElement();
                    if (node != null) {
                        List<Element> delAj = node.elements();
                        for (Element aj : delAj) {
                            dCMonXmlOutDel.setAjbs(aj.element("案件标识").getText());
                            dCMonXmlOutDel.setFydm(businessSgyDataMapper.getFydm(aj.element("经办法院").getText()));
                            dCMonXmlOutDel.setAjlx(aj.element("案件类型").getText());
                            dCMonXmlOutDel.setAjlx(aj.element("案件类型").getText());
                            dCMonXmlOutDelList.add(dCMonXmlOutDel);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(zipEntry.getName()+remoteFileName);
//                    logService.logger(serverName, 16, String.valueOf(logService.getId()), zipEntry.getName(),
//                            "JXJSWJMC", businessSgyDataMapper.getFydm(zipEntry.getName().substring(3, 7)),"004",
//                            "包名:" + file.getName() + "错误:" + e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
            return dCMonXmlOutDelList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void setRabbitMq(Map<String, Object> messageMap) {
//        rabbitTemplate.convertAndSend(RabbitmqConf.BUSINESS_DATA_EXCHANGE, RabbitmqConf.BUSINESS_DATA_ROUTEKEY,
//                messageMap, new MessagePostProcessor() {
//                    @Override
//                    public Message postProcessMessage(Message message) throws AmqpException {
//                        message.getMessageProperties().setPriority(MessageLevel.LEVEL_0);
//                        return message;
//                    }
//                }, new CorrelationData(UUID.randomUUID().toString()));
//    }

}
