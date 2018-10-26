package data.exchange.center.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:49:17</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
public class BlogConfig {

    @Value("${blog.host}")
    private String blogHost;

    public String getBlogHost() {
        return blogHost;
    }
}
