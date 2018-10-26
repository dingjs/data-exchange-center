package data.exchange.center.service.parse.xml.service;

import java.util.Map;

/**
 * 
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年4月15日 下午10:50:21</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface ParseStorageSPZTService {

    /**
     * 
     * @function 审判组织数据入库
     * @author wenyuguang
     * @creaetime 2017年4月15日 下午10:50:38
     * @param ajbs
     * @return
     * @throws Exception
     */
    Map<String, Object> parseStorageSPZT(String ajbs) throws Exception;
    
    /**
     * 
     * @function 案件删除
     * @author wenyuguang
     * @creaetime 2017年4月15日 下午10:50:38
     * @param ajbs
     * @return
     * @throws Exception
     */
    Map<String, Object> parseStorageSPZT_AJSC(String ajbs) throws Exception;
}
