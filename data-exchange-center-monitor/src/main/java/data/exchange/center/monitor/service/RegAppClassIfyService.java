package data.exchange.center.monitor.service;
/**
 * 
 * @author baimaojun
 *
 */
public interface RegAppClassIfyService {
	/*
	 * 获取业务分类树数据表
	 */
	Object getRegAppClassIfy();
	
	Object setRegAppClassIfy(String idArrayList);
	
	Object deleteRegAppClassIfy( String nodesIdList);
	
}
