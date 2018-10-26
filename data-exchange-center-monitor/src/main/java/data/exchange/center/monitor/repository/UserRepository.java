package data.exchange.center.monitor.repository;

import java.util.List;

import data.exchange.center.monitor.domain.modle.User;


/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:03</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface UserRepository {

    void add(User user);

    void update(User user);

    void updateRoles(String uid, List<String> rids);

    User get(String id);

    boolean contains(String name);

    List<User> list();

    boolean hasResourcePermission(String userId,String resourceCode);

    void remove(String id);

    void switchStatus(String id,boolean disabled);

    User findByUserName(String username);



}
