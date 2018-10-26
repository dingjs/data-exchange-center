package data.exchange.center.service.businessdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class DataExchangeCenterServiceScheduleBusinessdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceScheduleBusinessdataApplication.class, args);
	}
}
