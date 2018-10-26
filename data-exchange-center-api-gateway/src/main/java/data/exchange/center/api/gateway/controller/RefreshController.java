package data.exchange.center.api.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.api.gateway.service.RefreshCacheService;

@RestController
public class RefreshController {

	@Autowired
	private RefreshCacheService refreshCacheService;
	
	/**
	 * 
	 * @function 刷新缓存
	 * @author Tony
	 * @creaetime 2018年6月12日 上午11:31:41
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("refreshCache")
	public Object refreshCache() throws Exception{
		return refreshCacheService.refreshCache();
	}
}
