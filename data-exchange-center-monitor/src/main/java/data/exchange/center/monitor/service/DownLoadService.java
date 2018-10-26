package data.exchange.center.monitor.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年8月3日 下午5:00:32</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@FeignClient(value = "service-download")
public interface DownLoadService {

	@RequestMapping("/download")
	public byte[] download(@RequestParam("key")String key);
}
