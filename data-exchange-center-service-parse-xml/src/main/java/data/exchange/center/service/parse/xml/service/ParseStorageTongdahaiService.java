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
public interface ParseStorageTongdahaiService {

    /**
     * 
     * @function 通达海方提供的xml解析校验入库接口方法
     * @author wenyuguang
     * @creaetime 2017年3月17日 下午9:18:17
     * @param ajbs
     * @return
     * @throws Exception
     */
    Map<String, Object> parseStorageTongdahai(String ajbs) throws Exception;
}
