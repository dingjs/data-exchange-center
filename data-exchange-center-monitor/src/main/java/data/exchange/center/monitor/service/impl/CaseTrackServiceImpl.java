package data.exchange.center.monitor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.monitor.service.CaseTrackService;
import data.exchange.center.monitor.service.ElasticsearchService;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月25日 下午3:25:38</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class CaseTrackServiceImpl implements CaseTrackService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ElasticsearchService elasticsearchService;
	
	/**
	 * (non-Javadoc)
	 * @see org.bumishi.admin.service.CaseTrackService#getCaseTrack(java.lang.String)
	 */
	@Override
	public Object getCaseTrack(String ajbs) {
		try {
			return elasticsearchService.getCaseTrack(ajbs, 10, 50);
		}catch(Exception e) {
			logger.error("参数："+ajbs+",错误详情："+e.getMessage());
			return null;
		}
	}

	@Override
	public Object list() {
		Date d = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String dateNowStr = sdf.format(d);  
		try {
			return elasticsearchService.findLog("", "", "", dateNowStr, 10, 50);
		}catch(Exception e) {
			logger.error("参数："+dateNowStr+",错误详情："+e.getMessage());
			return null;
		}
	}
}
