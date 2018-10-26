package data.exchange.center.service.sfgk.domain.xz;

public class Dsr {

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
	 * 当事人类型代码
	 */
	private String dsrlxdm;
	/**
	 * 当事人类型名称
	 */
	private String dsrlxmc;
	/**
	 * 名称
	 */
	private String mc;
	/**
	 * 诉讼地位代码
	 */
	private String ssdwdm;
	/**
	 * 诉讼地位名称
	 */
	private String ssdwmc;
	/**
	 * 法定代表人
	 */
	private String fddbr;
	/**
	 * 地址
	 */
	private String dz;
	/**
	 * 提起赔偿金额
	 */
	private String tqpcje;
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
	public String getDsrlxdm() {
		return dsrlxdm = dsrlxdm == null?"":dsrlxdm.trim();
	}
	public void setDsrlxdm(String dsrlxdm) {
		this.dsrlxdm = dsrlxdm;
	}
	public String getDsrlxmc() {
		return dsrlxmc = dsrlxmc == null?"":dsrlxmc.trim();
	}
	public void setDsrlxmc(String dsrlxmc) {
		this.dsrlxmc = dsrlxmc;
	}
	public String getMc() {
		return mc = mc == null?"":mc.trim();
	}
	public void setMc(String mc) {
		this.mc = mc;
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
	public String getFddbr() {
		return fddbr = fddbr == null?"":fddbr.trim();
	}
	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}
	public String getDz() {
		return dz = dz == null?"":dz.trim();
	}
	public void setDz(String dz) {
		this.dz = dz;
	}
	public String getTqpcje() {
		return tqpcje = tqpcje == null?"":tqpcje.trim();
	}
	public void setTqpcje(String tqpcje) {
		this.tqpcje = tqpcje;
	}
}