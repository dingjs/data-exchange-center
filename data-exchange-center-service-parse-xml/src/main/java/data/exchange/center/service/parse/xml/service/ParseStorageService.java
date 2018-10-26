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
public interface ParseStorageService {

    /**
     * 
     * @function 最高法数据解析校验入库接口方法
     * @author wenyuguang
     * @creaetime 2017年3月3日 下午10:12:31
     * @param ajbs
     * @return
     * @throws Exception
     */
    Map<String, Object> parseStorage(String ajbs) throws Exception;
}
