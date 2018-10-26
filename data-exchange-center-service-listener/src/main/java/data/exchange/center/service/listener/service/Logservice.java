package data.exchange.center.service.listener.service;

import org.springframework.cloud.netflix.feign.FeignClient;

import data.exchange.center.common.log.LoggerService;

@FeignClient(value="service-log")
public interface Logservice extends LoggerService {

}
