package data.exchange.center.service.download;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月13日 下午2:48:31</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class DataExchangeCenterServiceDownloadApplication {

	@Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceDownloadApplication.class, args);
	}
}
