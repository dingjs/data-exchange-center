package data.exchange.center.monitor.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import data.exchange.center.monitor.service.CaseTrackService;

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
@RequestMapping("/casetrack")
public class CaseTrackController {

	@Autowired
	CaseTrackService caseTrackService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("list", caseTrackService.list());
		return "/casetrack/list";
	}
	
	@RequestMapping(value= "/getCaseTrack",method = RequestMethod.GET)
	@ResponseBody
	public Object getCaseTrack(HttpServletRequest request) {
		Object obj = caseTrackService.getCaseTrack(request.getParameter("ajbs"));
		Map<String, Object> map = new HashMap<>();
		map.put("aaData", obj);
		return JSON.toJSONString(map);
	}
	
}
