package data.exchange.center.service.elasticsearch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.elasticsearch.dao.LogRepository;
import data.exchange.center.service.elasticsearch.domain.LogObject;
import data.exchange.center.service.elasticsearch.service.KafkaToEsService;

/**
 * 
 * Description:kafka往es中写数据
 * <p>Company: xinya </p>
 * <p>Date:2017年7月4日 下午3:51:39</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service("kafkaToEsService")
public class KafkaToEsServiceImpl implements KafkaToEsService {
	
	private static Logger log = LoggerFactory.getLogger(KafkaToEsServiceImpl.class);  
	
	@Autowired
	private LogRepository logRepository;
	
	@Override
	public boolean KafkaToEs(LogObject logObject) throws Exception {
		try{
			logRepository.save(logObject);
		}catch(Exception e){
			log.error("error to save log in elasticsearch :"+e.getMessage());
			return false;
		}
		return true;
	}
}
