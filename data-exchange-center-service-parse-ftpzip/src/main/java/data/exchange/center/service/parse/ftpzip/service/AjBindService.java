package data.exchange.center.service.parse.ftpzip.service;

import java.util.Map;

public interface AjBindService {

	/**
	 * 
	 * @function 案件标识绑定
	 * @author wenyuguang
	 * @creaetime 2018年1月25日 下午3:25:54
	 * @param jhbh 交换编号
	 * @param ptajbh 平台案件编号
	 * @param flag 状态信息  1-绑定 2-退回 0-登记
	 * @param ah 案号
	 * @param ajbs 案件标识
	 * @return
	 * @throws Exception
	 */
	Map<String, String> bindAjbs(String jhbh, String ptajbh, String ajbs, String flag)throws Exception;

	/**
	 * 
	 * @function 通过日期查询交换日志信息，供下面人员查看
	 * @author wenyuguang
	 * @creaetime 2018年2月27日 下午3:08:05
	 * @param startDate
	 * @param endDate
	 * @param wsbh 
	 * @param ajbh 
	 * @return
	 * @throws Exception
	 */
	Object queryJhkLog(String startDate, String endDate, String dsr, String ajbh, String wsbh)throws Exception;

	Object queryCaseCount(String jsdw)throws Exception;

}
