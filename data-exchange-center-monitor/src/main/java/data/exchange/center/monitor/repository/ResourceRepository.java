package data.exchange.center.monitor.repository;


import java.util.List;

import data.exchange.center.monitor.domain.modle.Resource;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:09</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public interface ResourceRepository {

    void add(Resource resource);

    void update(Resource resource);

    Resource get(String code);

    List<Resource> list();

    void remove(String code);

    void switchStatus(String code,boolean disabled);

    List<Resource> listByRole(String roleId);

    List<Resource> getEnableResources();



}
