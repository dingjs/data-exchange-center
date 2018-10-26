package data.exchange.center.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import data.exchange.center.monitor.service.SysLogService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午11:21:50</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/syslog")
public class SysLogController {

	@Autowired
	protected SysLogService sysLogService;
//	@Autowired
//	EurekaService  eurekaService;

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void clear() {

	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
//		model.addAttribute("list", sysLogService.list());
		return "/syslog/list";
	}
	
//	@RequestMapping("/es")
//    @ResponseBody
//    public Map<String, Object> es() {
//    	Map<String, Object> map =null;
//    	try {
//    		map = eurekaService.getServiceInstance();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//        return map;
//    }
	@Autowired
	DiscoveryClient discoveryClient;

	/**
	 * (non-Javadoc)
	 * @see data.exchange.center.monitor.service.EurekaService#getServiceInstance()
	 */
//	@RequestMapping("/es")
	@ResponseBody
	public Map<String, Object> getServiceInstance() {
		Map<String, Object> serviceMap = new HashMap<String, Object>();
		List<String> serviceIdList = discoveryClient.getServices();
		for(String serviceId:serviceIdList) {
			List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
			List<Object> list = new ArrayList<Object>();
			for(ServiceInstance serviceInstance:serviceInstances) {
				list.add(serviceInstance);
			}
			serviceMap.put(serviceId,list);
		}
		System.out.println(serviceMap);
		return serviceMap;
	}
}
