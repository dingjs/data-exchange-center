package data.exchange.center.api.gateway.service;

import java.util.List;

import data.exchange.center.api.gateway.domain.RegisterInfo;
import data.exchange.center.api.gateway.domain.ServiceInfo;

public interface ApiGatewayService {

	/**
	 * 
	 * @function 根据服务名称获取鉴权信息
	 * @author wenyuguang
	 * @creaetime 2017年9月8日 下午6:11:15
	 * @param string
	 * @return
	 * @throws Exception
	 */
	RegisterInfo getTokenByServiceName(String serviceName) throws Exception;

	/**
	 * 
	 * @function 获取服务信息
	 * @author wenyuguang
	 * @creaetime 2017年9月14日 下午4:57:05
	 * @param string
	 * @return
	 * @throws Exception
	 */
	ServiceInfo getServiceInfoByServiceName(String serviceName) throws Exception;

	/**
	 * 
	 * @function 获取所有服务信息
	 * @author wenyuguang
	 * @creaetime 2017年9月15日 上午10:28:30
	 * @return
	 * @throws Exception
	 */
	List<ServiceInfo> getServiceInfo() throws Exception;

}
