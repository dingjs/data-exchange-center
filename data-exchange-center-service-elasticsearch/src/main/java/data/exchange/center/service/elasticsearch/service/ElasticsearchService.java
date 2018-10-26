package data.exchange.center.service.elasticsearch.service;

import java.util.List;

import data.exchange.center.service.elasticsearch.domain.LogObject;

public interface ElasticsearchService {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月6日 上午10:56:37
	 * @return
	 * @throws Exception
	 */
	public List<LogObject> findByLevel(String level, int pageNumber, int pageSize) throws Exception;
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月13日 下午3:00:12
	 * @param id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<LogObject> findById(String id,  int pageNumber, int pageSize) throws Exception;
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月13日 下午3:00:24
	 * @param systemName
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<LogObject> findBySystemName(String systemName, int pageNumber, int pageSize) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月13日 下午3:28:32
	 * @param systemName
	 * @throws Exception
	 */
	public void deleteBySystemName(String systemName) throws Exception;
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月13日 下午3:28:37
	 * @param id
	 * @throws Exception
	 */
	public void deleteById(String id) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月13日 下午3:28:35
	 * @param level
	 * @throws Exception
	 */
	public void deleteByLevel(String level) throws Exception;
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月24日 下午5:48:47
	 * @param ip
	 * @param level
	 * @param systemName
	 * @param searchDate
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Object findLog(String ip, String level, String systemName, String searchDate, int pageNumber, int pageSize);
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月25日 下午3:58:01
	 * @param ajbs
	 * @param pageSize 
	 * @param pageNumber 
	 * @return
	 */
	public Object getCaseTrack(String ajbs, int pageNumber, int pageSize);
}
