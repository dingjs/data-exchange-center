package data.exchange.center.service.guangan.service;

import java.util.List;
import java.util.Map;

import data.exchange.center.service.guangan.domain.AjbsInfo;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年6月26日 下午4:21:30</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface GuanganService {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年6月26日 下午5:33:40
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<AjbsInfo> getUpdateAjbs(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年6月26日 下午6:15:54
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAjbsInfo(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function  获取审判主体
	 * @author wenyuguang
	 * @creaetime 2017年7月5日 上午11:33:38
	 * @param param
	 * @throws Exception
	 */
	public List<Object> getSpzt(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function 根据案号获取原审案件信息
	 * @author wenyuguang
	 * @creaetime 2017年7月5日 上午11:52:36
	 * @param param
	 * @throws Exception
	 */
	public List<Object> getYsajxx(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function 根据日期获取被删除的案件
	 * @author wenyuguang
	 * @creaetime 2017年7月7日 下午1:07:13
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<String> getAjscList(String date) throws Exception;
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月12日 上午10:10:55
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object> getJzxx(Map<String, Object> param) throws Exception;
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月12日 上午11:17:06
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Object> getZx(Map<String, Object> param) throws Exception;
}
