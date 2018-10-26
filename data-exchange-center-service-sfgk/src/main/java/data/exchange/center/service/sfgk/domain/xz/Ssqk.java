package data.exchange.center.service.sfgk.domain.xz;

public class Ssqk {

	/**
	 * 编号
	 */
	private String bh;
	/**
	 * 案件编号
	 */
	private String ajbh;
	/**
	 * 上诉人
	 */
	private String ssr;
	/**
	 * 提出上诉日期
	 */
	private String tcssrq;
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
	public String getSsr() {
		return ssr = ssr == null?"":ssr.trim();
	}
	public void setSsr(String ssr) {
		this.ssr = ssr;
	}
	public String getTcssrq() {
		return tcssrq = tcssrq == null?"":tcssrq.trim();
	}
	public void setTcssrq(String tcssrq) {
		this.tcssrq = tcssrq;
	}
}