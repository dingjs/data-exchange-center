package data.exchange.center.monitor.service;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import data.exchange.center.monitor.domain.modle.SelectRole;
import data.exchange.center.monitor.domain.modle.User;
import data.exchange.center.monitor.domain.service.RoleSelectService;
import data.exchange.center.monitor.repository.RoleRepository;
import data.exchange.center.monitor.repository.UserRepository;

import java.util.Date;
import java.util.List;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:59:22</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected Md5PasswordEncoder md5PasswordEncoder;

    @Autowired
    protected RoleSelectService roleSelectService;

    @Autowired
    protected RoleRepository roleRepository;


    @SuppressWarnings("deprecation")
	@Caching(
            put = @CachePut(key = "#user.id"),
            evict = @CacheEvict(value = "user-list", key = "'list'")
    )
    public User create(User user) {
        validate(user);
        Assert.hasText(user.getPassword());
        user.setDisabled(false);
        user.setCreateTime(new Date());
        user.setSalt(RandomStringUtils.randomAscii(10));
        user.setPassword(md5PasswordEncoder.encodePassword(user.getPassword(), user.getSalt()));
        userRepository.add(user);
        return user;
    }


    @SuppressWarnings("deprecation")
	@Caching(
            put = @CachePut(key = "#user.id"),
            evict = @CacheEvict(value = "user-list", key = "'list'")
    )
    public User modify(User user) {
        Assert.hasText(user.getId());
        User old = get(user.getId());
        if (StringUtils.isNotBlank(user.getUsername())) {
            old.setUsername(user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            old.setPassword(md5PasswordEncoder.encodePassword(user.getPassword(), old.getSalt()));
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            old.setEmail(user.getEmail());
        }
        userRepository.update(old);
        return user;
    }

    @Caching(
            evict = {@CacheEvict(value = "user-list", key = "'list'"), @CacheEvict(key = "#id")}
    )
    public void delete(String id) {
        userRepository.remove(id);
    }

    @Cacheable
    public User get(String id) {
        return userRepository.get(id);
    }

    @Cacheable(value = "user-list", key = "'list'")
    public List<User> list() {
        return userRepository.list();
    }

    @Caching(
            evict = {@CacheEvict(value = "user-list", key = "'list'"), @CacheEvict(key = "#id")}
    )
    public void switchStatus(String id, boolean disable) {
        userRepository.switchStatus(id, disable);
    }



    @Caching(
            evict = {@CacheEvict(value = "user-nav-menu", key = "#uid")}
    )
    public void grantRole(String uid, List<String> roleIds) {
        userRepository.updateRoles(uid, roleIds);
    }


    @SuppressWarnings("deprecation")
	private void validate(User user) {
        Assert.hasText(user.getUsername());
        if (user.isRoot()) {
            throw new IllegalArgumentException("user loginName cannot is root");
        }
    }

    public List<SelectRole> selectRoles(String uid) {
        return roleSelectService.mergeRole(roleRepository.list(), roleRepository.getRoles(uid));
    }

}
