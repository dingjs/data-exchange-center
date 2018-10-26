package service.sjsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import service.sjsb.service.TaskService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年4月27日 上午9:34:45</p>
 * @author Tony
 * @version 1.0
 *
 */
@RestController
public class SjsbController {

	@Autowired
	private TaskService taskService;

	/**
	 * 
	 * @function 测试拉取昨天的数据
	 * @author Tony
	 * @creaetime 2018年4月28日 下午1:35:24
	 * @throws Exception
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() throws Exception {
		taskService.startTask();
	}
}
