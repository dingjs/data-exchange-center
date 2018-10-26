package data.exchange.center.service.parse.xml.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import data.exchange.center.service.parse.xml.domain.AjlxMeta;
import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.ScXmlContent;
import data.exchange.center.service.parse.xml.domain.SpztXmlContent;
import data.exchange.center.service.parse.xml.domain.TableMeta;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年3月18日 下午9:11:23</p>
 * @author Wen.Yuguang
 * @version 1.0 
 **/
public interface ParseStorageSPZTMapper {

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
     * @param tableName
     * @return
     * @throws Exception
     */
    List<TableMeta> getTableMeta(@Param("tableName") String tableName) throws Exception;

    /**
     * @function 获取xml
     * @author wenyuguang
     * @creaetime 2017年3月19日 下午9:41:37
     * @param map
     * @return
     */
    SpztXmlContent getXML(@Param("map")Map<String, Object> map) throws Exception;

    /**
     * @function 向案件删除信息表中插入内容
     * @author wenyuguang
     * @creaetime 2017年3月25日 下午11:59:35
     * @param mainTableParams
     * @return
     */
    int insert_AJSCXX_AJSCXX(@Param("map")Map<String, Object> params) throws Exception;

    /**
     * @function 根据ajbs删除案件记录
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午6:12:29
     * @param procParam
     */
    void deleteAjxxByAjbs(Map<String, Object> procParam) throws Exception;

    /**
     * @function 删除AJSCXX_AJSCXX表里面的记录
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午6:30:07
     * @param ajbs
     */
    void delete_AJSCXX_AJSCXX(String ajbs) throws Exception;

    /**
     * @function  删除SPZT_FYBM表里面的记录
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午6:54:17
     * @param ajbs
     */
    void delete_SPZT_FYBM(String ajbs) throws Exception;

    /**
     * @function 向SPZT_FYBM表里插入记录
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午6:56:31
     * @param params
     */
    int insert_SPZT_FYBM(@Param("map")Map<String, Object> params)  throws Exception;

    /**
     * @function 删除SPZT_ZZRY表里面的记录
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午6:58:13
     * @param ajbs
     */
    void delete_SPZT_ZZRY(String ajbs) throws Exception;

    /**
     * @function 向SPZT_ZZRY表里插入记录
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午6:58:43
     * @param params
     */
    int insert_SPZT_ZZRY(@Param("map")Map<String, Object> params)  throws Exception;

    /**
     * @function 删除SPZT_ZZJG表里面的记录
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午9:15:54
     * @param ajbs
     */
    void delete_SPZT_ZZJG(String ajbs) throws Exception;

    /**
     * @function 向SPZT_ZZJG表里插入记录
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午9:16:15
     * @param params
     * @return
     */
    int insert_SPZT_ZZJG(@Param("map")Map<String, Object> params) throws Exception;

    /**
     * @function 获取案件删除的xml
     * @author wenyuguang
     * @creaetime 2017年4月27日 下午10:27:04
     * @param map
     * @return
     */
    ScXmlContent getAJSCXML(@Param("map")Map<String, Object> map) throws Exception;

}
