package data.exchange.center.service.test.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Description:继承这个接口加上@FeignClient注解即可使用
 * 例子：
 @FeignClient(value="service-log")
 public interface Service extends LoggerService {
 
 }
 * <p>Company: xinya </p>
 * <p>Date:2018年3月12日 上午10:06:04</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@FeignClient(value="service-log")
public interface LoggerService {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @throws Exception 
	 * @creaetime 2018年2月7日 下午4:19:31
	 */
	@RequestMapping(value = "/logger", method ={RequestMethod.POST})
    @ResponseBody
	public void logger(
			@RequestParam("serviceName")String serviceName,
			@RequestParam("serviceId")  int serviceId,
			@RequestParam("id")         String id,
			@RequestParam("ajbs")       String ajbs,
			@RequestParam("lx")         String lx,
			@RequestParam("fydm")       String fydm,
			@RequestParam("srccode")    String srccode,
			@RequestParam("msg")        String msg) throws Exception;
	
	/**
	 * 
	 * @function 获取分布式id
	 * @author wenyuguang
	 * @creaetime 2018年2月8日 下午3:59:49
	 * @return
	 */
	@RequestMapping(value = "/getId", method ={RequestMethod.GET})
    @ResponseBody
	public long getId() throws Exception;
	
	/**
	 * 
	 * @function 删除日志
	 * @author wenyuguang
	 * @creaetime 2018年3月9日 下午5:10:12
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
	@RequestMapping(value = "/deleteLog", method ={RequestMethod.POST})
    @ResponseBody
	public void deleteLog(
			@RequestParam("serviceName")String serviceName,
			@RequestParam("serviceId")  String serviceId,
			@RequestParam("id")         String id,
			@RequestParam("ajbs")       String ajbs,
			@RequestParam("lx")         String lx,
			@RequestParam("fydm")       String fydm,
			@RequestParam("srccode")    String srccode,
			@RequestParam("msg")        String msg) throws Exception;
}
