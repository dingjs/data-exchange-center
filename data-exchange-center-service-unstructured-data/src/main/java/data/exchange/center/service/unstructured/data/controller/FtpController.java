package data.exchange.center.service.unstructured.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.unstructured.data.service.FtpService;

@RestController
public class FtpController {

	@Autowired
	private FtpService ftpService;
	
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
			@RequestParam("fydm")String fydm) throws Exception {
		return ftpService.handleFtpFile(ajbs, ajlx, fydm);
	}
	
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
			@RequestParam("uuid")String uuid) throws Exception {
		return ftpService.handleTempUnstructureData(ajbs, ajlx, fydm, uuid);
	}
	
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
			@RequestParam("uuid")String uuid) throws Exception {
		return ftpService.handlePath(ajbs, ajlx, fydm, uuid);
	}
}
