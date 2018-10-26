package data.exchange.center.service.kafka.service.impl;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import data.exchange.center.common.log.LogObject;
import data.exchange.center.service.kafka.service.SendService;

@Service
public class SendServiceImpl implements SendService {

	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value(value = "${spring.kafka.template.default-topic}")
	private String topic;
	
	@Override
	public void send(LogObject logObject) throws Exception {
		kafkaTemplate.send(topic, JSON.toJSONString(logObject));

		kafkaTemplate.metrics();

		kafkaTemplate.execute(new KafkaOperations.ProducerCallback<String, String, Object>() {
			@Override
			public Object doInKafka(Producer<String, String> producer) {
				// 这里可以编写kafka原生的api操作
				return null;
			}
		});

		// 消息发送的监听器，用于回调返回信息
		kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
			@Override
			public void onSuccess(String topic, Integer partition, String key, String value,
					RecordMetadata recordMetadata) {

			}
			@Override
			public void onError(String topic, Integer partition, String key, String value, Exception exception) {

			}
			@Override
			public boolean isInterestedInSuccess() {
				return false;
			}
		});
	}
	
	public void test() {
		kafkaTemplate.send(topic, "test");

		kafkaTemplate.metrics();

		kafkaTemplate.execute(new KafkaOperations.ProducerCallback<String, String, Object>() {
			@Override
			public Object doInKafka(Producer<String, String> producer) {
				// 这里可以编写kafka原生的api操作
				return null;
			}
		});

		// 消息发送的监听器，用于回调返回信息
		kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
			@Override
			public void onSuccess(String topic, Integer partition, String key, String value,
					RecordMetadata recordMetadata) {

			}
			@Override
			public void onError(String topic, Integer partition, String key, String value, Exception exception) {

			}
			@Override
			public boolean isInterestedInSuccess() {
				return false;
			}
		});
	}

}
