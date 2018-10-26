package data.exchange.center.service.parse.ftpzip.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description:中心处理过后的卷宗路径
 * <p>Company: xinya </p>
 * <p>Date:2017年10月20日 下午1:35:02</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class FtpPathInfo implements Serializable {

	/**
	 * 2017年10月20日下午1:35:00
	 * yuguang
	 */
	private static final long serialVersionUID = -7593108173399895465L;

	private String jzbh;
	private String mlbh;
	private String wjxh;
	private String wjlj;
	private String wjmc;
	private String wjxsmc;
	private String wjhz;
	private String wjsxh;
	private String wjdx;
	private String wjlx;
	private String wjtm;
	private String wjym;
	private String jhbh;
	private Date reg_time;
	public String getJzbh() {
		return jzbh;
	}
	public void setJzbh(String jzbh) {
		this.jzbh = jzbh;
	}
	public String getMlbh() {
		return mlbh;
	}
	public void setMlbh(String mlbh) {
		this.mlbh = mlbh;
	}
	public String getWjxh() {
		return wjxh;
	}
	public void setWjxh(String wjxh) {
		this.wjxh = wjxh;
	}
	public String getWjlj() {
		return wjlj;
	}
	public void setWjlj(String wjlj) {
		this.wjlj = wjlj;
	}
	public String getWjmc() {
		return wjmc;
	}
	public void setWjmc(String wjmc) {
		this.wjmc = wjmc;
	}
	public String getWjxsmc() {
		return wjxsmc;
	}
	public void setWjxsmc(String wjxsmc) {
		this.wjxsmc = wjxsmc;
	}
	public String getWjhz() {
		return wjhz;
	}
	public void setWjhz(String wjhz) {
		this.wjhz = wjhz;
	}
	public String getWjsxh() {
		return wjsxh;
	}
	public void setWjsxh(String wjsxh) {
		this.wjsxh = wjsxh;
	}
	public String getWjdx() {
		return wjdx;
	}
	public void setWjdx(String wjdx) {
		this.wjdx = wjdx;
	}
	public String getWjlx() {
		return wjlx;
	}
	public void setWjlx(String wjlx) {
		this.wjlx = wjlx;
	}
	public String getWjtm() {
		return wjtm;
	}
	public void setWjtm(String wjtm) {
		this.wjtm = wjtm;
	}
	public String getWjym() {
		return wjym;
	}
	public void setWjym(String wjym) {
		this.wjym = wjym;
	}
	public String getJhbh() {
		return jhbh;
	}
	public void setJhbh(String jhbh) {
		this.jhbh = jhbh;
	}
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
}
