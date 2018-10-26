package data.exchange.center.service.sfgk.service;

public interface TaskService {

	/**
	 * 
	 * @function 后台每晚十二点扫描一次进行司法公开信息xml打包
	 * @author Tony
	 * @throws Exception 
	 * @creaetime 2018年4月25日 下午4:37:43
	 */
	public void startTask() throws Exception;

	
}
