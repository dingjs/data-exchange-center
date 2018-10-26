package data.exchange.center.api.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.api.gateway.domain.RegisterInfo;
import data.exchange.center.api.gateway.domain.ServiceInfo;
import data.exchange.center.api.gateway.mapper.ApiGatewayMapper;
import data.exchange.center.api.gateway.service.ApiGatewayService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年9月8日 下午5:36:25</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class ApiGatewayServiceImpl implements ApiGatewayService{

	@Autowired
	private ApiGatewayMapper apiGatewayMapper;
	
	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.api.gateway.service.ApiGatewayService#getTokenByServiceName(java.lang.String)
	 */
	@Override
	public RegisterInfo getTokenByServiceName(String serviceName) throws Exception {
		return apiGatewayMapper.getTokenByServiceName(serviceName);
	}

	@Override
	public ServiceInfo getServiceInfoByServiceName(String serviceName) throws Exception {
		return apiGatewayMapper.getServiceInfoByServiceName(serviceName);
	}

	@Override
	public List<ServiceInfo> getServiceInfo() throws Exception {
		return apiGatewayMapper.getServiceInfo();
	}
}
