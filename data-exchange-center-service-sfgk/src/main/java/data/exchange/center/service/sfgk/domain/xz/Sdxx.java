package data.exchange.center.service.sfgk.domain.xz;

public class Sdxx {

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
	 * 文书类型代码
	 */
	private String wslxdm;
	/**
	 * 文书类型名称
	 */
	private String wslxmc;
	/**
	 * 送达方式代码
	 */
	private String sdfsdm;
	/**
	 * 送达方式名称
	 */
	private String sdfsmc;
	/**
	 * 受送达人
	 */
	private String ssdr;
	/**
	 * 送达日期
	 */
	private String sdrq;
	/**
	 * 签收时间
	 */
	private String qssj;
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
	public String getWslxdm() {
		return wslxdm = wslxdm == null?"":wslxdm.trim();
	}
	public void setWslxdm(String wslxdm) {
		this.wslxdm = wslxdm;
	}
	public String getWslxmc() {
		return wslxmc = wslxmc == null?"":wslxmc.trim();
	}
	public void setWslxmc(String wslxmc) {
		this.wslxmc = wslxmc;
	}
	public String getSdfsdm() {
		return sdfsdm = sdfsdm == null?"":sdfsdm.trim();
	}
	public void setSdfsdm(String sdfsdm) {
		this.sdfsdm = sdfsdm;
	}
	public String getSdfsmc() {
		return sdfsmc = sdfsmc == null?"":sdfsmc.trim();
	}
	public void setSdfsmc(String sdfsmc) {
		this.sdfsmc = sdfsmc;
	}
	public String getSsdr() {
		return ssdr = ssdr == null?"":ssdr.trim();
	}
	public void setSsdr(String ssdr) {
		this.ssdr = ssdr;
	}
	public String getSdrq() {
		return sdrq = sdrq == null?"":sdrq.trim();
	}
	public void setSdrq(String sdrq) {
		this.sdrq = sdrq;
	}
	public String getQssj() {
		return qssj = qssj == null?"":qssj.trim();
	}
	public void setQssj(String qssj) {
		this.qssj = qssj;
	}
}