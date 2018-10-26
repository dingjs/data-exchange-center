package data.exchange.center.service.unstructured.node.service.impl;

import org.springframework.cloud.netflix.feign.FeignClient;

import data.exchange.center.common.log.LoggerService;

@FeignClient(value="service-log")
public interface LogService extends LoggerService {
    
}
