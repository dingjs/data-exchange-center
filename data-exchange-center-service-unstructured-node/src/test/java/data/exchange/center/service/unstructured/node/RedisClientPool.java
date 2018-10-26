package data.exchange.center.service.unstructured.node;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;  
import redis.clients.jedis.JedisPoolConfig;  
public class RedisClientPool {
	public static void main(String[] args) { 
	      //Connecting to Redis server on localhost 
	      Jedis jedis = new Jedis("150.0.2.164");
	      jedis.auth("sgyRedis");
	      System.out.println(jedis.get("300000000021255_EAJ_JZ")); 
	   }   
}
