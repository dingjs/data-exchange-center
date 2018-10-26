package data.exchange.center.api.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import data.exchange.center.api.gateway.domain.RegisterInfo;
import data.exchange.center.api.gateway.domain.ServiceInfo;
import data.exchange.center.api.gateway.service.ApiGatewayService;
import data.exchange.center.api.gateway.service.RefreshCacheService;
import data.exchange.center.api.gateway.util.RedisUtil;

@Service
public class RefreshCacheServiceImpl implements RefreshCacheService {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
    private ApiGatewayService apiGatewayService;
	
	@Override
	public Object refreshCache() throws Exception {
		redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
		List<ServiceInfo> serviceInfoList = apiGatewayService.getServiceInfo();
		valueOperations.set(RedisUtil.SERVICE_LIST, serviceInfoList);
		for(ServiceInfo serviceInfo:serviceInfoList) {
			RegisterInfo registerInfo = apiGatewayService.getTokenByServiceName(serviceInfo.getcSrvename());
			valueOperations.set(String.format(RedisUtil.REGISTER_INFO, serviceInfo.getcSrvename()), registerInfo);
		}
		return "ok";
	}
}
