package data.exchange.center.service.parse.xml.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
import data.exchange.center.service.parse.xml.domain.AjlxMeta;
import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.TableMeta;
import data.exchange.center.service.parse.xml.domain.XmlContent;
import data.exchange.center.service.parse.xml.mapper.ParseStorageZxajMapper;
import data.exchange.center.service.parse.xml.service.ParseStorageHistoryZxajService;
import data.exchange.center.service.parse.xml.service.ParseXmlService;
import data.exchange.center.service.parse.xml.util.LAAYMappingData;
import data.exchange.center.service.parse.xml.util.MappingDataType;
import data.exchange.center.service.parse.xml.util.SpringContextUtil;
import data.exchange.center.service.parse.xml.util.Util;

/**
 * 
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年3月18日 下午7:55:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class ParseStorageZxajServiceImpl implements ParseStorageHistoryZxajService {

    private static Logger                logger = LoggerFactory.getLogger(ParseStorageZxajServiceImpl.class);
    @Autowired
    private ParseXmlService              parseXmlService;
    @Autowired
    private ParseStorageZxajMapper  parseStorageZxajMapper;
    
    @SuppressWarnings("unchecked")
    @Override
    public ConcurrentMap<String, Object> parseStorageHistoryZxaj(String ajbs) throws Exception {
        /**
         * 获取Spring容器的对象
         */
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        /**
         * 设置属性的默认属性
         */
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        /**
         * 设置事务的传播行为，此处是设置为开启一个新事物
         */
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        /**
         * 设置事务的隔离级别，此处是读已经提交
         */
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        /**
         * 从spring容器对象中获取DataSourceTransactionManager，这个根据配置文件中配置的id名（transactionManager）
         */
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
        /**
         * 获取事务状态对象
         */
        TransactionStatus transactionStatus = (TransactionStatus) transactionManager.getTransaction(definition);
        
        Long start = System.currentTimeMillis();
        ConcurrentMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
        if(StringUtils.isEmpty(ajbs)){
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            resultMap.put(CodeUtil.RETURN_MSG, "ajbs can not be null!");
            return resultMap;
        }
        /**
         * make params for select xml from database
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("param", ajbs);
        /**
         * get xml from database
         */
        XmlContent xmlContent = parseStorageZxajMapper.getXML(map);
        if(StringUtils.isEmpty(xmlContent)){
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            resultMap.put(CodeUtil.RETURN_MSG, "the buf database dont have the "+ajbs+"'s xml content !");
            return resultMap;
        }
        /**
         * 解析
         */
        Map<String, Object> xmlMap = null;
        if(xmlContent.getINPUTSRC().equalsIgnoreCase("DB")){
            xmlMap = parseXml(Util.BLOB2GBKString(xmlContent.getXMLNR()));
        }else{
            xmlMap = parseXml(Util.BLOB2UTF8String(xmlContent.getXMLNR()));
        }
        if(!StringUtils.isEmpty(xmlMap.get("msg"))){
            logger.error("parse xml file fail!");
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            resultMap.put(CodeUtil.RETURN_MSG, xmlMap.get("msg"));
            return resultMap;
        }else{
            /**
             * 入案件主表前先清空已存在的记录
             */
            parseStorageZxajMapper.delete_buf_eaj(ajbs);
            /**
             * 入案件主表数据
             */
            saveAjMainTableInfo(xmlMap, xmlContent);
            Set<String> set = xmlMap.keySet();
            if(set.size()>1){
                logger.error("one xml file has more than one ajlx type!!!");
            }else{
                /**
                 * 获取案件类型信息
                 */
                String ajlxEname = set.toString().replace("[", "").replace("]", "");
                /**
                 * 校验
                 */
                List<Map<String, Object>> xmlTableList = (List<Map<String, Object>>) xmlMap.get(set.toString().replace("[", "").replace("]", ""));
                /**
                 * 表元数据
                 */
                List<ColMeta> colMetaList = parseStorageZxajMapper.getColMeta(ajlxEname);
                ColMeta colMeta = null;
                Map<String, Object> tableMap = new HashMap<String, Object>();
                List<ColMeta> tableColMetaList = Collections.synchronizedList(new ArrayList<ColMeta>());
                for(int i=0; i<colMetaList.size(); i++){
                    colMeta=colMetaList.get(i);
                    if(i<colMetaList.size()-1){
                        ColMeta colMetaLast = colMetaList.get(i+1);
                        if(!colMetaLast.getcEtbname().equals(colMeta.getcEtbname())){
                            tableColMetaList.add(colMeta);
                            tableMap.put(colMeta.getcEtbname(), tableColMetaList);
                        }else if(i==colMetaList.size()-1){
                            tableColMetaList.add(colMeta);
                            tableMap.put(colMeta.getcEtbname(), tableColMetaList);
                        }else{
                            tableColMetaList.add(colMeta);
                        }
                    }
                }
                Map<String, Object> resultValidateStatus = ValidateXml(xmlTableList, tableColMetaList);
                if(resultValidateStatus.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)){
                    try{
                        /**
                         * delete history data before insert data to table
                         */
                        delete(xmlMap, ajlxEname);
                    }catch(Exception e){
                        logger.error(e.getMessage());
                        transactionManager.rollback(transactionStatus);
                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                        resultMap.put(CodeUtil.RETURN_MSG, e.getMessage());
                        return resultMap;
                    }
                    /**
                     * 校验成功,入库操作
                     */
                    ConcurrentMap<String, Object> sqlMap = saveXml(xmlTableList, tableColMetaList, xmlContent);
                    if(sqlMap.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)){
                        /**
                         * 处理成功提交事务
                         */
                        transactionManager.commit(transactionStatus);
                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                        resultMap.put(CodeUtil.RETURN_MSG, "ok!");
                    }else{
                        /**
                         * 处理失败回滚操作
                         */
                        transactionManager.rollback(transactionStatus);
                        return sqlMap;
                    }
                }else
                    /**
                     * 校验失败
                     */{
                    /**
                     * 写日志记录ajbs等信息，记录失败原因
                     */
                    resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                    resultMap.put(CodeUtil.RETURN_MSG, resultValidateStatus.get(CodeUtil.RETURN_MSG));
                }
            }
        }
        Long end = System.currentTimeMillis();
        resultMap.put("time", "耗时："+(end-start) +"毫秒");
        logger.info(ajbs+"案件耗时："+(end-start) +"毫秒");
        return resultMap;
    }

    /**
     * @function 保存信息到案件主表
     * @author wenyuguang
     * @creaetime 2017年3月25日 下午9:15:57
     * @param xmlMap
     * @param xmlContent
     * @return 1-成功；0-失败；
     */
    private int saveAjMainTableInfo(Map<String, Object> xmlMap, XmlContent xmlContent) {
        int state=0;
        /**
         * 适应数据范围：
         * SGY.BUF_EXTERNAL2XML.ARJSOURCE in ('002','003','004','005')
         * SGY.BUF_EXTERNAL2XML.DESTSCHEMA = 'SGY'
         */
        if(xmlContent.getDESTSCHEMA().equalsIgnoreCase("SGY")
                &&(xmlContent.getAJSOURCE().equals("002")
                ||xmlContent.getAJSOURCE().equals("003")
                ||xmlContent.getAJSOURCE().equals("004")
                ||xmlContent.getAJSOURCE().equals("005"))){
            /**
             * 统一部份：
--                  AJBS                            BUF_EXTERNAL2XML.AJBS
--                  AJLX                            BUF_EXTERNAL2XML.AJLX
--                  DEPTCODE                    BUF_EXTERNAL2XML.FYDM
--                  REGDATE                 BUF_EXTERNAL2XML.LARQ
--                  LASTUPDATE              BUF_EXTERNAL2XML.LASTUPDATE
--                  CONTENTUPD              Null
--                  AH                           BUF_EXTERNAL2XML.AH
--                  AJJZJD                      BUF_EXTERNAL2XML.AJZT
--                  LARQ                            BUF_EXTERNAL2XML.LARQ
--                  JARQ                            BUF_EXTERNAL2XML.JARQ
             */
            Map<String, Object> mainTableParams = new HashMap<String, Object>();
            if(!StringUtils.isEmpty(xmlContent.getAJSOURCE())){
                mainTableParams.put("SRCCODE", xmlContent.getAJSOURCE());
            }else{
                mainTableParams.put("SRCCODE", xmlContent.getAJSOURCE());
            }
            if(!StringUtils.isEmpty(xmlContent.getAJBS())){
                mainTableParams.put("AJBS", xmlContent.getAJBS());
            }else{
                mainTableParams.put("AJBS", "");
            }
            if(!StringUtils.isEmpty(xmlContent.getAJLX())){
                mainTableParams.put("AJLX", xmlContent.getAJLX());
            }else{
                mainTableParams.put("AJLX", "");
            }
            if(!StringUtils.isEmpty(xmlContent.getFYDM())){
                mainTableParams.put("DEPTCODE", xmlContent.getFYDM());
            }else{
                mainTableParams.put("DEPTCODE", "");
            }
            if(!StringUtils.isEmpty(xmlContent.getLARQ())){
                mainTableParams.put("REGDATE", xmlContent.getLARQ());
            }else{
                mainTableParams.put("REGDATE", "");
            }
            if(!StringUtils.isEmpty(xmlContent.getLASTUPDATE())){
                mainTableParams.put("LASTUPDATE", xmlContent.getLASTUPDATE());
            }else{
                mainTableParams.put("LASTUPDATE", "");
            }
            mainTableParams.put("CONTENTUPD", "");
            if(!StringUtils.isEmpty(xmlContent.getAH())){
                mainTableParams.put("AH", xmlContent.getAH());
            }else{
                mainTableParams.put("AH", "");
            }
            if(!StringUtils.isEmpty(xmlContent.getAJZT())){
                mainTableParams.put("AJJZJD", xmlContent.getAJZT());
            }else{
                mainTableParams.put("AJJZJD", "");
            }
            if(!StringUtils.isEmpty(xmlContent.getLARQ())){
                mainTableParams.put("LARQ", xmlContent.getLARQ());
            }else{
                mainTableParams.put("LARQ", "");
            }
            if(!StringUtils.isEmpty(xmlContent.getJARQ())){
                mainTableParams.put("JARQ", xmlContent.getJARQ());
            }else{
                mainTableParams.put("JARQ", "");
            }
            /**
             * LAAY 
             */
            String tableNameAndCol = LAAYMappingData.getMappingData().get(xmlContent.getAJLX());
            /**
             * 2017-4-10 21:10:56
             * 如果对应没有LAAYMappingData则留空
             */
            if(StringUtils.isEmpty(tableNameAndCol)){
                mainTableParams.put("LAAY", "");
                mainTableParams.put("CBSPTBS", "");
                mainTableParams.put("CBSPT", "");
                mainTableParams.put("CBR", "");
            }else{
                String tableName = tableNameAndCol.substring(0, tableNameAndCol.indexOf("."));
                String col = tableNameAndCol.substring(tableNameAndCol.indexOf(".")+1, tableNameAndCol.length());
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> xmlTableList = (List<Map<String, Object>>) xmlMap.get(xmlMap.keySet().toString().replace("[", "").replace("]", ""));
                AjlxMeta ajlxMeta=null;
                try {
                    ajlxMeta = parseStorageZxajMapper.getAjlxMetaE(xmlContent.getAJLX());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                /**
                 * 遍历查找
                 */
                /**
                 * CBSPTBS  mainTableParams.put("CBSPTBS", LAAY);
                   CBSPT
                   CBR  
                                               对于法标内案件，根据“前缀_AJSLQK.CBSPTBS”、“前缀_AJSLQK.CBSPT”、“前缀_AJSLQK.CBR”获取，没有搜索到的不填
                                                对于法标外案件，根据“前缀_SLXX.CBSPTBS”、“前缀_SLXX.CBSPT”获取，没有搜索到的不填,没法搜索填写CBR信息
                 */
                if(ajlxMeta.getcFb().equals("0")){//法标内
                    String LAAY = "";
                    for(Map<String, Object> map:xmlTableList){
                        @SuppressWarnings("unchecked")
                        List<Object> listCol = (List<Object>) map.get(tableName);
                        if(listCol!=null&&listCol.size()==1){
                            @SuppressWarnings("unchecked")
                            List<Map<String, Object>> list = (List<Map<String, Object>>)listCol.get(0);
                            Map<String, Object> colMap = (Map<String, Object>)list.get(0);
                            if(colMap.containsKey(col)){
                                LAAY = colMap.get(col).toString();
                            }else{
                                LAAY="";
                            }
                            break;
                        }else{
                            LAAY="";
                        }
                    }
                    String CBSPTBS = null;
                    String CBSPT = null;
                    String CBR = null;
                    for(Map<String, Object> map:xmlTableList){
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> tableList = 
                                (List<Map<String, Object>>) map.get(ajlxMeta.getcEprefix()+"_AJSLQK");
                        if(tableList!=null&&tableList.size()==1){
                            @SuppressWarnings("unchecked")
                            List<Map<String, Object>> list = (List<Map<String, Object>>)tableList.get(0);
                            Map<String, Object> colMap = (Map<String, Object>)list.get(0);
                            if(colMap.containsKey("CBSPTBS")){
                                CBSPTBS = colMap.get("CBSPTBS").toString();
                            }else{
                                CBSPTBS = "";
                            }
                            if(colMap.containsKey("CBSPT")){
                                CBSPT = colMap.get("CBSPT").toString();
                            }else{
                                CBSPT = "";
                            }
                            if(colMap.containsKey("CBR")){
                                CBR = colMap.get("CBR").toString();
                            }else{
                                CBR = "";
                            }
                            break;
                        }else{
                            CBSPTBS = "";
                            CBSPT = "";
                            CBR = "";
                        }
                    }
                    mainTableParams.put("LAAY", LAAY);
                    mainTableParams.put("CBSPTBS", CBSPTBS);
                    mainTableParams.put("CBSPT", CBSPT);
                    mainTableParams.put("CBR", CBR);
                    /**
                     * 法标外
                     */
                }else if(ajlxMeta.getcFb().equals("1")){
                    String LAAY = null;
                    for(Map<String, Object> map:xmlTableList){
                        @SuppressWarnings("unchecked")
                        List<List<Map<String, Object>>> listCol = (List<List<Map<String, Object>>>) map.get(tableName);
                        /**
                         * 2017年4月8日19:27:15
                         * 加代码<null!=listCol&&>
                         */
                        if(null!=listCol&&listCol.size()==1){
                            Map<String, Object> colMap = listCol.get(0).get(0);
                            if(colMap.containsKey(col)){
                                LAAY = colMap.get(col).toString();
                            }else{
                                LAAY="";
                            }
                            break;
                        }else{
                            LAAY="";
                        }
                    }
                    String CBSPTBS = null;
                    String CBSPT = null;
                    String CBR = null;
                    for(Map<String, Object> map:xmlTableList){
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> tableList = 
                                (List<Map<String, Object>>) map.get(ajlxMeta.getcEprefix()+"_SLXX");
                        /**
                         * 2017年4月8日19:33:56
                         */
                        if(null!=null&&tableList.size()==1){
                            Map<String, Object> colMap = tableList.get(0);
                            if(colMap.containsKey("CBSPTBS")){
                                CBSPTBS = colMap.get("CBSPTBS").toString();
                            }else{
                                CBSPTBS = "";
                            }
                            if(colMap.containsKey("CBSPT")){
                                CBSPT = colMap.get("CBSPT").toString();
                            }else{
                                CBSPT = "";
                            }
                            if(colMap.containsKey("CBR")){
                                CBR = colMap.get("CBR").toString();
                            }else{
                                CBR = "";
                            }
                            break;
                        }else{
                            CBSPTBS = "";
                            CBSPT = "";
                            CBR = "";
                        }
                    }
                    mainTableParams.put("LAAY", LAAY);
                    mainTableParams.put("CBSPTBS", CBSPTBS);
                    mainTableParams.put("CBSPT", CBSPT);
                    mainTableParams.put("CBR", CBR);
                }
            }
            try {
                state = parseStorageZxajMapper.insertTable_uf_eaj(mainTableParams);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return state;
    }

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年3月7日 上午11:37:29
     * @param xmlMap
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    private void delete(Map<String, Object> xmlMap, String ajlxEname) throws Exception {
        List<TableMeta> tableMetaList = parseStorageZxajMapper.getTableMeta(ajlxEname);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) xmlMap.get(xmlMap.keySet().toString().replace("[", "").replace("]", "").trim());
        String midSql = "";
        List<Object> table0 = (List<Object>)mapList.get(0).get(mapList.get(0).keySet().toString().replace("[", "").replace("]", "").trim());
        List<Object> list = (List<Object>)table0.get(0);
        Map<String, Object> table =(Map<String, Object>)list.get(0);
        String ajbs=table.get("AJBS").toString();
        for(TableMeta tableMeta:tableMetaList){
            /**
             * 排除不需要清除的表
             */
            if(!tableMeta.getcEtbname().toUpperCase().contains("DAXX")
                    &&!tableMeta.getcEtbname().toUpperCase().contains("MLXX")
                    &&!tableMeta.getcEtbname().toUpperCase().contains("STWJ")){
                midSql = midSql+" delete from "+tableMeta.getcEtbname()+" where ajbs = "+ajbs+"; ";
            }
        }
        String deleteSql = "begin "+midSql+" end;";
        /**
         * patch sql
         */
        parseXmlService.deleteTableRecord(deleteSql);
    }

    /**
     * @function xml入库
     * @author wenyuguang
     * @creaetime 2017年3月3日 上午1:00:49
     * @param xmlTableList
     * @param tableMetaList 
     * @param xmlContent 
     * @return
     */
    @SuppressWarnings("unchecked")
    private ConcurrentMap<String, Object> saveXml(List<Map<String, Object>> xmlTableList, 
                                              List<ColMeta> tableMetaList, XmlContent xmlContent) {
        ConcurrentMap<String, Object> sqlMap = new ConcurrentHashMap<String, Object>();
        Map<String, Object> xmlTable;
        /**
         * 如果插入sql出现异常，则设置为false，退出循环
         */
        boolean isDo=true;
        /**
         * get xml tableName
         */
        for(int i=0; i<xmlTableList.size(); i++){
            Map<String, Object> param = new HashMap<String, Object>();
            xmlTable = xmlTableList.get(i); 
            String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
            /**
             * 需要插入的表  对应的元数据信息
             */
            List<ColMeta> tableColMeta = new ArrayList<ColMeta>();
            for(ColMeta tableColMet:tableMetaList){//get related meta table
                if(tableColMet.getcEtbname().equals(tableName)){
                    tableColMeta.add(tableColMet);
                }
            }
            /**
             * 组装插入sql，取的字段从元数据中取
             */
            String column = "";
            /**
             * 封装#{参数，javaType=?}
             */
            String val = "";
            for(int k=0; k<tableColMeta.size(); k++){
                if(k==tableColMeta.size()-1){
                    column = column + tableColMeta.get(k).getcEcolname().toUpperCase();
                    val = val + "#{params."+tableColMeta.get(k).getcEcolname().toUpperCase()
                            +", jdbcType="+MappingDataType.dataType.get(tableColMeta.get(k).getcDatatype()).toUpperCase()+"}";
                }else{
                    column = column + tableColMeta.get(k).getcEcolname().toUpperCase()+", ";
                    val = val + "#{params."+tableColMeta.get(k).getcEcolname().toUpperCase()
                            +", jdbcType="+MappingDataType.dataType.get(tableColMeta.get(k).getcDatatype()).toUpperCase()+"}, ";
                }
            }
            /**
             * 如果tableColMeta.get(0)无数据，则说明此表对应的元数据为空
             */
            String insertSql = "insert into "+tableName + "(" +column+") values (" + val + ")";
            java.util.Date dat = null;
            /**
             * 注意格式化的表达式
             */
            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            try {
                dat = forma.parse(xmlContent.getCREATETIME());
            }
            catch (ParseException e) {
                e.printStackTrace();
                sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                sqlMap.put(CodeUtil.RETURN_MSG, e.getMessage());
                return sqlMap;
            }
            SimpleDateFormat updateTimeFormat =  new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");  
            java.util.Date updateTime = null;
            try {
                updateTime = updateTimeFormat.parse(updateTimeFormat.format(System.currentTimeMillis()));
            }
            catch (ParseException e1) {
                e1.printStackTrace();
                logger.error(e1.getMessage());
            }  
            param.put("REG_TIME", dat);
            param.put("UPDATE_TIME", updateTime);
            param.put("APP_CODE", "002");
            param.put("DEPT_CODE", xmlContent.getFYDM());
            /**
             * 创建多条记录用的map参数
             */
            for(Entry<String, Object> entry : xmlTable.entrySet()){
                List<Object> obj = (List<Object>) entry.getValue();
                for(int j=0; j<obj.size(); j++){
                    List<Object> list = (List<Object>) obj.get(j);
                    for(int h=0; h<list.size(); h++){
                        Map<String, Object> map = (Map<String, Object>) list.get(h);
                        param.put("ID", UUID.randomUUID().toString().replace("-", ""));
                        for(int k=0; k<tableColMeta.size(); k++){
                            boolean isFound = false;
                            for(Entry<String, Object> ent : map.entrySet()){
                                if(tableColMeta.get(k).getcEcolname().equals(ent.getKey())){
                                    if(tableColMeta.get(k).getcDatatype().equalsIgnoreCase("DATE")){
                                        /**
                                         * 字符串转date
                                         */
                                        java.util.Date date = null;
                                        if(ent.getValue().toString().contains("T")){
                                            /**
                                             * 注意格式化的表达式
                                             */
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                            try {
                                                date = format.parse(ent.getValue().toString());
                                            }
                                            catch (ParseException e) {
                                                e.printStackTrace();
                                                sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                                                sqlMap.put(CodeUtil.RETURN_MSG, tableColMeta.get(k).getcEtbname()+": "+tableColMeta.get(k).getcEcolname()+": "+ e.getMessage());
                                                return sqlMap;
                                            }
                                        }else if(!ent.getValue().toString().contains("-")&&!ent.getValue().toString().contains("T")){
                                        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                                            try {
                                                date = format.parse(ent.getValue().toString());
                                            }
                                            catch (ParseException e) {
                                                System.out.println(e.getMessage());
                                                sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                                sqlMap.put(CodeUtil.RETURN_MSG, tableColMeta.get(k).getcEtbname()+": "+tableColMeta.get(k).getcEcolname()+": "+ e.getMessage());
                                                return sqlMap;
                                            }
                                        }else{
                                            /**
                                             * 注意格式化的表达式
                                             */
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                date = format.parse(ent.getValue().toString());
                                            }
                                            catch (ParseException e) {
                                                e.printStackTrace();
                                                sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                                                sqlMap.put(CodeUtil.RETURN_MSG, tableColMeta.get(k).getcEtbname()+": "+tableColMeta.get(k).getcEcolname()+": "+ e.getMessage());
                                                return sqlMap;
                                            }
                                        }
                                        param.put(tableColMeta.get(k).getcEcolname(), date);
                                    }else if(tableColMeta.get(k).getcDatatype().equalsIgnoreCase("BLOB")){
                                        /**
                                         * 字符串转byte[]
                                         */
                                        byte[] b = ent.getValue().toString().getBytes();
                                        param.put(tableColMeta.get(k).getcEcolname(), b);
                                    }else{
                                        param.put(tableColMeta.get(k).getcEcolname(), ent.getValue());
                                    }
                                    isFound=true;
                                }
                            }
                            String colName = tableColMeta.get(k).getcEcolname();
                            if(!isFound&&!colName.equals("ID")&&!colName.equals("REG_TIME")
                                    &&!colName.equals("UPDATE_TIME")&&!colName.equals("APP_CODE")&&!colName.equals("DEPT_CODE")){
                                param.put(tableColMeta.get(k).getcEcolname(), "");
                            }
                        }
                        try {
                            parseXmlService.saveCase(insertSql, param);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage());
                            sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                            sqlMap.put(CodeUtil.RETURN_MSG, "unsuccess:"+e.getMessage());
                            isDo=false;
                            return sqlMap;
                        }
                        /**
                         * 抛异常退出循环
                         */
                        if(!isDo){
                            break;
                        }
                    }
                    /**
                     * 抛异常退出循环
                     */
                    if(!isDo){
                        break;
                    }
                }
                /**
                 * 抛异常退出循环
                 */
                if(!isDo){
                    break;
                }
            }
            /**
             * 抛异常退出循环
             */
            if(!isDo){
                break;
            }
        }
        if(!isDo){
            return sqlMap;
        }else{
            sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            sqlMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
            return sqlMap;
        }
    }

    /**
     * @function 校验xml
     * @author wenyuguang
     * @creaetime 2017年3月19日18:57:41
     * @param xmlTableList 解析后的xml map类型
     * @param tableColMetaList 元数据
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> ValidateXml
        (List<Map<String, Object>> xmlTableList, List<ColMeta> tableMetaList) {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean status = true;
        StringBuffer strBuf = new StringBuffer();
        /**
         * 存取筛选出来的元数据表信息
         */
        Map<String, Object> metaMap = new HashMap<String, Object>();
        for(Map<String, Object> xmlTable:xmlTableList){
            String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
            /**
             * 遍历元数据的表名，获取到对应表的元数据
             */
            List<ColMeta> colMetalist = new ArrayList<ColMeta>();
            ColMeta tableColMeta=null;
            for(int i=0; i<tableMetaList.size(); i++){
                tableColMeta = tableMetaList.get(i);
                /**
                 * 筛选出tableColMeta.getcCcolname()=tableName的 元数据
                 */
                if(tableName.equals(tableColMeta.getcEtbname())){
                    colMetalist.add(tableColMeta);
                }
            }
            metaMap.put(tableName, colMetalist);
        }
        /**
         * 校验pk,uk非空；字段名与元数据是否吻合
         */
        for(Map<String, Object> xmlTable:xmlTableList){
            if(xmlTable.keySet().size()>1){
                logger.error("some thing error!");
                status = false;
            }else{
                String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
                List<Object> list= (List<Object>) xmlTable.get(tableName);
                /**
                 * 是否包含字段名
                 */
                boolean isContain = false;
                for(Object obj:list){
                    List<Map<String, Object>> listM= (List<Map<String, Object>>) obj;
                    for(Map<String, Object> objMap:listM){
                        /**
                         * 遍历
                         */
                        List<ColMeta> colMetaList = (List<ColMeta>) metaMap.get(tableName);
                        for(ColMeta colMeta:colMetaList){
                            /**
                             * 字段名校验
                             */
                            for(Entry<String, Object> entry : objMap.entrySet()) {  
                                for(ColMeta colMet:colMetaList){
                                    if(colMet.getcEcolname().equals(entry.getKey())){
                                        isContain=true;
                                        break;
                                    }
                                }
                                if(!isContain){
                                    logger.error("元数据中没有表"+tableName+"的 "+entry.getKey()+"字段 ");
                                    strBuf.append("元数据中没有表"+tableName+"的 "+entry.getKey()+"字段 ");
                                    status = false;
                                    break;
                                }
                                isContain=false;
                            }
                            /**
                             * 2017-4-13 22:51:45
                             * 新增break语句
                             */
                            if(!status){
                                break;
                            }
                            /**
                             * 主键校验
                             */
                            if(colMeta.getcPucol().equalsIgnoreCase("YES")){
                                String cCcolname = colMeta.getcEcolname();
                                for(Entry<String, Object> entry : objMap.entrySet()) {  
                                    if(cCcolname.equals(entry.getKey())){
                                        if(StringUtils.isEmpty(entry.getValue())){
                                            logger.error("xml中主键"+colMeta.getcPucol()+"值不能为空！\n ");
                                            strBuf.append("xml中主键"+colMeta.getcPucol()+"值不能为空！\n ");
                                            status = false;
                                        }
                                    }
                                } 
                            }
                            /**
                             * 2017-4-13 22:51:45
                             * 新增break语句
                             */
                            if(!status){
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(status){
        	result.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            result.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
        }else{
        	result.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            result.put(CodeUtil.RETURN_FAIL, strBuf);
        }
        return result;
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
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        }
        catch (DocumentException e) {
            e.printStackTrace();
            xmlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            xmlMap.put(CodeUtil.RETURN_MSG, e.getMessage());
            return xmlMap;
        }
        Element rootElement = document.getRootElement();
        List<Element> childElements = rootElement.elements();
        List<Map<String, Object>> xmlContent = getXmlContent(childElements);
        
        /**
         * 获取xml 的ajlx字符代码
         */
        AjlxMeta ajlxMeta=null;
        try {
            ajlxMeta = parseStorageZxajMapper.getAjlxMetaC(rootElement.getName());
        }
        catch (Exception e) {
            logger.error("获取案件类型元数据错误 :"+e.getMessage());
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(ajlxMeta)){
            logger.error("没有查询到案件类型信息，可能是xml中案件类型名称错误。名称为：  "+rootElement.getName());
            xmlMap.put(CodeUtil.RETURN_MSG, "没有查询到案件类型信息，可能是xml中案件类型名称错误。名称为：  "+rootElement.getName());
            return xmlMap;
        }else{
            xmlMap.put(ajlxMeta.getcAjlx(), xmlContent);
            return xmlMap;
        }
    }
    
    /**
     * 
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
            /**
             * 排除表信息
             */
            if(!element.getName().toString().contains("DAXX")
                    &&!element.getName().toString().toUpperCase().contains("DAXX_GC")
                    &&!element.getName().toString().toUpperCase().contains("MLXX")
                    &&!element.getName().toString().toUpperCase().contains("MLXX_GC")
                    &&!element.getName().toString().toUpperCase().contains("STWJ")
                    &&!element.getName().toString().toUpperCase().contains("STWJ_GC")){
                tableMap.put(element.getName(), getTableContent(element.elements()));
                tablesList.add(tableMap);
            }
        }
        return tablesList;
    }

    /**
     * 
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
        for(Element element : elements){
            Map<String, Object> map_Rcol = new HashMap<String, Object>();
            /**
             * xml中有一张表多条记录
             */
            if("R".equalsIgnoreCase(element.getName())){
                addList = true;
                @SuppressWarnings("unchecked")
                List<Element> list_R = element.elements();
                /**
                 * 所有R
                 */
                for(Element element_R:list_R){
                    map_Rcol.put(element_R.getName(), element_R.getText());
                }
                listR.add(map_Rcol);
            }else{
                colMap.put(element.getName(), element.getText());
            }
        }
        if(addList){
            tableList.add(listR);
        }else{
            list.add(colMap);
            tableList.add(list);
        }
        return tableList;
    }
}
