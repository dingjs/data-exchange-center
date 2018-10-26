package data.exchange.center.service.meishan.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.meishan.service.MeishanService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年8月31日 上午10:04:58</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
public class MeishanController {

	private static Logger logger = LoggerFactory.getLogger(MeishanController.class); 
    
	@Autowired
	private MeishanService meishanService;
	
	/**
	 * 
	 * @function 获取的民商事案件列表（自16年以来所有案件）
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午12:59:52
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMssAjbsList" ,method = RequestMethod.GET)
	public Object getMssAjbsList(
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate,
			@RequestParam("pageNum")String pageNum
			) throws Exception{
		logger.info("parameter: "+startDate+", "+endDate+", "+pageNum);
		return meishanService.getMssAjbsList(startDate, endDate, pageNum);
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月10日 下午4:04:45
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMssAjbsCount" ,method = RequestMethod.GET)
	public Object getMssAjbsCount(
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate
			) throws Exception{
		logger.info("parameter: "+startDate+", "+endDate);
		return meishanService.getMssAjbsCount(startDate, endDate);
	}
	
	/**
	 * 
	 * @function 获取的民商事案件（自16年以来所有案件）结构化数据内容
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午1:02:51
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMssAjbsInfo" ,method = RequestMethod.GET)
	public Object getMssAjbsInfo(
			@RequestParam("ajbs")String ajbs) throws Exception{
		logger.info("parameter: "+ ajbs);
		return meishanService.getMssAjbsInfo(ajbs);
	}
	
	/**
	 * 
	 * @function 获取的执行案件列表（自16年以来所有案件）
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午1:06:17
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getZxajAjbsList" ,method = RequestMethod.GET)
	public Object getZxajAjbsList(
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate,
			@RequestParam("pageNum")String pageNum
			) throws Exception{
		logger.info("parameter: "+startDate+", "+endDate+", "+pageNum);
		return meishanService.getZxajAjbsList(startDate, endDate, pageNum);
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月10日 下午4:05:34
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getZxajAjbsCount" ,method = RequestMethod.GET)
	public Object getZxajAjbsCount(
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate
			) throws Exception{
		logger.info("parameter: "+startDate+", "+endDate);
		return meishanService.getZxajAjbsCount(startDate, endDate);
	}
	
	/**
	 * 
	 * @function 获取的执行案件（自16年以来所有案件）结构化数据内容
	 * @author wenyuguang
	 * @creaetime 2017年9月11日 下午1:06:12
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getZxajAjbsInfo" ,method = RequestMethod.GET)
	public Map<String, Object> getZxajAjbsInfo(
			@RequestParam("ajbs")String ajbs) throws Exception{
		logger.info("parameter: "+ ajbs);
		return meishanService.getZxajAjbsInfo(ajbs);
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年9月13日 上午11:10:09
	 * @param ajbs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWsInfo" ,method = RequestMethod.GET)
	public Map<String, Object> getWsInfo(
			@RequestParam("ajbs")String ajbs,
			@RequestParam("fydm")String fydm,
			@RequestParam("ajlx")String ajlx) throws Exception{
		logger.info("parameter: "+ajbs+", "+fydm+", "+ajlx);
		return meishanService.getWsInfo(ajbs,fydm,ajlx);
	}
}
