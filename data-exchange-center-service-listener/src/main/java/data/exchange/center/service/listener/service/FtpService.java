package data.exchange.center.service.listener.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * Description:处理缓存表结构化数据服务
 * <p>Company: xinya </p>
 * <p>Date:2017年11月30日 上午10:47:27</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@FeignClient(value = "service-unstructureddata")
public interface FtpService {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年11月30日 下午3:47:38
	 * @param ajbs
	 * @param ajlx
	 * @param fydm
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/handleFtpFile", method = RequestMethod.GET)
	public Object handleFtpFile(
			@RequestParam("ajbs")String ajbs,
			@RequestParam("ajlx")String ajlx,
			@RequestParam("fydm")String fydm) throws Exception;
	
	/**
	 * 
	 * @function 处理非结构化数据对应的结构化数据
	 * @author wenyuguang
	 * @creaetime 2018年1月9日 下午3:55:29
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/handleTempUnstructureData" ,method = RequestMethod.GET)
	public Object handleTempUnstructureData(
			@RequestParam("ajbs")String ajbs,
			@RequestParam("ajlx")String ajlx,
			@RequestParam("fydm")String fydm,
			@RequestParam("uuid")String uuid) throws Exception;
	
	/**
	 * 
	 * @function 处理路径信息
	 * @author wenyuguang
	 * @creaetime 2018年1月12日 下午1:07:38
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/handlePath" ,method = RequestMethod.GET)
	public Object handlePath(
			@RequestParam("ajbs")String ajbs,
			@RequestParam("ajlx")String ajlx,
			@RequestParam("fydm")String fydm,
			@RequestParam("uuid")String uuid) throws Exception;
}
