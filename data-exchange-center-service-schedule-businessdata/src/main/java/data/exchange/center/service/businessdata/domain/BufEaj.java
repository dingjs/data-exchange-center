package data.exchange.center.service.businessdata.domain;

import java.io.Serializable;

/**
 * 
 * @author bmj
 *
 */
public class BufEaj implements Serializable{
	private String ajbs;
	private String ajlx;
	private String fydm;
	private String ajzt;
	private String ah;

	public String getAjbs() {
		return ajbs;
	}

	public void setAjbs(String ajbs) {
		this.ajbs = ajbs;
	}

	public String getAjlx() {
		return ajlx;
	}

	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}

	public String getFydm() {
		return fydm;
	}

	public void setFydm(String fydm) {
		this.fydm = fydm;
	}

	public String getAjzt() {
		return ajzt;
	}

	public void setAjzt(String ajzt) {
		this.ajzt = ajzt;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}
}
