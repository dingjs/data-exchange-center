/**
 * data-governance-unstructured-agent-push
 * created by yuguang at 2017年5月24日
 */
package data.exchange.center.service.unstructured.node.service;

import java.util.List;
import java.util.Map;

import data.exchange.center.service.unstructured.node.domain.Ajxx;
import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年5月24日 下午12:57:28</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public interface AgentGetDataService {

    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月24日 下午5:04:14
     * @return
     * @throws Exception
     */
    List<TempEajJz> getEajJzFromTDH(String ajbs) throws Exception;
    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月24日 下午5:04:31
     * @return
     * @throws Exception
     */
    List<TempEajJzwjAll> getEajJzWjAllFromTDH(String ajbs) throws Exception;
    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月24日 下午5:05:16
     * @return
     * @throws Exception
     */
    List<TempEajJzwjAllNew> getEajJzwjAllNewFromTDH(String ajbs) throws Exception;
    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月24日 下午5:05:20
     * @return
     * @throws Exception
     */
    List<TempEajMlxx> getEajMlxxFromTDH(String ajbs) throws Exception;
    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月24日 下午5:05:23
     * @return
     * @throws Exception
     */
    List<TempEajMlxxGc> getEajMlxxGcFromTDH(String ajbs) throws Exception;
    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月24日 下午5:05:25
     * @return
     * @throws Exception
     */
    List<TempEajSsjcyx> getEajSsjcyxFromTDH(String ajbs) throws Exception;
    
    /**
     * 
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月24日 下午6:04:52
     * @return
     * @throws Exception
     */
    List<Ajxx> getAllAjxxFromTDH(Map<String, Object> params) throws Exception;
}
