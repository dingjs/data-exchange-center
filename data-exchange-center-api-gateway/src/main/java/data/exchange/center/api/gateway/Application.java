package data.exchange.center.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import data.exchange.center.api.gateway.filter.AccessFilter;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年6月27日 下午5:56:58</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@EnableZuulProxy
@SpringCloudApplication
public class Application {

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}
	
//	@Bean
//	public ResponseFilterByWen responseFilterByWen() {
//		return new ResponseFilterByWen();
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
