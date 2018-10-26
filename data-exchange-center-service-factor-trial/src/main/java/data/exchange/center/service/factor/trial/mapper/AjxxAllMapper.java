package data.exchange.center.service.factor.trial.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface AjxxAllMapper {
	/**
	 * 获取所有的数据表
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getTablename(String ajlx) throws Exception;
	/**
	 * 获取数据
	 * @param ajbs
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getAjxx(Map<String, Object> map) throws Exception;
	/**
	 * 获取数据
	 * @param ajbs
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	String getAjlxMc(String ajlx) throws Exception;
	
	/**
	 * 获取数据
	 * @param ajbs
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getAjDaj(Map<String, Object> map) throws Exception;
	/**
	 * 获取数据
	 * @param ajbs
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getAjGcj(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取数据
	 * @param ajbs
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getajbs(Map<String, Object> param) throws Exception;
	
	/**
	 * 获取所有的数据表
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getMetaTablename() throws Exception;
	
	/**
	 * 获取所有的数据表
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getMetaTabcol(String tableId) throws Exception;
	/**
	 * 获取所有的数据表
	 * @param ajlx
	 * @return
	 * @throws Exception
	 */
	Integer insertAjbs(String ajbs) throws Exception;
}
