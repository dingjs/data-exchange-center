package data.exchange.center.service.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.log.service.IdService;
import data.exchange.center.service.log.service.SendService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年2月7日 下午4:21:52</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
public class LogController {

	@Autowired
	private SendService sendService;
	
	@Autowired
	private IdService idService;
	
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
			@RequestParam("msg")        String msg) throws Exception {
		sendService.logger(serviceName, serviceId, id, ajbs, lx, fydm, srccode, msg);
	}
	
	/**
	 * 
	 * @function 获取分布式id
	 * @author wenyuguang
	 * @creaetime 2018年2月8日 下午3:59:49
	 * @return
	 */
	@RequestMapping(value = "/getId", method ={RequestMethod.GET})
    @ResponseBody
	public long getId() throws Exception {
		return idService.IdWorker();
	}
	
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
			@RequestParam("msg")        String msg) throws Exception {
		sendService.deleteLog(serviceName, serviceId, id, ajbs, lx, fydm, srccode, msg);
	}
}
