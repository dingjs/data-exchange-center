package data.exchange.center.service.listener.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {

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
	 * @function 从通达海抓取数据
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 下午5:35:01
	 * @throws Exception
	 */
	void getDataFromTdh() throws Exception;
}
