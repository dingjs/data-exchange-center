package data.exchange.center.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import data.exchange.center.monitor.domain.Mraj;
import data.exchange.center.monitor.domain.modle.Ajzt;
import data.exchange.center.monitor.service.IndexService;

/**
 * @author qiang.xie
 * @date 2016/11/17
 */
@Controller
public class IndexController {

	@Autowired
	IndexService indexService;
	
    @RequestMapping("/")
    public String index(Model model) {
    	Mraj mraj = indexService.getMraj();
    	model.addAttribute("newCase", mraj.getXz());
    	model.addAttribute("updateCase", mraj.getGx());
    	model.addAttribute("deleteCase", mraj.getSc());
        return "index";
    }
    
    @RequestMapping("/indexDt")
    public String indexDt(Model model) {
    	Mraj mraj = indexService.getMraj();
    	model.addAttribute("newCase", mraj.getXz());
    	model.addAttribute("updateCase", mraj.getGx());
    	model.addAttribute("deleteCase", mraj.getSc());
        return "index_dt";
    }
    @SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET,value="getAjtj")
    @ResponseBody
    public Object getAjtj(HttpServletRequest request){
    	String ajzt =request.getParameter("ajzt");
    	List<Object> objList = new ArrayList<>();
    	try {
    		List<Object> list = (List<Object>) indexService.getAjtj(ajzt);
        	for (int i = 0; i < list.size(); i++) {
    			Ajzt ajztmap= (data.exchange.center.monitor.domain.modle.Ajzt) list.get(i);
    			Map<String, Object> map =new HashMap<>();
    			if(StringUtils.isNotBlank(ajztmap.getFyjc())){
        		map.put("name",ajztmap.getFyjc());
    			
        		if(ajzt.equalsIgnoreCase("xs")){
        			map.put("value",ajztmap.getXs()==null?0:ajztmap.getXs());
        			
        		}else if(ajzt.equalsIgnoreCase("jc")){
        			map.put("value",ajztmap.getJc()==null?0:ajztmap.getJc());
        			
        		}else if(ajzt.equalsIgnoreCase("yj")){
        			map.put("value",ajztmap.getYj()==null?0:ajztmap.getYj());
        			
        		}else if(ajzt.equalsIgnoreCase("wj")){
        			map.put("value",ajztmap.getWj()==null?0:ajztmap.getWj());
        		}
        		objList.add(map);
    			}
    		}
            return JSON.toJSONString(objList).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    @RequestMapping(method = RequestMethod.GET,value="getFqjs")
    @ResponseBody
    public Object getAjs(HttpServletRequest request){
    	String fymc =request.getParameter("fymc");
    	String ajzt =request.getParameter("ajzt");
    	List<Object> objList = new ArrayList<>();
    	try {
    		List<Object> list = (List<Object>) indexService.getAjs(ajzt, fymc);
        	for (int i = 0; i < list.size(); i++) {
    			Ajzt ajztmap= (data.exchange.center.monitor.domain.modle.Ajzt) list.get(i);
    			Map<String, Object> map =new HashMap<>();
    			if(StringUtils.isNotBlank(ajztmap.getFyjc())){
        		map.put("name",ajztmap.getFyjc());
    			
        		if(ajzt.equalsIgnoreCase("xs")){
        			map.put("value",ajztmap.getXs()==null?0:ajztmap.getXs());
        			
        		}else if(ajzt.equalsIgnoreCase("jc")){
        			map.put("value",ajztmap.getJc()==null?0:ajztmap.getJc());
        			
        		}else if(ajzt.equalsIgnoreCase("yj")){
        			map.put("value",ajztmap.getYj()==null?0:ajztmap.getYj());
        			
        		}else if(ajzt.equalsIgnoreCase("wj")){
        			map.put("value",ajztmap.getWj()==null?0:ajztmap.getWj());
        		}
        		objList.add(map);
    			}
    		}
            return JSON.toJSONString(objList).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
}
