package data.exchange.center.service.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import zipkin.server.EnableZipkinServer;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月3日 下午12:00:11</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@MapperScan("data.exchange.center.service.test.mapper")
@SpringBootApplication
@EnableZipkinServer
@EnableDiscoveryClient
@EnableFeignClients
//@EnableAdminServer
public class DataExchangeCenterServiceTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceTestApplication.class, args);
	}
}
