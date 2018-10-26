package data.exchange.center.service.guangan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.guangan.domain.AjbsInfo;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年6月26日 下午5:18:39</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface GuanganMapper {

	/**
	 * 
	 * @function  根据时间查询出已更新的ajbs
	 * @author wenyuguang
	 * @creaetime 2017年6月26日 下午5:26:05
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<AjbsInfo> getUpdateAjbs(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年6月26日 下午6:14:17
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Object> getAjbsInfo(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月5日 上午11:34:33
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Object> getSpzt(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月5日 上午11:53:37
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Object> getYsajxx(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月7日 下午1:07:51
	 * @param date
	 * @return
	 * @throws Exception
	 */
	List<String> getAjscList(String date) throws Exception;
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月12日 上午10:10:27
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Object> getJzxx(Map<String, Object> param) throws Exception;
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月12日 上午11:16:51
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Object> getZx(Map<String, Object> param) throws Exception;
}
