package data.exchange.center.service.huayu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月15日 上午11:17:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface HuayuMapper {

	/**
	 * 获取数据
	 * @param ajbs
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getAjlb(Map<String, Object> map) throws Exception;
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
	String getAjQz(String ajlx) throws Exception;
	
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
	 * 获取组织机构
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getZzjg(Map<String, Object> map) throws Exception;
	/**
	 * 获取组织人员
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> getZzry(Map<String, Object> map) throws Exception;

}
