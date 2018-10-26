package data.exchange.center.service.parse.ftpzip.domain;

import java.io.Serializable;
import java.util.Date;

public class WsFtpPathInfo implements Serializable{

	/**
	 * 2017年10月24日下午8:51:48
	 * yuguang
	 */
	private static final long serialVersionUID = 561071849729142396L;

	private String wsbh;
	private String wswh;
	private String wsflmc;
	private String wsflbm;
	private String wsmc;
	private String wslx;
	private String cflj;
	private String xssx;
	private Date cjsj;
	private String jhbh;
	private Date reg_time;
	public String getWsbh() {
		return wsbh;
	}
	public void setWsbh(String wsbh) {
		this.wsbh = wsbh;
	}
	public String getWswh() {
		return wswh;
	}
	public void setWswh(String wswh) {
		this.wswh = wswh;
	}
	public String getWsflmc() {
		return wsflmc;
	}
	public void setWsflmc(String wsflmc) {
		this.wsflmc = wsflmc;
	}
	public String getWsflbm() {
		return wsflbm;
	}
	public void setWsflbm(String wsflbm) {
		this.wsflbm = wsflbm;
	}
	public String getWsmc() {
		return wsmc;
	}
	public void setWsmc(String wsmc) {
		this.wsmc = wsmc;
	}
	public String getWslx() {
		return wslx;
	}
	public void setWslx(String wslx) {
		this.wslx = wslx;
	}
	public String getCflj() {
		return cflj;
	}
	public void setCflj(String cflj) {
		this.cflj = cflj;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
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
