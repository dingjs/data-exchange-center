package data.exchange.center.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import data.exchange.center.monitor.service.NodeInfoService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午11:20:18</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/node")
public class NodeController {

	@Autowired
	NodeInfoService nodeInfoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("list", nodeInfoService.getAllServiceInfo());
		return "/node/list";
	}
	
	@RequestMapping(value="/service", method = RequestMethod.GET)
	@ResponseBody
	public Object lists(Model model) {
		return nodeInfoService.getAllServiceInfo();
	}
}
