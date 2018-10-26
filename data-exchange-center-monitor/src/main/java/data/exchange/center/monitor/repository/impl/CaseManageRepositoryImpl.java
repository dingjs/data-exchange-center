package data.exchange.center.monitor.repository.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.exchange.center.monitor.domain.FileList;
import data.exchange.center.monitor.repository.CaseManageRepository;
import oracle.jdbc.OracleTypes;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年8月1日 下午3:52:49</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Repository
public class CaseManageRepositoryImpl implements CaseManageRepository {

	@Autowired
    protected JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<FileList> getFileList(String ajbs) {
		String sql = "{call DCADM.GET_FILE_LIST(?, ?)}";
		List<FileList> list = (List<FileList>) jdbcTemplate.execute(sql,  
                new CallableStatementCallback<Object>() {  
                    public Object doInCallableStatement(CallableStatement cs)  
                            throws SQLException, DataAccessException {  
                    	List<FileList> fileList = new ArrayList<FileList>();
                        cs.setString(1, ajbs);
                        cs.registerOutParameter(2, OracleTypes.CURSOR);
                        cs.execute();
  
                        ResultSet rs = (ResultSet) cs.getObject(2);  
                        while (rs.next()) {
                        	FileList file = new FileList();
                        	if(StringUtils.isEmpty(rs.getString("ajbs"))) {
                        		file.setAjbs("");
                        	}else {
                        		file.setAjbs(rs.getString("ajbs"));
                        	}
                        	if(StringUtils.isEmpty(rs.getString("ajlx"))) {
                        		file.setAjlx("");
                        	}else {
                        		file.setAjlx(rs.getString("ajlx"));
                        	}
                        	if(StringUtils.isEmpty(rs.getString("app_code"))) {
                        		file.setAppCode("");
                        	}else {
                        		file.setAppCode(rs.getString("app_code"));
                        	}
                        	if(StringUtils.isEmpty(rs.getString("dept_code"))) {
                        		file.setDeptCode("");
                        	}else {
                        		file.setDeptCode(rs.getString("dept_code"));
                        	}
                        	if(StringUtils.isEmpty(rs.getString("mc"))) {
                        		file.setMc("");
                        	}else {
                        		file.setMc(rs.getString("mc"));
                        	}
                        	file.setRegTime(   rs.getDate("reg_time"));
                        	file.setUpdateTime(rs.getDate("update_time"));
                        	if(StringUtils.isEmpty(rs.getString("wjhz"))) {
                        		file.setWjhz("");
                        	}else {
                        		file.setWjhz(      rs.getString("wjhz"));
                        	}
                        	if(StringUtils.isEmpty(rs.getString("wjlj"))) {
                        		file.setWjlj("");
                        	}else {
                        		file.setWjlj(rs.getString("wjlj"));
                        	}
                        	if(StringUtils.isEmpty(rs.getString("xh"))) {
                        		file.setXh("");
                        	}else {
                        		file.setXh(rs.getString("xh"));
                        	}
                        	file.setChakan("查看");
                        	
                        	fileList.add(file);  
                        }
						return fileList;
                    }  
                });  
        return list;
		
		
		
		
//		String sql = "select file_name, create_date, file_identify, "
//				+ "ajlx_code, dept_code "
//				+ "from sgyzyml.t_file "
//				+ "where substr(file_identify,1,15) = ?";
//		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(FileList.class),ajbs);
	}
}
