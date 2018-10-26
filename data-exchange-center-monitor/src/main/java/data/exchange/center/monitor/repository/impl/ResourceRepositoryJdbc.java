package data.exchange.center.monitor.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import data.exchange.center.monitor.domain.modle.Resource;
import data.exchange.center.monitor.repository.ResourceRepository;

import java.util.List;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:25</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Repository
public class ResourceRepositoryJdbc implements ResourceRepository{

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void add(Resource resource) {
        jdbcTemplate.update("INSERT INTO dcadm.resources (id,title,disabled,url,description) VALUES (?,?,?,?,?)",resource.gettId(),resource.gettTitle(),resource.istDisabled()?1:0,resource.gettUrl(),resource.gettDescription());
    }

    @Override
    public void update(Resource resource) {
        jdbcTemplate.update("UPDATE dcadm.resources SET title=?,disabled=?,url=?,description=? WHERE id=?",resource.gettTitle(),resource.istDisabled()?1:0,resource.gettUrl(),resource.gettDescription(),resource.gettId());
    }

    @Override
    public Resource get(String id) {
        return jdbcTemplate.queryForObject("select * from dcadm.resources where id=?",BeanPropertyRowMapper.newInstance(Resource.class),id);
    }

    @Override
    public List<Resource> list() {
        return jdbcTemplate.query("select * from dcadm.resources",BeanPropertyRowMapper.newInstance(Resource.class));
    }

    @Override
    public void remove(String id) {
        jdbcTemplate.update("DELETE FROM dcadm.resources WHERE id=?",id);
    }

    public void switchStatus(String id,boolean disabled){
        jdbcTemplate.update("update dcadm.resources SET disabled=? WHERE id=?",disabled?1:0,id);
    }


    @Override
    public List<Resource> listByRole(String roleId) {
        return jdbcTemplate.query("select re.* from  dcadm.role_resource rr  join dcadm.resources re on re.id=rr.resource_id where rr.role_id=?", BeanPropertyRowMapper.newInstance(Resource.class), roleId);
    }


//    @Override
//    public List<Resource> getUrlResources(String userId) {
//        return jdbcTemplate.query("select r.* from resource r join role_resource rr on r.id=rr.resource_id join user_role ur on rr.role_id=ur.role_id where ur.uid=?",BeanPropertyRowMapper.newInstance(Resource.class),userId);
//    }

    @Override
    public List<Resource> getEnableResources() {
        return jdbcTemplate.query("select * from dcadm.resources WHERE disabled=0",BeanPropertyRowMapper.newInstance(Resource.class));

    }
}
