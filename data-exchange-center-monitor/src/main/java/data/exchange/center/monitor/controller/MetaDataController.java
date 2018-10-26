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
@RequestMapping("/metadata")
public class MetaDataController {

    @Autowired
    protected MetaDataService metaDataService;

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void clear(){
    	metaDataService.clear();
    }
   
    @RequestMapping(value= "/{treeid}", method = RequestMethod.GET)
    public String list(Model model,@PathVariable String treeid){
    	Map<String, String> map =new HashMap<>();
    	List<Object> list =new ArrayList<>();
    	map.put("treeid", treeid);
    	list.add(map);
        model.addAttribute("tree",list);
        return "/metadata/list";
    }
    
    @RequestMapping(value= "getTable/{nodeId}", method = RequestMethod.GET)
    @ResponseBody
    public String getTable(@PathVariable String nodeId){
    	Map<String, Object> map = new HashMap<>();
		map.put("data", metaDataService.getTableCol(nodeId));
        return JSON.toJSONString(map);
    }
    
    @RequestMapping(value= "getMetadata/{treeid}", method = RequestMethod.GET)
    @ResponseBody
    public Object getMetadata(Model model,@PathVariable String treeid){
    	return JSON.toJSONString(metaDataService.getMetadata(treeid));
    }
}
