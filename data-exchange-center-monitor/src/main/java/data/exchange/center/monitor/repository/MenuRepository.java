package data.exchange.center.monitor.repository;


import java.util.List;
import java.util.Map;

import data.exchange.center.monitor.domain.MainMeta;
import data.exchange.center.monitor.domain.MetaTable;
import data.exchange.center.monitor.domain.modle.Menu;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface MenuRepository {

    void add(Menu menu);

    void update(Menu menu);

    Menu get(String code);

    boolean contains(String code);

    List<Menu> list();

    void remove(String code);

    void switchStatus(String code,boolean disabled);

    List<Menu> roleMenus(String roleId);

    List<Menu> getNavMenus(String userId);
    /**
     * 
     * @function 获取元数据主菜单
     * @author wenyuguang
     * @creaetime 2017年7月19日 上午11:42:25
     * @return
     */
    List<MainMeta> getMainMeta();
    
    /**
     * 
     * @function 根据元数据主菜单树ID查询
     * @author wenyuguang
     * @creaetime 2017年7月19日 上午11:42:30
     * @return
     */
    Map<String, Object> getMainMetaNode(String treeId);

    /**
     * 
     * @function 根据ajlx获取元数据表列表
     * @author wenyuguang
     * @creaetime 2017年7月20日 上午10:09:53
     * @param ajlx
     * @return
     */
	List<MetaTable> getTables(String ajlx);
}
