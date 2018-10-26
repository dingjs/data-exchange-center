package data.exchange.center.service.sfgk.domain.xs;

public class Ycsx {

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
	 * 申请日期
	 */
	private String sqrq;
	/**
	 * 申请事由或原因代码
	 */
	private String sqsyhyydm;
	/**
	 * 申请事由或原因名称
	 */
	private String sqsyhyymc;
	/**
	 * 延长期间代码
	 */
	private String ycqjdm;
	/**
	 * 延长期间名称
	 */
	private String ycqjmc;
	/**
	 * 审批意见
	 */
	private String spyj;
	/**
	 * 开始日期
	 */
	private String ksrq;
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
	public String getSqrq() {
		return sqrq = sqrq == null?"":sqrq.trim();
	}
	public void setSqrq(String sqrq) {
		this.sqrq = sqrq;
	}
	public String getSqsyhyydm() {
		return sqsyhyydm = sqsyhyydm == null?"":sqsyhyydm.trim();
	}
	public void setSqsyhyydm(String sqsyhyydm) {
		this.sqsyhyydm = sqsyhyydm;
	}
	public String getSqsyhyymc() {
		return sqsyhyymc = sqsyhyymc == null?"":sqsyhyymc.trim();
	}
	public void setSqsyhyymc(String sqsyhyymc) {
		this.sqsyhyymc = sqsyhyymc;
	}
	public String getYcqjdm() {
		return ycqjdm = ycqjdm == null?"":ycqjdm.trim();
	}
	public void setYcqjdm(String ycqjdm) {
		this.ycqjdm = ycqjdm;
	}
	public String getYcqjmc() {
		return ycqjmc = ycqjmc == null?"":ycqjmc.trim();
	}
	public void setYcqjmc(String ycqjmc) {
		this.ycqjmc = ycqjmc;
	}
	public String getSpyj() {
		return spyj = spyj == null?"":spyj.trim();
	}
	public void setSpyj(String spyj) {
		this.spyj = spyj;
	}
	public String getKsrq() {
		return ksrq = ksrq == null?"":ksrq.trim();
	}
	public void setKsrq(String ksrq) {
		this.ksrq = ksrq;
	}
}