package data.exchange.center.ommp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import data.exchange.center.ommp.domain.SystemUser;
import data.exchange.center.ommp.service.user.UserService;

@Component
public class SystemUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

//	@Autowired
//    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("用户的用户名: {}", username);
		// 根据用户名，查找到对应的密码，与权限
		SystemUser systemUser = null;
		try {
			systemUser = userService.findUserByUserName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String password = passwordEncoder.encode("123456");
//        logger.info("password: {}", password);
		// 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        User user = new User(
        		username, 
        		systemUser.getPassword(), 
        		AuthorityUtils.commaSeparatedStringToAuthorityList(systemUser.getRole()));
//		User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		return user;
	}
}