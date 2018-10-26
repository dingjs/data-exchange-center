package data.exchange.center.monitor.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import data.exchange.center.monitor.domain.modle.Role;
import data.exchange.center.monitor.domain.modle.SelectRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:53:32</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class RoleSelectService {

    /***
     * 用户具备的角色与所有角色合并，合并结果是所有的角色中，用户具备的角色checked=true
     *
     * @param all
     * @param part
     * @return
     */
    public List<SelectRole> mergeRole(List<Role> all, List<Role> part) {
        if (CollectionUtils.isEmpty(all)) {
            return null;
        }

        if (CollectionUtils.isEmpty(part)) {
            return all.stream().map(role -> new SelectRole(role.getId(), role.getName(), false)).collect(Collectors.toList());
        }

        return all.stream().map(role -> {
            if (part.contains(role)) {
                return new SelectRole(role.getId(), role.getName(), true);
            }
            return new SelectRole(role.getId(), role.getName(), false);
        }).collect(Collectors.toList());
    }
}
