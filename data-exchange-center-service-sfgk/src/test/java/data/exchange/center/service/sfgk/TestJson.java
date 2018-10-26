package data.exchange.center.service.sfgk;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;

public class TestJson {

	@Test
	public void testJson() {
		Map<String, Object> map = new HashMap<>();
		map.put("VERSION", "VERSION");
		map.put("FYBH",    "FYBH");
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("CODE", 200);
		retMap.put("MESSAGE", "成功");
		retMap.put("DATA", map);
		String json = JSON.toJSONString(retMap);
		System.out.println(json);
		
	}
	public static void main(String[] args) {
		String json = "\"MESSAGE\":\"成功\",\"DATA\":{\"URL\":\"kqvCNdpyD3NNZanmV2Df8wxyhTd7o3rD2Oju/7AiGmNf3DJR1C2Y+KkqM5RenIFNzWu5H+gd9SLmUwKNN5+l14MwhcLUDKWsSEdby/BChjJzchJL0QyZDzYV2phgqx48lmH88Czbf1nv8VGnqMzJuhdkwpaBN5ZiLDM45Lf5iuQ=\"},\"CODE\":200}";
		JSONObject jsonobject=JSONObject.fromObject(json);
		System.out.println(jsonobject);
	}
}
