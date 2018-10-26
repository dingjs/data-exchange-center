package data.exchange.center.service.meishan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import data.exchange.center.service.meishan.domain.AjbsInfo;
import data.exchange.center.service.meishan.domain.DsrInfo;
import data.exchange.center.service.meishan.domain.MssAjbsInfo;
import data.exchange.center.service.meishan.domain.WsInfo;
import data.exchange.center.service.meishan.domain.Yastml;
import data.exchange.center.service.meishan.domain.ZxajAjbsInfo;
import data.exchange.center.service.meishan.mapper.MeishanMapper;
import data.exchange.center.service.meishan.service.MeishanService;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年8月31日 上午10:04:33</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service("meishanService")
public class MeishanServiceImpl implements MeishanService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MeishanMapper meishanMapper;
	
	@Override
	public List<AjbsInfo> getMssAjbsList(String startDate, String endDate, String pageNum) throws Exception {
		logger.info("params:"+startDate+","+ endDate+", "+pageNum);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("min", (Integer.valueOf(pageNum)-1)*10000);
		params.put("max", Integer.valueOf(pageNum)*10000);
		
		List<AjbsInfo> mssAjbsList = meishanMapper.getMssAjbsList(params);
		return mssAjbsList;
	}

	@Override
	public Map<String, Object> getMssAjbsInfo(String ajbs) throws Exception {
		logger.info("parameters: "+ajbs);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ajbs", ajbs);
		meishanMapper.getAjbsInfo(param);
		@SuppressWarnings("unchecked")
		List<MssAjbsInfo> mssAjbsInfo = (List<MssAjbsInfo>) param.get("v_cursor1");
		@SuppressWarnings("unchecked")
		List<DsrInfo> dsrInfo         = (List<DsrInfo>)     param.get("v_cursor2");
		
		@SuppressWarnings("unchecked")
		List<Yastml>  yastml          = (List<Yastml>)      param.get("v_cursor4");
		param.clear();
		param.put("ajbsInfo", mssAjbsInfo);
		param.put("dsrInfo",  dsrInfo);
		param.put("yastml",   yastml);
		return param;
	}

	@Override
	public List<AjbsInfo> getZxajAjbsList(String startDate, String endDate, String pageNum) throws Exception {
		logger.info("params:"+startDate+","+ endDate+", "+pageNum);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("min", (Integer.valueOf(pageNum)-1)*10000);
		params.put("max", Integer.valueOf(pageNum)*10000);
		List<AjbsInfo> zxajAjbsList = meishanMapper.getZxajAjbsList(params);
		return zxajAjbsList;
	}

	@Override
	public Map<String, Object> getZxajAjbsInfo(String ajbs) throws Exception {
		logger.info("parameters: "+ajbs);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ajbs", ajbs);
		meishanMapper.getAjbsInfo(param);
		@SuppressWarnings("unchecked")
		List<ZxajAjbsInfo> zxajAjbsInfo = (List<ZxajAjbsInfo>) param.get("v_cursor3");
		param.clear();
		param.put("ajbsInfo", zxajAjbsInfo);
		return param;
	}

	@Override
	public Map<String, Object> getWsInfo(String ajbs, String fydm, String ajlx) throws Exception {
		logger.info("parameter: "+ajbs+", "+fydm+", "+ajlx);
		String enAjlx = meishanMapper.getEnAjlx(ajlx);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ajbs", ajbs);
		param.put("fydm", fydm);
		param.put("ajlx", enAjlx);
		List<WsInfo> wsInfoList = meishanMapper.getWsInfo(param);
		param.clear();
		int index = 1;
		if(wsInfoList.size()>0) {
			for(WsInfo wsInfo:wsInfoList) {
				if(StringUtils.isEmpty(wsInfo)) {
					param.put("nr"+index,"");
					param.put("count", 0);
				}else {
					param.put("count", wsInfoList.size());
					param.put("nr"+index, new String(wsInfo.getNR().getBytes((long)1, (int)wsInfo.getNR().length())));
				}
				index = index +1;
			}
		}
		return param;
	}

	@Override
	public Object getZxajAjbsCount(String startDate, String endDate) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startDate", startDate);
		param.put("endDate",   endDate);
		int count = meishanMapper.getZxajAjbsCount(param);
		int end = (count  +  10000  - 1) / 10000;
		return end;
	}

	@Override
	public Object getMssAjbsCount(String startDate, String endDate) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startDate", startDate);
		param.put("endDate",   endDate);
		int count = meishanMapper.getMssAjbsCount(param);
		int end = (count  +  10000  - 1) / 10000;
		return end;
	}
}
