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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
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
import data.exchange.center.service.parse.xml.mapper.ParseStorageJxjsMapper;
import data.exchange.center.service.parse.xml.mapper.ParseStorageSPZTMapper;
import data.exchange.center.service.parse.xml.mapper.ParseStorageTongdahaiMapper;
import data.exchange.center.service.parse.xml.service.ParseStorageJxjsService;
import data.exchange.center.service.parse.xml.service.ParseXmlService;
import data.exchange.center.service.parse.xml.util.MappingDataType;
import data.exchange.center.service.parse.xml.util.SpringContextUtil;
import data.exchange.center.service.parse.xml.util.Util;

@Service
public class ParseStorageJxjsServiceImpl implements ParseStorageJxjsService{

	private static Logger                logger = LoggerFactory.getLogger(ParseStorageJxjsServiceImpl.class);
    @Autowired
    private ParseXmlService              parseXmlService;
    @Autowired
    private ParseStorageTongdahaiMapper  parseStorageTongdahaiMapper;
    @Autowired
    private ParseStorageJxjsMapper       parseStorageJxjsMapper;
    @Autowired
    private ParseStorageSPZTMapper       parseStorageSPZTMapper;
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> parseStorageJxjs(String ajbs) throws Exception {
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
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
        XmlContent xmlContent = parseStorageJxjsMapper.getXML(map);
        if(StringUtils.isEmpty(xmlContent)){
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
            resultMap.put(CodeUtil.RETURN_MSG, String.format("the buf database dont have the %s's xml content !", ajbs));
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
            parseStorageTongdahaiMapper.delete_buf_eaj(ajbs);
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
                List<ColMeta> colMetaList = parseStorageJxjsMapper.getColMeta(ajlxEname);
                ColMeta colMeta = null;
                Map<String, Object> tableMap = new HashMap<String, Object>();
                List<ColMeta> tableColMetaList = new ArrayList<ColMeta>();
                for(int i=0; i<colMetaList.size(); i++){
                    colMeta=colMetaList.get(i);
                    if(i<colMetaList.size()-1){
                        ColMeta colMetaLast = colMetaList.get(i+1);
                        if(!colMetaLast.getcCtbname().equals(colMeta.getcCtbname())){
                            tableColMetaList.add(colMeta);
                            tableMap.put(colMeta.getcCtbname(), tableColMetaList);
                        }else if(i==colMetaList.size()-1){
                            tableColMetaList.add(colMeta);
                            tableMap.put(colMeta.getcCtbname(), tableColMetaList);
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
                    Map<String, Object> sqlMap = saveXml(xmlTableList, tableColMetaList, xmlContent);
                    if(sqlMap.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)){
                        /**
                         * 处理成功提交事务
                         */
                        transactionManager.commit(transactionStatus);
                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                        resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
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
                    resultMap.put(CodeUtil.RETURN_MSG, resultValidateStatus.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS));
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
            mainTableParams.put("SRCCODE", xmlContent.getAJSOURCE()==null?"":xmlContent.getAJSOURCE());
            mainTableParams.put("AJBS", xmlContent.getAJBS()==null?"":xmlContent.getAJBS());
            mainTableParams.put("AJLX", xmlContent.getAJLX()==null?"":xmlContent.getAJLX());
            mainTableParams.put("DEPTCODE", xmlContent.getFYDM()==null?"":xmlContent.getFYDM());
            mainTableParams.put("REGDATE", xmlContent.getLARQ()==null?"":xmlContent.getLARQ());
            mainTableParams.put("LASTUPDATE", xmlContent.getLASTUPDATE()==null?"":xmlContent.getLASTUPDATE());
            mainTableParams.put("CONTENTUPD", "");
            mainTableParams.put("AH", xmlContent.getAH()==null?"":xmlContent.getAH());
            mainTableParams.put("AJJZJD", xmlContent.getAJZT()==null?"":xmlContent.getAJZT());
            mainTableParams.put("LARQ", xmlContent.getLARQ()==null?"":xmlContent.getLARQ());
            mainTableParams.put("JARQ", xmlContent.getJARQ()==null?"":xmlContent.getJARQ());
            /**
             * LAAY 
             */
            String tableNameAndCol = "收案和立案信息.主罪名";
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
                    ajlxMeta = parseStorageTongdahaiMapper.getAjlxMetaE(xmlContent.getAJLX());
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
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
                            LAAY = colMap.get(col).toString()==null?"":colMap.get(col).toString();
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
                                (List<Map<String, Object>>) map.get("案件审理情况");
                        if(tableList!=null&&tableList.size()==1){
                            @SuppressWarnings("unchecked")
                            List<Map<String, Object>> list = (List<Map<String, Object>>)tableList.get(0);
                            Map<String, Object> colMap = (Map<String, Object>)list.get(0);
                            CBSPTBS = colMap.get("承办审判庭标识").toString()==null?"":colMap.get("承办审判庭标识").toString();
                            CBSPT   = colMap.get("承办审判庭").toString()    ==null?"":colMap.get("承办审判庭").toString();
                            CBR     = colMap.get("承办人").toString()        ==null?"":colMap.get("承办人").toString();
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
                            LAAY = colMap.get(col).toString()==null?"":colMap.get(col).toString();
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
                            CBSPTBS = colMap.get("CBSPTBS").toString()==null?"":colMap.get("CBSPTBS").toString();
                            CBSPT   = colMap.get("CBSPT").toString()==null?"":colMap.get("CBSPT").toString();
                            CBR     = colMap.get("CBR").toString()==null?"":colMap.get("CBR").toString();
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
                state = parseStorageTongdahaiMapper.insertTable_uf_eaj(mainTableParams);
            }
            catch (Exception e) {
                System.out.println(e.toString());
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
        List<TableMeta> tableMetaList = parseStorageJxjsMapper.getTableMeta(ajlxEname);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) xmlMap.get(xmlMap.keySet().toString().replace("[", "").replace("]", "").trim());
        String midSql = "";
//        List<Object> table0 = (List<Object>)mapList.get(1).get(mapList.get(1).keySet().toString().replace("[", "").replace("]", "").trim());
//        List<Object> list = (List<Object>)table0.get(0);
//        Map<String, Object> table =(Map<String, Object>)list.get(0);
        String ajbs = (String)mapList.get(0).get(mapList.get(0).keySet().toString().replace("[", "").replace("]", "").trim());
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
    private Map<String, Object> saveXml(List<Map<String, Object>> xmlTableList, 
                                              List<ColMeta> tableMetaList, XmlContent xmlContent) {
        Map<String, Object> sqlMap = new HashMap<String, Object>();
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
            if(tableName.equals("案件标识")) 
            	continue;
            /**
             * 需要插入的表  对应的元数据信息
             */
            String eTableName = "";
            List<ColMeta> tableColMeta = new ArrayList<ColMeta>();
            for(ColMeta tableColMet:tableMetaList){//get related meta table
                if(tableColMet.getcCtbname().equals(tableName)){
                    tableColMeta.add(tableColMet);
                    eTableName = tableColMet.getcEtbname();
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
                            +", jdbcType="+MappingDataType.dataType.get(tableColMeta.get(k).getcDatatype().toUpperCase()).toUpperCase()+"}";
                }else{
                    column = column + tableColMeta.get(k).getcEcolname().toUpperCase()+", ";
                    val = val + "#{params."+tableColMeta.get(k).getcEcolname().toUpperCase()
                            +", jdbcType="+MappingDataType.dataType.get(tableColMeta.get(k).getcDatatype().toUpperCase()).toUpperCase()+"}, ";
                }
            }
            /**
             * 如果tableColMeta.get(0)无数据，则说明此表对应的元数据为空
             */
            String insertSql = "insert into "+eTableName + "(" +column+") values (" + val + ")";
            java.util.Date dat = null;
            /**
             * 注意格式化的表达式
             */
            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            try {
                dat = forma.parse(xmlContent.getCREATETIME());
            }
            catch (ParseException e) {
                System.out.println(e.getMessage());
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
                logger.error(e1.toString());
            }  
            param.put("REG_TIME", dat);
            param.put("UPDATE_TIME", updateTime);
            param.put("APP_CODE", "002");
            param.put("DEPT_CODE", xmlContent.getFYDM());
            param.put("AJBS", xmlContent.getAJBS());
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
                                if(tableColMeta.get(k).getcCcolname().equals(ent.getKey())){
                                    if(tableColMeta.get(k).getcDatatype().equalsIgnoreCase("DATE")){
                                        /**
                                         * 字符串转date
                                         */
                                        java.util.Date date = null;
                                        if(ent.getValue().toString().contains("T")&&ent.getValue().toString().contains("-")){
                                            /**
                                             * 注意格式化的表达式
                                             */
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                            try {
                                                date = format.parse(ent.getValue().toString());
                                            }
                                            catch (ParseException e) {
                                                System.out.println(e.getMessage());
                                                sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
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
                                        }else if(ent.getValue().toString().contains("-")&&appearNumber(ent.getValue().toString(),":")==2&&!ent.getValue().toString().contains("T")){//适配2018-04-26 09:00:00格式
                                        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                                                System.out.println(e.getMessage());
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
                                    	
                                        byte[] b = Base64.decodeBase64(ent.getValue().toString());
                                        param.put(tableColMeta.get(k).getcEcolname(), b);
                                    }else{
                                        param.put(tableColMeta.get(k).getcEcolname(), ent.getValue());
                                    }
                                    isFound=true;
                                }
                            }
                            String colName = tableColMeta.get(k).getcEcolname();
                            if(!isFound&&!colName.equals("ID")&&!colName.equals("REG_TIME")
                                    &&!colName.equals("UPDATE_TIME")
                                    &&!colName.equals("APP_CODE")
                                    &&!colName.equals("DEPT_CODE")
                                    &&!colName.equals("AJBS")){
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
        try {
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
                    if(tableName.equals("案件标识")) {
                    	continue;
                    }
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
                result.put(CodeUtil.RETURN_MSG, "xml校验成功！");
            }else{
            	result.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                result.put(CodeUtil.RETURN_MSG, strBuf);
            }
		} catch (Exception e) {
			e.printStackTrace();
			result.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            result.put(CodeUtil.RETURN_MSG, e.toString());
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
//        	// 创建saxReader对象  
//            SAXReader reader = new SAXReader();  
//            // 通过read方法读取一个文件 转换成Document对象  
//            document = reader.read(new File("F:\\AJ_3195_20180531200258_01\\15_319500200006258.xml"));
        }
        catch (DocumentException e) {
            System.out.println(e.getMessage());
            xmlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            xmlMap.put(CodeUtil.RETURN_MSG, e.getMessage());
            return xmlMap;
        }
        Element rootElement = document.getRootElement();
        List<Element> childElements = rootElement.elements();
        List<Map<String, Object>> xmlContent = getXmlContent(childElements);
        xmlMap.put("刑罚变更", xmlContent);
        return xmlMap;
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
        	Map<String, Object> tableMap1 = new HashMap<String, Object>();
        	Map<String, Object> tableMap2 = new HashMap<String, Object>();
            /**
             * 排除表信息
             */
            if(!element.getName().toString().contains("实体文件")){
            	if(element.getName().equals("案件标识")) {
            		tableMap.put(element.getName(), element.getTextTrim());
            	}else if("申请刑罚变更罪犯".equals(element.getName())){
            		List<Element> list = element.elements();
            		List<Object> tableList = new ArrayList<Object>();
            		List<Object> tableList1 = new ArrayList<Object>();
            		List<Object> tableList2 = new ArrayList<Object>();
                    /**
                     * 存放没有R标签
                     */
                    Map<String, Object> xfbgzfMap = new HashMap<String, Object>();
                    Map<String, Object> xfbgqkMap = new HashMap<String, Object>();
                    Map<String, Object> sxpjxfMap = new HashMap<String, Object>();
                    List<Object> lists = new ArrayList<Object>();
                    List<Object> lists1 = new ArrayList<Object>();
                    List<Object> lists2 = new ArrayList<Object>();
            		for(Element xfbg:list) {
            			if(xfbg.getName().equals("罪犯")||xfbg.getName().equals("累犯")) {
            				xfbgzfMap.put(xfbg.getName(), xfbg.getTextTrim());
            			}
            			if(xfbg.getName().equals("申请刑罚变更情况")) {
            				List<Element> xfbgqkList = xfbg.elements();
            				for(Element el:xfbgqkList) {
            					xfbgqkMap.put(el.getName(), el.getText());
            				}
            			}
            			if("罪犯生效判决刑罚".equals(xfbg.getName())) {
            				List<Element> sxpjxfList = xfbg.elements();
            				for(Element el:sxpjxfList) {
            					sxpjxfMap.put(el.getName(), el.getText());
            				}
            			}
            		}
            		lists.add(xfbgzfMap);
            		lists1.add(xfbgqkMap);
            		lists2.add(sxpjxfMap);
            		tableList.add(lists);
            		tableList1.add(lists1);
            		tableList2.add(lists2);
            		tableMap.put("刑罚变更罪犯", tableList);
            		tableMap1.put("申请刑罚变更情况", tableList1);
            		tableMap2.put("生效判决刑罚", tableList2);
            	}else {
            		tableMap.put(element.getName(), getTableContent(element.elements()));
            	}
            	if(!tableMap.isEmpty())
            		tablesList.add(tableMap);
            	if(!tableMap1.isEmpty())
            		tablesList.add(tableMap1);
            	if(!tableMap2.isEmpty())
            		tablesList.add(tableMap2);
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
    
    /**
     * 获取指定字符串出现的次数
     * 
     * @param srcText 源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

	@Override
	public Map<String, Object> deleteJxjs(String ajbs, String fydm, String ajlx) {
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
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("AJBS", ajbs);
            params.put("AJLX", ajlx);
            params.put("JBFY", "");
            params.put("SPR", "");
            params.put("SPRBS", "");
            params.put("SPRQ", "");
            
            params.put("SSYY", "");
            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date dat = null;
            try {
                dat = forma.parse(forma.format(new java.util.Date()));
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            params.put("ID", "");
            params.put("REG_TIME", dat);
            params.put("SSRQ", forma.format(new java.util.Date()));
            params.put("UPDATE_TIME", new Date(new java.util.Date().getTime()));
            params.put("APP_CODE", "004");
            params.put("DEPT_CODE", fydm);
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
               
                int key = parseStorageSPZTMapper.insert_AJSCXX_AJSCXX(params);
                if ( key == 1 ) {
                    transactionManager.commit(transactionStatus);
                    resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                    resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
                }
                else {
                    transactionManager.rollback(transactionStatus);
                    resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                    resultMap.put(CodeUtil.RETURN_MSG, "delete ajbs error!");
                }
            }
            else {
                logger.error("删除案件信息表出错!");
                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                resultMap.put(CodeUtil.RETURN_MSG, "删除案件信息表出错:" + msg);
                return resultMap;
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
