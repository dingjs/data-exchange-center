package data.exchange.center.service.meishan.domain;

import java.io.Serializable;

/**
 * 
 * Description:当事人信息
 * <p>Company: xinya </p>
 * <p>Date:2017年9月15日 下午4:40:22</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class DsrInfo implements Serializable {

	/**
	 * 2017年9月15日下午4:40:20
	 * yuguang
	 */
	private static final long serialVersionUID = 758813794832493498L;
	private String AJLX;
	private String MC;
	private String SFZHM;
	private String JS;
	private String DZ;
	private String MZ;
	private String WHCD;
	private String CSRQ;
	private String SF;
	private String LXDH;
	private String HJSZD;
	private String LX;
	private String LY;
	private String XB;
	private String SSDW1;
	private String SSDW2;
	public String getAJLX() {
		return AJLX;
	}
	public void setAJLX(String aJLX) {
		AJLX = aJLX;
	}
	public String getMC() {
		return MC;
	}
	public void setMC(String mC) {
		MC = mC;
	}
	public String getSFZHM() {
		return SFZHM;
	}
	public void setSFZHM(String sFZHM) {
		SFZHM = sFZHM;
	}
	public String getJS() {
		return JS;
	}
	public void setJS(String jS) {
		JS = jS;
	}
	public String getDZ() {
		return DZ;
	}
	public void setDZ(String dZ) {
		DZ = dZ;
	}
	public String getMZ() {
		return MZ;
	}
	public void setMZ(String mZ) {
		MZ = mZ;
	}
	public String getWHCD() {
		return WHCD;
	}
	public void setWHCD(String wHCD) {
		WHCD = wHCD;
	}
	public String getCSRQ() {
		return CSRQ;
	}
	public void setCSRQ(String cSRQ) {
		CSRQ = cSRQ;
	}
	public String getSF() {
		return SF;
	}
	public void setSF(String sF) {
		SF = sF;
	}
	public String getLXDH() {
		return LXDH;
	}
	public void setLXDH(String lXDH) {
		LXDH = lXDH;
	}
	public String getHJSZD() {
		return HJSZD;
	}
	public void setHJSZD(String hJSZD) {
		HJSZD = hJSZD;
	}
	public String getLX() {
		return LX;
	}
	public void setLX(String lX) {
		LX = lX;
	}
	public String getLY() {
		return LY;
	}
	public void setLY(String lY) {
		LY = lY;
	}
	public String getXB() {
		return XB;
	}
	public void setXB(String xB) {
		XB = xB;
	}
	public String getSSDW1() {
		return SSDW1;
	}
	public void setSSDW1(String sSDW1) {
		SSDW1 = sSDW1;
	}
	public String getSSDW2() {
		return SSDW2;
	}
	public void setSSDW2(String sSDW2) {
		SSDW2 = sSDW2;
	}
	@Override
	public String toString() {
		return "DsrInfo [AJLX=" + AJLX + ", MC=" + MC + ", SFZHM=" + SFZHM + ", JS=" + JS + ", DZ=" + DZ + ", MZ=" + MZ
				+ ", WHCD=" + WHCD + ", CSRQ=" + CSRQ + ", SF=" + SF + ", LXDH=" + LXDH + ", HJSZD=" + HJSZD + ", LX="
				+ LX + ", LY=" + LY + ", XB=" + XB + ", SSDW1=" + SSDW1 + ", SSDW2=" + SSDW2 + "]";
	}
}