/**
 * a-springcloud-test-provider-one
 * created by yuguang at 2017年4月26日
 */
package data.exchange.center.service.sefon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.common.date.PatternDate;
import data.exchange.center.service.sefon.domain.Laxx;
import data.exchange.center.service.sefon.service.AjxxAllService;
import data.exchange.center.service.sefon.service.LaxxService;
import net.sf.json.JSONArray;

/**
 * Description:
 * <p>
 * Company: pelox
 * </p>
 * <p>
 * Date:2017年4月26日 下午10:20:02
 * </p>
 * 
 * @author Wen.Yuguang
 * @version 1.0
 **/
@RestController
@Transactional
public class LaxxController {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	LaxxService laxxService;
	
	@Autowired
	AjxxAllService ajxxAllService;

	/**
	 * 
	 * @function
	 * @author wenyuguang
	 * @creaetime 2017年10月16日 上午11:42:46
	 * @param lasUpdate
	 * @return
	 */
	@RequestMapping(value = "/getAjbsList", method = RequestMethod.GET)
	public Map<String, Object> getAjbsList(@RequestParam("lastUpdate") String lastUpdate) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(lastUpdate)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "参数startDate或者endDate不能为空");
				return returnMap;
			}
			if(!PatternDate.patternDate(lastUpdate)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "【开始时间】格式不正确,请用YYYYMMDD格式字符串类型日期");
				return returnMap;
			}
			
			param.put("lastUpdate", lastUpdate);
			List<Laxx> laxx = laxxService.getAjbsInfo(param);
			map.put("jxjsLaxx", laxx);
			returnMap.put("flag", "1");
			returnMap.put("data", map);
			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取案件基本信息错误：参数:案件更新时间" + lastUpdate + "; 错误详情:" + e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}

	}

	/**
	 * 
	 * @function
	 * @author wenyuguang
	 * @creaetime 2017年10月16日 上午11:42:50
	 * @param ajbs
	 * @return
	 */
	@RequestMapping(value = "/getAjxxAll", method = RequestMethod.GET)
	public Object getAjxxAll(@RequestParam("ajbs") String ajbs) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(ajbs)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "参数startDate或者endDate不能为空");
				return returnMap;
			}
			returnMap = ajxxAllService.getAjbsInfo(ajbs, String.valueOf(15));
			JSONArray json = JSONArray.fromObject(returnMap);
			returnMap.put("flag", "1");
			returnMap.put("data", json);
			return json;
		} catch (Exception e) {
			logger.debug("四方获取减刑假释案件所有信息错误:ajbs" + ajbs + "错误详情：" + e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}
	}
}
