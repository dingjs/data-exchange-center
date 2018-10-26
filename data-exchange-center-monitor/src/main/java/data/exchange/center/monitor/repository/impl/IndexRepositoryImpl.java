package data.exchange.center.monitor.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import data.exchange.center.monitor.domain.Mraj;
import data.exchange.center.monitor.domain.RegAppclassify;
import data.exchange.center.monitor.domain.modle.Ajzt;
import data.exchange.center.monitor.repository.IndexRepository;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月24日 下午3:13:47</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Repository
public class IndexRepositoryImpl implements IndexRepository {

	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	@Override
	public Mraj getMraj() {
		String sql = "select xz,gx,sc from dcadm.MONITOR_XZGXSC";
		List<Mraj>  MrajList= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Mraj.class));
		return  MrajList.get(0);
	}

	@Override
	public int getUpdateCaseCount() {
		return jdbcTemplate.queryForObject("select count(1) from sgy.buf_eaj "
				+ "where to_char(lastupdate,'yyyymmdd') = to_char(sysdate,'yyyymmdd') "
				+ "and to_char(larq,'yyyymmdd') <> to_char(sysdate,'yyyymmdd')",Integer.class);
	}

	@Override
	public int getDeleteCaseCount() {
		return jdbcTemplate.queryForObject("select count(distinct ajbs) from sgy.ajscxx_ajscxx "
				+ "where to_char(update_time,'yyyymmdd') = to_char(sysdate,'yyyymmdd')",Integer.class);
	}
	@Override
	public Object getAjtj(String ajzt) {
		return jdbcTemplate.query("select  "+ajzt+" ,fyjc from dcadm.MONITOR_ECHARTS left join dcadm.MONITOR_XSJCYJWJ on fydm=jbfy where  substr（fydm,5,2）='00' ",BeanPropertyRowMapper.newInstance(Ajzt.class));
	}
	@Override
	public 	Object getAjs(String ajzt,String fymc) {
		return jdbcTemplate.query("select  "+ajzt+",fyjc from dcadm.MONITOR_ECHARTS left join dcadm.MONITOR_XSJCYJWJ on fydm=jbfy where jbfy like（  select substr（fydm,1,4）||'%' from dcadm.MONITOR_ECHARTS where  fyjc =?）",BeanPropertyRowMapper.newInstance(Ajzt.class),fymc);
	}


}
