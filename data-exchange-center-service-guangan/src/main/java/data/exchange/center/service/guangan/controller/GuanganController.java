package data.exchange.center.service.guangan.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.guangan.domain.AjbsInfo;
import data.exchange.center.service.guangan.domain.Ajcyrxx;
import data.exchange.center.service.guangan.domain.Ajxx;
import data.exchange.center.service.guangan.domain.Fybm;
import data.exchange.center.service.guangan.domain.Sksxx;
import data.exchange.center.service.guangan.domain.Ysqk;
import data.exchange.center.service.guangan.domain.Zxakxx;
import data.exchange.center.service.guangan.domain.Zxcsxx;
import data.exchange.center.service.guangan.domain.Zzjg;
import data.exchange.center.service.guangan.domain.jz.Daxx;
import data.exchange.center.service.guangan.domain.jz.DaxxGc;
import data.exchange.center.service.guangan.domain.jz.Mlxx;
import data.exchange.center.service.guangan.domain.jz.MlxxGc;
import data.exchange.center.service.guangan.domain.jz.Stwj;
import data.exchange.center.service.guangan.domain.jz.StwjGc;
import data.exchange.center.service.guangan.domain.zxaj.Fk;
import data.exchange.center.service.guangan.domain.zxaj.Jc;
import data.exchange.center.service.guangan.domain.zxaj.Jl;
import data.exchange.center.service.guangan.domain.zxaj.Sc;
import data.exchange.center.service.guangan.service.GuanganService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年6月26日 下午4:24:35</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
public class GuanganController {
	
	private static Logger logger = LoggerFactory.getLogger(GuanganController.class); 
    
	@Autowired
	private GuanganService guanganService;
	
	/**
	 * 
	 * @function 根据更新时间获取案件标识
	 * @author wenyuguang
	 * @creaetime 2017年6月26日 下午6:08:35
	 * @param startDate yyyymmdd
	 * @param endDate   yyyymmdd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAjbsList" ,method = RequestMethod.GET)
	public Map<String, Object> getAjbsList(
			@RequestParam("startDate")String startDate,
			@RequestParam("endDate")String endDate) throws Exception{
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try{
			if(StringUtils.isEmpty(startDate)||StringUtils.isEmpty(endDate)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "参数startDate或者endDate不能为空");
				return returnMap;
			}
			if(!patternDate(startDate)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "【开始时间】格式不正确,请用YYYYMMDD格式字符串类型日期");
				return returnMap;
			}
			if(!patternDate(endDate)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "【结束时间】格式不正确,请用YYYYMMDD格式字符串类型日期");
				return returnMap;
			}
			if(day(startDate,endDate)>1){
				returnMap.put("flag", "-1");
				returnMap.put("data", "只允许查询一天以内的数据");
				return returnMap;
			}
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("startDate", startDate);
			param.put("endDate", endDate);
			List<AjbsInfo> list = guanganService.getUpdateAjbs(param);
			returnMap.put("flag", "1");
			returnMap.put("data", list);
			return returnMap;
		}catch(Exception e){
			logger.error("错误详情："+e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAjbsInfo" ,method = RequestMethod.GET)
	public Map<String, Object> getAjbsInfo(@RequestParam("ajbs")String ajbs){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try{
			if(StringUtils.isEmpty(ajbs)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "参数ajbs不能为空");
				return returnMap;
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("ajbs", ajbs);
			guanganService.getAjbsInfo(param);
			Map<String,Object> map = new HashMap<String,Object>();
			/**
             * 案件主表
             */
            List<Ajxx> ajxxList = (List<Ajxx>) param.get("v_cursor1");
            /**
             * 上抗诉信息
             */
            List<Sksxx> ajsksxxList = (List<Sksxx>) param.get("v_cursor2");
            /**
             * 案件参与人信息
             */
            List<Ajcyrxx> ajcyrxxList = (List<Ajcyrxx>) param.get("v_cursor3");
            /**
             * 执行措施信息
             */
            List<Zxcsxx> zxcsxxList = (List<Zxcsxx>) param.get("v_cursor4");
            /**
             * 执行案款信息
             */
            List<Zxakxx> zxakxxList = (List<Zxakxx>) param.get("v_cursor5");
            /**
             * 原审案件信息
             */
            List<Ysqk> ysqkList = (List<Ysqk>) param.get("v_cursor6");
            
