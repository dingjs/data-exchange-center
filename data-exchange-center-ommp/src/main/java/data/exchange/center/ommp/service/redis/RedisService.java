package data.exchange.center.ommp.service.redis;

import java.util.List;
import java.util.Map;

import data.exchange.center.ommp.domain.redis.Operate;
import data.exchange.center.ommp.domain.redis.RedisInfoDetail;

public interface RedisService {

	/**
	 * 
	 * @function 获取redis服务器信息
	 * @author Tony
	 * @creaetime 2018年4月20日 下午4:40:49
	 * @return
	 */
    public List<RedisInfoDetail> getRedisInfo();

    /**
     * 
     * @function 获取redis日志列表
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:02
     * @param entries
     * @return
     */
    public List<Operate> getLogs(long entries);
    /**
     * 
     * @function 获取日志总数
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:11
     * @return
     */
    public Long getLogLen();

    /**
     * 
     * @function 清空日志
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:17
     * @return
     */
    public String logEmpty();
    /**
     * 
     * @function 获取当前数据库中key的数量
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:22
     * @return
     */
    public Map<String,Object> getKeysSize();

    /**
     * 
     * @function 获取当前redis使用内存大小情况
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:28
     * @return
     */
    public Map<String,Object> getMemeryInfo();
}