package data.exchange.center.service.sfgk.domain.xz;

public class Bgxzxw {

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
	 * 被告
	 */
	private String bg;
	/**
	 * 被告类型代码
	 */
	private String bglxdm;
	/**
	 * 被告类型名称
	 */
	private String bglxmc;
	/**
	 * 行政行为种类代码
	 */
	private String xzxwzldm;
	/**
	 * 行政行为种类名称
	 */
	private String xzxwzlmc;
	/**
	 * 做出行政行为日期
	 */
	private String zcxzxwrq;
	/**
	 * 赔偿处理代码
	 */
	private String pccldm;
	/**
	 * 赔偿处理名称
	 */
	private String pcclmc;
	/**
	 * 赔偿方式代码
	 */
	private String pcfsdm;
	/**
	 * 赔偿方式名称
	 */
	private String pcfsmc;
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
	public String getBg() {
		return bg = bg == null?"":bg.trim();
	}
	public void setBg(String bg) {
		this.bg = bg;
	}
	public String getBglxdm() {
		return bglxdm = bglxdm == null?"":bglxdm.trim();
	}
	public void setBglxdm(String bglxdm) {
		this.bglxdm = bglxdm;
	}
	public String getBglxmc() {
		return bglxmc = bglxmc == null?"":bglxmc.trim();
	}
	public void setBglxmc(String bglxmc) {
		this.bglxmc = bglxmc;
	}
	public String getXzxwzldm() {
		return xzxwzldm = xzxwzldm == null?"":xzxwzldm.trim();
	}
	public void setXzxwzldm(String xzxwzldm) {
		this.xzxwzldm = xzxwzldm;
	}
	public String getXzxwzlmc() {
		return xzxwzlmc = xzxwzlmc == null?"":xzxwzlmc.trim();
	}
	public void setXzxwzlmc(String xzxwzlmc) {
		this.xzxwzlmc = xzxwzlmc;
	}
	public String getZcxzxwrq() {
		return zcxzxwrq = zcxzxwrq == null?"":zcxzxwrq.trim();
	}
	public void setZcxzxwrq(String zcxzxwrq) {
		this.zcxzxwrq = zcxzxwrq;
	}
	public String getPccldm() {
		return pccldm = pccldm == null?"":pccldm.trim();
	}
	public void setPccldm(String pccldm) {
		this.pccldm = pccldm;
	}
	public String getPcclmc() {
		return pcclmc = pcclmc == null?"":pcclmc.trim();
	}
	public void setPcclmc(String pcclmc) {
		this.pcclmc = pcclmc;
	}
	public String getPcfsdm() {
		return pcfsdm = pcfsdm == null?"":pcfsdm.trim();
	}
	public void setPcfsdm(String pcfsdm) {
		this.pcfsdm = pcfsdm;
	}
	public String getPcfsmc() {
		return pcfsmc = pcfsmc == null?"":pcfsmc.trim();
	}
	public void setPcfsmc(String pcfsmc) {
		this.pcfsmc = pcfsmc;
	}
	public String getPcje() {
		return pcje = pcje == null?"":pcje.trim();
	}
	public void setPcje(String pcje) {
		this.pcje = pcje;
	}
}