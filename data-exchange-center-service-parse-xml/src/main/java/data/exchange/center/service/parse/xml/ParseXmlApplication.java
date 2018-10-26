package data.exchange.center.service.parse.xml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import feign.Request;
import feign.Retryer;

@MapperScan("data.exchange.center.service.parse.xml.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class ParseXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParseXmlApplication.class, args);
	}
	/**
	 * 
	 * @function feign取消重试
	 * @author wenyuguang
	 * @creaetime 2017年11月7日 下午1:05:15
	 * @return
	 */
	@Bean
	Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}
	
	/**
	 * 
	 * @function feign请求超时设置
	 * @author wenyuguang
	 * @creaetime 2017年11月7日 下午1:05:03
	 * @param env
	 * @return
	 */
	@Bean
	Request.Options requestOptions(ConfigurableEnvironment env) {
		int ribbonReadTimeout = env.getProperty("ribbon.ReadTimeout", int.class, 30000000);
		int ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", int.class, 30000);

		return new Request.Options(ribbonConnectionTimeout, ribbonReadTimeout);
	}
}
