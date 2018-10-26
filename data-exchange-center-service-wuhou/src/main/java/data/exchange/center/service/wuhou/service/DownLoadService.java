package data.exchange.center.service.wuhou.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-download")
public interface DownLoadService {

	@RequestMapping("/download")
	public byte[] download(@RequestParam("key")String key);
}
