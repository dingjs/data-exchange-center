/**
 * data-governance-unstructured-agent-push
 * created by yuguang at 2017年5月24日
 */
package data.exchange.center.service.unstructured.node.mapper.tdh;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.unstructured.node.domain.Ajxx;
import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年10月11日 下午5:02:06</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface AgentGetDataMapper {

    /**
     * @function 
     * @author wenyuguang
     * @param params 
     * @creaetime 2017年5月24日 下午6:05:42
     * @return
     */
    List<Ajxx> getAllAjxxFromTDH(Map<String, Object> params) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:56:56
     * @return
     */
    List<TempEajJz> getEajJzFromTDH(Map<String,String> map) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:57:03
     * @param ajbs
     * @return
     */
    List<TempEajJzwjAll> getEajJzWjAllFromTDH(String ajbs) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:57:09
     * @param ajbs
     * @return
     */
    List<TempEajJzwjAllNew> getEajJzwjAllNewFromTDH(String ajbs) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:57:18
     * @param ajbs
     * @return
     */
    List<TempEajMlxx> getEajMlxxFromTDH(String ajbs) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:57:26
     * @param ajbs
     * @return
     */
    List<TempEajMlxxGc> getEajMlxxGcFromTDH(String ajbs) throws Exception;

    /**
     * @function 
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:57:32
     * @param ajbs
     * @return
     */
    List<TempEajSsjcyx> getEajSsjcyxFromTDH(String ajbs) throws Exception;

    /**
     * 
     * @function 获取总记录数
     * @author wenyuguang
     * @creaetime 2017年10月12日 下午5:18:09
     * @return
     * @throws Exception
     */
	int getPageAllAjxxFromTDH() throws Exception;
	
	   /**
  * 
  * @function 获取总记录数
  * @author bmj
  * @creaetime 2017年10月12日 下午5:18:09
  * @return
  * @throws Exception
  */
	int getPageAllAjxxFromTDHRw(Map<String, Object> params) throws Exception;
 /**
  * @function 
  * @author bmj
  * @param params 
  * @creaetime 2017年5月24日 下午6:05:42
  * @return
  */
 List<Ajxx> getAllAjxxFromTDHRw(Map<String, Object> params) throws Exception;

}
