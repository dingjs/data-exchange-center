package data.exchange.center.monitor.repository;


import java.util.List;

import data.exchange.center.monitor.domain.modle.Role;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:06</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface RoleRepository {

    void add(Role role);

    void update(Role role);

    void updateMenus(String rid, List<String> mids);

    void updateResources(String rid, List<String> resources);

    boolean contains(String roleName);

    Role get(String id);

    List<Role> list();

    void remove(String id);

    void removeRoleMenuByMenuId(String menuId);

    void removeRoleResourceByResourceId(String resourceId);

    void switchStatus(String id,boolean disabled);

    List<Role> getRoles(String userId);


}
