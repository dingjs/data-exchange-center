package data.exchange.center.service.download.domain;

import java.io.Serializable;

/**
 * Description:FTPClient配置类，封装了FTPClient的相关配置
 * <p>Company: pelox </p>
 * <p>Date:2017年5月5日 下午6:11:28</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class FTPClientInfoConfigure implements Serializable{
    /**
     * 2017年5月17日上午11:12:13
     * yuguang
     */
    private static final long serialVersionUID = 8237266008781383862L;
    
    private String host;
    private String port;
    private String password;
    private String userName;
    private String localPassiveModel;
    private String encoding;
    private String timeOut;
    private String poolSize;
    private String transferFileType;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLocalPassiveModel() {
		return localPassiveModel;
	}
	public void setLocalPassiveModel(String localPassiveModel) {
		this.localPassiveModel = localPassiveModel;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(String poolSize) {
		this.poolSize = poolSize;
	}
	public String getTransferFileType() {
		return transferFileType;
	}
	public void setTransferFileType(String transferFileType) {
		this.transferFileType = transferFileType;
	}
	@Override
	public String toString() {
		return "FTPClientInfoConfigure [host=" + host + ", port=" + port + ", password=" + password + ", userName="
				+ userName + ", localPassiveModel=" + localPassiveModel + ", encoding=" + encoding + ", timeOut="
				+ timeOut + ", poolSize=" + poolSize + ", transferFileType=" + transferFileType + "]";
	}
}
