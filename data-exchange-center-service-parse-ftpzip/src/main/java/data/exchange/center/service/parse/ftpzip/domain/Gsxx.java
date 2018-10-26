package data.exchange.center.service.parse.ftpzip.domain;

import java.io.Serializable;

public class Gsxx implements Serializable {

	/**
	 * 2017年11月1日上午10:55:45
	 * yuguang
	 */
	private static final long serialVersionUID = 4966001206534808998L;

	private String GSZZM;
	private String DSRC;
	private String GSSBH;
	private String GSJG;
	public String getGSZZM() {
		return GSZZM;
	}
	public void setGSZZM(String gSZZM) {
		GSZZM = gSZZM;
	}
	public String getDSRC() {
		return DSRC;
	}
	public void setDSRC(String dSRC) {
		DSRC = dSRC;
	}
	public String getGSSBH() {
		return GSSBH;
	}
	public void setGSSBH(String gSSBH) {
		GSSBH = gSSBH;
	}
	public String getGSJG() {
		return GSJG;
	}
	public void setGSJG(String gSJG) {
		GSJG = gSJG;
	}
}
