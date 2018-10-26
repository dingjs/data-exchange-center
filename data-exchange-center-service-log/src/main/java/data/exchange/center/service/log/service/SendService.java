package data.exchange.center.service.log.service;

public interface SendService {
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年3月9日 下午4:59:51
	 * @param serviceName
	 * @param serviceId
	 * @param id
	 * @param ajbs
	 * @param lx
	 * @param fydm
	 * @param srccode
	 * @param msg
	 */
	public void logger(String serviceName, int serviceId, String id, String ajbs, String lx, String fydm,
			String srccode, String msg) throws Exception;

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2018年3月9日 下午5:11:47
	 * @param serviceName
	 * @param serviceId
	 * @param id
	 * @param ajbs
	 * @param lx
	 * @param fydm
	 * @param srccode
	 * @param msg
	 * @throws Exception
	 */
	public void deleteLog(String serviceName, String serviceId, String id, String ajbs, String lx, String fydm,
			String srccode, String msg) throws Exception;
}
