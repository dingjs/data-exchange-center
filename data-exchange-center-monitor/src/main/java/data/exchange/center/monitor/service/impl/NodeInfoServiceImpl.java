package data.exchange.center.monitor.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import data.exchange.center.monitor.service.NodeInfoService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月21日 上午9:37:14</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class NodeInfoServiceImpl implements NodeInfoService {

	@Autowired
	DiscoveryClient discoveryClient;

	/**
	 * (non-Javadoc)
	 * @see org.bumishi.admin.service.NodeInfoService#getAllServiceInfo()
	 */
	@Override
	public Object getAllServiceInfo() {
		List<Object> list = new LinkedList<>();
		List<String> serviceIdList = discoveryClient.getServices();
		for(String serviceId:serviceIdList) {
			List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
			for(ServiceInstance serviceInstance:serviceInstances) {
				list.add(serviceInstance);
			}
		}
		return list;
	}
}
