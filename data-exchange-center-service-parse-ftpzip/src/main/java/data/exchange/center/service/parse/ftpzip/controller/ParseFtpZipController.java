package data.exchange.center.service.parse.ftpzip.controller;

import java.net.URLDecoder;
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
import data.exchange.center.common.date.PatternDate;
import data.exchange.center.service.parse.ftpzip.service.ParseFtpZipService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月31日 上午11:23:32</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
public class ParseFtpZipController {

	@Autowired
	private ParseFtpZipService parseFtpZipService;

	/**
	 * 
	 * @function
	 * @author wenyuguang
	 * @creaetime 2017年10月20日 上午10:43:06
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/unzipAndParse", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> unzipAndParse(@RequestParam("taskId") String taskId) throws Exception {
		Map<String, Object> obj = parseFtpZipService.unzipAndParse(taskId);
		return obj;
	}

	@RequestMapping(value = "/callBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> callBack(
			@RequestParam("xtptbh")String xtptbh,
			@RequestParam("lcjdbh")String lcjdbh,
			@RequestParam("lcslbh")String lcslbh,
			@RequestParam("rwh")   String rwh,
			@RequestParam("jsdwbm")String jsdwbm,
			@RequestParam("jsdwlx")String jsdwlx,
			@RequestParam("jsdwmc")String jsdwmc,
			@RequestParam("fsdwlx")String fsdwlx,
			@RequestParam("fsdwbm")String fsdwbm,
			@RequestParam("fsdwmc")String fsdwmc,
			@RequestParam("jgzt")  String jgzt,
			@RequestParam("ztms")  String ztms,
			@RequestParam("fhsj")  String fhsj,
			
			@RequestParam("ywlcbm")String ywlcbm,//业务流程编码
			@RequestParam("jdbm")  String jdbm//节点编码
			) throws Exception{
		Map<String, Object> returnMap = new HashMap<>();
		
		boolean isReturn = false;
		StringBuffer strBuf = new StringBuffer();
		if(StringUtils.isEmpty(xtptbh)) {
			strBuf.append("字段 [xtptbh]协同平台编号不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(lcjdbh)) {
			strBuf.append("字段 [lcjdbh]流程阶段编号不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(lcslbh)) {
			strBuf.append("字段 [lcslbh]流程实例编号不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(rwh)) {
			strBuf.append("字段 [rwh]任务号不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(jsdwbm)) {
			strBuf.append("字段 [jsdwbm]接收单位编码不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(jsdwlx)) {
			strBuf.append("字段 [jsdwlx]接收单位类型不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(jsdwmc)) {
			strBuf.append("字段 [jsdwmc]接收单位名称不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(fsdwlx)) {
			strBuf.append("字段 [fsdwlx]发送单位类型不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(fsdwbm)) {
			strBuf.append("字段 [fsdwbm]发送单位编码不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(fsdwmc)) {
			strBuf.append("字段 [fsdwmc]发送单位名称不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(jgzt)) {
			strBuf.append("字段 [jgzt]结果状态不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(ztms)) {
			strBuf.append("字段 [ztms]状态描述不能为空;    ");
			isReturn = true;
		}
		if(StringUtils.isEmpty(fhsj)) {
			strBuf.append("字段 [fhsj]返回时间不能为空;    ");
			isReturn = true;
		}
		if(!StringUtils.isEmpty(fhsj)&&!PatternDate.patternDate(fhsj, "datetime")) {
			strBuf.append("字段 [fhsj]返回时间格式不正确，请按格式[yyyyMMddHHmmss]填写;    ");
			isReturn = true;
		}
		
		if(isReturn) {
			returnMap.put(CodeUtil.RETURN_CODE, CodeUtil.RETURN_FAIL);
			returnMap.put(CodeUtil.RETURN_MSG, strBuf.toString());
			return returnMap;
		}
		
		return parseFtpZipService.callBack(
				URLDecoder.decode(xtptbh, "UTF-8"), 
				URLDecoder.decode(lcjdbh, "UTF-8"), 
				URLDecoder.decode(lcslbh, "UTF-8"), 
				URLDecoder.decode(rwh,    "UTF-8"), 
				URLDecoder.decode(jsdwbm, "UTF-8"), 
				URLDecoder.decode(jsdwlx, "UTF-8"), 
				URLDecoder.decode(jsdwmc, "UTF-8"), 
				URLDecoder.decode(fsdwlx, "UTF-8"), 
				URLDecoder.decode(fsdwbm, "UTF-8"), 
				URLDecoder.decode(fsdwmc, "UTF-8"), 
				URLDecoder.decode(jgzt,   "UTF-8"), 
				URLDecoder.decode(ztms,   "UTF-8"), 
				URLDecoder.decode(fhsj,   "UTF-8"), 
				URLDecoder.decode(ywlcbm, "UTF-8"), 
				URLDecoder.decode(jdbm,   "UTF-8"));
	}
	
	/**
	 * 
	 * @function 反馈信息处理
	 * @author wenyuguang
	 * @creaetime 2017年12月22日 下午3:46:02
	 * @param xtptbh
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/handleCallBack", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> handleCallBack(@RequestParam("rwh") String rwh) throws Exception {
		Map<String, Object> obj = parseFtpZipService.handleCallBack(rwh);
		return obj;
	}
	
	/**
	 * 
	 * @function 保存错误消息
	 * @author wenyuguang
	 * @creaetime 2018年3月9日 上午10:31:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveErrMsg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveErrMsg(@RequestParam("taskId")String taskId) throws Exception{
		return parseFtpZipService.saveErrMsg(taskId);
	}
}
