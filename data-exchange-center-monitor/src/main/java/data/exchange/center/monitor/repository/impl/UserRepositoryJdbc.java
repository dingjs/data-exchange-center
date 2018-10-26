package data.exchange.center.monitor.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import data.exchange.center.monitor.domain.modle.User;
import data.exchange.center.monitor.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:18</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Repository
public class UserRepositoryJdbc implements UserRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void add(User user) {
        jdbcTemplate.update("INSERT dcadm.users (id,username,password,email,disabled,createTime,salt) VALUES (?,?,?,?,?,?,?)",user.getId(),user.getUsername(),user.getPassword(),user.getEmail(),user.isDisabled()?1:0,new Date(),user.getSalt());
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE dcadm.users SET username=?,email=?,password=? WHERE id=?",user.getUsername(),user.getEmail(),user.getPassword(),user.getId());
    }

    @Override
    public void updateRoles(String uid, List<String> rids) {
        jdbcTemplate.update("DELETE FROM dcadm.users_role WHERE \"UID\"=?", uid);
        if (!CollectionUtils.isEmpty(rids)) {
            jdbcTemplate.batchUpdate("INSERT dcadm.users_role (\"UID\",role_id) VALUES (?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, uid);
                    ps.setString(2, rids.get(i));
                }

                @Override
                public int getBatchSize() {
                    return rids.size();
                }
            });
        }
    }

    @Override
    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from dcadm.users where id=?", BeanPropertyRowMapper.newInstance(User.class),id);
    }

    @Override
    public boolean contains(String name) {
        return jdbcTemplate.query("select count(username) from dcadm.users where username=?", rs -> rs.getInt(1)>0,name);
    }

    @Override
    public List<User> list() {
        return jdbcTemplate.query("select * from dcadm.users where username <> 'root'", BeanPropertyRowMapper.newInstance(User.class));
    }


    @Override
    public boolean hasResourcePermission(String uid,String resourceCode) {
        return jdbcTemplate.query("select count(*) from dcadm.users_role ur join dcadm.role_resource rr on ur.role_id=rr.role_id where ur.\"UID\"=? and rr.resource_id=?",rs -> rs.getInt(0)>0,uid,resourceCode);
    }



    @Override
    public void remove(String id) {
        User user=get(id);
        if(user.isRoot()){
            return;
        }
        jdbcTemplate.update("DELETE FROM dcadm.users WHERE id=?",id);
        jdbcTemplate.update("DELETE FROM dcadm.users_role WHERE \"UID\"=?",id);
    }

    public void switchStatus(String id,boolean disabled){
        jdbcTemplate.update("update dcadm.users SET disabled=? WHERE id=?",disabled?1:0,id);
    }


    @Override
    public User findByUserName(String username) {
        try {
            return jdbcTemplate.queryForObject("select * from dcadm.users where username=? ", BeanPropertyRowMapper.newInstance(User.class), username);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

}
