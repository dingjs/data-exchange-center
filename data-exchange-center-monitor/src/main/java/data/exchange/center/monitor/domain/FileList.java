package data.exchange.center.monitor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年8月1日 下午3:46:04</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class FileList implements Serializable {

	/**
	 * 2017年8月1日下午3:46:21
	 * yuguang
	 */
	private static final long serialVersionUID = 1787069381437496885L;

	private String ajbs; 
	private String xh;
	private Date regTime;
	private Date updateTime;
	private String deptCode;
	private String appCode;
	private String ajlx;
	private String mc;
	private String wjlj;
	private String wjhz;
	private String chakan;
	public String getChakan() {
		return chakan;
	}
	public void setChakan(String chakan) {
		this.chakan = chakan;
	}
	public String getAjbs() {
		return ajbs;
	}
	public void setAjbs(String ajbs) {
		this.ajbs = ajbs;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getAjlx() {
		return ajlx;
	}
	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getWjlj() {
		return wjlj;
	}
	public void setWjlj(String wjlj) {
		this.wjlj = wjlj;
	}
	public String getWjhz() {
		return wjhz;
	}
	public void setWjhz(String wjhz) {
		this.wjhz = wjhz;
	}
}
