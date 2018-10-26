package data.exchange.center.service.parse.ftpzip.controller;

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
import data.exchange.center.service.parse.ftpzip.service.AjBindService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年1月30日 上午10:20:08</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
public class AjBindController {

	@Autowired
	private AjBindService ajBindService;
	
	/**
	 * 
	 * @function 案件绑定
	 * @author wenyuguang
	 * @creaetime 2018年1月30日 上午10:19:32
	 * @param jhbh
	 * @param ptajbh
	 * @param ajbs
	 * @param ah
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/bindAjbs", method = RequestMethod.POST)
	@ResponseBody
	public Object bindAjbs(
			@RequestParam("jhbh")String jhbh,
			@RequestParam("ptajbh")String ptajbh,
			@RequestParam("ajbs")String ajbs,
			@RequestParam("flag")String flag) throws Exception {
		if(StringUtils.isEmpty(jhbh)) {
			Map<String, String> rtMp = new HashMap<>();
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMp.put(CodeUtil.RETURN_MSG, "parameter jhbh cann't be empty!please check!");
			return rtMp;
		}
		if(StringUtils.isEmpty(ptajbh)) {
			Map<String, String> rtMp = new HashMap<>();
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMp.put(CodeUtil.RETURN_MSG, "parameter ptajbh cann't be empty!please check!");
			return rtMp;
		}
		if(StringUtils.isEmpty(ajbs)) {
			Map<String, String> rtMp = new HashMap<>();
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMp.put(CodeUtil.RETURN_MSG, "parameter ajbs cann't be empty!please check!");
			return rtMp;
		}
		if(StringUtils.isEmpty(flag)) {
			Map<String, String> rtMp = new HashMap<>();
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMp.put(CodeUtil.RETURN_MSG, "parameter flag cann't be empty!please check!");
			return rtMp;
		}
		if(flag.length()>1) {
			Map<String, String> rtMp = new HashMap<>();
			rtMp.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			rtMp.put(CodeUtil.RETURN_MSG, "flag参数超长，长度只能为1");
			return rtMp;
		}
		System.out.println(jhbh + " " + ptajbh + " " + ajbs + " " + flag);
		return ajBindService.bindAjbs(jhbh, ptajbh, ajbs, flag);
	}
	
	/**
	 * 
	 * @function 查询交换日志
	 * @author wenyuguang
	 * @creaetime 2018年2月27日 下午4:09:02
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryJhkLog", method = RequestMethod.GET)
	@ResponseBody
	public Object queryJhkLog (
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")  String endDate,
			@RequestParam("dsr")      String dsr,
			@RequestParam("ajbh")     String ajbh,
			@RequestParam("wsbh")     String wsbh
			) throws Exception {
		System.out.println("******正在查询收案情况;开始日期:"+startDate+"; 结束日期："+endDate+";当事人："+dsr+";案件编号"+ajbh+";接收单位："+wsbh);
		return ajBindService.queryJhkLog(startDate, endDate, dsr, ajbh, wsbh);
	}
	
	/**
	 * 
	 * @function 
	 * @author Tony
	 * @creaetime 2018年3月23日 下午4:57:17
	 * @param startDate
	 * @param endDate
	 * @param dsr
	 * @param ajbh
	 * @param wsbh
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCaseCount", method = RequestMethod.GET)
	@ResponseBody
	public Object queryCaseCount (@RequestParam("jsdw")String jsdw) throws Exception {
		System.out.println("******正在查询收案统计情况;接收单位:"+jsdw);
		return ajBindService.queryCaseCount(jsdw);
	}
}
