package data.exchange.center.service.pingshan.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.pingshan.service.PingshanService;
import net.sf.json.JSONArray;

@RestController
public class PingShanController {

	@Autowired
	private PingshanService pingshanService;
	
	/**
	 * 
	 * @function 获取案件列表
	 * @author wenyuguang
	 * @creaetime 2018年1月5日 上午11:18:29
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAjbsList",method = RequestMethod.GET)
	public Object getAjbsList(	
			@RequestParam("tjsj")String tjsj
			) throws Exception {
		if(StringUtils.isEmpty(tjsj)) {
			Map<String, Object> map = new HashMap<>();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG, "tjsj can not be null");
			return map;
		}
		return pingshanService.getAjbsList(tjsj);
	}
	
	//法院信息代码
	@RequestMapping("getFybm")
	public Object getFybm() throws Exception {
		return pingshanService.getFybm();
	}
	
	//部门信息
	@RequestMapping("getBmxx")
	public Object getBmxx() throws Exception {
		return pingshanService.getBmxx();
	}
	
	//登录用户信息
	@RequestMapping("getYhxx")
	public Object getYhxx() throws Exception {
		return pingshanService.getYhxx();
	}
	
	//编码信息
	@RequestMapping("getAjbmxx")
	public Object getAjbmxx() throws Exception {
		return pingshanService.getAjbmxx();
	}
	
	//获取案件信息，包含多张表
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getAjxx",method = RequestMethod.GET)
	public Object getAjxx(
			@RequestParam("ajbs")String ajbs,
			@RequestParam("ajlx")String ajlx) throws Exception {
		
		if(StringUtils.isEmpty(ajbs)||StringUtils.isEmpty(ajlx)) {
			Map<String, Object> map = new HashMap<>();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG, "ajbs or ajlx can not be null");
			return map;
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap = (Map<String, Object>) pingshanService.getAjxx(ajbs, ajlx);
		JSONArray json = JSONArray.fromObject(returnMap); 
		return json;
	}
	//获取案件信息，包含多张表
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getDelAj",method = RequestMethod.GET)
	public Object getDelAj(

			@RequestParam("tjsj")String tjsj) throws Exception {
		
		if(StringUtils.isEmpty(tjsj)) {
			Map<String, Object> map = new HashMap<>();
			map.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			map.put(CodeUtil.RETURN_MSG, "tjsj can not be null");
			return map;
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap = (Map<String, Object>) pingshanService.getDelAj(tjsj);
		JSONArray json = JSONArray.fromObject(returnMap); 
		return json;
	}
	
}
