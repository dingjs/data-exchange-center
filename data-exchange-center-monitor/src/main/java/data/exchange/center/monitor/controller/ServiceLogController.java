package data.exchange.center.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import data.exchange.center.monitor.service.ElasticsearchService;
import data.exchange.center.monitor.service.ServiceLogService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午11:21:34</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/servicelog")
public class ServiceLogController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ServiceLogService serviceLogService;

	@Autowired
	ElasticsearchService elasticsearchService;

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void clear() {
		serviceLogService.clear();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Object obj = elasticsearchService.findLog("", "", "", date, 10, 50);
		model.addAttribute("list", obj);
		return "/servicelog/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "findLog")
	@ResponseBody
	public Object findLog(Model model, HttpServletRequest request) {
		try {
			String ip = request.getParameter("ip");
			String level = request.getParameter("level");
			String systemName = request.getParameter("systemName");
			String date = request.getParameter("searchDate");
			if(StringUtils.isEmpty(date)) {
				date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			}
			if(level.endsWith("ALL")){
				level="";
			}
			Object obj = elasticsearchService.findLog(ip, level, systemName, date, 10, 50);
			Map<String, Object> map = new HashMap<>();
			map.put("aaData", obj);
			return JSON.toJSONString(map);
		}catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
	}
}
