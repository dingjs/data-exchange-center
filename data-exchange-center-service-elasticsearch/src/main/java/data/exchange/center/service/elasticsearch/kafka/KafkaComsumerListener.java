package data.exchange.center.service.elasticsearch.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import data.exchange.center.service.elasticsearch.domain.LogObject;
import data.exchange.center.service.elasticsearch.service.KafkaToEsService;

/**
 * 
 * Description:kafka消费接收端
 * <p>Company: xinya </p>
 * <p>Date:2017年7月4日 下午3:43:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Component
public class KafkaComsumerListener implements MessageListener<Object, Object>{

	private static Logger log = LoggerFactory.getLogger(KafkaComsumerListener.class); 
	
	@Autowired
	private KafkaToEsService kafkaToEsService;

    @Override
	@KafkaListener(topics = "logTopic")
	public void onMessage(ConsumerRecord<Object, Object> data) {

		Object obj = data.value();
		try {
        	LogObject logObject = (LogObject) JSON.parseObject(obj.toString(), LogObject.class);  
			kafkaToEsService.KafkaToEs(logObject);
		} catch (Exception e) {
			log.error("error to save log in elasticsearch :"+e.getMessage()+"content:"+obj);
		}
	}
}