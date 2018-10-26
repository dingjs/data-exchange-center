package data.exchange.center.service.sfgk.domain.dx;

public class Dxtzjl {

	/**
	 * 编号
	 */
	private String bh;
	/**
	 * 案件编号
	 */
	private String ajbh;
	/**
	 * 案件标识
	 */
	private String ajbs;
	/**
	 * 接收人
	 */
	private String jsr;
	/**
	 * 接收人手机号
	 */
	private String jsrsjh;
	/**
	 * 接收人类型代码
	 */
	private String jsrlxdm;
	/**
	 * 接收人类型名称
	 */
	private String jsrlxmc;
	/**
	 * 法院编号
	 */
	private String fybh;
	/**
	 * 短信内容
	 */
	private String dxnr;
	/**
	 * 发送时间
	 */
	private String fssj;
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
	public String getAjbs() {
		return ajbs = ajbs == null?"":ajbs.trim();
	}
	public void setAjbs(String ajbs) {
		this.ajbs = ajbs;
	}
	public String getJsr() {
		return jsr = jsr == null?"":jsr.trim();
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public String getJsrsjh() {
		return jsrsjh = jsrsjh == null?"":jsrsjh.trim();
	}
	public void setJsrsjh(String jsrsjh) {
		this.jsrsjh = jsrsjh;
	}
	public String getJsrlxdm() {
		return jsrlxdm = jsrlxdm == null?"":jsrlxdm.trim();
	}
	public void setJsrlxdm(String jsrlxdm) {
		this.jsrlxdm = jsrlxdm;
	}
	public String getJsrlxmc() {
		return jsrlxmc = jsrlxmc == null?"":jsrlxmc.trim();
	}
	public void setJsrlxmc(String jsrlxmc) {
		this.jsrlxmc = jsrlxmc;
	}
	public String getFybh() {
		return fybh = fybh == null?"":fybh.trim();
	}
	public void setFybh(String fybh) {
		this.fybh = fybh;
	}
	public String getDxnr() {
		return dxnr = dxnr == null?"":dxnr.trim();
	}
	public void setDxnr(String dxnr) {
		this.dxnr = dxnr;
	}
	public String getFssj() {
		return fssj = fssj == null?"":fssj.trim();
	}
	public void setFssj(String fssj) {
		this.fssj = fssj;
	}
}