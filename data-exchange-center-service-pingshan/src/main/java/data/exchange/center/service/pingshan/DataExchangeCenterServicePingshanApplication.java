package data.exchange.center.service.pingshan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataExchangeCenterServicePingshanApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServicePingshanApplication.class, args);
	}
}
