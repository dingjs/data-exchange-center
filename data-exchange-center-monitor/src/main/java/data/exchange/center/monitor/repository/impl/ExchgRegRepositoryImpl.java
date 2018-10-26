package data.exchange.center.monitor.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import data.exchange.center.monitor.domain.ExchgReg.RegApp;
import data.exchange.center.monitor.domain.ExchgReg.RegExchg;
import data.exchange.center.monitor.domain.ExchgReg.RegFtp;
import data.exchange.center.monitor.domain.ExchgReg.RegNode;
import data.exchange.center.monitor.domain.ExchgReg.RegService;
import data.exchange.center.monitor.domain.ExchgReg.RegSoaToken;
import data.exchange.center.monitor.repository.ExchgRegRepository;
@Repository
public class ExchgRegRepositoryImpl implements ExchgRegRepository{
	@Autowired
    protected JdbcTemplate jdbcTemplate;
	@Override
	public List<RegNode> getRegNode() {
		String sql ="SELECT N_NODEID  NODEID,C_NODENAME  NODENAME,CASE  C_NODETYPE WHEN '0' THEN '普通节点' ELSE '远程节点'  END AS   NODETYPE,C_NODEIP  NODEIP,C_DEPTCODE  DEPTCODE,"
				+ "CASE C_DISTRIBUTE WHEN '1' THEN '拥有该组件' ELSE '无该组件' END AS DISTRIBUTE, CASE  C_KAFKA  WHEN '1' THEN '拥有该组件' ELSE '无该组件' END AS KAFKA,"
				+ "CASE C_REDIS      WHEN '1' THEN '拥有该组件' ELSE '无该组件' END AS REDIS,      CASE C_ELASTIC WHEN '1' THEN '拥有该组件' ELSE '无该组件' END AS ELASTIC,"
				+ "CASE C_REMOTE     WHEN '1' THEN '拥有该组件' ELSE '无该组件' END AS  REMOTE,"
				+ "CASE C_DISTSTATUS   WHEN '1' THEN '正在运行' ELSE '已停止' END AS  DISTSTATUS, CASE C_KAFKSTATUS  WHEN '1' THEN '正在运行' ELSE '已停止' END AS  KAFKSTATUS,"
				+ "CASE C_REDISTATUS  WHEN '1' THEN '正在运行' ELSE '已停止' END AS   REDISTATUS, CASE C_ELASTATUS   WHEN '1' THEN '正在运行' ELSE '已停止' END AS  ELASTATUS,"
				+ "CASE C_RETSTATUS  WHEN '1' THEN '正在运行' ELSE '已停止' END AS   RETSTATUS  FROM DCADM.DC_REG_NODE ORDER BY N_NODEID";
				  
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RegNode.class));
	}
	@Override
	public List<RegApp> getRegAPP() {
		String sql = "SELECT TO_CHAR(C_APPCODE) APPCODE,C_APPNAME APPNAME,C_ZIPCODE ZIPCODE,C_DZIPCODE DZIPCODE,"
				+"CASE C_LEVEL WHEN '0' THEN ' 最高部署 'WHEN '1' THEN '高院部署'  WHEN '2' THEN ' 中院部署 ' "
				+"WHEN '3' THEN ' 基层院部署 '  WHEN '4' THEN '省政法委部署 '"
				+"WHEN 'Z' THEN ' 其他部署 ' END AS  CLEVEL,C_ENABLE ENABLE,C_USERID USERID,"
				+"TO_CHAR (D_CREATE,'YYYYMMDD') CREATETIME,TO_CHAR (D_UPDATE,'YYYYMMDD') UPDATETIME	 FROM DCADM.DC_REG_APP ORDER BY C_APPCODE";
				
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RegApp.class));
	}
	@Override
	public List<RegFtp> getRegFtp() {
		String sql ="SELECT  C_FTPNAME FTPNAME,C_FTPALIAS FTPALIAS,C_USERNAME USERNAME,C_PASSWORD PASSWORD,"
				+"C_HOSTNAME HOSTNAME,C_IP IP,N_PORT PORT,C_PATH PATH,C_ENCODING ENCODING,"
				+"N_PASSIVE PASSIVE,N_POOLSIZE POOLSIZE,N_TRANSTYPE TRANSTYPE,N_TIMEOUT TIMEOUT,"
				+"CASE  TO_CHAR(N_EXTERNAL) WHEN '0' THEN '内部FTP' WHEN '1' THEN '外部FTP' END AS  EXTERNAL,"
				+"C_DESC CDESC  FROM  DCADM.DC_REG_FTP ORDER BY C_FTPNAME";
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RegFtp.class));
	}
	@Override
	public List<RegService> getRegService() {
		String sql ="SELECT N_SRVID SRVID,C_SRVCNAME SRVCNAME,C_SRVENAME SRVENAME,C_SRVLANG SRVLANG,C_SRVTYPE SRVTYPE,"
				+"C_SRVURL SRVURL,CODESRVMODE.C_DESC  CDESC,N_INTERVAL INTERVAL,C_ENABLE ENABLE,C_USERID USERID,"
				+"D_CREATE CCREATE,D_UPDATE CUPDATE,CODESRVMODE.C_NAME SRVNAME "
				+"FROM DCADM.DC_REG_SERVICE REGSERVICE,DCADM.DC_CODE_SRVMODE CODESRVMODE WHERE CODESRVMODE.N_SRVMODE=REGSERVICE.N_SRVMODE";

		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RegService.class));
	}
	@Override
	public List<RegSoaToken> getRegSoaToken() {
		String sql ="SELECT N_SRVID SRVID,C_DEPTCODE DEPTCODE,C_UNAME UNAME,C_PNAME PNAME,C_MPHONE MPHONE,C_INNERIP INNERIP,C_TOKEN TOKEN,"
				+"CASE C_METHOD WHEN '0'  THEN '可逆加解密算法' ELSE '不可逆加密算法' END AS METHOD,C_USERID USERID,D_CREATE DCREATE,"
				+"D_UPDATE DUPDATE FROM dcadm.DC_REG_SOATOKEN ORDER BY N_SRVID";
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RegSoaToken.class));
	}
	@Override
	public List<RegExchg> getRegExchg() {
		String sql ="SELECT N_EXCHGID EXCHGID,C_SRCTYPE SRCTYPE,SRVMODE.C_NAME SRVMODE,C_BUFMODE BUFMODE,C_SRCAPP SRCAPP,C_TGTAPP TGTAPP, CASE C_TGTRW  WHEN 'R' THEN '只读' WHEN 'W' THEN '只写'"
				+"ELSE '可读写' END AS TGTRW,"
				+"C_KCOLSTR KCOLSTR,C_COLSTRNAME COLSTRNAME,EXCHG.C_DESC CDESC,C_ENABLE ENABLE,C_USERID USERID,D_CREATE DCREATE,D_UPDATE DUPDATE FROM DCADM.DC_REG_EXCHG EXCHG,DCADM.DC_CODE_SRVMODE SRVMODE "
				+"WHERE EXCHG.N_SRVMODE = SRVMODE.N_SRVMODE";
		return  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(RegExchg.class));
	}

}
