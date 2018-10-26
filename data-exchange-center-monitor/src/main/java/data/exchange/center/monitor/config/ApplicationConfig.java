package data.exchange.center.monitor.config;

import org.bumishi.toolbox.qiniu.QiNiuApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:49:11</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Configuration
public class ApplicationConfig {

    @Value("${spring.application.qiniu.access-key}")
    private String qiniu_access_key;

    @Value("${spring.application.qiniu.securt-key}")
    private String qiniu_securt_key;

    @Value("${spring.application.qiniu.bucket}")
    private String qiniu_bucket;

    @Value("${spring.application.qiniu.domain}")
    private String qiniu_domain;//图片地址的访问域名

    @Bean
    public QiNiuApi qiNiuApi() {
        return new QiNiuApi(qiniu_access_key, qiniu_securt_key);
    }

    public String getQiniu_bucket() {
        return qiniu_bucket;
    }

    public String getQiniu_domain() {
        return qiniu_domain;
    }
}
