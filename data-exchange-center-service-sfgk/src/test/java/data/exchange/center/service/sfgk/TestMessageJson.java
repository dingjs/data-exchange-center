package data.exchange.center.service.sfgk;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class TestMessageJson {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("ajbs", "test");
		map.put("fydm", "510000");
		map.put("ajlx", "12");
		System.out.println(JSON.toJSONString(map));
	}
}
