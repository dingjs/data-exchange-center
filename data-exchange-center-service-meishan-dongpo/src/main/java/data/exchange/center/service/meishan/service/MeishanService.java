package data.exchange.center.service.meishan.service;

import java.util.List;
import java.util.Map;

import data.exchange.center.service.meishan.domain.AjbsInfo;

public interface MeishanService {

	/**
	 * 
	 * @function 获取民商事案件标识列表
	 * @author wenyuguang
	 * @param date  根据日期来
	 * @param endDate 
	 * @param pageNum 
	 * @creaetime 2017年9月11日 下午3:58:16
	 * @return
	 * @throws Exception
	 */
	List<AjbsInfo> getMssAjbsList(String date, String endDate, String pageNum) throws Exception;

	/**
	 * 
	 * @function 根据ajbs获取民商事对应结构化数据信息
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午4:01:38
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getMssAjbsInfo(String ajbs) throws Exception;

	/**
	 * 
	 * @function 获取执行事案件标识列表
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午4:02:49
	 * @param date
	 * @param endDate 
	 * @return
	 * @throws Exception
	 */
	List<AjbsInfo> getZxajAjbsList(String date, String endDate, String pageNum) throws Exception;

	/**
	 * 
	 * @function 根据ajbs获取执行对应结构化数据信息
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午4:01:38
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getZxajAjbsInfo(String ajbs) throws Exception;

	/**
	 * 
	 * @function 获取非结构化数据
	 * @author wenyuguang
	 * @creaetime 2017年9月13日 上午11:13:18
	 * @param ajbs
	 * @param fydm
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getWsInfo(String ajbs, String fydm, String ajlx) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月10日 下午4:06:09
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	Object getZxajAjbsCount(String startDate, String endDate) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月10日 下午4:06:12
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	Object getMssAjbsCount(String startDate, String endDate) throws Exception;
}
