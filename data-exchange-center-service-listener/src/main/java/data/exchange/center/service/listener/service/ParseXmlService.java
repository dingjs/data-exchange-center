package data.exchange.center.service.listener.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-parse-xml")
public interface ParseXmlService {

	/**
	 * 
	 * @function 业务数据同步更新
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 上午11:45:19
	 * @param ajbs
	 * @return
	 */
	@RequestMapping("/parseStorageTongdahai")
	public Map<String, Object> parseStorageTongdahai(@RequestParam("ajbs")String ajbs);
	
	/**
	 * 
	 * @function 审判主体信息更新
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 上午11:46:45
	 * @param ajbs
	 * @return
	 */
	@RequestMapping("/parseStorageSpzt")
	public Map<String, Object> parseStorageSPZT(@RequestParam("ajbs")String ajbs);
	
	/**
	 * 
	 * @function 案件删除
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 上午11:47:14
	 * @param ajbs
	 * @return
	 */
	@RequestMapping("/parseStorageSpztAjsc")
	public Map<String, Object> parseStorageSPZT_AJSC(@RequestParam("ajbs")String ajbs);
}
