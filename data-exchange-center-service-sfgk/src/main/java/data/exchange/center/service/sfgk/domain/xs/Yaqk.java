package data.exchange.center.service.sfgk.domain.xs;

public class Yaqk {

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
	 * 案号
	 */
	private String ah;
	/**
	 * 经办法院代码
	 */
	private String jbfydm;
	/**
	 * 经办法院名称
	 */
	private String jbfymc;
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
	public String getAh() {
		return ah = ah == null?"":ah.trim();
	}
	public void setAh(String ah) {
		this.ah = ah;
	}
	public String getJbfydm() {
		return jbfydm = jbfydm == null?"":jbfydm.trim();
	}
	public void setJbfydm(String jbfydm) {
		this.jbfydm = jbfydm;
	}
	public String getJbfymc() {
		return jbfymc = jbfymc == null?"":jbfymc.trim();
	}
	public void setJbfymc(String jbfymc) {
		this.jbfymc = jbfymc;
	}
}