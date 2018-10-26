package data.exchange.center.monitor.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import data.exchange.center.monitor.domain.RegAppclassify;
import data.exchange.center.monitor.repository.RegAppClassIfyRepository;
@Repository
public class RegAppClassIfyJdbc implements RegAppClassIfyRepository{
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	@Override
	public List<RegAppclassify> getRegAppClassIfy() {
	String sql ="SELECT  C_TREEID AS TREEID , C_PARENTID AS PID ,INFOCLASSIFY.C_NODEID AS ID,C_NAME AS NAME,N_ORDER AS ORDERS,C_TYPE AS TYPE ,C_APPCODE AS APPCODE "
				+ "FROM DCADM.DC_META_INFOCLASSIFY INFOCLASSIFY,DCADM.DC_REG_APPCLASSIFY APPCLASSIFY "
				+ "	WHERE INFOCLASSIFY.C_NODEID = APPCLASSIFY.C_NODEID ORDER BY N_ORDER";
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RegAppclassify.class));
	}
	@Override
	public List<RegAppclassify> getRegApp() {
		String sql ="select c_appcode appcode ,c_appname name, c_appcode id from dcadm.dc_reg_app order by c_appcode";
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RegAppclassify.class));
	}
	@Override
	public int setRegAppClassIfy(String sqlSrc) {
		String sql ="begin " + sqlSrc + " end;" ;
		return  jdbcTemplate.update(sql);
	}
	@Override
	public int deleteRegAppClassIfy(String sqlSrc) {
		String sql =" delete from dcadm.dc_reg_appclassify where" + sqlSrc;
		return  jdbcTemplate.update(sql);
	}
}
	