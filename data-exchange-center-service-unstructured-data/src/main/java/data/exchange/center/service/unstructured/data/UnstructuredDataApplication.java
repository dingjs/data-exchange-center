package data.exchange.center.service.unstructured.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("data.exchange.center.service.unstructured.data.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class UnstructuredDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnstructuredDataApplication.class, args);
	}
}
