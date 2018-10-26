package data.exchange.center.monitor.domain.modle;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月1日 下午3:07:33</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class LogObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8315498426095775577L;
	
	/**
	 * 对象ID
	 */
	private String uuid;
	/**
	 * 软件模块名称
	 */
	private String systemName;
	/**
	 * 主机名
	 */
	private String hostName;
	/**
	 * IP
	 */
	private String ip;
	/**
	 * 时间
	 */
	private String time;
	/**
	 * 日志级别
	 */
	private String level;
	/**
	 * 类名
	 */
	private String className;
	/**
	 * 方法名
	 */
	private String methodName;
	/**
	 * 行数
	 */
	private String line;
	/**
	 * 日志内容
	 */
	private String message;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "LogObject [uuid=" + uuid + ", systemName=" + systemName + ", hostName=" + hostName + ", ip=" + ip
				+ ", time=" + time + ", level=" + level + ", className=" + className + ", methodName=" + methodName
				+ ", line=" + line + ", message=" + message + "]";
	}
}