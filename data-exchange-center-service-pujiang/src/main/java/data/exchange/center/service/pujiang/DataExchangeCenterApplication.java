package data.exchange.center.service.pujiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@MapperScan("data.exchange.center.service.pujiang.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DataExchangeCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterApplication.class, args);
	}
}
