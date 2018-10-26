package data.exchange.center.service.sfgk.domain.ms;

public class Pcglr {

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
	 * 管理人类型代码
	 */
	private String glrlxdm;
	/**
	 * 管理人类型名称
	 */
	private String glrlxmc;
	/**
	 * 担任管理人职务代码
	 */
	private String drglrzwdm;
	/**
	 * 担任管理人职务名称
	 */
	private String drglrzwmc;
	/**
	 * 破产管理人
	 */
	private String pcglr;
	/**
	 * 管理人指定日期
	 */
	private String glrzdrq;
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
	public String getGlrlxdm() {
		return glrlxdm = glrlxdm == null?"":glrlxdm.trim();
	}
	public void setGlrlxdm(String glrlxdm) {
		this.glrlxdm = glrlxdm;
	}
	public String getGlrlxmc() {
		return glrlxmc = glrlxmc == null?"":glrlxmc.trim();
	}
	public void setGlrlxmc(String glrlxmc) {
		this.glrlxmc = glrlxmc;
	}
	public String getDrglrzwdm() {
		return drglrzwdm = drglrzwdm == null?"":drglrzwdm.trim();
	}
	public void setDrglrzwdm(String drglrzwdm) {
		this.drglrzwdm = drglrzwdm;
	}
	public String getDrglrzwmc() {
		return drglrzwmc = drglrzwmc == null?"":drglrzwmc.trim();
	}
	public void setDrglrzwmc(String drglrzwmc) {
		this.drglrzwmc = drglrzwmc;
	}
	public String getPcglr() {
		return pcglr = pcglr == null?"":pcglr.trim();
	}
	public void setPcglr(String pcglr) {
		this.pcglr = pcglr;
	}
	public String getGlrzdrq() {
		return glrzdrq = glrzdrq == null?"":glrzdrq.trim();
	}
	public void setGlrzdrq(String glrzdrq) {
		this.glrzdrq = glrzdrq;
	}
}