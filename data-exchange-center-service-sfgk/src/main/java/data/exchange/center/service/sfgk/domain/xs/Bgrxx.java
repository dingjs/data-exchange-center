package data.exchange.center.service.sfgk.domain.xs;

public class Bgrxx {

	/**
	 * 编号
	 */
	private String bh;
	/**
	 * 案件编号
	 */
	private String ajbh;
	/**
	 * 序号
	 */
	private String xh;
	/**
	 * 类型代码
	 */
	private String lxdm;
	/**
	 * 类型名称
	 */
	private String lxmc;
	/**
	 * 诉讼地位代码
	 */
	private String ssdwdm;
	/**
	 * 诉讼地位名称
	 */
	private String ssdwmc;
	/**
	 * 名称
	 */
	private String mc;
	/**
	 * 法定代表人
	 */
	private String fddbr;
	public String getBh() {
		return bh = bh == null?"":bh.trim();
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getAjbh() {
		return ajbh = ajbh == null?"":ajbh.trim();
	}
	public void setAjbh(String ajbh) {
		this.ajbh = ajbh;
	}
	public String getXh() {
		return xh = xh == null?"":xh.trim();
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getLxdm() {
		return lxdm = lxdm == null?"":lxdm.trim();
	}
	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}
	public String getLxmc() {
		return lxmc = lxmc == null?"":lxmc.trim();
	}
	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}
	public String getSsdwdm() {
		return ssdwdm = ssdwdm == null?"":ssdwdm.trim();
	}
	public void setSsdwdm(String ssdwdm) {
		this.ssdwdm = ssdwdm;
	}
	public String getSsdwmc() {
		return ssdwmc = ssdwmc == null?"":ssdwmc.trim();
	}
	public void setSsdwmc(String ssdwmc) {
		this.ssdwmc = ssdwmc;
	}
	public String getMc() {
		return mc = mc == null?"":mc.trim();
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getFddbr() {
		return fddbr = fddbr == null?"":fddbr.trim();
	}
	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}
}