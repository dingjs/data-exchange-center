package data.exchange.center.api.gateway.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.api.gateway.domain.RegisterInfo;
import data.exchange.center.api.gateway.domain.ServiceInfo;

@Mapper
public interface ApiGatewayMapper {

	/**
	 * 
	 * @function 根据服务名称获取鉴权信息
	 * @author wenyuguang
	 * @creaetime 2017年9月8日 下午6:12:57
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	RegisterInfo getTokenByServiceName(String serviceName) throws Exception;

	/**
	 * 
	 * @function 根据服务名获取服务信息
	 * @author wenyuguang
	 * @creaetime 2017年9月14日 下午5:11:32
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
	ServiceInfo getServiceInfoByServiceName(String serviceName) throws Exception;

	/**
	 * 
	 * @function 获取所有服务信息
	 * @author wenyuguang
	 * @creaetime 2017年9月15日 上午10:30:36
	 * @return
	 * @throws Exception
	 */
	List<ServiceInfo> getServiceInfo() throws Exception;
	
}
