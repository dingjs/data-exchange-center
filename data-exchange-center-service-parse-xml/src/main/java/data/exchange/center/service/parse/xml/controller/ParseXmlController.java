package data.exchange.center.service.parse.xml.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.common.code.CodeUtil;
import data.exchange.center.service.parse.xml.service.ParseStorageJxjsService;
import data.exchange.center.service.parse.xml.service.ParseStorageSPZTService;
import data.exchange.center.service.parse.xml.service.ParseStorageTongdahaiService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年1月24日 上午11:30:51</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
public class ParseXmlController {

	@Autowired
	private ParseStorageTongdahaiService parseStorageTongdahaiService;
	@Autowired
	private ParseStorageSPZTService      parseStorageSPZTService;
	@Autowired
	private ParseStorageJxjsService      parseStorageJxjsService;
	/**
	 * 
	 * @function 业务数据同步更新
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 上午11:45:19
	 * @param ajbs
	 * @return
	 */
	@RequestMapping("/parseStorageTongdahai")
	public Map<String, Object> parseStorageTongdahai(@RequestParam("ajbs")String ajbs) {
		try {
			return parseStorageTongdahaiService.parseStorageTongdahai(ajbs);
		}catch(Exception e) {
			Map<String, Object> rtMap = new HashMap<>();
			rtMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMap.put(CodeUtil.RETURN_MSG, e.toString());
			return rtMap;
		}
	}
	
	/**
	 * 
	 * @function 审判主体信息更新
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 上午11:46:45
	 * @param ajbs
	 * @return
	 */
	@RequestMapping("/parseStorageSpzt")
	public Map<String, Object> parseStorageSPZT(@RequestParam("ajbs")String ajbs) {
		try {
			return parseStorageSPZTService.parseStorageSPZT(ajbs);
		}catch(Exception e) {
			Map<String, Object> rtMap = new HashMap<>();
			rtMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMap.put(CodeUtil.RETURN_MSG, e.toString());
			return rtMap;
		}
	}
	
	/**
	 * 
	 * @function 案件删除
	 * @author wenyuguang
	 * @creaetime 2018年1月24日 上午11:47:14
	 * @param ajbs
	 * @return
	 */
	@RequestMapping("/parseStorageSpztAjsc")
	public Map<String, Object> parseStorageSPZT_AJSC(@RequestParam("ajbs")String ajbs) {
		try {
			return parseStorageSPZTService.parseStorageSPZT_AJSC(ajbs);
		}catch(Exception e) {
			Map<String, Object> rtMap = new HashMap<>();
			rtMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMap.put(CodeUtil.RETURN_MSG, e.toString());
			return rtMap;
		}
	}
	
	/**
	 * 
	 * @function 解析减刑假释案件
	 * @author Tony
	 * @creaetime 2018年6月5日 上午9:58:08
	 * @param ajbs
	 * @return
	 */
	@RequestMapping("/parseStorageJxjs")
	public Map<String, Object> parseStorageJxjs(@RequestParam("ajbs")String ajbs) {
		try {
			return parseStorageJxjsService.parseStorageJxjs(ajbs);
		}catch(Exception e) {
			Map<String, Object> rtMap = new HashMap<>();
			rtMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMap.put(CodeUtil.RETURN_MSG, e.toString());
			return rtMap;
		}
	}
	/**
	 * 
	 * @function 减刑假释案件删除
	 * @author Tony
	 * @creaetime 2018年6月6日 下午4:32:19
	 * @param ajbs
	 * @return
	 */
	@RequestMapping("/deleteJxjs")
	public Map<String, Object> deleteJxjs(
			@RequestParam("ajbs")String ajbs,
			@RequestParam("fydm")String fydm,
			@RequestParam("ajlx")String ajlx) {
		try {
			return parseStorageJxjsService.deleteJxjs(ajbs, fydm, ajlx);
		}catch(Exception e) {
			Map<String, Object> rtMap = new HashMap<>();
			rtMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMap.put(CodeUtil.RETURN_MSG, e.toString());
			return rtMap;
		}
	}
}
