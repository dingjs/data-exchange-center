package data.exchange.center.service.sfgk.domain.pc;

public class Pcywjg {

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
	 * 赔偿义务机关
	 */
	private String pcywjg;
	/**
	 * 赔偿金额
	 */
	private String pcje;
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
	public String getPcywjg() {
		return pcywjg = pcywjg == null?"":pcywjg.trim();
	}
	public void setPcywjg(String pcywjg) {
		this.pcywjg = pcywjg;
	}
	public String getPcje() {
		return pcje = pcje == null?"":pcje.trim();
	}
	public void setPcje(String pcje) {
		this.pcje = pcje;
	}
}