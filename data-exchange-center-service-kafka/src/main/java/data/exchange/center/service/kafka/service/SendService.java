package data.exchange.center.service.kafka.service;

import data.exchange.center.common.log.LogObject;

public interface SendService {
	
	public void test();
	public void send(LogObject logObject) throws Exception;
}
