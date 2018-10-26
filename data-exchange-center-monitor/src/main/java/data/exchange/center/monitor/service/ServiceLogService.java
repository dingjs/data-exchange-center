package data.exchange.center.monitor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import data.exchange.center.monitor.domain.modle.LogObject;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:59:14</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@Service
public class ServiceLogService {
//
//    @Autowired
//    protected SysLogRepository sysLogRepository;

    public void create(LogObject logObject){
//        sysLogRepository.add(sysLog);
    }

    @CacheEvict(value = "servicelog", allEntries = true)
    public void clear(){
//        sysLogRepository.clear();
    }
	
    @Cacheable(value = "servicelog")
    public List<LogObject> list(){
    	List<LogObject> list = new ArrayList<LogObject>();
    	LogObject logObject = new LogObject();
    	logObject.setClassName("classname");
    	logObject.setHostName("host");
    	logObject.setIp("127.0.0.1");
    	logObject.setLevel("info");
    	logObject.setLine("23");
    	logObject.setMessage("07/08/2017 15:09:10 DEBUG [http-nio-10000-exec-2]o.s.j.core.JdbcTemplate - Executing prepared SQL statement [select * from user where username=? ]\n" + 
    			"");
    	logObject.setMethodName("methodName");
    	logObject.setSystemName("systemName");
    	logObject.setTime("20170708");
    	logObject.setUuid(UUID.randomUUID().toString());
    	for(int i =0; i< 50; i++){
    		list.add(logObject);
    	}
        return list;
    }
}
