package data.exchange.center.service.elasticsearch.service;

import data.exchange.center.service.elasticsearch.domain.LogObject;

public interface KafkaToEsService {

	/**
	 * 
	 * @function kafka往es中写数据
	 * @author wenyuguang
	 * @creaetime 2017年7月4日 下午3:52:36
	 * @return
	 * @throws Exception
	 */
	boolean KafkaToEs(LogObject logObject) throws Exception;
}
