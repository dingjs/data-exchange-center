package data.exchange.center.ommp;

import java.util.List;

import data.exchange.center.ommp.domain.redis.RedisInfoDetail;
import data.exchange.center.ommp.service.redis.RedisService;
import data.exchange.center.ommp.service.redis.impl.RedisServiceImpl;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;

public class TestRedis {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("150.0.2.164");
		jedis.auth("sgyRedis");
		int keyNum = 0;
		//key数量
		for(int i=0;i<16; i++) {
			Client client = jedis.getClient();
			client.dbSize();
			keyNum +=client.getIntegerReply();
		}
		RedisService redisService = new RedisServiceImpl(jedis);
		List<RedisInfoDetail> list = redisService.getRedisInfo();
        System.out.println(list);
	}
}
