package data.exchange.center.service.parse.xml.service;

import java.util.Map;

public interface ParseStorageJxjsService {

	/**
	 * 
	 * @function 解析减刑假释案件
	 * @author Tony
	 * @creaetime 2018年6月4日 下午3:04:31
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
    Map<String, Object> parseStorageJxjs(String ajbs) throws Exception;
    /**
     * 
     * @function 减刑假释案件删除
     * @author Tony
     * @creaetime 2018年6月6日 下午4:48:22
     * @param ajbs
     * @param fydm
     * @param ajlx
     * @return
     */
	Map<String, Object> deleteJxjs(String ajbs, String fydm, String ajlx);
}