            map.put("ajxxList",    ajxxList);
            map.put("ajsksxxList", ajsksxxList);
            map.put("ajcyrxxList", ajcyrxxList);
            map.put("zxcsxxList",  zxcsxxList);
            map.put("zxakxxList",  zxakxxList);
            map.put("ysqkList",    ysqkList);
            returnMap.put("flag", "1");
			returnMap.put("data", map);
			return returnMap;
		}catch(Exception e){
			logger.error("参数:"+ajbs+"; 错误详情:"+e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}
	}
	
	/**
	 * 获取审判主体信息
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月5日 上午11:32:32
	 * @param ajbs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSpzt" ,method = RequestMethod.GET)
	public Map<String, Object> getSpzt(){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			guanganService.getSpzt(param);
			Map<String,Object> map = new HashMap<String,Object>();
			/**
             * 法院编码
             */
            List<Fybm> fybmList = (List<Fybm>) param.get("v_cursor1");
            /**
             * 组织机构
             */
            List<Zzjg> zzjgList = (List<Zzjg>) param.get("v_cursor2");
            
            map.put("fybmList", fybmList);
            map.put("zzjgList", zzjgList);
            returnMap.put("flag", "1");
			returnMap.put("data", map);
			return returnMap;
		}catch(Exception e){
			logger.error("错误详情："+e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}
	}
	
	/**
	 * 
	 * @function 根据ah案号返回原审案件信息
	 * @author wenyuguang
	 * @creaetime 2017年7月5日 上午11:50:13
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getYsajxx" ,method = RequestMethod.GET)
	public Map<String, Object> getYsajxx(@RequestParam("ah")String ah){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		try{
			if(StringUtils.isEmpty(ah)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "参数ah不能为空");
				return returnMap;
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("ah", ah);
			guanganService.getYsajxx(param);
			Map<String,Object> map = new HashMap<String,Object>();
			/**
             * 案件主表
             */
            List<Ajxx> ajxxList = (List<Ajxx>) param.get("v_cursor1");
            /**
             * 上抗诉信息
             */
            List<Sksxx> ajsksxxList = (List<Sksxx>) param.get("v_cursor2");
            /**
             * 案件参与人信息
             */
            List<Ajcyrxx> ajcyrxxList = (List<Ajcyrxx>) param.get("v_cursor3");
            /**
             * 执行措施信息
             */
            List<Zxcsxx> zxcsxxList = (List<Zxcsxx>) param.get("v_cursor4");
            /**
             * 执行案款信息
             */
            List<Zxakxx> zxakxxList = (List<Zxakxx>) param.get("v_cursor5");
            /**
             * 原审案件信息
             */
            List<Ysqk> ysqkList = (List<Ysqk>) param.get("v_cursor6");
            
            map.put("ajxxList",    ajxxList);
            map.put("ajsksxxList", ajsksxxList);
            map.put("ajcyrxxList", ajcyrxxList);
            map.put("zxcsxxList",  zxcsxxList);
            map.put("zxakxxList",  zxakxxList);
            map.put("ysqkList",    ysqkList);
            returnMap.put("flag", "1");
			returnMap.put("data", map);
			return returnMap;
		}catch(Exception e){
			logger.error("参数:"+ah+";错误详情："+e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}
	}
	@RequestMapping(value = "/getAjscList" ,method = RequestMethod.GET)
	public Map<String, Object> getAjscList(@RequestParam("date")String date){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		try{
			if(StringUtils.isEmpty(date)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "参数startDate或者endDate不能为空");
				return returnMap;
			}
			if(!patternDate(date)){
				returnMap.put("flag", "-1");
				returnMap.put("data", "【开始时间】格式不正确,请用YYYYMMDD格式字符串类型日期");
				return returnMap;
			}
			List<String> ajscList = guanganService.getAjscList(date);
			
            returnMap.put("flag", "1");
			returnMap.put("data", ajscList);
			return returnMap;
		}catch(Exception e){
			logger.error("参数:"+date+";错误详情："+e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getJzxx" ,method = RequestMethod.GET)
	public Map<String, Object> getJzxx(@RequestParam("ajbs")String ajbs){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("ajbs", ajbs);
			guanganService.getJzxx(param);
			
			
			
			List<Daxx>   daxxList   = (List<Daxx>) param.get("v_cursor1");
			List<DaxxGc> daxxGcList = (List<DaxxGc>) param.get("v_cursor2");
			List<Mlxx>   mlxxList   = (List<Mlxx>) param.get("v_cursor3");
			List<MlxxGc> mlxxGcList = (List<MlxxGc>) param.get("v_cursor4");
			List<Stwj>   stwjList   = (List<Stwj>) param.get("v_cursor5");
			List<StwjGc> stwjGcList = (List<StwjGc>) param.get("v_cursor6");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("daxxList",   daxxList);
            map.put("daxxGcList", daxxGcList);
            map.put("mlxxList",   mlxxList);
            map.put("mlxxGcList", mlxxGcList);
            map.put("stwjList",   stwjList);
            map.put("stwjGcList", stwjGcList);
            returnMap.put("flag", "1");
			returnMap.put("data", map);
			return returnMap;
		}catch(Exception e){
			logger.error("错误详情："+e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getZx" ,method = RequestMethod.GET)
	public Map<String, Object> getZx(@RequestParam("ajbs")String ajbs){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("ajbs", ajbs);
			guanganService.getZx(param);
			
			List<Jc> jsList = (List<Jc>) param.get("v_cursor1");
			List<Sc> scList = (List<Sc>) param.get("v_cursor2");
			List<Fk> fkList = (List<Fk>) param.get("v_cursor3");
			List<Jl> jlList = (List<Jl>) param.get("v_cursor4");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("jsList", jsList);
            map.put("scList", scList);
            map.put("fkList", fkList);
            map.put("jlList", jlList);
            returnMap.put("flag", "1");
			returnMap.put("data", map);
			return returnMap;
		}catch(Exception e){
			logger.error("错误详情："+e.getMessage());
			returnMap.put("flag", "-1");
			returnMap.put("data", e.getMessage());
			return returnMap;
		}
	}
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年6月26日 下午5:46:00
	 * @param dateStr
	 * @return
	 */
	private static boolean patternDate(String dateStr){
		String eL = "(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}
	
	public long day(String startD, String endD) throws ParseException{
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");    
	    java.util.Date beginDate;
	    java.util.Date endDate;
	    beginDate = format.parse(startD);
	    endDate= format.parse(endD);    
	    long day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
	    return day;
	}
}
