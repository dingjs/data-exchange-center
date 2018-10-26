package data.exchange.center.service.parse.ftpzip.domain;

import java.io.Serializable;


public class Bsxx implements Serializable {

	/**
	 * 2017年11月1日上午10:41:11
	 * yuguang
	 */
	private static final long serialVersionUID = -7570315070131334053L;
	private String CASENO; 
	private String FSDW; 
	private String FSDWMC; 
	private String JSDW; 
	private String JSDWMC;
	private String LEIX;
	public String getCASENO() {
		return CASENO;
	}
	public void setCASENO(String cASENO) {
		CASENO = cASENO;
	}
	public String getFSDW() {
		return FSDW;
	}
	public void setFSDW(String fSDW) {
		FSDW = fSDW;
	}
	public String getFSDWMC() {
		return FSDWMC;
	}
	public void setFSDWMC(String fSDWMC) {
		FSDWMC = fSDWMC;
	}
	public String getJSDW() {
		return JSDW;
	}
	public void setJSDW(String jSDW) {
		JSDW = jSDW;
	}
	public String getJSDWMC() {
		return JSDWMC;
	}
	public void setJSDWMC(String jSDWMC) {
		JSDWMC = jSDWMC;
	}
	public String getLEIX() {
		return LEIX;
	}
	public void setLEIX(String lEIX) {
		LEIX = lEIX;
	}
}
