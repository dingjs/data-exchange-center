package data.exchange.center.service.guangan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年6月26日 下午4:21:37</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@MapperScan("data.exchange.center.service.guangan.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class DataExchangeCenterServiceGuanganApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceGuanganApplication.class, args);
	}
}
