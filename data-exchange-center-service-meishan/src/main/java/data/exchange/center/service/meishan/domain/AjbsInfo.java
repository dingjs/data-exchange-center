package data.exchange.center.service.meishan.domain;

import java.io.Serializable;

public class AjbsInfo implements Serializable {

	/**
	 * 2017年9月13日上午11:11:18
	 * yuguang
	 */
	private static final long serialVersionUID = 2361925993437817266L;

	private String ajbs;
	private String fydm;
	private String ajlx;
	public String getAjbs() {
		return ajbs;
	}
	public void setAjbs(String ajbs) {
		this.ajbs = ajbs;
	}
	public String getFydm() {
		return fydm;
	}
	public void setFydm(String fydm) {
		this.fydm = fydm;
	}
	public String getAjlx() {
		return ajlx;
	}
	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	@Override
	public String toString() {
		return "AjbsInfo [ajbs=" + ajbs + ", fydm=" + fydm + ", ajlx=" + ajlx + "]";
	}
}
