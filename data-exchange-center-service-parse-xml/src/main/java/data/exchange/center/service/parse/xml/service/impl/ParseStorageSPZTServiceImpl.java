package data.exchange.center.service.parse.xml.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.parse.xml.domain.ScXmlContent;
import data.exchange.center.service.parse.xml.domain.SpztXmlContent;
import data.exchange.center.service.parse.xml.mapper.ParseStorageSPZTMapper;
import data.exchange.center.service.parse.xml.service.ParseStorageSPZTService;
import data.exchange.center.service.parse.xml.util.SpringContextUtil;
import data.exchange.center.service.parse.xml.util.Util;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年3月18日 下午7:55:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 */
@Service
public class ParseStorageSPZTServiceImpl implements ParseStorageSPZTService {

    private static Logger logger = LoggerFactory.getLogger(ParseStorageSPZTServiceImpl.class);
    @Autowired
    private ParseStorageSPZTMapper parseStorageSPZTMapper;

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> parseStorageSPZT(String ajbs) throws Exception {
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext
                .getBean("transactionManager");
        TransactionStatus transactionStatus = (TransactionStatus) transactionManager.getTransaction(definition);

        Long start = System.currentTimeMillis();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if ( StringUtils.isEmpty(ajbs) ) {
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            resultMap.put(CodeUtil.RETURN_MSG, "ajbs can not be null!");
            return resultMap;
        }
        try {
            /**
             * make params for select xml from database
             */
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("param", ajbs);
            /**
             * get xml from database
             */
            SpztXmlContent xmlContent = parseStorageSPZTMapper.getXML(map);
            if ( StringUtils.isEmpty(xmlContent) ) {
                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                resultMap.put(CodeUtil.RETURN_MSG, "the buf database dont have the " + ajbs + "'s xml content !");
                return resultMap;
            }
            /**
             * 解析
             */
            Map<String, Object> xmlMap = null;
            if ( xmlContent.getINPUTSRC().equalsIgnoreCase("DB") ) {
                xmlMap = parseXml(Util.BLOB2GBKString(xmlContent.getXMLNR()));
            }
            else {
                xmlMap = parseXml(Util.BLOB2UTF8String(xmlContent.getXMLNR()));
            }
            if ( !StringUtils.isEmpty(xmlMap.get("msg")) ) {
                logger.error("parse xml file fail!");
                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                resultMap.put(CodeUtil.RETURN_MSG, xmlMap.get("msg"));
                return resultMap;
            }
            else {
                Set<String> set = xmlMap.keySet();
                if ( set.size() > 1 ) {
                    logger.error("one xml file has more than one type!!!");
                }
                else {
                    /**
                     * xml头部名称
                     */
                    String xmlName = set.toString().replace("[", "").replace("]", "");
                    /**
                     * 如果头部名称为“案件删除信息”
                     */
                    List<Map<String, Object>> tableList = (List<Map<String, Object>>) xmlMap
                            .get(xmlName);
                    if ( xmlName.contains("案件删除信息") ) {

                        /**
                         * 删除内容超过一条，强行报错
                         */
                        if ( tableList.size() > 1 ) {
                            logger.error("案件删除信息xml里面有多张表，默认只处理一张表!");
                            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                            resultMap.put(CodeUtil.RETURN_MSG, "案件删除信息xml里面有多张表，默认只处理一张表");
                            return resultMap;
                        }
                        else {
                            Map<String, Object> table = tableList.get(0);
                            for (Entry<String, Object> entry : table.entrySet()) {
                                List<Object> listObj = (List<Object>) entry.getValue();
                                List<Object> listTbale = (List<Object>) listObj.get(0);
                                Map<String, Object> tableMa = (Map<String, Object>) listTbale.get(0);
                                /**
                                 * 遍历字段
                                 */
                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put("AJBS", "");
                                params.put("ID", "");
                                params.put("REG_TIME", "");
                                params.put("UPDATE_TIME", "");
                                params.put("DEPT_CODE", "");
                                params.put("APP_CODE", "");
                                params.put("AJLX", "");
                                params.put("JBFY", "");
                                params.put("SPR", "");
                                params.put("SPRBS", "");
                                params.put("SPRQ", "");
                                params.put("SSRQ", "");
                                params.put("SSYY", "");
                                for (Entry<String, Object> tableEntry : tableMa.entrySet()) {
                                    params.put(tableEntry.getKey().toUpperCase(), tableEntry.getValue());
                                }
                                /**
                                 * 先执行清空表操作
                                 */
                                Map<String, Object> procParam = new HashMap<String, Object>();
                                procParam.put("ajbs", ajbs);
                                parseStorageSPZTMapper.deleteAjxxByAjbs(procParam);
                                Integer status = (Integer) procParam.get("v_cursor1");
                                String msg = (String) procParam.get("v_cursor2");
                                /**
                                 * 等于0正常
                                 * 正常的入AJSCXX_AJSCXX之前需要先删除掉记录，不能用update
                                 * 然后再次insert
                                 */
                                if ( status == 0 ) {
                                    parseStorageSPZTMapper.delete_AJSCXX_AJSCXX(ajbs);
                                    SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    java.util.Date dat = null;
                                    try {
                                        dat = forma.parse(forma.format(xmlContent.getLASTUPDATE()));
                                    }
                                    catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    params.put("ID", "");
                                    params.put("REG_TIME", dat);
                                    params.put("UPDATE_TIME", new Date(new java.util.Date().getTime()));
                                    params.put("APP_CODE", "");
                                    params.put("DEPT_CODE", xmlContent.getFYDM());
                                    int key = parseStorageSPZTMapper.insert_AJSCXX_AJSCXX(params);
                                    if ( key == 1 ) {
                                        transactionManager.commit(transactionStatus);
                                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                                        resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
                                    }
                                    else {
                                        transactionManager.rollback(transactionStatus);
                                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                        resultMap.put(CodeUtil.RETURN_MSG, "insert_AJSCXX_AJSCXX error, rollback!");
                                    }
                                }
                                else {
                                    logger.error("删除案件信息表出错!");
                                    resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                    resultMap.put(CodeUtil.RETURN_MSG, "删除案件信息表出错:" + msg);
                                    return resultMap;
                                }
                            }
                        }
                    }
                    else if ( xmlName.contains("法院编码") ) {
                        Map<String, Object> table = tableList.get(0);
                        for (Entry<String, Object> entry : table.entrySet()) {
                            List<Object> listObj = (List<Object>) entry.getValue();
                            List<Object> listTbale = (List<Object>) listObj.get(0);
                            Map<String, Object> tableMa = (Map<String, Object>) listTbale.get(0);
                            /**
                             * 遍历字段
                             */
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("ID", "");
                            params.put("REG_TIME", "");
                            params.put("UPDATE_TIME", "");
                            params.put("DEPT_CODE", "");
                            params.put("APP_CODE", "");
                            params.put("FYBZ", "");
                            params.put("DM", "");
                            params.put("JGLX", "");
                            params.put("FYMC", "");
                            params.put("YX", "");
                            params.put("DQM", "");
                            params.put("BZ", "");
                            params.put("FYJC", "");
                            for (Entry<String, Object> tableEntry : tableMa.entrySet()) {
                                params.put(tableEntry.getKey().toUpperCase(), tableEntry.getValue());
                            }

                            /**
                             * 先删除对应记录
                             * 再添加数据
                             */
                            parseStorageSPZTMapper.delete_SPZT_FYBM(ajbs);
                            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            java.util.Date dat = null;
                            try {
                                dat = forma.parse(forma.format(xmlContent.getLASTUPDATE()));
                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }
                            params.put("ID", UUID.randomUUID().toString());
                            params.put("REG_TIME", dat);
                            params.put("UPDATE_TIME", new Date(new java.util.Date().getTime()));
                            params.put("APP_CODE", "002");
                            params.put("DEPT_CODE", xmlContent.getFYDM());
                            int key = parseStorageSPZTMapper.insert_SPZT_FYBM(params);
                            if ( key == 1 ) {
                                transactionManager.commit(transactionStatus);
                                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                                resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
                            }
                            else {
                                transactionManager.rollback(transactionStatus);
                                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                resultMap.put(CodeUtil.RETURN_MSG, "insert_SPZT_FYBM error rollback!");
                            }
                        }
                    }
                    else if ( xmlName.contains("组织人员") ) {
                        Thread.currentThread();
                        Thread.sleep(50);
                        Map<String, Object> table = tableList.get(0);
                        for (Entry<String, Object> entry : table.entrySet()) {
                            List<Object> listObj = (List<Object>) entry.getValue();
                            List<Object> listTbale = (List<Object>) listObj.get(0);
                            Map<String, Object> tableMa = (Map<String, Object>) listTbale.get(0);
                            /**
                             * 遍历字段
                             */
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("ID", "");
                            params.put("REG_TIME", "");
                            params.put("UPDATE_TIME", "");
                            params.put("DEPT_CODE", "");
                            params.put("APP_CODE", "");
                            params.put("RYBZ", "");
                            params.put("XM", "");
                            params.put("DLBZ", "");
                            params.put("XB", "");
                            params.put("CSRQ", "");
                            params.put("MZ", "");
                            params.put("WHCD", "");
                            params.put("HYZK", "");
                            params.put("SFZHM", "");
                            params.put("ZZMM", "");
                            params.put("XZJB", "");
                            params.put("ZW", "");
                            params.put("ZSBZ", "");
                            params.put("DHHM", "");
                            params.put("YX", "");
                            params.put("FGDJ", "");
                            params.put("FJDJ", "");
                            params.put("SZJG", "");
                            params.put("BZ", "");
                            params.put("ZZRYBZ", "");
                            params.put("FYBZ", "");
                            params.put("DM", "");
                            params.put("MM", "");
                            for (Entry<String, Object> tableEntry : tableMa.entrySet()) {
                                params.put(tableEntry.getKey().toUpperCase(), tableEntry.getValue());
                            }

                            /**
                             * 先删除对应记录
                             * 再添加数据
                             */
                            parseStorageSPZTMapper.delete_SPZT_ZZRY(ajbs);
                            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            java.util.Date dat = null;
                            try {
                                dat = forma.parse(forma.format(xmlContent.getLASTUPDATE()));
                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }
                            params.put("ID", UUID.randomUUID().toString());
                            params.put("REG_TIME", dat);
                            params.put("UPDATE_TIME", new Date(new java.util.Date().getTime()));
                            params.put("APP_CODE", "002");
                            params.put("DEPT_CODE", xmlContent.getFYDM());
                            int key = parseStorageSPZTMapper.insert_SPZT_ZZRY(params);
                            if ( key == 1 ) {
                                transactionManager.commit(transactionStatus);
                                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                                resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
                            }
                            else {
                                transactionManager.rollback(transactionStatus);
                                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                resultMap.put(CodeUtil.RETURN_MSG, "insert_SPZT_ZZRY error rollback!");
                                logger.error("insert_SPZT_ZZRY error rollback!");
                            }
                        }
                    }
                    else if ( xmlName.contains("组织机构") ) {
                        Thread.currentThread();
                        Thread.sleep(50);
                        Map<String, Object> table = tableList.get(0);
                        for (Entry<String, Object> entry : table.entrySet()) {
                            List<Object> listObj = (List<Object>) entry.getValue();
                            List<Object> listTbale = (List<Object>) listObj.get(0);
                            Map<String, Object> tableMa = (Map<String, Object>) listTbale.get(0);
                            /**
                             * 遍历字段
                             */
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("ID", "");
                            params.put("REG_TIME", "");
                            params.put("UPDATE_TIME", "");
                            params.put("DEPT_CODE", "");
                            params.put("APP_CODE", "");
                            params.put("JGBZ", "");
                            params.put("JGMC", "");
                            params.put("JGLX", "");
                            params.put("YX", "");
                            params.put("JGJB", "");
                            params.put("SJJGBZ", "");
                            params.put("JGZN", "");
                            params.put("BZ", "");
                            params.put("ZZJGBZ", "");
                            params.put("FYBZ", "");
                            params.put("DM", "");
                            for (Entry<String, Object> tableEntry : tableMa.entrySet()) {
                                params.put(tableEntry.getKey().toUpperCase(), tableEntry.getValue());
                            }

                            /**
                             * 先删除对应记录
                             * 再添加数据
                             */
                            parseStorageSPZTMapper.delete_SPZT_ZZJG(ajbs);
                            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            java.util.Date dat = null;
                            try {
                                dat = forma.parse(forma.format(xmlContent.getLASTUPDATE()));
                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }
                            params.put("ID", UUID.randomUUID().toString());
                            params.put("REG_TIME", dat);
                            params.put("UPDATE_TIME", new Date(new java.util.Date().getTime()));
                            params.put("APP_CODE", "002");
                            params.put("DEPT_CODE", xmlContent.getFYDM());
                            int key = parseStorageSPZTMapper.insert_SPZT_ZZJG(params);
                            if ( key == 1 ) {
                                transactionManager.commit(transactionStatus);
                                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                                resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
                            }
                            else {
                                transactionManager.rollback(transactionStatus);
                                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                resultMap.put(CodeUtil.RETURN_MSG, "insert_SPZT_ZZJG error rollback!");
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            resultMap.put(CodeUtil.RETURN_MSG, e.getMessage());
            transactionManager.rollback(transactionStatus);
        }

        Long end = System.currentTimeMillis();
        resultMap.put("time", "耗时：" + (end - start) + "毫秒");
        logger.info(resultMap.toString());
        return resultMap;
    }

    /**
     * @function parse String type xml content
     * @author wenyuguang
     * @creaetime 2017-3-19 18:57:51
     * @param xml
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> parseXml(String xml) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        String xmlName = rootElement.getQName().getName();
        List<Element> childElements = rootElement.elements();
        List<Map<String, Object>> xmlContent = getXmlContent(childElements);
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put(xmlName, xmlContent);
        return xmlMap;
    }

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017-3-19 18:57:57
     * @param childElements
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getXmlContent(List<Element> childElements) {

        List<Map<String, Object>> tablesList = new ArrayList<Map<String, Object>>();
        for (Element element : childElements) {
            Map<String, Object> tableMap = new HashMap<String, Object>();
            tableMap.put(element.getName(), getTableContent(element.elements()));
            tablesList.add(tableMap);
        }
        return tablesList;
    }

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年3月19日18:58:02
     * @param elements
     * @return
     */
    private List<Object> getTableContent(List<Element> elements) {
        List<Object> tableList = new ArrayList<Object>();
        /**
         * 存放没有R标签
         */
        Map<String, Object> colMap = new HashMap<String, Object>();
        List<Object> list = new ArrayList<Object>();
        /**
         * 存放多个R标签内容
         */
        List<Object> listR = new ArrayList<Object>();
        boolean addList = false;
        for (Element element : elements) {
            Map<String, Object> map_Rcol = new HashMap<String, Object>();
            /**
             * xml中有一张表多条记录
             */
            if ( "R".equalsIgnoreCase(element.getName()) ) {
                addList = true;
                @SuppressWarnings("unchecked")
                List<Element> list_R = element.elements();
                /**
                 * 所有R
                 */
                for (Element element_R : list_R) {
                    map_Rcol.put(element_R.getName(), element_R.getText());
                }
                listR.add(map_Rcol);
            }
            else {
                colMap.put(element.getName(), element.getText());
            }
        }
        if ( addList ) {
            tableList.add(listR);
        }
        else {
            list.add(colMap);
            tableList.add(list);
        }
        return tableList;
    }

    /** (non-Javadoc)
     * @see org.data.governance.api.parse.parsestorageSPZT.service.ParseStorageSPZTService#parseStorageSPZT_AJSC(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> parseStorageSPZT_AJSC(String ajbs) throws Exception {
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext
                .getBean("transactionManager");
        TransactionStatus transactionStatus = (TransactionStatus) transactionManager.getTransaction(definition);

        Long start = System.currentTimeMillis();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if ( StringUtils.isEmpty(ajbs) ) {
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            resultMap.put(CodeUtil.RETURN_MSG, "ajbs can not be null!");
            return resultMap;
        }
        try {
            /**
             * make params for select xml from database
             */
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("param", ajbs);
            /**
             * get xml from database
             */
            ScXmlContent xmlContent = parseStorageSPZTMapper.getAJSCXML(map);
            if ( StringUtils.isEmpty(xmlContent) ) {
                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                resultMap.put(CodeUtil.RETURN_MSG, "the buf database dont have the " + ajbs + "'s xml content !");
                return resultMap;
            }
            /**
             * 解析
             */
            Map<String, Object> xmlMap = null;
            if ( xmlContent.getINPUTSRC().equalsIgnoreCase("DB") ) {
                xmlMap = parseXml(Util.BLOB2GBKString(xmlContent.getXMLNR()));
            }
            else {
                xmlMap = parseXml(Util.BLOB2UTF8String(xmlContent.getXMLNR()));
            }
            if ( !StringUtils.isEmpty(xmlMap.get("msg")) ) {
                logger.error("parse xml file fail!");
                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                resultMap.put(CodeUtil.RETURN_MSG, xmlMap.get(CodeUtil.RETURN_MSG));
                return resultMap;
            }
            else {
                Set<String> set = xmlMap.keySet();
                if ( set.size() > 1 ) {
                    logger.error("one xml file has more than one type!!!");
                }
                else {
                    /**
                     * xml头部名称
                     */
                    String xmlName = set.toString().replace("[", "").replace("]", "");
                    /**
                     * 如果头部名称为“案件删除信息”
                     */
                    List<Map<String, Object>> tableList = (List<Map<String, Object>>) xmlMap.get(xmlName);
                    if ( xmlName.contains("案件删除信息") ) {

                        /**
                         * 删除内容超过一条，强行报错
                         */
                        if ( tableList.size() > 1 ) {
                            logger.error("案件删除信息xml里面有多张表，默认只处理一张表!");
                            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                            resultMap.put(CodeUtil.RETURN_MSG, "案件删除信息xml里面有多张表，默认只处理一张表");
                            return resultMap;
                        }
                        else {
                            Map<String, Object> table = tableList.get(0);
                            for (Entry<String, Object> entry : table.entrySet()) {
                                List<Object> listObj = (List<Object>) entry.getValue();
                                List<Object> listTbale = (List<Object>) listObj.get(0);
                                Map<String, Object> tableMa = (Map<String, Object>) listTbale.get(0);
                                /**
                                 * 遍历字段
                                 */
                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put("AJBS", "");
                                params.put("ID", "");
                                params.put("REG_TIME", "");
                                params.put("UPDATE_TIME", "");
                                params.put("DEPT_CODE", "");
                                params.put("APP_CODE", "");
                                params.put("AJLX", "");
                                params.put("JBFY", "");
                                params.put("SPR", "");
                                params.put("SPRBS", "");
                                params.put("SPRQ", "");
                                params.put("SSRQ", "");
                                params.put("SSYY", "");
                                for (Entry<String, Object> tableEntry : tableMa.entrySet()) {
                                    params.put(tableEntry.getKey().toUpperCase(), tableEntry.getValue());
                                }
                                /**
                                 * 先执行清空表操作
                                 */
                                Map<String, Object> procParam = new HashMap<String, Object>();
                                procParam.put("ajbs", ajbs);
                                parseStorageSPZTMapper.deleteAjxxByAjbs(procParam);
                                Integer status = (Integer) procParam.get("v_cursor1");
                                String msg = (String) procParam.get("v_cursor2");
                                /**
                                 * 等于0正常
                                 * 正常的入AJSCXX_AJSCXX之前需要先删除掉记录，不能用update
                                 * 然后再次insert
                                 */
                                if ( status == 0 ) {
                                    parseStorageSPZTMapper.delete_AJSCXX_AJSCXX(ajbs);
                                    SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    java.util.Date dat = null;
                                    try {
                                        dat = forma.parse(forma.format(xmlContent.getLASTUPDATE()));
                                    }
                                    catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    params.put("ID", "");
                                    params.put("REG_TIME", dat);
                                    params.put("UPDATE_TIME", new Date(new java.util.Date().getTime()));
                                    params.put("APP_CODE", "");
                                    params.put("DEPT_CODE", xmlContent.getFYDM());
                                    int key = parseStorageSPZTMapper.insert_AJSCXX_AJSCXX(params);
                                    if ( key == 1 ) {
                                        transactionManager.commit(transactionStatus);
                                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                                        resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
                                    }
                                    else {
                                        transactionManager.rollback(transactionStatus);
                                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                        resultMap.put(CodeUtil.RETURN_MSG, "error!");
                                    }
                                }
                                else {
                                    logger.error("删除案件信息表出错!");
                                    resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                    resultMap.put(CodeUtil.RETURN_MSG, "删除案件信息表出错:" + msg);
                                    return resultMap;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            resultMap.put(CodeUtil.RETURN_MSG, "error!");
            transactionManager.rollback(transactionStatus);
        }

        Long end = System.currentTimeMillis();
        resultMap.put("time", "耗时：" + (end - start) + "毫秒");
        logger.info(ajbs + "案件耗时：" + (end - start) + "毫秒");
        return resultMap;
    }
}
