package data.exchange.center.service.sfgk.domain.ms;

public class Ssdbxx {

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
	 * 当事人
	 */
	private String dsr;
	/**
	 * 事由代码
	 */
	private String sydm;
	/**
	 * 事由名称
	 */
	private String symc;
	/**
	 * 担保人
	 */
	private String dbr;
	/**
	 * 与当事人关系代码
	 */
	private String ydsrgxdm;
	/**
	 * 与当事人关系名称
	 */
	private String ydsrgxmc;
	/**
	 * 担保方式代码
	 */
	private String dbfsdm;
	/**
	 * 担保方式名称
	 */
	private String dbfsmc;
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
	public String getDsr() {
		return dsr = dsr == null?"":dsr.trim();
	}
	public void setDsr(String dsr) {
		this.dsr = dsr;
	}
	public String getSydm() {
		return sydm = sydm == null?"":sydm.trim();
	}
	public void setSydm(String sydm) {
		this.sydm = sydm;
	}
	public String getSymc() {
		return symc = symc == null?"":symc.trim();
	}
	public void setSymc(String symc) {
		this.symc = symc;
	}
	public String getDbr() {
		return dbr = dbr == null?"":dbr.trim();
	}
	public void setDbr(String dbr) {
		this.dbr = dbr;
	}
	public String getYdsrgxdm() {
		return ydsrgxdm = ydsrgxdm == null?"":ydsrgxdm.trim();
	}
	public void setYdsrgxdm(String ydsrgxdm) {
		this.ydsrgxdm = ydsrgxdm;
	}
	public String getYdsrgxmc() {
		return ydsrgxmc = ydsrgxmc == null?"":ydsrgxmc.trim();
	}
	public void setYdsrgxmc(String ydsrgxmc) {
		this.ydsrgxmc = ydsrgxmc;
	}
	public String getDbfsdm() {
		return dbfsdm = dbfsdm == null?"":dbfsdm.trim();
	}
	public void setDbfsdm(String dbfsdm) {
		this.dbfsdm = dbfsdm;
	}
	public String getDbfsmc() {
		return dbfsmc = dbfsmc == null?"":dbfsmc.trim();
	}
	public void setDbfsmc(String dbfsmc) {
		this.dbfsmc = dbfsmc;
	}
}