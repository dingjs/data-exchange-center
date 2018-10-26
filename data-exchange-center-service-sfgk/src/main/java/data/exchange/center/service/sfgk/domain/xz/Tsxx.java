package data.exchange.center.service.sfgk.domain.xz;

public class Tsxx {

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
	 * 法庭用途代码
	 */
	private String ftytdm;
	/**
	 * 法庭用途名称
	 */
	private String ftytmc;
	/**
	 * 开始时间
	 */
	private String kssj;
	/**
	 * 结束时间
	 */
	private String jssj;
	/**
	 * 地点
	 */
	private String dd;
	/**
	 * 是否公开开庭代码
	 */
	private String sfgkktdm;
	/**
	 * 是否公开开庭名称
	 */
	private String sfgkktmc;
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
	public String getFtytdm() {
		return ftytdm = ftytdm == null?"":ftytdm.trim();
	}
	public void setFtytdm(String ftytdm) {
		this.ftytdm = ftytdm;
	}
	public String getFtytmc() {
		return ftytmc = ftytmc == null?"":ftytmc.trim();
	}
	public void setFtytmc(String ftytmc) {
		this.ftytmc = ftytmc;
	}
	public String getKssj() {
		return kssj = kssj == null?"":kssj.trim();
	}
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	public String getJssj() {
		return jssj = jssj == null?"":jssj.trim();
	}
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	public String getDd() {
		return dd = dd == null?"":dd.trim();
	}
	public void setDd(String dd) {
		this.dd = dd;
	}
	public String getSfgkktdm() {
		return sfgkktdm = sfgkktdm == null?"":sfgkktdm.trim();
	}
	public void setSfgkktdm(String sfgkktdm) {
		this.sfgkktdm = sfgkktdm;
	}
	public String getSfgkktmc() {
		return sfgkktmc = sfgkktmc == null?"":sfgkktmc.trim();
	}
	public void setSfgkktmc(String sfgkktmc) {
		this.sfgkktmc = sfgkktmc;
	}
}