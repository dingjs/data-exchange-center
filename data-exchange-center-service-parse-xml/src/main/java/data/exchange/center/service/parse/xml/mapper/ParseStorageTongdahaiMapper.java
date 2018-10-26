package data.exchange.center.service.parse.xml.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import data.exchange.center.service.parse.xml.domain.AjlxMeta;
import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.TableMeta;
import data.exchange.center.service.parse.xml.domain.XmlContent;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年3月18日 下午9:11:23</p>
 * @author Wen.Yuguang
 * @version 1.0 
 **/
public interface ParseStorageTongdahaiMapper {

    /**
     * 
     * @function 获取案件类型元数据信息
     * @author wenyuguang
     * @creaetime 2017年3月18日 下午9:18:50
     * @param ajlxCname 案件类型中文名
     * @return
     * @throws Exception
     */
    AjlxMeta getAjlxMetaC(@Param("ajlxCname")String ajlxCname) throws Exception;
    
    /**
     * 
     * @function 获取案件类型元数据信息
     * @author wenyuguang
     * @creaetime 2017年3月18日 下午9:18:50
     * @param ajlxCname 案件类型字符代码
     * @return
     * @throws Exception
     */
    AjlxMeta getAjlxMetaE(@Param("ajlxEname")String ajlxCname) throws Exception;

    /**
     * @function 获取列元数据
     * @author wenyuguang
     * @creaetime 2017年3月18日 下午9:55:19
     * @param ajlxEname 英文加数字案件类型
     * @return
     */
    List<ColMeta> getColMeta(@Param("ajlxEname")String ajlxEname) throws Exception;
    
    /**
     * 
     * @function 获取表元数据
     * @author wenyuguang
     * @creaetime 2017年3月19日 下午7:15:27
     * @param ajlxChName
     * @return
     * @throws Exception
     */
    List<TableMeta> getTableMeta(@Param("ajlxEname") String ajlxEname) throws Exception;

    /**
     * @function 获取xml
     * @author wenyuguang
     * @creaetime 2017年3月19日 下午9:41:37
     * @param map
     * @return
     */
    XmlContent getXML(@Param("map")Map<String, Object> map) throws Exception;

    /**
     * @function 向案件主表中插入内容
     * @author wenyuguang
     * @creaetime 2017年3月25日 下午11:59:35
     * @param mainTableParams
     * @return
     */
    int insertTable_uf_eaj(@Param("map")Map<String, Object> mainTableParams) throws Exception;

    /**
     * @function 删除案件主表中已存在的ajbs记录
     * @author wenyuguang
     * @creaetime 2017年3月26日 下午10:49:45
     * @param ajbs
     */
    void delete_buf_eaj(@Param("ajbs")String ajbs) throws Exception;
}
