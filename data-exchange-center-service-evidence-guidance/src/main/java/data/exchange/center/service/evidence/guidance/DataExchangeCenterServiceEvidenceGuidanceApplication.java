package data.exchange.center.service.evidence.guidance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月27日 下午6:02:36</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableAdminServer
public class DataExchangeCenterServiceEvidenceGuidanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceEvidenceGuidanceApplication.class, args);
	}
}
