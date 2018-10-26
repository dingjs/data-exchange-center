package data.exchange.center.service.sfgk.domain.xs;

public class Xsfdmsdsr {

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
	 * 附带民诉当事人
	 */
	private String fdmsdsr;
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
	public String getFdmsdsr() {
		return fdmsdsr = fdmsdsr == null?"":fdmsdsr.trim();
	}
	public void setFdmsdsr(String fdmsdsr) {
		this.fdmsdsr = fdmsdsr;
	}
}