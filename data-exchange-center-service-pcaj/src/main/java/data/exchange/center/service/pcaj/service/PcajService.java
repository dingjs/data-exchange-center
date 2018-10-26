package data.exchange.center.service.pcaj.service;

import java.util.List;
import java.util.Map;

import data.exchange.center.service.pcaj.domain.ColMeta;
import data.exchange.center.service.pcaj.domain.TableMeta;

public interface PcajService {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年12月28日 上午9:53:01
	 * @param xml xml内容
	 * @param prex 最高对应的法院代码（需要转换）
	 * @param ajlx 案件类型代码
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> parseXml(String xml, String prex, String ajlx) throws Exception;
	
	List<ColMeta> getColMeta(String ajlx) throws Exception;

	void deleteTableRecord(String deleteSql) throws Exception;

	List<TableMeta> getTableMeta(String param) throws Exception;

	int saveCase(String insertSql, Map<String, Object> param) throws Exception;

}
