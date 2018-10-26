package data.exchange.center.service.filewatcher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.filewatcher.service.FileWatcherService;

@RestController
public class FileWatcherController {

	@Autowired
	private FileWatcherService fileWatcherService;
	
	/**
	 * 
	 * @function 发布任务到消息队列，可以进行重试等操作
	 * @author wenyuguang
	 * @creaetime 2018年3月9日 下午2:06:51
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendTask", method = RequestMethod.GET)
	@ResponseBody
	public Object sendTask (@RequestParam("taskId")String taskId) throws Exception {
		return fileWatcherService.sendTask(taskId);
	}
}
