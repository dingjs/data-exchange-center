package data.exchange.center.ommp.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年4月18日 下午5:30:52</p>
 * @author Tony
 * @version 1.0
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Autowired  
//    DataSource dataSource;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home","/druid").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error")
                .permitAll()
                .and()
            .rememberMe()
            	.tokenValiditySeconds(60*60*2)//cookies俩小时过期
                .and()
            .logout()
            	.logoutSuccessUrl("/home")
            	.invalidateHttpSession(true)
//    			.addLogoutHandler(logoutHandler)
//    			.deleteCookies(cookieNamesToClear)
                .permitAll();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//             User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
    
//    @Autowired  
//    public void configAuthentication(AuthenticationManagerBuilder auth)   
//        throws Exception {  
//          
//        auth.jdbcAuthentication().dataSource(dataSource)  
//            .passwordEncoder(passwordEncoder())  
//            .usersByUsernameQuery("sql...")  
//            .authoritiesByUsernameQuery("sql...");  
//    }     
      
    @Bean  
    public PasswordEncoder passwordEncoder(){  
        return new BCryptPasswordEncoder();
    }  
}