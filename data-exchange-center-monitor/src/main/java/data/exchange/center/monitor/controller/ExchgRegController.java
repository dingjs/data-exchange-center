package data.exchange.center.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import data.exchange.center.monitor.domain.ExchgReg.RegApp;
import data.exchange.center.monitor.domain.ExchgReg.RegFtp;
import data.exchange.center.monitor.domain.ExchgReg.RegNode;
import data.exchange.center.monitor.domain.ExchgReg.RegService;
import data.exchange.center.monitor.domain.ExchgReg.RegSoaToken;
import data.exchange.center.monitor.service.ExchgRegService;
import data.exchange.center.monitor.service.MetaDataService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月20日 下午5:55:31</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/exchgreg")
public class ExchgRegController {
	@Autowired
	ExchgRegService exchgRegService;
	
    @RequestMapping(value="/regNode",method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public String getRegNode(Model model){
		List<RegNode> regNode = (List<RegNode>) exchgRegService.getRegNode();
        model.addAttribute("list",regNode);
        return "/exchgreg/regNode_list";
    }
    @RequestMapping(value="/regApp",method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public String getRegApp(Model model){
		List<RegApp> regApp = (List<RegApp>) exchgRegService.getRegApp();
        model.addAttribute("list",regApp);
        return "/exchgreg/regApp_list";
    }
    
    @RequestMapping(value="/regFtp",method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public String getRegFtp(Model model){
		List<RegFtp> regFtp = (List<RegFtp>) exchgRegService.getRegFtp();
        model.addAttribute("list",regFtp);
        return "/exchgreg/regFtp_list";
    }
    @RequestMapping(value="/regService",method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public String getRegService(Model model){
		List<RegService> regService = (List<RegService>) exchgRegService.getRegService();
        model.addAttribute("list",regService);
        return "/exchgreg/regService_list";
    }
    @RequestMapping(value="/regSoaToken",method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public String getRegSoaToken(Model model){
		List<RegSoaToken> regService = (List<RegSoaToken>) exchgRegService.getRegSoaToken();
        model.addAttribute("list",regService);
        return "/exchgreg/regSoaToken_list";
    }
    @RequestMapping(value="/regExchg",method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public String getRegExchg(Model model){
		List<RegSoaToken> regService = (List<RegSoaToken>) exchgRegService.getRegExchg();
        model.addAttribute("list",regService);
        return "/exchgreg/regExchg_list";
    }
}
