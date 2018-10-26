package data.exchange.center.service.parse.ftpzip.domain;

import java.io.Serializable;

public class Jzxx implements Serializable {

	/**
	 * 2017年11月1日上午10:58:34
	 * yuguang
	 */
	private static final long serialVersionUID = -6278483755187142612L;
	private String LX;
	private String WSXH;
	private String CLBT;
	private String PATH;
	private String WSMC;
	private String WJGS;
	public String getLX() {
		return LX;
	}
	public void setLX(String lX) {
		LX = lX;
	}
	public String getWSXH() {
		return WSXH;
	}
	public void setWSXH(String wSXH) {
		WSXH = wSXH;
	}
	public String getCLBT() {
		return CLBT;
	}
	public void setCLBT(String cLBT) {
		CLBT = cLBT;
	}
	public String getPATH() {
		return PATH;
	}
	public void setPATH(String pATH) {
		PATH = pATH;
	}
	public String getWSMC() {
		return WSMC;
	}
	public void setWSMC(String wSMC) {
		WSMC = wSMC;
	}
	public String getWJGS() {
		return WJGS;
	}
	public void setWJGS(String wJGS) {
		WJGS = wJGS;
	}
}
