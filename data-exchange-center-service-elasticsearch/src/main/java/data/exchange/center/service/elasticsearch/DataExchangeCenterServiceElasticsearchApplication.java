package data.exchange.center.service.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * Description:从kafka中获取数据回写到elasticsearch中去
 * <p>Company: xinya </p>
 * <p>Date:2017年7月4日 下午3:23:05</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"data.exchange.center.service.elasticsearch"})
@EnableDiscoveryClient
public class DataExchangeCenterServiceElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceElasticsearchApplication.class, args);
	}
}
