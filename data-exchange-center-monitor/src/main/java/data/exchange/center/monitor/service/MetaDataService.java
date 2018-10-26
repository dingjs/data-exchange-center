package data.exchange.center.monitor.service;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月20日 上午10:56:08</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface MetaDataService {

	void clear();

	/**
	 * 
	 * @function 根据表英文名获取列字段信息
	 * @author wenyuguang
	 * @creaetime 2017年7月20日 上午10:56:05
	 * @param table
	 * @return
	 */
	Object getTableCol(String nodeId);
	
	/**
	 * 
	 * @function 根据id组装结构
	 * @author wenyuguang
	 * @creaetime 2017年7月20日 上午10:56:05
	 * @param table
	 * @return
	 */
	Object getMetadata(String treeid);

}
