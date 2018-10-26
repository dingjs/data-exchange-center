package data.exchange.center.service.pcaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataExchangeCenterServicePcajApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServicePcajApplication.class, args);
	}
}
