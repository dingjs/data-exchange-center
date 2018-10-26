package data.exchange.center.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import de.codecentric.boot.admin.config.EnableAdminServer;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月19日 上午10:53:41</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 *
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAdminServer
@EnableDiscoveryClient
@EnableFeignClients
public class MonitorApplication {

    public static void main(String[] arg){
        SpringApplication.run(MonitorApplication.class);
    }
}

