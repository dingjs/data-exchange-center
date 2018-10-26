package data.exchange.center.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import data.exchange.center.monitor.domain.modle.Resource;
import data.exchange.center.monitor.repository.ResourceRepository;
import data.exchange.center.monitor.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:58:59</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
@CacheConfig(cacheNames = "resource")
public class ResourceService {

    @Autowired
    protected ResourceRepository resourceRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Caching(
            put = @CachePut(key = "#resource.id"),
            evict = @CacheEvict(key = "'list'")
    )
    public Resource create(Resource resource) {
        validate(resource);
        resource.settId(UUID.randomUUID().toString());
        resourceRepository.add(resource);
        return resource;
    }

    @Caching(
            put = @CachePut(key = "#resource.id"),
            evict = @CacheEvict(key = "'list'")
    )
    public Resource modify(Resource resource) {
        validate(resource);
        resourceRepository.update(resource);
        return resource;
    }

    @Cacheable
    public Resource get(String code){
        return resourceRepository.get(code);
    }

    @Cacheable(key = "'list'")
    public List<Resource> list(){
        return resourceRepository.list();
    }

    @Caching(
            evict = {@CacheEvict(key = "#code"), @CacheEvict(key = "'list'")}
    )
    public void delete(String code){
        roleRepository.removeRoleResourceByResourceId(code);
        resourceRepository.remove(code);
    }

    @Caching(
            evict = {@CacheEvict(key = "#code"), @CacheEvict(key = "'list'")}
    )
    public void switchStatus(String code,boolean disable){
        resourceRepository.switchStatus(code,disable);
    }

    @SuppressWarnings("deprecation")
	private void validate(Resource resource) {
        Assert.hasText(resource.gettTitle());
        Assert.hasText(resource.gettUrl());

    }
}
