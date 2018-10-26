package data.exchange.center.service.parse.xml.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import data.exchange.center.service.parse.xml.domain.AjlxMeta;
import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.TableMeta;

/**
 * <p>Title: DataVerificationMapper.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月3日 上午12:10:43</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
@Repository
public interface GetTableMetaMapper {

    /**
     * @function 
     * @author wenyuguang
     * @param ajlxChName 
     * @creaetime 2017年3月3日 上午12:20:18
     * @return
     */
    public AjlxMeta getAjlxMeta(@Param("ajlxChName") String ajlxChName) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年3月4日 下午11:20:57
     * @param ajlxChName
     * @return
     */
    public List<TableMeta> getTableMeta(@Param("ajlxChName") String ajlxChName) throws Exception;

    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年3月5日 上午12:01:55
     * @param ajlxChName
     * @return
     * @throws Exception
     */
    public List<ColMeta> getColMeta(@Param("ajlxChName") String ajlxChName) throws Exception;
}
