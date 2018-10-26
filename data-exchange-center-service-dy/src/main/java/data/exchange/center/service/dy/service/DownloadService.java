package data.exchange.center.service.dy.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "service-download")
public interface DownloadService  {
    @RequestMapping(method = RequestMethod.GET, value = "/download")
    public byte[] download(@RequestParam("key")String key);
}
