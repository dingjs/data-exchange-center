package data.exchange.center.monitor.repository;

import java.util.List;

import data.exchange.center.monitor.domain.RegAppclassify;

/**
 * 
 * @author baimaojun
 *
 */
public interface RegAppClassIfyRepository {
	/*
	 * 获取业务分类树数据表
	 */
	List<RegAppclassify> getRegAppClassIfy();
	/*
	 * 获取业务分类树数据表app
	 */
	List<RegAppclassify> getRegApp();
	
	/*
	 * 写入数据
	 */
	int setRegAppClassIfy(String sqlSrc);
	
	/*
	 * 刪除数据
	 */
	int deleteRegAppClassIfy(String sqlSrc);
}
