package data.exchange.center.service.parse.ftpzip.cachescheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import data.exchange.center.common.time.TimeUtils;
import data.exchange.center.service.parse.ftpzip.domain.RetInfo;
import data.exchange.center.service.parse.ftpzip.mapper.AjBindMapper;
import data.exchange.center.service.parse.ftpzip.util.Code;
import data.exchange.center.service.parse.ftpzip.util.SerializationUtil;

/**
 * 
 * Description: 定时查询数据
 * <p>Company: xinya </p>
 * <p>Date:2018年3月30日 下午5:30:18</p>
 * @author Tony
 * @version 1.0
 *
 */
@Component
public class CacheScheduler implements CommandLineRunner{
	
	private static Logger logger =   LoggerFactory.getLogger(CacheScheduler.class);
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	private AjBindMapper ajBindMapper;
	
	
	/**
 	 * 定时任务执行器
 	 */
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月17日 上午11:09:43
	 */
    @Override
	public void run(String... args) throws Exception {
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
            	System.out.println("开始缓存查询");
            	try {
            		RedisSerializer<?> stringSerializer = new StringRedisSerializer();
            	    redisTemplate.setKeySerializer(stringSerializer);
            	    redisTemplate.setHashKeySerializer(stringSerializer);
        			ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        			
        			Map<String, String> paramMap = new HashMap<>();
        			List<RetInfo> list = ajBindMapper.queryJhkLog(paramMap);
        			Map<String, Object> retMap = new HashMap<>();
        			retMap.put("data", list);
        			if(redisTemplate.hasKey(Code.TOTAL_CASE)) {
        				redisTemplate.delete(Code.TOTAL_CASE);
        			}
        			operations.set(Code.TOTAL_CASE, SerializationUtil.serialize(retMap));
        		} catch (Exception e) {
        			logger.error("案件查询结果保存到redis出错："+e.getMessage());
        			e.printStackTrace();
        		}
            }
        }, TimeUtils.FIVE_SECOND, TimeUtils.FIVE_SECOND, TimeUnit.MILLISECONDS);
	}
}
