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

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.parse.xml.domain.AjlxMeta;
import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.TableMeta;
import data.exchange.center.service.parse.xml.domain.XmlContent;
import data.exchange.center.service.parse.xml.mapper.ParseStorageHistoryMapper;
import data.exchange.center.service.parse.xml.service.ParseStorageHistoryService;
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
public class ParseStorageHistoryServiceImpl implements ParseStorageHistoryService {

    private static Logger                logger = LoggerFactory.getLogger(ParseStorageHistoryServiceImpl.class);
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> parseStorageHistory(String ajbs) throws Exception {
        ConcurrentMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
        SqlSession sgySqlSession = ((SqlSessionFactory)SpringContextUtil
                .getApplicationContext()
                .getBean("lsSqlSessionFactory"))
                .openSession();
        ParseStorageHistoryMapper parseStorageHistoryMapper = sgySqlSession.getMapper(ParseStorageHistoryMapper.class);
        try{
            Long start = System.currentTimeMillis();
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
            XmlContent xmlContent = parseStorageHistoryMapper.getXML(map);
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
                xmlMap = parseXml(Util.BLOB2GBKString(xmlContent.getXMLNR()),parseStorageHistoryMapper);
            }else{
                xmlMap = parseXml(Util.BLOB2UTF8String(xmlContent.getXMLNR()),parseStorageHistoryMapper);
            }
            if(!StringUtils.isEmpty(xmlMap.get("msg"))){
                logger.error("parse xml file fail!");
                resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                resultMap.put(CodeUtil.RETURN_MSG, xmlMap.get(CodeUtil.RETURN_MSG));
                return resultMap;
            }else{
                /**
                 * 入案件主表前先清空已存在的记录
                 */
                parseStorageHistoryMapper.delete_buf_eaj(ajbs);
                /**
                 * 入案件主表数据
                 */
                int saveMainTableState = saveAjMainTableInfo(xmlMap, xmlContent,parseStorageHistoryMapper);
                if(saveMainTableState==1){
                    logger.info(ajbs+"->insert main table buf_eaj success!");
                }
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
                    List<ColMeta> colMetaList = parseStorageHistoryMapper.getColMeta(ajlxEname);
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
                    try{
                        /**
                         * delete history data before insert data to table
                         */
                        delete(xmlMap, ajlxEname,parseStorageHistoryMapper);
                    }catch(Exception e){
                        logger.error(e.getMessage());
                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                        resultMap.put(CodeUtil.RETURN_MSG, e.getMessage());
                        return resultMap;
                    }
                    /**
                     * 校验成功,入库操作
                     */
                    ConcurrentMap<String, Object> sqlMap = saveXml(xmlTableList, tableColMetaList, xmlContent,parseStorageHistoryMapper);
                    if(sqlMap.get(CodeUtil.RETURN_CODE).equals(CodeUtil.RETURN_SUCCESS)){
                        /**
                         * 处理成功提交事务
                         */
                        resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
                        resultMap.put(CodeUtil.RETURN_MSG, CodeUtil.RETURN_SUCCESS);
                    }else{
                        /**
                         * 处理失败回滚操作
                         */
                        return sqlMap;
                    }
                }
            }
            Long end = System.currentTimeMillis();
            resultMap.put("time", "耗时："+(end-start) +"毫秒");
            logger.info(ajbs+"案件耗时："+(end-start) +"毫秒");
            return resultMap;
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
            resultMap.put(CodeUtil.RETURN_MSG, e.getMessage());
            return resultMap;
        }finally{
            sgySqlSession.close();
        }
    }

    /**
     * @function 保存信息到案件主表
     * @author wenyuguang
     * @creaetime 2017年3月25日 下午9:15:57
     * @param xmlMap
     * @param xmlContent
     * @param parseStorageHistoryMapper 
     * @return 1-成功；0-失败；
     */
    private int saveAjMainTableInfo(Map<String, Object> xmlMap, XmlContent xmlContent, ParseStorageHistoryMapper parseStorageHistoryMapper) {
        int state=0;
        /**
         * 适应数据范围：
         * SGY.BUF_EXTERNAL2XML.ARJSOURCE in ('002','003','004','005')
         * SGY.BUF_EXTERNAL2XML.DESTSCHEMA = 'SGY'
         */
        if(xmlContent.getDESTSCHEMA().equalsIgnoreCase("SGY")
                &&(xmlContent.getAJSOURCE().equals("001")
                ||xmlContent.getAJSOURCE().equals("002")
                ||xmlContent.getAJSOURCE().equals("003")
                ||xmlContent.getAJSOURCE().equals("004")
                ||xmlContent.getAJSOURCE().equals("005"))){
            /**
             * 统一部份：
--                  AJBS                            BUF_EXTERNAL2XML.AJBS
--                  AJLX                            BUF_EXTERNAL2XML.AJLX
--                  DEPTCODE                        BUF_EXTERNAL2XML.FYDM
--                  REGDATE                         BUF_EXTERNAL2XML.LARQ
--                  LASTUPDATE                      BUF_EXTERNAL2XML.LASTUPDATE
--                  CONTENTUPD                      Null
--                  AH                              BUF_EXTERNAL2XML.AH
--                  AJJZJD                          BUF_EXTERNAL2XML.AJZT
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
                    ajlxMeta = parseStorageHistoryMapper.getAjlxMetaE(xmlContent.getAJLX());
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
                state = parseStorageHistoryMapper.insertTable_uf_eaj(mainTableParams);
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
     * @param parseStorageHistoryMapper 
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    private void delete(Map<String, Object> xmlMap, String ajlxEname, ParseStorageHistoryMapper parseStorageHistoryMapper) throws Exception {
        List<TableMeta> tableMetaList = parseStorageHistoryMapper.getTableMeta(ajlxEname);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) xmlMap.get(xmlMap.keySet().toString().replace("[", "").replace("]", "").trim());
        String midSql = "";
        List<Object> table0 = (List<Object>)mapList.get(0).get(mapList.get(0).keySet().toString().replace("[", "").replace("]", "").trim());
        List<Object> list = (List<Object>)table0.get(0);
        Map<String, Object> table =(Map<String, Object>)list.get(0);
        String ajbs=table.get("AJBS").toString();
        for(TableMeta tableMeta:tableMetaList){
            midSql = midSql+" delete from "+tableMeta.getcEtbname()+" where ajbs = "+ajbs+"; ";
        }
        String deleteSql = "begin "+midSql+" end;";
        /**
         * patch sql
         */
        parseStorageHistoryMapper.deleteTableRecord(deleteSql);
    }

    /**
     * @function xml入库
     * @author wenyuguang
     * @creaetime 2017年3月3日 上午1:00:49
     * @param xmlTableList
     * @param tableMetaList 
     * @param xmlContent 
     * @param parseStorageHistoryMapper 
     * @return
     */
    @SuppressWarnings("unchecked")
    private ConcurrentMap<String, Object> saveXml(List<Map<String, Object>> xmlTableList, 
                                              List<ColMeta> tableMetaList, XmlContent xmlContent, ParseStorageHistoryMapper parseStorageHistoryMapper) {
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
            }
            param.put("REG_TIME", dat);
            param.put("UPDATE_TIME", updateTime);
            param.put("APP_CODE", "001");
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
                        int result =0;
                        try {
                            param.put("insertSql", insertSql);
                            result = parseStorageHistoryMapper.saveCase(param);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage());
                            sqlMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
                            sqlMap.put(CodeUtil.RETURN_MSG, "unsuccess:"+e.getMessage());
                            isDo=false;
                            return sqlMap;
                        }
                        if(result==1){
                            logger.info("成功执行sql："+insertSql);
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
     * @function parse String type xml content
     * @author wenyuguang
     * @creaetime 2017-3-19 18:57:51
     * @param xml
     * @param parseStorageHistoryMapper 
     * @return 
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> parseXml(String xml, ParseStorageHistoryMapper parseStorageHistoryMapper) {
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
            ajlxMeta = parseStorageHistoryMapper.getAjlxMetaC(rootElement.getName().trim());
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
            tableMap.put(element.getName(), getTableContent(element.elements()));
            tablesList.add(tableMap);
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
