package data.exchange.center.ommp.service.user;

import data.exchange.center.ommp.domain.SystemUser;

public interface UserService {

	/**
	 * 
	 * @function 根据用户名查找用户信息
	 * @author Tony
	 * @creaetime 2018年4月18日 下午5:31:40
	 * @param username
	 * @return
	 * @throws Exception
	 */
	SystemUser findUserByUserName(String username) throws Exception;

}
