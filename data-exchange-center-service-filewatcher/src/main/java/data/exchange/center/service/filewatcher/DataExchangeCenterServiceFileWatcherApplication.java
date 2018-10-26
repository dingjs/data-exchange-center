package data.exchange.center.service.filewatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import data.exchange.center.service.filewatcher.msg.LoggerMessage;
import data.exchange.center.service.filewatcher.msg.LoggerQueue;
import feign.Request;
import feign.Retryer;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月10日 上午11:22:11</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@EnableFeignClients
@ComponentScan(basePackages = { "data.exchange.center.service.filewatcher" })
@EnableEurekaClient
@SpringBootApplication
@EnableWebSocketMessageBroker
public class DataExchangeCenterServiceFileWatcherApplication {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(DataExchangeCenterServiceFileWatcherApplication.class, args);
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
	/**
	 * 推送日志到/topic/pullLogger
	 */
	@PostConstruct
	public void pushLogger(){
		ExecutorService executorService=Executors.newFixedThreadPool(2);
		Runnable runnable=new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						LoggerMessage log = LoggerQueue.getInstance().poll();
						if(log!=null){
							if(messagingTemplate!=null)
							messagingTemplate.convertAndSend("/topic/pullLogger",log);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		executorService.submit(runnable);
		executorService.submit(runnable);
	}
}
