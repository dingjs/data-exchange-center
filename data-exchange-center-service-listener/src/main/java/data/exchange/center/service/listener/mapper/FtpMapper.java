package data.exchange.center.service.listener.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FtpMapper {

	/**
	 * 
	 * @function 查询有多少ftp
	 * @author Tony
	 * @creaetime 2018年4月19日 下午3:56:32
	 * @param map
	 * @return
	 */
	public int selectCount(Map<String, String> map);
	/**
	 * 
	 * @function 增加新ftp
	 * @author Tony
	 * @creaetime 2018年4月19日 下午3:56:46
	 * @param map
	 * @return
	 */
	public int addNewFtp(Map<String, String> map);
	/**
	 * 
	 * @function 更新ftp
	 * @author Tony
	 * @creaetime 2018年4月19日 下午3:56:54
	 * @param map
	 * @return
	 */
	public int updateFtp(Map<String, String> map);
	/**
	 * 
	 * @function 查询有多少更新的ftp待确认处理编码字符格式
	 * @author Tony
	 * @creaetime 2018年4月19日 下午5:00:20
	 * @return
	 */
	public int getUpdateCount();
	/**
	 * 
	 * @function 插入回收表
	 * @author Tony
	 * @creaetime 2018年5月9日 上午11:52:32
	 * @param params
	 */
	public void addRecoveryAjbs(Map<String, String> params);
}
