package data.exchange.center.ommp.util.redis;

import java.util.List;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.util.Slowlog;

public class RedisUtil {

	private Jedis jedis;

	public RedisUtil(Jedis jedis) {
		this.jedis = jedis;
	}
	
	// 获取redis 服务器信息
	public String getRedisInfo() {
		try {
			Client client = jedis.getClient();
			client.info();
			String info = client.getBulkReply();
			return info;
		} finally {
			jedis.close();
		}
	}

	// 获取日志列表
	public List<Slowlog> getLogs(long entries) {
		try {
			List<Slowlog> logList = jedis.slowlogGet(entries);
			return logList;
		} finally {
			jedis.close();
		}
	}

	// 获取日志条数
	public Long getLogsLen() {
		try {
			long logLen = jedis.slowlogLen();
			return logLen;
		} finally {
			jedis.close();
		}
	}

	// 清空日志
	public String logEmpty() {
		try {
			return jedis.slowlogReset();
		} finally {
			jedis.close();
		}
	}

	// 获取占用内存大小
	public Long dbSize() {
		try {
			// 配置redis服务信息
			Client client = jedis.getClient();
			client.dbSize();
			return client.getIntegerReply();
		} finally {
			jedis.close();
		}
	}
}