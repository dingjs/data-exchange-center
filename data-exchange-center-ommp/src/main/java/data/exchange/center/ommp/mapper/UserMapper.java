package data.exchange.center.ommp.mapper;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.ommp.domain.SystemUser;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年4月18日 下午5:30:47</p>
 * @author Tony
 * @version 1.0
 *
 */
@Mapper
public interface UserMapper {

	SystemUser findUserByUserName(String username) throws Exception;

}
