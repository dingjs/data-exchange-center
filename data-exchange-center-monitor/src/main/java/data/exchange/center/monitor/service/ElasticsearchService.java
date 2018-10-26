package data.exchange.center.monitor.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * Description:调用elasticsearch服务包接口
 * <p>Company: xinya </p>
 * <p>Date:2017年7月25日 下午4:02:30</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@FeignClient(value = "service-elasticsearch")
public interface ElasticsearchService {
	
	@RequestMapping(value = "/findBySystemName", method = RequestMethod.GET)
	public Object findBySystemName(
			@RequestParam("systemName")String systemName, 
			@RequestParam("pageNumber")int pageNumber, 
			@RequestParam("pageSize")int pageSize) ;
	
	/**
	 * 
	 * @function 根据案件标识获取处理跟踪信息
	 * @author wenyuguang
	 * @creaetime 2017年7月25日 下午4:02:10
	 * @param ajbs
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getCaseTrack", method = RequestMethod.GET)
	public Object getCaseTrack(
			@RequestParam("ajbs")      String ajbs,
			@RequestParam("pageNumber")int pageNumber,
			@RequestParam("pageSize")  int pageSize);
	
	/**
	 * 
	 * @function 根据ip，level，systemName，searchDate查询日志，至少一个参数不为空
	 * @author wenyuguang
	 * @creaetime 2017年7月25日 下午4:14:45
	 * @param ip
	 * @param level
	 * @param systemName
	 * @param searchDate
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findLog", method = RequestMethod.GET)
	public Object findLog(
			@RequestParam("ip")        String ip, 
			@RequestParam("level")     String level, 
			@RequestParam("systemName")String systemName, 
			@RequestParam("searchDate")String searchDate, 
			@RequestParam("pageNumber")int pageNumber, 
			@RequestParam("pageSize")  int pageSize);
}
