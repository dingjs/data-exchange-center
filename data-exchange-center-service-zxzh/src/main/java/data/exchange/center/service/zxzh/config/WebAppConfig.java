package data.exchange.center.service.zxzh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
	
	public void addInterceptors(InterceptorRegistry registry) {  
        //注册自定义拦截器，添加拦截路径
        InterceptorRegistration addInterceptor = registry.addInterceptor(new InterceptorConfig()); 
        addInterceptor.addPathPatterns("/**");
	} 
}
