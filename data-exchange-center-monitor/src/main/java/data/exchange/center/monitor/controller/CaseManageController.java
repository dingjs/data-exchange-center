package data.exchange.center.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import data.exchange.center.monitor.service.CaseManageService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月25日 下午3:33:21</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/casemanage")
public class CaseManageController {

	@Autowired
	CaseManageService caseManageService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
//		model.addAttribute("list", serviceLogService.list());
		return "/casemanage/list";
	}
}
