package data.exchange.center.common.log;

import java.net.UnknownHostException;

import data.exchange.center.common.net.NetUtils;
import data.exchange.center.common.time.TimeUtils;

public class LogObjectInstance {

	private static LogObject instance;
	
	public static synchronized LogObject newInstance() throws UnknownHostException {
		if(instance == null) {
			instance = new LogObject();
		}
//		instance.setClassName(ClassUtils.getClassFileName());
		instance.setHostName(NetUtils.getHostName());
		instance.setIp(NetUtils.getIp());
//		instance.setLevel(level);
//		instance.setLine(ClassUtils.getLineInfo());
//		instance.setMessage(message);
//		instance.setMethodName(ClassUtils.getMethodName());
//		instance.setServiceName(serviceName);
		instance.setTime(TimeUtils.getNowTime());
//		instance.setUuid(uuid);
		return instance;
	}
}
