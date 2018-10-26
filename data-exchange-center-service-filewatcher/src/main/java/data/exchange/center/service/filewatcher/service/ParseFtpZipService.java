package data.exchange.center.service.filewatcher.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * Description:调用协同办案平台服务包
 * <p>Company: xinya </p>
 * <p>Date:2017年10月25日 下午3:52:44</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@FeignClient(value = "service-parse-ftpzip")
public interface ParseFtpZipService {
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月25日 下午3:54:51
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value = "/unzipAndParse", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> unzipAndParse(@RequestParam("taskId") String taskId);
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年12月22日 下午4:07:51
	 * @param rwh
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/handleCallBack", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> handleCallBack(@RequestParam("rwh") String rwh) throws Exception;
	
	/**
	 * 
	 * @function 保存错误消息
	 * @author wenyuguang
	 * @creaetime 2018年3月9日 上午10:31:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveErrMsg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveErrMsg(@RequestParam("taskId")String taskId) throws Exception;
}
