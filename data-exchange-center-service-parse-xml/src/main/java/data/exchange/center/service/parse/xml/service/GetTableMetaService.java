package data.exchange.center.service.parse.xml.service;

import java.util.List;

import org.springframework.stereotype.Service;

import data.exchange.center.service.parse.xml.domain.AjlxMeta;
import data.exchange.center.service.parse.xml.domain.ColMeta;
import data.exchange.center.service.parse.xml.domain.TableMeta;

/**
 * <p>Title: DataVerification.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月3日 上午12:08:49</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
@Service
public interface GetTableMetaService {

    /**
     * 
     * @function 获取案件信息
     * @author wenyuguang
     * @param ajlxChName 
     * @creaetime 2017年3月3日 上午12:18:59
     * @return
     * @throws Exception
     */
    public AjlxMeta getAjlxMeta(String ajlxChName) throws Exception;
    
    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年3月4日 下午11:19:17
     * @param ajlxChName
     * @return
     * @throws Exception
     */
    public List<TableMeta> getTableMeta(String ajlxChName) throws Exception;

    /**
     * 
     * @function 根据ajlx获取表的col信息
     * @author wenyuguang
     * @creaetime 2017年3月5日 上午12:01:22
     * @param ajlxChName
     * @return
     * @throws Exception
     */
    public List<ColMeta> getColMeta(String ajlxChName) throws Exception;
}
