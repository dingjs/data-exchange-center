package data.exchange.center.service.sfgk.domain.ms;

public class Spzzcy {

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
	 * 姓名
	 */
	private String xm;
	/**
	 * 角色代码
	 */
	private String jsdm;
	/**
	 * 角色名称
	 */
	private String jsmc;
	/**
	 * 联系电话
	 */
	private String lxdh;
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
	public String getXm() {
		return xm = xm == null?"":xm.trim();
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getJsdm() {
		return jsdm = jsdm == null?"":jsdm.trim();
	}
	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}
	public String getJsmc() {
		return jsmc = jsmc == null?"":jsmc.trim();
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	public String getLxdh() {
		return lxdh = lxdh == null?"":lxdh.trim();
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
}