package data.exchange.center.service.sfgk.domain.pc;

public class Kcsx {

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
	 * 扣除审限类型代码
	 */
	private String kcsxlxdm;
	/**
	 * 扣除审限类型名称
	 */
	private String kcsxlxmc;
	/**
	 * 扣除审限事由代码
	 */
	private String kcsxsydm;
	/**
	 * 扣除审限事由名称
	 */
	private String kcsxsymc;
	/**
	 * 起始日期
	 */
	private String qsrq;
	/**
	 * 结束日期
	 */
	private String jsrq;
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
	public String getKcsxlxdm() {
		return kcsxlxdm = kcsxlxdm == null?"":kcsxlxdm.trim();
	}
	public void setKcsxlxdm(String kcsxlxdm) {
		this.kcsxlxdm = kcsxlxdm;
	}
	public String getKcsxlxmc() {
		return kcsxlxmc = kcsxlxmc == null?"":kcsxlxmc.trim();
	}
	public void setKcsxlxmc(String kcsxlxmc) {
		this.kcsxlxmc = kcsxlxmc;
	}
	public String getKcsxsydm() {
		return kcsxsydm = kcsxsydm == null?"":kcsxsydm.trim();
	}
	public void setKcsxsydm(String kcsxsydm) {
		this.kcsxsydm = kcsxsydm;
	}
	public String getKcsxsymc() {
		return kcsxsymc = kcsxsymc == null?"":kcsxsymc.trim();
	}
	public void setKcsxsymc(String kcsxsymc) {
		this.kcsxsymc = kcsxsymc;
	}
	public String getQsrq() {
		return qsrq = qsrq == null?"":qsrq.trim();
	}
	public void setQsrq(String qsrq) {
		this.qsrq = qsrq;
	}
	public String getJsrq() {
		return jsrq = jsrq == null?"":jsrq.trim();
	}
	public void setJsrq(String jsrq) {
		this.jsrq = jsrq;
	}
}