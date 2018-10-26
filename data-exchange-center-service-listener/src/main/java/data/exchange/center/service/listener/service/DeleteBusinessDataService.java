package data.exchange.center.service.listener.service;

import java.util.Map;

//删除缓存表
public interface DeleteBusinessDataService {

	/**
	 * 
	 * @function 删除业务数据缓存表
	 * @author Tony
	 * @throws Exception 
	 * @creaetime 2018年3月22日 下午5:24:43
	 */
	public void delete_BUF_EXTERNAL2XML(String ajbs) throws Exception;

	/**
	 * 
	 * @function 删除案件删除缓存表
	 * @author Tony
	 * @throws Exception 
	 * @creaetime 2018年3月22日 下午5:24:43
	 */
	public void delete_BUF_AJSC2XML(String ajbs) throws Exception;

	/**
	 * 
	 * @function 删除组织机构缓存表
	 * @author Tony
	 * @throws Exception 
	 * @creaetime 2018年3月22日 下午5:24:43
	 */
	public void delete_BUF_ZZJGRY2XML(String ajbs) throws Exception;

	/**
	 * 
	 * @function 删除非结构化
	 * @author Tony
	 * @creaetime 2018年3月26日 下午5:20:01
	 * @param params
	 * @throws Exception
	 */
	public void delete_FJGH(Map<String, Object> params) throws Exception;
}
