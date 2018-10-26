package data.exchange.center.service.sfgk.domain.xs;

public class Zjxx {

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
	 * 证据名称
	 */
	private String zjmc;
	/**
	 * 证据类型代码
	 */
	private String zjlxdm;
	/**
	 * 证据类型名称
	 */
	private String zjlxmc;
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
	public String getZjmc() {
		return zjmc = zjmc == null?"":zjmc.trim();
	}
	public void setZjmc(String zjmc) {
		this.zjmc = zjmc;
	}
	public String getZjlxdm() {
		return zjlxdm = zjlxdm == null?"":zjlxdm.trim();
	}
	public void setZjlxdm(String zjlxdm) {
		this.zjlxdm = zjlxdm;
	}
	public String getZjlxmc() {
		return zjlxmc = zjlxmc == null?"":zjlxmc.trim();
	}
	public void setZjlxmc(String zjlxmc) {
		this.zjlxmc = zjlxmc;
	}
}