package data.exchange.center.service.sfgk.domain.ms;

public class Zzhf {

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
	 * 中止日期
	 */
	private String zzrq;
	/**
	 * 中止事由代码
	 */
	private String zzsydm;
	/**
	 * 中止事由名称
	 */
	private String zzsymc;
	/**
	 * 恢复日期
	 */
	private String hfrq;
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
	public String getZzrq() {
		return zzrq = zzrq == null?"":zzrq.trim();
	}
	public void setZzrq(String zzrq) {
		this.zzrq = zzrq;
	}
	public String getZzsydm() {
		return zzsydm = zzsydm == null?"":zzsydm.trim();
	}
	public void setZzsydm(String zzsydm) {
		this.zzsydm = zzsydm;
	}
	public String getZzsymc() {
		return zzsymc = zzsymc == null?"":zzsymc.trim();
	}
	public void setZzsymc(String zzsymc) {
		this.zzsymc = zzsymc;
	}
	public String getHfrq() {
		return hfrq = hfrq == null?"":hfrq.trim();
	}
	public void setHfrq(String hfrq) {
		this.hfrq = hfrq;
	}
}