package data.exchange.center.monitor.interfaces.system.facade.assembler;

import org.springframework.util.CollectionUtils;

import data.exchange.center.monitor.domain.modle.User;
import data.exchange.center.monitor.infrastructure.BeanUtil;
import data.exchange.center.monitor.interfaces.system.facade.commondobject.ProfileCommand;
import data.exchange.center.monitor.interfaces.system.facade.commondobject.UserCommond;
import data.exchange.center.monitor.interfaces.system.facade.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月18日 下午12:18:28</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class UserAssembler {

    public static User commondToDomain(UserCommond commond) {
        User user=new User();
        BeanUtil.copeProperties(commond,user);
        return user;
    }

    public static User commondToDomain(String uid, UserCommond commond) {
        User user = new User();
        BeanUtil.copeProperties(commond, user);
        user.setId(uid);
        return user;
    }

    public static User profileToDomain(String uid, ProfileCommand commond) {
        User user = new User();
        BeanUtil.copeProperties(commond, user);
        user.setId(uid);
        return user;
    }


    public static UserDto domainToDto(User user){
        UserDto dto=new UserDto();
       BeanUtil.copeProperties(user,dto);
        return dto;
    }

    public static List<UserDto> domainToDto(List<User> user){
       if(CollectionUtils.isEmpty(user)){
           return null;
       }
        List<UserDto> dtos=new ArrayList<>(user.size());
        user.stream().forEach(user1 -> {
            dtos.add(domainToDto(user1));
        });
        return dtos;
    }

}
