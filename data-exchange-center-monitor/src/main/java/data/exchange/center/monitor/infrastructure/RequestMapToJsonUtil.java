package data.exchange.center.monitor.infrastructure;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:54:03</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class RequestMapToJsonUtil {

    public static String toJson(Map<String, String[]> params) {
        @SuppressWarnings("unchecked")
		Map<String,String> dataMap=new HashedMap(params.size()-1);
        for(String key : params.keySet()) {
            if (key.equals("_csrf")) {
                continue;
            }
            dataMap.put(key,params.get(key)[0]);
        }
        return JSON.toJSONString(dataMap);
    }

}
