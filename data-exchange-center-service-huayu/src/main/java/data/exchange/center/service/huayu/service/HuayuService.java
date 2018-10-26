package data.exchange.center.service.huayu.service;

import java.util.List;
import java.util.Map;

public interface HuayuService {

	public List<Map<String, Object>>  getAjlb(Map<String,Object> param) throws Exception;
	public Map<String, Object> getAjbsInfo(String ajbs, String ajlx) throws Exception;
	public Map<String, Object> getAjDaJz(String ajbs, String ajlx) throws Exception;
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
