package data.exchange.center.ommp.service.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.ommp.domain.SystemUser;
import data.exchange.center.ommp.mapper.UserMapper;
import data.exchange.center.ommp.service.user.UserService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年4月18日 下午5:30:57</p>
 * @author Tony
 * @version 1.0
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public SystemUser findUserByUserName(String username) throws Exception {
		
		try {
			SystemUser systemUser = userMapper.findUserByUserName(username);
			logger.info("查询用户信息 {}",systemUser.toString());
			return systemUser;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
}
