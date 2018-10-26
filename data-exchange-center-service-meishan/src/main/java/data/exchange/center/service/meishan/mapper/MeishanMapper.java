package data.exchange.center.service.meishan.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.meishan.domain.AjbsInfo;
import data.exchange.center.service.meishan.domain.WsInfo;
import data.exchange.center.service.meishan.domain.ZxajAjbsInfo;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年8月31日 上午10:04:13</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface MeishanMapper {

	/**
	 * 
	 * @function 获取民商事案件列表
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午5:24:30
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<AjbsInfo> getMssAjbsList(Map<String, Object> params) throws Exception;

	/**
	 * 
	 * @function 获取执行案件列表
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午5:35:48
	 * @param date
	 * @return
	 * @throws Exception
	 */
	List<AjbsInfo> getZxajAjbsList(Map<String, Object> params) throws Exception;

	/**
	 * 
	 * @function 获取执行案件的结构化信息
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午5:36:13
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
	ZxajAjbsInfo getAjbsInfo(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function 获取案件类型对应英文字符
	 * @author wenyuguang
	 * @creaetime 2017年9月13日 上午11:14:33
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	String getEnAjlx(String ajlx) throws Exception;

	/**
	 * 
	 * @function 获取文书
	 * @author wenyuguang
	 * @creaetime 2017年9月13日 上午11:45:11
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<WsInfo> getWsInfo(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月10日 下午4:11:53
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int getZxajAjbsCount(Map<String, Object> param) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月10日 下午4:11:56
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int getMssAjbsCount(Map<String, Object> param) throws Exception;
	
}
