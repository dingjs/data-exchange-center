package data.exchange.center.ommp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.ommp.service.redis.RedisService;

@RestController
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	/**
	 * 
	 * @function 获取redis服务器信息
	 * @author Tony
	 * @creaetime 2018年4月20日 下午4:40:49
	 * @return
	 */
    public Map<String,Object> getRedisInfo(){
    	Map<String,Object> retMap = new HashMap<String, Object>();
    	try {
    		retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
    		retMap.put(CodeUtil.RETURN_MSG, redisService.getRedisInfo());
    		return retMap;
		} catch (Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
    		retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
    		return retMap;
		}
    }

    /**
     * 
     * @function 获取redis日志列表
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:02
     * @param entries
     * @return
     */
    public Map<String,Object> getLogs(@RequestParam("entries")long entries){
    	Map<String,Object> retMap = new HashMap<String, Object>();
    	try {
    		retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
    		retMap.put(CodeUtil.RETURN_MSG, redisService.getLogs(entries));
    		return retMap;
		} catch (Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
    		retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
    		return retMap;
		}
    }
    /**
     * 
     * @function 获取日志总数
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:11
     * @return
     */
    public Map<String,Object> getLogLen(){
    	Map<String,Object> retMap = new HashMap<String, Object>();
    	try {
    		retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
    		retMap.put(CodeUtil.RETURN_MSG, redisService.getLogLen());
    		return retMap;
		} catch (Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
    		retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
    		return retMap;
		}
    }

    /**
     * 
     * @function 清空日志
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:17
     * @return
     */
    public Map<String,Object> logEmpty(){
    	Map<String,Object> retMap = new HashMap<String, Object>();
    	try {
    		retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
    		retMap.put(CodeUtil.RETURN_MSG, redisService.logEmpty());
    		return retMap;
		} catch (Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
    		retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
    		return retMap;
		}
    }
    /**
     * 
     * @function 获取当前数据库中key的数量
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:22
     * @return
     */
    public Map<String,Object> getKeysSize(){
    	Map<String,Object> retMap = new HashMap<String, Object>();
    	try {
    		retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
    		retMap.put(CodeUtil.RETURN_MSG, redisService.getKeysSize());
    		return retMap;
		} catch (Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
    		retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
    		return retMap;
		}
    }

    /**
     * 
     * @function 获取当前redis使用内存大小情况
     * @author Tony
     * @creaetime 2018年4月20日 下午4:41:28
     * @return
     */
    public Map<String,Object> getMemeryInfo(){
    	Map<String,Object> retMap = new HashMap<String, Object>();
    	try {
    		retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_SUCCESS);
    		retMap.put(CodeUtil.RETURN_MSG, redisService.getMemeryInfo());
    		return retMap;
		} catch (Exception e) {
			retMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
    		retMap.put(CodeUtil.RETURN_MSG, e.getMessage());
    		return retMap;
		}
    }
}
