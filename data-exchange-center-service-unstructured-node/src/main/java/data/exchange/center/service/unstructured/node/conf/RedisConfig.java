package data.exchange.center.service.unstructured.node.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * Description:全局redis锁配置中心
 * <p>Company: xinya </p>
 * <p>Date:2017年11月29日 上午10:24:18</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
public class RedisConfig {

	@Bean(name = "sgyRedisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(
			@Value("${sgy.redis.hostName}")
			String hostName,
			@Value("${sgy.redis.port}")
			int port,
			@Value("${sgy.redis.password}")
			String password,
			@Value("${sgy.redis.maxIdle}")
			int maxIdle,
			@Value("${sgy.redis.maxTotal}")
			int maxTotal,
			@Value("${sgy.redis.index}")
			int index,
			@Value("${sgy.redis.maxWaitMillis}")
			long maxWaitMillis,
			@Value("${sgy.redis.testOnBorrow}")
			boolean testOnBorrow) {
		RedisTemplate<Object, Object> temple = new RedisTemplate<Object, Object>();
		temple.setConnectionFactory(
				connectionFactory(hostName, port, password, maxIdle, maxTotal, index, maxWaitMillis, testOnBorrow));
		temple.setKeySerializer(new StringRedisSerializer());
		temple.setHashKeySerializer(new StringRedisSerializer());
		return temple;
	}

	public RedisConnectionFactory connectionFactory(String hostName, int port, String password, int maxIdle,
			int maxTotal, int index, long maxWaitMillis, boolean testOnBorrow) {
		JedisConnectionFactory jedis = new JedisConnectionFactory();
		jedis.setHostName(hostName);
		jedis.setPort(port);
		if (!StringUtils.isEmpty(password)) {
			jedis.setPassword(password);
		}
		if (index != 0) {
			jedis.setDatabase(index);
		}
		jedis.setPoolConfig(poolCofig(maxIdle, maxTotal, maxWaitMillis, testOnBorrow));
		// 初始化连接pool
		jedis.afterPropertiesSet();
		RedisConnectionFactory factory = jedis;
		return factory;
	}

	public JedisPoolConfig poolCofig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
		JedisPoolConfig poolCofig = new JedisPoolConfig();
		poolCofig.setMaxIdle(maxIdle);
		poolCofig.setMaxTotal(maxTotal);
		poolCofig.setMaxWaitMillis(maxWaitMillis);
		poolCofig.setTestOnBorrow(testOnBorrow);
		return poolCofig;
	}
}