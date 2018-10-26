package data.exchange.center.service.shehong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@MapperScan("data.exchange.center.service.wuhou.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ShehongApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShehongApplication.class, args);
	}
}
