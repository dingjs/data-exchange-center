package data.exchange.center.ommp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.ommp.domain.Permission;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年4月20日 上午10:59:56</p>
 * @author Tony
 * @version 1.0
 *
 */
@Mapper
public interface PermissionMapper {

	/**
	 * 
	 * @function 
	 * @author Tony
	 * @creaetime 2018年4月20日 上午11:36:42
	 * @return
	 */
	public List<Permission> findAll();
}
