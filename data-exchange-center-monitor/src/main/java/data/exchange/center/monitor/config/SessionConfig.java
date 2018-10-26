package data.exchange.center.monitor.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

/**
 * 
 * Description:由于这只是一个后台应用，没必要使用redis等其他存储来存储会话。如果你确实有需要可以自行变更
 * 这里使用Hazelcast存储会话,方便使用，不需要额外安装外部应用
 *
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:51:22</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = 45 * 60)
public class SessionConfig {

    @Bean
    public HazelcastInstance embeddedHazelcast() {
        Config hazelcastConfig = new Config();
        return Hazelcast.newHazelcastInstance(hazelcastConfig);
    }

}
