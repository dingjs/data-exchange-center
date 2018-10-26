package data.exchange.center.monitor.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import data.exchange.center.monitor.domain.MainMeta;
import data.exchange.center.monitor.domain.MainMetaNode;
import data.exchange.center.monitor.domain.MetaTable;
import data.exchange.center.monitor.domain.modle.Menu;
import data.exchange.center.monitor.repository.MenuRepository;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:57:29</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Repository
public class MenuRepositoryJdbc implements MenuRepository{

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void add(Menu menu) {
        jdbcTemplate.update("INSERT into dcadm.menu (id,label,path,level,order,url,type,style,disabled) VALUES (?,?,?,?,?,?,?,?,?)", menu.getId(), menu.getLabel(), menu.getPath(), menu.getLevel(), menu.getOrder(), menu.getUrl(), menu.getType(), menu.getStyle(), menu.isDisabled() ? 1 : 0);
    }

    @Override
    public void update(Menu menu) {
        jdbcTemplate.update("update dcadm.menu SET label=?,order=?,url=?,disabled=?,type=?,style=? WHERE id=?", menu.getLabel(), menu.getOrder(), menu.getUrl(), menu.isDisabled() ? 1 : 0, menu.getType(), menu.getStyle(), menu.getId());
    }

    @Override
    public Menu get(String id) {
        return jdbcTemplate.queryForObject("select * from dcadm.menu where id=?",createMapper(),id);
    }

    @Override
    public boolean contains(String id) {
        return jdbcTemplate.queryForObject("select count(id) from dcadm.menu where id=?",Integer.class,id)>0;
    }

    @Override
    public List<Menu> list() {
        return jdbcTemplate.query("select * from dcadm.menu",createMapper());
    }

    @Override
    public void remove(String id) {       //删除父节点没必要删除所有子节点，因为删除父节点后子节点并不会被展示
        jdbcTemplate.update("DELETE FROM dcadm.menu WHERE id=?",id);
    }

    public void switchStatus(String id,boolean disabled){ //禁用父节点没必要禁用所有子节点，同上
        jdbcTemplate.update("update dcadm.menu SET disabled=? WHERE id=?",disabled?1:0,id);
    }

    private RowMapper<Menu> createMapper() {
//        return (rs, rowNum) -> {
//            Menu menu = new Menu();
//            menu.setId(rs.getString("id"));
//            menu.setLabel(rs.getString("label"));
//            menu.setUrl(rs.getString("url"));
//            menu.setDisabled(rs.getBoolean("disabled"));
//            menu.setPath(rs.getString("path"));
//            menu.setOrder(rs.getInt("order"));
//            menu.setType(rs.getInt("type"));
//            menu.setStyle(rs.getString("style"));
//            return menu;
//        };
        return BeanPropertyRowMapper.newInstance(Menu.class);
    }

    @Override
    public List<Menu> roleMenus(String roleId) {
        return jdbcTemplate.query("select m.* from dcadm.menu m join dcadm.role_menu rm on m.id=rm.menu_id where rm.role_id=?", createMapper(), roleId);
    }

    @Override
    public List<Menu> getNavMenus(String userId) {
        return jdbcTemplate.query("select m.* from dcadm.menu m join dcadm.role_menu rm on m.id=rm.menu_id join dcadm.user_role ur on rm.role_id=ur.role_id where m.disabled=0 and ur.uid=?", createMapper(),userId);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MainMeta> getMainMeta() {
		String sql = "{call DCADM.DC_METADATA.DBR_TREELIST(?)}";
		List<MainMeta> list = (List<MainMeta>) this.jdbcTemplate.execute(sql,  
                new CallableStatementCallback() {  
                    public Object doInCallableStatement(CallableStatement cs)  
                            throws SQLException, DataAccessException {  
                        List<MainMeta> mainMetaList = new ArrayList<MainMeta>();  
                        cs.registerOutParameter(1, OracleTypes.CURSOR);  
                        cs.execute();  
  
                        ResultSet rs = (ResultSet) cs.getObject(1);  
                        while (rs.next()) {  
                        	MainMeta mainMeta = new MainMeta();
                        	mainMeta.setTreeId(rs.getString("c_treeid"));
                        	mainMeta.setName(rs.getString("c_name"));
                        	mainMeta.setDesc(rs.getString("c_desc"));
                            mainMetaList.add(mainMeta);  
                        }  
                        return mainMetaList;  
                    }  
                });  
        return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> getMainMetaNode(String treeId) {
		String sql = "{call DCADM.DC_METADATA.DBR_TREEFLEV(?, ?, ?)}";
		Map<String, Object> mapList = (Map<String, Object>) jdbcTemplate.execute(sql,  
                new CallableStatementCallback() {  
                    public Object doInCallableStatement(CallableStatement cs)  
                            throws SQLException, DataAccessException {  
                    	List<MainMetaNode> mainMetaList = new ArrayList<MainMetaNode>();
                    	List<MainMetaNode> mainMetaLists = new ArrayList<MainMetaNode>();
                        cs.setString(1, treeId);
                        cs.registerOutParameter(2, OracleTypes.CURSOR);
                        cs.registerOutParameter(3, OracleTypes.CURSOR);
                        cs.execute();
  
                        ResultSet rs = (ResultSet) cs.getObject(2);  
                        while (rs.next()) {
                        	MainMetaNode mainMeta = new MainMetaNode();
                        	mainMeta.setTableId( rs.getString("c_tableid"));
                        	mainMeta.setTreeId(  rs.getString("c_treeid"));
                        	mainMeta.setNodeId(  rs.getString("c_nodeid"));
                        	mainMeta.setNodeType(rs.getString("c_nodetype"));
                        	mainMeta.seteTbName( rs.getString("c_etbname"));
                        	mainMeta.setcTbName( rs.getString("c_ctbname"));
                        	mainMeta.setDesc(    rs.getString("c_desc"));
                        	mainMeta.setAjlx(    rs.getString("c_ajlxcode"));
                        	
                            mainMetaList.add(mainMeta);  
                        }
                        ResultSet rss = (ResultSet) cs.getObject(3);  
                        while (rss.next()) {
                        	MainMetaNode mainMeta = new MainMetaNode();
                        	mainMeta.setTableId( rss.getString("c_tableid"));
                        	mainMeta.setTreeId(  rss.getString("c_treeid"));
                        	mainMeta.setNodeId(  rss.getString("c_nodeid"));
                        	mainMeta.setNodeType(rss.getString("c_nodetype"));
                        	mainMeta.seteTbName( rss.getString("c_etbname"));
                        	mainMeta.setcTbName( rss.getString("c_ctbname"));
                        	mainMeta.setDesc(    rss.getString("c_desc"));
                        	mainMeta.setAjlx(    rss.getString("c_ajlxcode"));
                        	
                        	mainMetaLists.add(mainMeta);  
                        }
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("info", mainMetaList);
                        map.put("node", mainMetaLists);
                        return map;
                    }  
                });  
        return mapList;
	}

	@Override
	public List<MetaTable> getTables(String ajlx) {
		String sql = "select c_tableid ctableid,c_treeid ctreeid,"
				+ "c_nodeid cnodeid,c_etbname cetbname,c_ctbname cctbname,c_desc cdesc,"
				+ "c_ajlxcode cajlxcode,c_userid cuserid,d_create dcreate,d_update dupdate "
				+ "from dcadm.dc_meta_treetable "
				+ "where c_ajlxcode =?";
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MetaTable.class), ajlx);
	}
	
	
	
}
