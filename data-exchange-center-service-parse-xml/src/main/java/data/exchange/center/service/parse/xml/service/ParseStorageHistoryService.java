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
public interface ParseStorageHistoryService {

    /**
     * 
     * @function 华宇历史数据解析校验入库入库接口方法
     * @author wenyuguang
     * @creaetime 2017年3月11日 下午2:02:25
     * @param ajbs
     * @return
     * @throws Exception
     */
    Map<String, Object> parseStorageHistory(String ajbs) throws Exception;
}
