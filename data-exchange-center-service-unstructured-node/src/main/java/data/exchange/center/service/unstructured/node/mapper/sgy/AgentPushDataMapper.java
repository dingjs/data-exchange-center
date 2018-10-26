/**
 * data-governance-unstructured-agent-push
 * created by yuguang at 2017年5月24日
 */
package data.exchange.center.service.unstructured.node.mapper.sgy;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import data.exchange.center.service.unstructured.node.domain.Ajxx;
import data.exchange.center.service.unstructured.node.domain.FjghInit;
import data.exchange.center.service.unstructured.node.domain.RcbAjbs;
import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAll;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxx;
import data.exchange.center.service.unstructured.node.domain.TempEajMlxxGc;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;

/**
 * 
 * Description:
 * <p>
 * Company: xinya
 * </p>
 * <p>
 * Date:2017年10月11日 下午5:02:01
 * </p>
 * 
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Mapper
public interface AgentPushDataMapper {

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:54:46
     * @param tempEajMlxx
     * @return
     */
    int pushEajMlxxToSGY(List<TempEajMlxx> tempEajMlxx) throws Exception;

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:55:10
     * @param tempEajSsjcyx
     * @return
     */
    int pushEajSsjcyxToSGY(List<TempEajSsjcyx> tempEajSsjcyx) throws Exception;

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:55:18
     * @param tempEajMlxxGc
     * @return
     */
    int pushEajMlxxGcToSGY(List<TempEajMlxxGc> tempEajMlxxGc) throws Exception;

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:55:26
     * @param tempEajJzwjAll
     * @return
     */
    int pushEajJzwjAllToSGY(TempEajJzwjAll tempEajJzwjAll) throws Exception;

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:55:34
     * @param tempEajJz
     * @return
     */
    int pushEajJzToSGY(TempEajJz tempEajJz) throws Exception;

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年5月25日 上午11:55:40
     * @param tempEajJzwjAllNew
     * @return
     */
    int pushEajJzwjAllNewToSGY(List<TempEajJzwjAllNew> tempEajJzwjAllNew) throws Exception;

    /**
     * @function
     * @author BMJ
     * @param
     * @return
     */
    public int pushEajAll(Map<String, Object> map) throws Exception;

    /**
     * 
     * @function
     * @author wenyuguang
     * @creaetime 2017年6月20日 下午5:04:17
     * @param ajbs
     * @return
     */
    String isExist(String ajbs);

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年6月20日 下午5:11:08
     * @param string
     */
    void updateEajSsjcyxAJZT(String ajbs);

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年6月20日 下午5:11:19
     * @param string
     */
    void updateEajMlxxAJZT(String ajbs);

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年6月20日 下午5:11:29
     * @param string
     */
    void updateEajMlxxGcAJZT(String ajbs);

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年6月20日 下午5:11:35
     * @param string
     */
    void updateEajJzAJZT(String ajbs);

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年6月20日 下午5:11:41
     * @param string
     */
    void updateEajJzAllNewAJZT(String ajbs);

    /**
     * @function
     * @author wenyuguang
     * @creaetime 2017年6月20日 下午5:55:53
     * @param Map
     */
    void insertTempEajAll(Map<String, Object> map);

    /**
     * @function
     * @author bmj
     * @return
     */
    int getAjbs(String ajbs);

    /**
     * 
     * @function 获取总记录数
     * @author bmj
     * @creaetime 2017年10月12日 下午5:18:09
     * @return
     * @throws Exception
     */
    int getPageAllAjxxFromSgyRw(Map<String, Object> params) throws Exception;

    /**
     * @function
     * @author bmj
     * @param params
     * @creaetime 2017年5月24日 下午6:05:42
     * @return
     */
    List<Ajxx> getAllAjxxFromSgyRw(Map<String, Object> params) throws Exception;

    /**
     * @function
     * @author wenyuguang
     * @param params
     * @creaetime 2017年5月24日 下午6:05:42
     * @return
     */
    List<Ajxx> getAllAjxxFromSgy(Map<String, Object> params) throws Exception;

    /**
     * 
     * @function 获取总记录数
     * @author wenyuguang
     * @creaetime 2017年10月12日 下午5:18:09
     * @return
     * @throws Exception
     */
    int getPageAllAjxxFromSgy(String fydm) throws Exception;

    /**
     * 
     * @function 获取回收池数据
     * @author bmj
     * @creaetime 2017年10月12日 下午5:18:09
     * @return
     * @throws Exception
     */
    List<RcbAjbs> getDelRedis(String fydm) throws Exception;

    /**
     * @Title: getAllAjxxFromDelRedis 
     * @Description: 查找出需要重新跑的数据
     *  @param @param
     * fydm @param @return 参数 @return List<Ajxx> 返回类型 @throws
     */
    Ajxx getAllAjxxFromDelRedis(String ajbs) throws Exception;

    /**
     * @Title: getAllAjxxFromDelRedis @Description: 删除改法院回收次的案件 @param @param
     * fydm @param @return 参数 @return List<Ajxx> 返回类型 @throws
     */
    int getDelRcbAjbs(String ajbs) throws Exception;
    /**
     * @Title: pushFjghInit
     * @Description: 写入临时缓存表
     * @param @param map    参数
     * @return void    返回类型
     * @throws
     */
    int pushFjghInit(Map<String, Object> map) throws Exception;
    /**
     * @Title: pushFjghInit
     * @Description: 删除临时缓存表
     * @param @param map    参数
     * @return void    返回类型
     * @throws
     */
    int delFjghInit(Map<String, Object> map) throws Exception;
    /**
     * @Title: delTempAllAndInit
     * @Description: 第一次进入删除所有上次可能残留的数据
     * @param @param map
     * @param @return
     * @param @throws Exception    参数
     * @return int    返回类型
     * @throws
     */
    int delTempAllAndInit(Map<String, Object> map) throws Exception;
    
    /**
     * @Title: delTempAllToInit
     * @Description: 初始化缓存表，以免上次强制关闭有数据残留
     * @param @param fydm    参数
     * @return void    返回类型
     * @throws
     */
    int  delTempAllToInit(String fydm) throws Exception;
    /**
     * @Title: getFjghInit
     * @Description:获取非结构化临时锁定表
     * @param @param fydm
     * @param @return
     * @param @throws Exception    参数
     * @return List<FjghInit>    返回类型
     * @throws
     */
    List<FjghInit> getFjghInit (String fydm)  throws Exception;
    
    
}
