package data.exchange.center.registry.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DataExchangeCenterRegistryEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterRegistryEurekaApplication.class, args);
	}
}
