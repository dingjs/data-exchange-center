package data.exchange.center.api.gateway;

import data.exchange.center.api.gateway.util.RedisUtil;

public class StrTest {

	static String str = "test";
	public static void main(String[] args) {
		System.out.println(str.concat(" 连接起来"));
		System.out.println(String.format(RedisUtil.REGISTER_INFO, "service_guangan"));
	}
}
