package data.exchange.center.service.listener;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import feign.Request;
import feign.Retryer;


@ComponentScan(basePackages = {"data.exchange.center.service.listener"})
@MapperScan("data.exchange.center.service.listener.mapper")
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ServiceListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceListenerApplication.class, args);
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
		int ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", int.class, 3000000);

		return new Request.Options(ribbonConnectionTimeout, ribbonReadTimeout);
	}
}
