package data.exchange.center.service.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.common.log.LogObject;
import data.exchange.center.service.kafka.service.IdService;
import data.exchange.center.service.kafka.service.SendService;

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
public class KafkaController {

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
	@RequestMapping(value = "/sendMessage", method ={RequestMethod.POST})
    @ResponseBody
	public void sendMessage(@RequestBody LogObject logObject) throws Exception {
		sendService.send(logObject);
	}
	
	@RequestMapping(value = "/test", method ={RequestMethod.POST})
    @ResponseBody
	public void test() throws Exception {
		sendService.test();
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
}
