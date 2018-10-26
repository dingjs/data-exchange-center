package data.exchange.center.monitor.repository;

import java.util.List;
import java.util.Map;

import data.exchange.center.monitor.domain.MetaCol;
import data.exchange.center.monitor.domain.MetaInfoclassify;


/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月20日 上午10:57:44</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface MetaDataRepository {

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年7月20日 上午10:59:37
	 * @param table
	 * @return
	 */
	List<MetaCol> getTableCol(String tableId);
	/**
	 * baimaojun
	 * 获取元数据树队列
	 * @return
	 */
    List<MetaInfoclassify> getMetaInfoclassify(String treeid);
    List<MetaInfoclassify> getMetaInfotree();

}
