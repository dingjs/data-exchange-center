package data.exchange.center.logback;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月1日 下午3:07:13</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class SgyLogerConvert extends ClassicConverter {

	long lastTimestamp = -1;
	String timestampStrCache = null;
	SimpleDateFormat simpleFormat = null;

	String systemName = null;

	static String hostName;
	static String localIp;
	static LogObject log = new LogObject();

	static {
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
			hostName = ia.getHostName();
			localIp = ia.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String convert(ILoggingEvent le) {
		log.setUuid(UUID.randomUUID().toString().toLowerCase());
		log.setSystemName(systemName);
		log.setIp(localIp);
		log.setHostName(hostName);
		log.setTime(getTime(le));
		log.setLevel(le.getLevel().toString());
		log.setClassName(getFullyQualifiedName(le));
		log.setMethodName(getMethodName(le));
		log.setLine(getLineNumber(le));
		log.setMessage(le.getFormattedMessage());
		return JSON.toJSONString(log);
	}

	public void start() {
		systemName = getFirstOption();
		systemName = systemName == null ? "defaultSystemName" : systemName;
		String datePattern = CoreConstants.ISO8601_PATTERN;//.YYYY_MM_DD_HH_MM_SS.getValue();
		try {
			simpleFormat = new SimpleDateFormat(datePattern);
			// maximumCacheValidity =
			// CachedDateFormat.getMaximumCacheValidity(pattern);
		} catch (IllegalArgumentException e) {
			addWarn("Could not instantiate SimpleDateFormat with pattern " + datePattern, e);
			// default to the ISO8601 format
			simpleFormat = new SimpleDateFormat(CoreConstants.ISO8601_PATTERN);
		}
		List<?> optionList = getOptionList();
		// if the option list contains a TZ option, then set it.
		if (optionList != null && optionList.size() > 1) {
			TimeZone tz = TimeZone.getTimeZone((String) optionList.get(1));
			simpleFormat.setTimeZone(tz);
		}
	}

	private String getTime(ILoggingEvent le) {
		long timestamp = le.getTimeStamp();
		synchronized (this) {
			// if called multiple times within the same millisecond
			// return cache value
			if (timestamp == lastTimestamp) {
				return timestampStrCache;
			} else {
				lastTimestamp = timestamp;
				// SimpleDateFormat is not thread safe.
				// See also http://jira.qos.ch/browse/LBCLASSIC-36
				timestampStrCache = simpleFormat.format(new Date(timestamp));
				return timestampStrCache;
			}
		}
	}

	private String getFullyQualifiedName(ILoggingEvent le) {

		StackTraceElement[] cda = le.getCallerData();
		if (cda != null && cda.length > 0) {
			return cda[0].getClassName();
		} else {
			return CallerData.NA;
		}
	}

	private String getLineNumber(ILoggingEvent le) {
		StackTraceElement[] cda = le.getCallerData();
		if (cda != null && cda.length > 0) {
			return Integer.toString(cda[0].getLineNumber());
		} else {
			return CallerData.NA;
		}
	}

	private String getMethodName(ILoggingEvent le) {
		StackTraceElement[] cda = le.getCallerData();
		if (cda != null && cda.length > 0) {
			return cda[0].getMethodName();
		} else {
			return CallerData.NA;
		}
	}
}