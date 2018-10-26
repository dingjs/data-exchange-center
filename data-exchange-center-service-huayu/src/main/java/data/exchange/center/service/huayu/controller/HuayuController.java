package data.exchange.center.service.huayu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import data.exchange.center.service.huayu.service.DownloadService;
import data.exchange.center.service.huayu.service.HuayuService;
import net.sf.json.JSONArray;


/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月15日 上午11:14:33</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
public class HuayuController {

	private static Logger logger = LoggerFactory.getLogger(HuayuController.class); 
    
	@Autowired
	private HuayuService huayuService;
	@Autowired
	DownloadService downloadService;
	
	@RequestMapping(value = "/getAjlb" ,method = RequestMethod.GET)
	public List<Map<String, Object>> getMssAjbsList(
			@RequestParam("tjsj")String tjsj,
			@RequestParam("rybs")String rybs
			) throws Exception{
		try {
			logger.info("parameter: tjsj"+tjsj+",rybs:"+rybs);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("tjsj", tjsj);
			param.put("rybs", rybs);
			return huayuService.getAjlb(param);
		} catch (Exception e) {
			logger.info("parameter: tjsj"+tjsj+"错误日志"+e.getMessage());
		}
		return null;
		
	}
	
	@RequestMapping(value = "/getAjxxAll", method = RequestMethod.GET)
	public Object getAjxxAll(@RequestParam("ajbs") String ajbs,@RequestParam("ajlx") String ajlx) {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = huayuService.getAjbsInfo(ajbs, ajlx);
			JSONArray json = JSONArray.fromObject(returnMap); 
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("获取案件所有信息错误:ajbs"+ajbs+"案件类型"+ajlx+"错误详情：" + e.getMessage());
		}
		return null;

	}
	
	@RequestMapping(value = "/getAjDaJz", method = RequestMethod.GET)
	public Object getAjDaJz(@RequestParam("ajbs") String ajbs,@RequestParam("ajlx") String ajlx) {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap = huayuService.getAjDaJz(ajbs, ajlx);
			JSONArray json = JSONArray.fromObject(returnMap); 
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("获取案件所有信息错误:ajbs"+ajbs+"案件类型"+ajlx+"错误详情：" + e.getMessage());
		}
		return null;

	}
	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	public Object getFile(@RequestParam("ajbs") String ajbs,@RequestParam("xh") String xh) {
		try {
			 return downloadService.download(ajbs+"_"+xh);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下载非结构化数据错误:ajbs"+ajbs+"xh"+xh+"错误详情" + e.getMessage());
		}
		return null;

	}
	@RequestMapping(value = "/getZzjg", method = RequestMethod.GET)
	public Object getZzjg(@RequestParam("jgbz") String jgbz) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jgbz", jgbz);
			JSONArray json = JSONArray.fromObject(huayuService.getZzjg(map)); 
			 return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取组织机构出现问题:jgbz"+jgbz+"错误详情" + e.getMessage());
		}
		return null;
	}
	@RequestMapping(value = "/getZzry", method = RequestMethod.GET)
	public Object getZzry(@RequestParam("fybz") String fybz,@RequestParam("dlbz") String dlbz
			,@RequestParam("mm") String mm) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fybz", fybz);
			map.put("dlbz", dlbz);
			map.put("mm", mm);
			JSONArray json = JSONArray.fromObject(huayuService.getZzry(map)); 
			 return json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取组织机构出现问题:fybz"+fybz+"dlbz"+dlbz+"mm"+mm+"错误详情" + e.getMessage());
		}
		return null;
	}
	
}
