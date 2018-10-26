package data.exchange.center.service.listener.service;

import java.util.List;
import java.util.Map;

public interface DataService {

	
	/**
	 * 
	 * @function 获取业务数据
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 下午3:46:47
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getBusinessData() throws Exception;
	
	/**
	 * 
	 * @function 获取审判主体数据
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 下午3:47:07
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getSpztData() throws Exception;

	/**
	 * 
	 * @function 从通达海抓数据
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 下午5:30:49
	 * @throws Exception
	 */
	void getDataFromTdh() throws Exception;
}
