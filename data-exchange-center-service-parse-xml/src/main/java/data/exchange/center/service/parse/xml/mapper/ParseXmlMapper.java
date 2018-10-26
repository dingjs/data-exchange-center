package data.exchange.center.service.parse.xml.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import data.exchange.center.service.parse.xml.domain.XmlContent;

/**
 * <p>Title: GetXmlMapper.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年2月27日 下午8:25:50</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
@Repository
public interface ParseXmlMapper {

    /**
     * 
     * @function get xml content from db
     * @author wenyuguang
     * @param map 
     * @creaetime 2017年2月27日 下午8:24:10
     * @return
     * @throws Exception
     */
    public XmlContent getXML(@Param("map") Map<String, Object> map) throws Exception;
    
    /**
     * 
     * @function 执行拼装sql
     * @author wenyuguang
     * @param sql 
     * @param params 
     * @creaetime 2017年3月2日 下午11:15:35
     * @return
     * @throws Exception
     */
    public int saveCase(@Param("params") Map<String, Object> params) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年3月7日 上午11:50:24
     * @param deleteSql
     * @return
     */
    public int deleteTableRecord(@Param("deleteSql") String deleteSql);
}
