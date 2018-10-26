package data.exchange.center.service.parse.xml.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.TableMeta;
import data.exchange.center.service.parse.xml.domain.XmlContent;
import data.exchange.center.service.parse.xml.service.GetTableMetaService;
import data.exchange.center.service.parse.xml.service.ParseStorageService;
import data.exchange.center.service.parse.xml.service.ParseXmlService;
import data.exchange.center.service.parse.xml.util.MappingDataType;
import data.exchange.center.service.parse.xml.util.SpringContextUtil;
import data.exchange.center.service.parse.xml.util.Util;

/**
 * <p>Title: ParseStorage.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月1日 下午12:40:55</p>
 * @author Wen.Yuguang
 * @version 1.0 ApplicationContextAware
 **/
@Service
public class ParseStorageServiceImpl implements ParseStorageService {

    private static Logger                logger = LoggerFactory.getLogger(ParseStorageServiceImpl.class);
    @Autowired
    private ParseXmlService              parseXmlService;
    @Autowired
    private GetTableMetaService          getTableMetaService;
    
    @SuppressWarnings("unchecked")
    @Override
    public ConcurrentMap<String, Object> parseStorage(String ajbs) throws Exception {
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
            resultMap.put(CodeUtil.RETURN_MSG, "ajbs can't be null!");
            return resultMap;
        }
        // make params for select xml from database
        ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("param", ajbs);
        // get xml from database
        XmlContent xmlContent = parseXmlService.getXML(map);
        ConcurrentMap<String, Object> xmlMap = null;
        if(xmlContent.getINPUTSRC().equalsIgnoreCase("DB")){
            xmlMap = parseXml(Util.BLOB2GBKString(xmlContent.getXMLNR()));
        }else{
            xmlMap = parseXml(Util.BLOB2UTF8String(xmlContent.getXMLNR()));
        }
        Set<String> set = xmlMap.keySet();
        if(set.size()>1){
            logger.error("one xml file has two ajlx");
        }else{
            //获取列
            String ajlx = set.toString().replace("案件", "").replace("[", "").replace("]", "");
            List<ColMeta> colMetaList = getTableMetaService.getColMeta(ajlx);
            ColMeta colMeta = null;
            ConcurrentMap<String, Object> tableMap = new ConcurrentHashMap<String, Object>();
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
            //封装成Map<"表名",List<ColMeta>>
            //开始校验
            List<ConcurrentMap<String, Object>> xmlTableList = (List<ConcurrentMap<String, Object>>) xmlMap.get(set.toString().replace("[", "").replace("]", ""));
            ConcurrentMap<String, Object> resultValidateStatus = ValidateXml(xmlTableList, tableColMetaList);
            if(resultValidateStatus.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)){
                //delete history data before insert data to table
                delete(xmlMap);
                //校验成功,入库操作
                ConcurrentMap<String, Object> sqlMap = null;
                try{
                    sqlMap = saveXml(xmlTableList, tableColMetaList, xmlContent);
                }catch(RuntimeException e){
                    resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                    resultMap.put(CodeUtil.RETURN_MSG, e.getMessage());
                    return resultMap;
                }catch(Exception e){
                    resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                    resultMap.put(CodeUtil.RETURN_MSG, e.getMessage());
                    return resultMap;
                }
                if(sqlMap.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)){
                    /**
                     * 处理成功提交事务
                     */
                    transactionManager.commit(transactionStatus);
                    //入库成功
                    resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                    resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
                }else{
                    /**
                     * 处理失败回滚操作
                     */
                    transactionManager.rollback(transactionStatus);
                    return sqlMap;
                }
            }else{//校验失败
                //写日志记录ajbs等信息，记录失败原因
                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                resultMap.put(CodeUtil.RETURN_MSG, resultValidateStatus.get(CodeUtil.RETURN_MSG));
            }
        }
        Long end = System.currentTimeMillis();
        resultMap.put("time", "调用耗时："+(end-start) +"毫秒");
        logger.info(ajbs+"案件调用耗时："+(end-start) +"毫秒");
        return resultMap;
    }

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年3月7日 上午11:37:29
     * @param xmlMap
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    private void delete(ConcurrentMap<String, Object> xmlMap) throws Exception {
        String param = xmlMap.keySet().toString().replace("案件", "").replace("[", "").replace("]", "").trim();
        List<TableMeta> tableMetaList = getTableMetaService.getTableMeta(param);
        List<ConcurrentMap<String, Object>> mapList = (List<ConcurrentMap<String, Object>>) xmlMap.get(xmlMap.keySet().toString().replace("[", "").replace("]", "").trim());
        String midSql = "";
        String ajbs = mapList.get(0).get(mapList.get(0).keySet().toString().replace("[", "").replace("]", "").trim()).toString();
        for(TableMeta tableMeta:tableMetaList){
            midSql = midSql+" delete from "+tableMeta.getcEtbname()+" where ajbs = "+ajbs+"; ";
        }
        String deleteSql = "begin "+midSql+" end;";
        parseXmlService.deleteTableRecord(deleteSql);
    }

    /**
     * @function xml入库
     * @author wenyuguang
     * @creaetime 2017年3月3日 上午1:00:49
     * @param xmlTableList
     * @param tableColMetaList 
     * @param xmlContent 
     * @return
     */
    @SuppressWarnings("unchecked")
    private ConcurrentMap<String, Object> saveXml(List<ConcurrentMap<String, Object>> xmlTableList, 
                                              List<ColMeta> tableColMetaList, XmlContent xmlContent) {

        ConcurrentMap<String, Object> sqlMap = new ConcurrentHashMap<String, Object>();
        ConcurrentMap<String, Object> xmlTable;
        /**
         * 如果插入sql出现异常，则设置为false，退出循环
         */
        boolean isDo=true;
        for(int i=1; i<xmlTableList.size(); i++){//get xml tableName
            ConcurrentMap<String, Object> param = new ConcurrentHashMap<String, Object>();
            xmlTable = xmlTableList.get(i); 
            String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
            //需要插入的表  对应的元数据信息
            List<ColMeta> tableColMeta = Collections.synchronizedList(new ArrayList<ColMeta>());
            for(ColMeta tableColMet:tableColMetaList){//get related meta table
                if(tableColMet.getcCtbname().equals(tableName)){
                    tableColMeta.add(tableColMet);
                }
            }
            //组装插入sql，取的字段从元数据中取
            String column = "";
            //封装#{参数，javaType=?}
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
            //如果tableColMeta.get(0)无数据，则说明此表对应的元数据为空
            String insertSql="";
            if(tableColMeta.size()>0){
                insertSql = "insert into "+tableColMeta.get(0).getcEtbname() + "(" +column+") values (" + val + ")";
            }else{
                try {
                    logger.error("缺少元数据信息");
                    throw new Exception("缺少元数据信息");
                }
                catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
            //AJBS, ID, REG_TIME, UPDATE_TIME, DEPT_CODE, APP_CODE, 
            //AH, AJZLX, SDQQRQ, SARQ, AJJZJD, SALY, SAAJAH, DCQZSCAH, SPLCYGK
            param.put("AJBS", xmlTableList.get(0).get("案件标识").toString());
            java.util.Date dat = null;
            SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意格式化的表达式
            
            try {
                dat = forma.parse(xmlContent.getCREATETIME());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            param.put("REG_TIME", dat);
            param.put("UPDATE_TIME", new Date (new java.util.Date().getTime()));
            param.put("APP_CODE", "002");
            param.put("DEPT_CODE", xmlContent.getFYDM());
            
            //创建多条记录用的map参数
            for(Entry<String, Object> entry : xmlTable.entrySet()){
                List<Object> obj = (List<Object>) entry.getValue();
                for(int j=0; j<obj.size(); j++){
                    List<Object> list = (List<Object>) obj.get(j);
                    for(int h=0; h<list.size(); h++){
                        ConcurrentMap<String, Object> map = (ConcurrentMap<String, Object>) list.get(h);
                        param.put("ID", UUID.randomUUID().toString().replace("-", ""));
                        for(int k=0; k<tableColMeta.size(); k++){
                            boolean isFound = false;
                            for(Entry<String, Object> ent : map.entrySet()){
                                if(tableColMeta.get(k).getcCcolname().equals(ent.getKey())){
                                    if(tableColMeta.get(k).getcDatatype().equalsIgnoreCase("DATE")){
                                        //字符串转date
                                        java.util.Date date = null;
                                        if(ent.getValue().toString().contains("T")){
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
                                            try {
                                                date = format.parse(ent.getValue().toString());
                                            }
                                            catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }else{
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//注意格式化的表达式
                                            try {
                                                date = format.parse(ent.getValue().toString());
                                            }
                                            catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        param.put(tableColMeta.get(k).getcEcolname(), date);
                                    }else if(tableColMeta.get(k).getcDatatype().equalsIgnoreCase("BLOB")){
                                        //字符串转byte[]
                                        byte[] b = ent.getValue().toString().getBytes();
                                        param.put(tableColMeta.get(k).getcEcolname(), b);
                                    }else{
                                        param.put(tableColMeta.get(k).getcEcolname(), ent.getValue());
                                    }
                                    isFound=true;
                                }
                            }
                            String colName = tableColMeta.get(k).getcEcolname();
                            if(!isFound&&!colName.equals("AJBS")&&!colName.equals("ID")&&!colName.equals("REG_TIME")
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
                        }
                    }
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
     * @creaetime 2017年3月3日 上午12:23:45
     * @param xmlTableList 解析后的xml map类型
     * @param tableColMetaList 元数据
     * @return
     */
    @SuppressWarnings("unchecked")
    private ConcurrentMap<String, Object> ValidateXml
        (List<ConcurrentMap<String, Object>> xmlTableList, List<ColMeta> tableColMetaList) {
        ConcurrentMap<String, Object> result = new ConcurrentHashMap<String, Object>();
        
        if(xmlTableList.get(0).containsKey("案件标识")&&StringUtils.isEmpty(xmlTableList.get(0).get("案件标识"))){
            logger.error("xml中[案件标识]值为空！");
            result.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            result.put(CodeUtil.RETURN_MSG, "xml中[案件标识]值为空！");
            return result;
        }else if(!xmlTableList.get(0).containsKey("案件标识")){
            logger.error("xml中没有[案件标识]标签！");
            result.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            result.put(CodeUtil.RETURN_MSG, "xml中没有[案件标识]标签！");
            return result;
        }
        boolean status = true;
        StringBuffer strBuf = new StringBuffer();
        //存取筛选出来的元数据表信息
        ConcurrentMap<String, Object> metaMap = new ConcurrentHashMap<String, Object>();
        for(ConcurrentMap<String, Object> xmlTable:xmlTableList){
            String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
            //遍历元数据的表名，获取到对应表的元数据
            List<ColMeta> colMetalist = Collections.synchronizedList(new ArrayList<ColMeta>());
            ColMeta tableColMeta=null;
            for(int i=0; i<tableColMetaList.size(); i++){
                tableColMeta = tableColMetaList.get(i);
                //筛选出tableColMeta.getcCcolname()=tableName的 元数据
                if(tableName.equals(tableColMeta.getcCtbname())){
                    colMetalist.add(tableColMeta);
                }
            }
            metaMap.put(tableName, colMetalist);
        }
        //校验pk,uk非空；字段名与元数据是否吻合
        for(ConcurrentMap<String, Object> xmlTable:xmlTableList){
            if(xmlTable.keySet().size()>1){
                logger.error("some thing error!");
                strBuf.append("xml error!");
                status = false;
            }else if(!"案件标识".equals(xmlTable.keySet().toString().replace("[", "").replace("]", ""))){
                String tableName = xmlTable.keySet().toString().replace("[", "").replace("]", "");
                List<Object> list= (List<Object>) xmlTable.get(tableName);
                //是否包含字段名
                boolean isContain = false;
                for(Object obj:list){
                    List<ConcurrentMap<String, Object>> listM= (List<ConcurrentMap<String, Object>>) obj;
                    for(ConcurrentMap<String, Object> objMap:listM){
                      //遍历
                        List<ColMeta> colMetaList = (List<ColMeta>) metaMap.get(tableName);
                        for(ColMeta colMeta:colMetaList){
                            //字段名校验
                            for(Entry<String, Object> entry : objMap.entrySet()) {  
                                for(ColMeta colMet:colMetaList){
                                    if(colMet.getcCcolname().equals(entry.getKey())){
                                        isContain=true;
                                        break;
                                    }
                                }
                                if(!isContain){
                                    logger.error("ERROR！元数据中没有表"+tableName+"的 "+entry.getKey()+"字段 ");
                                    strBuf.append("ERROR！元数据中没有表"+tableName+"的 "+entry.getKey()+"字段 \n ");
                                    status = false;
                                }
                                isContain=false;
                            }
                            //主键校验
                            if(colMeta.getcPucol().equalsIgnoreCase("YES")&&!colMeta.getcCcolname().equals("案件标识")){
                                String cCcolname = colMeta.getcCcolname();
                                for(Entry<String, Object> entry : objMap.entrySet()) {  
                                    if(cCcolname.equals(entry.getKey())){
                                        if(StringUtils.isEmpty(entry.getValue())){
                                            logger.error("ERROR！xml中主键"+colMeta.getcPucol()+"值不能为空！\n ");
                                            strBuf.append("ERROR！xml中主键"+colMeta.getcPucol()+"值不能为空！\n ");
                                            status = false;
                                        }
                                    }
                                } 
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
            result.put(CodeUtil.RETURN_MSG, strBuf);
        }
        return result;
    }
    
    /**
     * @function parse String type xml content
     * @author wenyuguang
     * @creaetime 2017年3月1日 下午12:57:56
     * @param xml
     * @return 
     */
    @SuppressWarnings("unchecked")
    private ConcurrentMap<String, Object> parseXml(String xml) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        List<Element> childElements = rootElement.elements();
        List<ConcurrentMap<String, Object>> xmlContent = getXmlContent(childElements);
        ConcurrentMap<String, Object> xmlMap = new ConcurrentHashMap<String, Object>();
        xmlMap.put(rootElement.getName(), xmlContent);
        return xmlMap;
    }
    
    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年3月3日 下午5:38:02
     * @param childElements
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<ConcurrentMap<String, Object>> getXmlContent(List<Element> childElements) {
        
        List<ConcurrentMap<String, Object>> tablesList = Collections.synchronizedList(new ArrayList<ConcurrentMap<String, Object>>());
        for (Element element : childElements) {
            ConcurrentMap<String, Object> tableMap = new ConcurrentHashMap<String, Object>();
            if(element.elements().size()==0){//ajbs
                tableMap.put(element.getName(), element.getText());
            }else{
                tableMap.put(element.getName(), getTableContent(element.elements()));
            }
            tablesList.add(tableMap);
        }
        return tablesList;
    }

    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年3月3日 下午5:38:22
     * @param elements
     * @return
     */
    private List<Object> getTableContent(List<Element> elements) {
        List<Object> tableList = Collections.synchronizedList(new ArrayList<Object>());
        ConcurrentMap<String, Object> colMap = new ConcurrentHashMap<String, Object>();//存放没有R标签
        List<Object> list = Collections.synchronizedList(new ArrayList<Object>());
        List<Object> listR = Collections.synchronizedList(new ArrayList<Object>());//存放多个R标签内容
        boolean addList = false;
        for(Element element : elements){
            ConcurrentMap<String, Object> map_Rcol = new ConcurrentHashMap<String, Object>();
            if("R".equalsIgnoreCase(element.getName())){//xml中有一张表多条记录
                addList = true;
                @SuppressWarnings("unchecked")
                List<Element> list_R = element.elements();
                for(Element element_R:list_R){//所有R
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
