package data.exchange.center.service.sfgk.domain.xs;

public class Xsfdmsxx {

	/**
	 * 编号
	 */
	private String bh;
	/**
	 * 案件编号
	 */
	private String ajbh;
	/**
	 * 提起人类型代码
	 */
	private String tqrlxdm;
	/**
	 * 提起人类型名称
	 */
	private String tqrlxmc;
	/**
	 * 提起诉讼日期
	 */
	private String tqssrq;
	/**
	 * 处理日期
	 */
	private String clrq;
	/**
	 * 处理方式代码
	 */
	private String clfsdm;
	/**
	 * 处理方式名称
	 */
	private String clfsmc;
	/**
	 * 请求赔偿金额
	 */
	private String qqpcje;
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
	public String getTqrlxdm() {
		return tqrlxdm = tqrlxdm == null?"":tqrlxdm.trim();
	}
	public void setTqrlxdm(String tqrlxdm) {
		this.tqrlxdm = tqrlxdm;
	}
	public String getTqrlxmc() {
		return tqrlxmc = tqrlxmc == null?"":tqrlxmc.trim();
	}
	public void setTqrlxmc(String tqrlxmc) {
		this.tqrlxmc = tqrlxmc;
	}
	public String getTqssrq() {
		return tqssrq = tqssrq == null?"":tqssrq.trim();
	}
	public void setTqssrq(String tqssrq) {
		this.tqssrq = tqssrq;
	}
	public String getClrq() {
		return clrq = clrq == null?"":clrq.trim();
	}
	public void setClrq(String clrq) {
		this.clrq = clrq;
	}
	public String getClfsdm() {
		return clfsdm = clfsdm == null?"":clfsdm.trim();
	}
	public void setClfsdm(String clfsdm) {
		this.clfsdm = clfsdm;
	}
	public String getClfsmc() {
		return clfsmc = clfsmc == null?"":clfsmc.trim();
	}
	public void setClfsmc(String clfsmc) {
		this.clfsmc = clfsmc;
	}
	public String getQqpcje() {
		return qqpcje = qqpcje == null?"":qqpcje.trim();
	}
	public void setQqpcje(String qqpcje) {
		this.qqpcje = qqpcje;
	}
}