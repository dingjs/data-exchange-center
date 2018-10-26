package data.exchange.center.service.elasticsearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.elasticsearch.service.ElasticsearchService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月6日 上午10:06:10</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController	
public class ElasticsearchController {

	private static Logger logger = LoggerFactory.getLogger(ElasticsearchController.class);  
	
	@Autowired
	ElasticsearchService elasticsearchService;
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月14日 上午10:29:08
	 * @param level
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findByLevel", method = RequestMethod.GET)
	public Object findByLevel(
			@RequestParam("level")String level, 
			@RequestParam("pageNumber")int pageNumber, 
			@RequestParam("pageSize")int pageSize) {
		try {
			Object obj = elasticsearchService.findByLevel(level, pageNumber, pageSize);
			return obj;
		}catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月14日 上午10:29:44
	 * @param id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public Object findById(
			@RequestParam("id")String id, 
			@RequestParam("pageNumber")int pageNumber, 
			@RequestParam("pageSize")int pageSize) {
		try {
			Object obj = elasticsearchService.findById(id, pageNumber, pageSize);
			return obj;
		}catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月14日 上午10:29:41
	 * @param systemName
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findBySystemName", method = RequestMethod.GET)
	public Object findBySystemName(
			@RequestParam("systemName")String systemName, 
			@RequestParam("pageNumber")int pageNumber, 
			@RequestParam("pageSize")int pageSize) {
		try {
			Object obj = elasticsearchService.findBySystemName(systemName, pageNumber, pageSize);
			return obj;
		}catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @function ip，level，systemName，searchName至少有一个参数有值
	 * @author wenyuguang
	 * @creaetime 2017年7月24日 下午5:55:30
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
			@RequestParam("pageSize")  int pageSize) {
		try {
			Object obj = elasticsearchService.findLog(ip, level, systemName, searchDate, pageNumber, pageSize);
			return obj;
		}catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月14日 上午10:28:39
	 * @param level
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.GET)
	public void deleteById(@RequestParam("id")String id) {
		try {
			elasticsearchService.deleteById(id);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月14日 上午10:28:03
	 * @param level
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/deleteByLevel", method = RequestMethod.GET)
	public void deleteByLevel(@RequestParam("level")String level) {
		try {
			elasticsearchService.deleteByLevel(level);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月14日 上午10:27:35
	 * @param systemName
	 */
	@RequestMapping(value = "/deleteBySystemName", method = RequestMethod.GET)
	public void deleteBySystemName(@RequestParam("systemName")String systemName) {
		try {
			elasticsearchService.deleteBySystemName(systemName);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 
	 * @function 根据案件标识查询出所有对应的数据
	 * @author wenyuguang
	 * @creaetime 2017年7月25日 下午3:58:09
	 * @param ajbs
	 * @return
	 */
	@RequestMapping(value = "/getCaseTrack", method = RequestMethod.GET)
	public Object getCaseTrack(
			@RequestParam("ajbs")      String ajbs,
			@RequestParam("pageNumber")int pageNumber,
			@RequestParam("pageSize")  int pageSize) {
		try {
			return elasticsearchService.getCaseTrack(ajbs, pageNumber, pageSize);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
