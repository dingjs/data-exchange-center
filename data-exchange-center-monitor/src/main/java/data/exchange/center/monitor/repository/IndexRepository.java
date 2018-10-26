package data.exchange.center.monitor.repository;

import data.exchange.center.monitor.domain.Mraj;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月24日 下午3:11:37</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface IndexRepository {

	/**
	 * 
	 * @function 新收案件
	 * @author wenyuguang
	 * @creaetime 2017年7月24日 下午3:10:09
	 * @return
	 */
	Mraj getMraj();
	
	/**
	 * 
	 * @function 更新案件
	 * @author wenyuguang
	 * @creaetime 2017年7月24日 下午3:10:35
	 * @return
	 */
	int getUpdateCaseCount();
	
	/**
	 * 
	 * @function 删除案件
	 * @author wenyuguang
	 * @creaetime 2017年7月24日 下午3:11:07
	 * @return
	 */
	int getDeleteCaseCount();
	/**
	 * 
	 * @function 各市新收旧存已结未接案件
	 * @author baimaojun
	 * @creaetime 2017年8月11日
	 * @return
	 */
	Object getAjtj(String ajzt);
	/**
	 * 
	 * @function 各区/县新收旧存已结未接案件
	 * @author baimaojun
	 * @creaetime 2017年8月11日
	 * @return
	 */
	Object getAjs(String ajzt,String fymc);


}
