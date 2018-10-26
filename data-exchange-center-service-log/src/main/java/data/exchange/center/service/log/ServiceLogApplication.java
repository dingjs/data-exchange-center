package data.exchange.center.service.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import data.exchange.center.service.log.idproduce.SnowflakeIdWorker;

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
@ComponentScan(basePackages = {"data.exchange.center.service.log"})
@EnableFeignClients
public class ServiceLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceLogApplication.class, args);
	}
	
	@Bean
	public SnowflakeIdWorker snowflakeIdWorker() {
		return new SnowflakeIdWorker(0,0);
	}
}
