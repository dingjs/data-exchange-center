package data.exchange.center.service.sfgk.domain.xz;

public class Fyxx {

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
	 * 申请人
	 */
	private String sqr;
	/**
	 * 申请日期
	 */
	private String sqrq;
	/**
	 * 处理日期
	 */
	private String clrq;
	/**
	 * 处理结果代码
	 */
	private String cljgdm;
	/**
	 * 处理结果名称
	 */
	private String cljgmc;
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
	public String getSqr() {
		return sqr = sqr == null?"":sqr.trim();
	}
	public void setSqr(String sqr) {
		this.sqr = sqr;
	}
	public String getSqrq() {
		return sqrq = sqrq == null?"":sqrq.trim();
	}
	public void setSqrq(String sqrq) {
		this.sqrq = sqrq;
	}
	public String getClrq() {
		return clrq = clrq == null?"":clrq.trim();
	}
	public void setClrq(String clrq) {
		this.clrq = clrq;
	}
	public String getCljgdm() {
		return cljgdm = cljgdm == null?"":cljgdm.trim();
	}
	public void setCljgdm(String cljgdm) {
		this.cljgdm = cljgdm;
	}
	public String getCljgmc() {
		return cljgmc = cljgmc == null?"":cljgmc.trim();
	}
	public void setCljgmc(String cljgmc) {
		this.cljgmc = cljgmc;
	}
}