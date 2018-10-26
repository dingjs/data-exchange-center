package data.exchange.center.api.gateway.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import data.exchange.center.api.gateway.domain.RegisterInfo;
import data.exchange.center.api.gateway.domain.ServiceInfo;
import data.exchange.center.api.gateway.service.RefreshCacheService;
import data.exchange.center.api.gateway.util.NetUtils;
import data.exchange.center.api.gateway.util.RedisUtil;
import data.exchange.center.api.gateway.util.StrUtil;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年6月27日 下午5:56:51</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class AccessFilter extends ZuulFilter  {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
	private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RefreshCacheService           refreshCacheService;
    
    /**
     * (non-Javadoc)
     * @see com.netflix.zuul.ZuulFilter#filterType()
     * 返回一个字符串代表过滤器的类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * (non-Javadoc)
     * @see com.netflix.zuul.ZuulFilter#filterOrder()
     * 通过int值来定义过滤器的执行顺序
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * (non-Javadoc)
     * @see com.netflix.zuul.IZuulFilter#shouldFilter()
     * 返回一个boolean类型来判断该过滤器是否要执行
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * (non-Javadoc)
     * @see com.netflix.zuul.IZuulFilter#run()
     * 过滤器的具体逻辑
     */
    @Override
    public Object run() {
    	RequestContext ctx = RequestContext.getCurrentContext();
    	ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
    	redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    	try{
            HttpServletRequest request = ctx.getRequest();
            request.setAttribute(StrUtil.INVOKE_TIME, System.currentTimeMillis());

            /**
             * 请求url
             */
            String url = request.getRequestURI().toLowerCase();
            /**
             * 请求ip
             */
            String ip = NetUtils.getIpAddress(request);
            logger.info(String.format(ip+ request.getMethod()+" request to "+request.getRequestURL().toString()));
            if(!redisTemplate.hasKey(RedisUtil.SERVICE_LIST)) {
            	logger.info("未命中缓存，开始缓存");
            	refreshCacheService.refreshCache();
            }
            @SuppressWarnings("unchecked")
			List<ServiceInfo> serviceInfoList = (List<ServiceInfo>) valueOperations.get(RedisUtil.SERVICE_LIST);
//            List<ServiceInfo> serviceInfoList = apiGatewayService.getServiceInfo();
            /**
             * 鉴权token
             */
            String accessToken = null;
            try {
            	accessToken = request.getParameter("accessToken").toString();
            }catch(Exception e) {
            	//抛异常一般都说明没有accessToken参数，这里需要对全开放的服务进行处理让其可以正常访问2018年5月18日10:16:14
            	for(ServiceInfo serviceInfo:serviceInfoList) {
            		String service_name = serviceInfo.getcSrvename();
            		if(url.contains(service_name)){
            			/**
                    	 * 服务是否为开启状态
                    	 */
                    	if(serviceInfo.getcEnable().equalsIgnoreCase("enable")) {
                    		RegisterInfo registerInfo = (RegisterInfo) valueOperations.get(String.format(RedisUtil.REGISTER_INFO, serviceInfo.getcSrvename()));
                    		
//                    		RegisterInfo registerInfo = apiGatewayService.getTokenByServiceName(service_name);
                    		if(registerInfo.getcToken().equals("*")){
                    			return null;
                    		}
                    	}
            		}
            	}
            	logger.error("api-gateway error: "+e.toString());
        		ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody("request error: accessToken is "+e.toString());
        		return null;
            }
            
            
            for(ServiceInfo serviceInfo:serviceInfoList) {
            	String service_name = serviceInfo.getcSrvename();
            	/**
            	 * 对url鉴权
            	 */
            	if(url.contains(service_name)){
            		/**
                	 * 服务是否为开启状态
                	 */
                	if(serviceInfo.getcEnable().equalsIgnoreCase("enable")) {
                		RegisterInfo registerInfo = (RegisterInfo) valueOperations.get(String.format(RedisUtil.REGISTER_INFO, serviceInfo.getcSrvename()));
                		
//                		RegisterInfo registerInfo = apiGatewayService.getTokenByServiceName(service_name);
                		/**
                    	 * 验证token
                    	 */
                    	if(accessToken.equalsIgnoreCase(registerInfo.getcToken())){
                    		/**
                    		 * 验证ip  数据库中多个ip用英文逗号隔开
                    		 */
                    		if(registerInfo.getcInnerip().contains(",")) {
                    			String[] accessIps = registerInfo.getcInnerip().split(",");
                    			boolean isHasLeve = false;
                    			for(String accessIp:accessIps) {
                    				if(accessIp.equals(ip)) {
                    					isHasLeve = true;
                    					return null;
                    				}
                    			}
                    			if(!isHasLeve) {
                    				logger.error(service_name.concat(" access ip is not allowed, please contact the administrator"));
                                    ctx.setSendZuulResponse(false);
                                    ctx.setResponseStatusCode(401);
                                    ctx.setResponseBody(service_name.concat(" access ip is not allowed, please contact the administrator"));
                                    return null;
                    			}
                    		}else {
                    			if(registerInfo.getcInnerip().equals("*")) {
                        			return null;
                        		}else if(registerInfo.getcInnerip().equals(ip)) {
                        			return null;
                        		}else {
                        			logger.error(service_name.concat(" access ip is not allowed, please contact the administrator"));
                                    ctx.setSendZuulResponse(false);
                                    ctx.setResponseStatusCode(401);
                                    ctx.setResponseBody(service_name.concat(" access ip is not allowed, please contact the administrator"));
                                    return null;
                        		}
                    		}
                    	}else{
                    		logger.error(service_name.concat(" access token is not correct, please contact the administrator"));
                            ctx.setSendZuulResponse(false);
                            ctx.setResponseStatusCode(401);
                            ctx.setResponseBody(service_name.concat(" access token is not correct, please contact the administrator"));
                            return null;
                    	}
                	}else {
                		/**
                		 * 服务disabled状态 关闭
                		 */
                		logger.error(service_name.concat(" is disabled, please contact the administrator "));
                        ctx.setSendZuulResponse(false);
                        ctx.setResponseStatusCode(401);
                        ctx.setResponseBody(service_name.concat(" is disabled, please contact the administrator "));
                        return null;
                	}
            	}
            }
            return null;
    	}catch(Exception e){
    		logger.error("api-gateway error: "+e.toString());
    		ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("request error: "+e.toString());
    		return null;
    	}
    }
}