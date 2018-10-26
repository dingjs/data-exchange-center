package data.exchange.center.service.listener.scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import data.exchange.center.service.listener.mapper.FtpMapper;

@Component
public class FtpSyncScheduler{
	
	private static Logger logger =   LoggerFactory.getLogger(FtpSyncScheduler.class);
	
	private static String threadName = "thread-Notify";
	
	protected volatile boolean dos = true; 

    @Autowired
    private FtpMapper ftpMapper;
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年10月17日 上午11:09:43
	 */
    @Scheduled(cron="0 30 0 * * ?")//每晚十二点半执行一次
	public void run() throws Exception {
    	logger.info("开始缓存查询");
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@150.0.2.15:1521:oraapp22", "lckd", "lckd");
    		Statement statement = connection.createStatement();
    		String sql = "SELECT * FROM FTPPATH ORDER BY FYDM, FTPNAME";
    		ResultSet resultset = statement.executeQuery(sql);
    		
    		ResultSetMetaData data = resultset.getMetaData();
            List<Map<String,Object>> ftpMapList = new ArrayList<Map<String,Object>>();
            while(resultset.next()){
                Map<String, Object> map = new HashMap<String,Object>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    map.put(data.getColumnName(i).toLowerCase(), resultset.getObject(i));
                }
                ftpMapList.add(map);
            }
            Map<String, String> params = new HashMap<String, String>();
            for(Map<String,Object> map:ftpMapList) {
            	String fydm    =(String) map.get("fydm");
                String ftpname =(String) map.get("ftpname");
                String ipaddr  =(String) map.get("ipaddr");
                String ftpport =(String) map.get("ftpport");
                String passwd  =(String) map.get("passwd");
                String nowftp  =(String) map.get("nowftp");
                String root    =(String) map.get("root");
                String usrname =(String) map.get("usrname");
//                String platform=(String) map.get("platform");
                params.put("fydm",    fydm);
                params.put("ftpname", ftpname);
                //fydm和ftpname 都存在就
                int count = ftpMapper.selectCount(params);
                params.put("ipaddr",  ipaddr);
                params.put("ftpport", ftpport);
                params.put("usrname", usrname);
                params.put("passwd",  passwd);
                params.put("root",    root);
                params.put("nowftp",  nowftp);
                if(count>0) {//对比ip和端口和账号和密码
                    int num = ftpMapper.selectCount(params);
                    if(num==0) {//不存在记录说明通达海已经有对应这条FTP更新
                    	ftpMapper.updateFtp(params);
                    }
                }else {//不存在说明通达海有新增ftp
                	ftpMapper.addNewFtp(params);
                }
                params.clear();
            }
            //默认有一条code叫做[新增FTP]的
            int updateFtpCount = ftpMapper.getUpdateCount();
            Thread thread = new Thread(new Notify());
            thread.setName(threadName);
            
            ThreadGroup group = Thread.currentThread().getThreadGroup();
            ThreadGroup topGroup = group;
            while (group != null) {
                topGroup = group;
                group = group.getParent();
            }
            int slackSize = topGroup.activeCount() * 2;
            Thread[] slackThreads = new Thread[slackSize];
            int actualSize = topGroup.enumerate(slackThreads);
            Thread[] atualThreads = new Thread[actualSize];
            if(updateFtpCount>1) {
            	dos = true;
            	StringBuffer strBuf = new StringBuffer();
            	for (Thread threa : atualThreads) {
                    strBuf.append(threa.getName()).append(",");
                }
            	if(!strBuf.toString().contains(threadName)) {
            		thread.start();
            	}
            }else {
            	dos = false;
            }
    	} catch (Exception e) {
			logger.error(String.format("同步FTP服务器信息出错：%s",e.toString()));
			e.printStackTrace();
		}
	}
    class Notify implements Runnable{
    	@Override
    	public void run() {
    		while(dos) {
    			System.out.println("**********FTP有更新，需要手动处理编码格式************");
    			try {
    				TimeUnit.MILLISECONDS.sleep(500);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
}
