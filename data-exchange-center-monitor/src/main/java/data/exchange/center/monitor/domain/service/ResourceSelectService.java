package data.exchange.center.monitor.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import data.exchange.center.monitor.domain.modle.Menu;
import data.exchange.center.monitor.domain.modle.Resource;
import data.exchange.center.monitor.domain.modle.SelectMenu;
import data.exchange.center.monitor.domain.modle.SelectResource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:53:30</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class ResourceSelectService {

    /***
     * 角色具备的资源与所有资源合并，合并结果是所有的资源中，角色具备的资源checked=true
     *
     * @param all
     * @param part
     * @return
     */
    public List<SelectResource> mergeResource(List<Resource> all, List<Resource> part) {
        if (CollectionUtils.isEmpty(all)) {
            return null;
        }

        if (CollectionUtils.isEmpty(part)) {
            return all.stream().map(role -> new SelectResource(role.gettId(), role.gettTitle(), false)).collect(Collectors.toList());
        }

        return all.stream().map(role -> {
            if (part.contains(role)) {
                return new SelectResource(role.gettId(), role.gettTitle(), true);
            }
            return new SelectResource(role.gettId(), role.gettTitle(), false);
        }).collect(Collectors.toList());
    }


    /***
     * 角色具备的菜单资源与所有菜单合并，合并结果是所有的菜单中，角色具备的菜单checked=true
     *
     * @param all
     * @param part
     * @return
     */
    public List<SelectMenu> mergeMenus(List<Menu> all, List<Menu> part) {
        if (CollectionUtils.isEmpty(all)) {
            return null;
        }

        if (CollectionUtils.isEmpty(part)) {
            return all.stream().map(role -> new SelectMenu(role.getId(), role.getLabel(), false)).collect(Collectors.toList());
        }

        return all.stream().map(role -> {
            if (part.contains(role)) {
                return new SelectMenu(role.getId(), role.getLabel(), true);
            }
            return new SelectMenu(role.getId(), role.getLabel(), false);
        }).collect(Collectors.toList());
    }
}
