package data.exchange.center.service.meishan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年8月31日 上午10:13:14</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@MapperScan("data.exchange.center.service.meishan.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class DataExchangeCenterServiceMeishanApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceMeishanApplication.class, args);
	}
}
