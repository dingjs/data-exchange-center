package data.exchange.center.service.parse.ftpzip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月21日 下午2:32:10</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@ComponentScan("data.exchange.center.service.parse.ftpzip")
@SpringBootApplication
@EnableDiscoveryClient
public class ParseFtpzipApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParseFtpzipApplication.class, args);
	}
}
