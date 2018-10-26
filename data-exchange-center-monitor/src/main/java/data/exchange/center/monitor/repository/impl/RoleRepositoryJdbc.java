package data.exchange.center.monitor.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import data.exchange.center.monitor.domain.modle.Role;
import data.exchange.center.monitor.repository.RoleRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:22</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Repository
public class RoleRepositoryJdbc implements RoleRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void add(Role role) {
       jdbcTemplate.update("INSERT dcadm.role (id,name,disabled,description) VALUES (?,?,?,?)",role.getId(),role.getName(),role.isDisabled()?1:0,role.getDescription());
    }

    @Override
    public void update(Role role) {
        jdbcTemplate.update("update dcadm.role SET name =?,disabled=?,description=? WHERE id=?",role.getName(),role.isDisabled()?1:0,role.getDescription(),role.getId());
    }

    @Override
    public void updateMenus(String rid, List<String> mids) {
        jdbcTemplate.update("DELETE FROM dcadm.role_menu WHERE role_id=?", rid);
        if (!CollectionUtils.isEmpty(mids)) {
            jdbcTemplate.batchUpdate("INSERT dcadm.role_menu (role_id,menu_id) VALUES (?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, rid);
                    ps.setString(2, mids.get(i));
                }

                @Override
                public int getBatchSize() {
                    return mids.size();
                }
            });
        }

    }

    @Override
    public void updateResources(String rid, List<String> resources) {
        jdbcTemplate.update("DELETE FROM dcadm.role_resource WHERE role_id=?", rid);
        if (!CollectionUtils.isEmpty(resources)) {
            jdbcTemplate.batchUpdate("INSERT dcadm.role_resource (role_id,resource_id) VALUES (?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, rid);
                    ps.setString(2, resources.get(i));
                }

                @Override
                public int getBatchSize() {
                    return resources.size();
                }
            });
        }
    }

    @Override
    public boolean contains(String roleName) {
        return jdbcTemplate.queryForObject("select count(name) from dcadm.role where name=?", Integer.class,roleName)>0;
    }

    @Override
    public Role get(String id) {
        return jdbcTemplate.queryForObject("select * from dcadm.role where id=?", BeanPropertyRowMapper.newInstance(Role.class),id);
    }

    @Override
    public List<Role> list() {
        return jdbcTemplate.query("select id, name, disabled, description from dcadm.role",BeanPropertyRowMapper.newInstance(Role.class));
    }

    @Override
    public void remove(String id) {
        jdbcTemplate.update("DELETE FROM dcadm.role_menu WHERE role_id=?", id);
        jdbcTemplate.update("DELETE FROM dcadm.role_resource WHERE role_id=?", id);
        jdbcTemplate.update("DELETE FROM dcadm.role WHERE id=?",id);
    }

    @Override
    public void removeRoleMenuByMenuId(String menUid) {
        jdbcTemplate.update("DELETE FROM dcadm.role_menu WHERE menu_id=?", menUid);
    }

    @Override
    public void removeRoleResourceByResourceId(String resourceId) {
        jdbcTemplate.update("DELETE FROM dcadm.role_resource WHERE resource_id=?", resourceId);
    }

    public void switchStatus(String id, boolean disabled) {
        jdbcTemplate.update("update dcadm.role SET disabled=? WHERE id=?",disabled?1:0,id);
    }


    @Override
    public List<Role> getRoles(String userId) {
        return jdbcTemplate.query("select * from dcadm.role r join dcadm.user_role ur on r.id=ur.role_id where ur.\"UID\"=?",BeanPropertyRowMapper.newInstance(Role.class),userId);
    }
}
