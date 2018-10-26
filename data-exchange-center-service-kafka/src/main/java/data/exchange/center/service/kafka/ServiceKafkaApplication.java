package data.exchange.center.service.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import data.exchange.center.service.kafka.idproduce.SnowflakeIdWorker;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年2月8日 下午4:59:40</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"data.exchange.center.service.kafka"})
public class ServiceKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceKafkaApplication.class, args);
	}
	
	@Bean
	public SnowflakeIdWorker snowflakeIdWorker() {
		return new SnowflakeIdWorker(0,0);
	}
}
