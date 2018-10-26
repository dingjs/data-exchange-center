package data.exchange.center.service.parse.ftpzip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.parse.ftpzip.domain.RetInfo;

@Mapper
public interface AjBindMapper {

	/**
	 * 查询汇总数量，是否有该条记录
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年1月30日 上午9:47:27
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	int queryCount(Map<String, String> paramMap)throws Exception;

	/**
	 * 
	 * @function 新增绑定案件
	 * @author wenyuguang
	 * @creaetime 2018年1月30日 上午9:51:06
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	int insertBindAjbs(Map<String, String> paramMap)throws Exception;

	/**
	 * 
	 * @function 删除绑定案件
	 * @author wenyuguang
	 * @creaetime 2018年1月30日 上午9:52:10
	 * @param paramMap
	 * @throws Exception
	 */
	void deleteBindAjbs(Map<String, String> paramMap)throws Exception;

	/**
	 * 
	 * @function 查询交换日志信息
	 * @author wenyuguang
	 * @creaetime 2018年2月27日 下午3:10:38
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	List<RetInfo> queryJhkLog(Map<String, String> paramMap)throws Exception;

	List<RetInfo> queryCaseCount(Map<String, String> paramMap)throws Exception;

}
