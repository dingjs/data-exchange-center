package data.exchange.center.monitor.domain.ExchgReg;

public class RegFtp {
	private String ftpname;
	private String ftpalias;
	private String username;
	private String password;
	private String hostname;
	private String ip;
	private String port;
	private String path;
	private String encoding;
	private String passive;
	private String poolsize;
	private String transtype;
	private String timeout;
	private String external;
	private String cdesc;
	
	public String getFtpname() {
		return ftpname;
	}
	public void setFtpname(String ftpname) {
		this.ftpname = ftpname;
	}
	public String getFtpalias() {
		return ftpalias;
	}
	public void setFtpalias(String ftpalias) {
		this.ftpalias = ftpalias;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getPassive() {
		return passive;
	}
	public void setPassive(String passive) {
		this.passive = passive;
	}
	public String getPoolsize() {
		return poolsize;
	}
	public void setPoolsize(String poolsize) {
		this.poolsize = poolsize;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getExternal() {
		return external;
	}
	public void setExternal(String external) {
		this.external = external;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
}
