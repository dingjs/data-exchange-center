package data.exchange.center.service.sefon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月16日 上午11:44:37</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class DataExchangeCenterServiceFactorTrialApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceFactorTrialApplication.class, args);
	}
}
