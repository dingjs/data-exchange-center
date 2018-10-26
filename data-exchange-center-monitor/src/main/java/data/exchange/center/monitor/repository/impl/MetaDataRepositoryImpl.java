package data.exchange.center.monitor.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import data.exchange.center.monitor.domain.MetaCol;
import data.exchange.center.monitor.domain.MetaInfoclassify;
import data.exchange.center.monitor.repository.MetaDataRepository;
/**
 * @author baimaojun
 *
 */
@Repository
public class MetaDataRepositoryImpl implements MetaDataRepository {

	@Autowired
    protected JdbcTemplate jdbcTemplate;
	/**
	 * 获取表字段
	 */
	@Override
	public List<MetaCol> getTableCol(String nodeId) {
		String sql = "  select  c_tableid, n_sn, c_ecolname, c_ccolname, c_datatype,n_datalen,"
				+ "case when n_precision  is null then 0 else n_precision end as n_precision,"
				+ " case when c_desc is null then ' ' else c_desc end as c_desc, "
				+ "case when c_codeid  is null then ' ' else  c_codeid   end as c_codeid , "
				+ "c_notnull, c_pucol,c_userid, d_create, d_update from dcadm.dc_meta_tabcol where c_tableid "
				+ "  = (select C_TABLEID from dcadm.dc_meta_treetable  where C_NODEID=?)";
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MetaCol.class),nodeId);
	}
	/**
	 * 获取表数据
	 */
	@Override
	public List<MetaInfoclassify> getMetaInfoclassify(String treeid) {
		String sql="select  c_treeid as treeid , c_parentid as Pid ,c_nodeid as id,c_name as name,n_order as "
				+ "orders,c_type as type from dcadm.DC_META_INFOCLASSIFY t  order by n_order";
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MetaInfoclassify.class));
	}
	@Override
	public List<MetaInfoclassify> getMetaInfotree() {
		String sql ="select c_treeid as treeid ,c_treeid as id ,c_name as name ,'0' as type  from dcadm.dc_meta_infotree where c_treeid < 2";
		
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MetaInfoclassify.class));
	}

}
