package data.exchange.center.monitor.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import data.exchange.center.monitor.service.CaseManageService;
import data.exchange.center.monitor.service.DownLoadService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月25日 下午3:33:14</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/caselook")
public class CaseLookController {

	@Autowired
	CaseManageService caseManageService;
	
	@Autowired
	DownLoadService downLoadService;

	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "/caselook/list";
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年8月1日 下午4:02:28
	 * @param request
	 * @return
	 */
	@RequestMapping(value= "/getFileList",method = RequestMethod.GET)
	@ResponseBody
	public Object getFileList(HttpServletRequest request) {
		Object obj = caseManageService.getFileList(request.getParameter("ajbs"));
		Map<String, Object> map = new HashMap<>();
		map.put("aaData", obj);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年8月3日 下午5:03:18
	 * @param request
	 * @return
	 */
	@RequestMapping(value= "/getFile",method = RequestMethod.GET)
	@ResponseBody
	public Object getFile(HttpServletRequest request) {
		return downLoadService.download(request.getParameter("key"));
	}
}
