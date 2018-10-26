/**
 * data-governance-unstructured-agent-push
 * created by yuguang at 2017年5月24日
 */
package data.exchange.center.service.unstructured.node.service;

import java.util.Map;

import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年5月24日 下午5:06:45</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public interface AgentPushDataService {

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:47:23
     * @param tempEajSsjcyx
     * @return
     */
    int pushEajSsjcyxToSGY(TempEajSsjcyx tempEajSsjcyx) throws Exception;
    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:47:41
     * @param tempEajMlxx
     * @return
     */
    int pushEajMlxxToSGY(TempEajMlxx tempEajMlxx) throws Exception;
    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:47:50
     * @param tempEajMlxxGc
     * @return
     */
    int pushEajMlxxGcToSGY(TempEajMlxxGc tempEajMlxxGc) throws Exception;
    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:48:17
     * @param tempEajJzwjAll
     * @return
     */
    int pushEajJzwjAllToSGY(TempEajJzwjAll tempEajJzwjAll) throws Exception;
    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:48:31
     * @param tempEajJz
     * @return
     */
    int pushEajJzToSGY(TempEajJz tempEajJz) throws Exception;
    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:49:50
     * @param tempEajJzwjAllNew
     * @return
     */
    int pushEajJzNewToSGY(TempEajJzwjAllNew tempEajJzwjAllNew) throws Exception;
    /**
     * @function 
     * @author BMJ
     * @creaetime 2017年5月25日 上午11:49:50
     * @param tempEajJzwjAllNew
     * @return
     */
	public int  pushEajAll(Map<String, Object> map)throws Exception;
	/**
	 * @Title: callFunction
	 * @Description: 写入排除日志
	 * @param @param map
	 * @param @throws Exception    参数
	 * @return void    返回类型
	 * @throws
	 */
	 public void callFunction(Map<String, Object> map)throws Exception;
	
}
