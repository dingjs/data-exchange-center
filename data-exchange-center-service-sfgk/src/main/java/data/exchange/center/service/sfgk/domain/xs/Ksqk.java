package data.exchange.center.service.sfgk.domain.xs;

public class Ksqk {

	/**
	 * 编号
	 */
	private String bh;
	/**
	 * 案件编号
	 */
	private String ajbh;
	/**
	 * 抗诉机关
	 */
	private String ksjg;
	/**
	 * 提出抗诉日期
	 */
	private String tcksrq;
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
	public String getKsjg() {
		return ksjg = ksjg == null?"":ksjg.trim();
	}
	public void setKsjg(String ksjg) {
		this.ksjg = ksjg;
	}
	public String getTcksrq() {
		return tcksrq = tcksrq == null?"":tcksrq.trim();
	}
	public void setTcksrq(String tcksrq) {
		this.tcksrq = tcksrq;
	}
}