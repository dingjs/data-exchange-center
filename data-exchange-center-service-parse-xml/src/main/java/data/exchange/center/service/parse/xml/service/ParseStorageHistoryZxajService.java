package data.exchange.center.service.parse.xml.service;

import java.util.Map;

/**
 * 
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年3月1日 下午1:04:27</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface ParseStorageHistoryZxajService {

    /**
     * 
     * @function 历史执行案件入中心库
     * @author wenyuguang
     * @creaetime 2017年3月29日 下午8:32:34
     * @param ajbs
     * @return
     * @throws Exception
     */
    Map<String, Object> parseStorageHistoryZxaj(String ajbs) throws Exception;
}
