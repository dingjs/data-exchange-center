package data.exchange.center.service.sfgk.domain.wzfwl;

public class Wzfwl {

	/**
	 * 编号
	 */
	private String bh;
	/**
	 * 法院编号
	 */
	private String fybh;
	/**
	 * 访问量
	 */
	private String fwl;
	/**
	 * 统计访问量的对应日期
	 */
	private String tjfwlddyrq;
	/**
	 * 更新时间
	 */
	private String gxsj;
	public String getBh() {
		return bh = bh == null?"":bh.trim();
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getFybh() {
		return fybh = fybh == null?"":fybh.trim();
	}
	public void setFybh(String fybh) {
		this.fybh = fybh;
	}
	public String getFwl() {
		return fwl = fwl == null?"":fwl.trim();
	}
	public void setFwl(String fwl) {
		this.fwl = fwl;
	}
	public String getTjfwlddyrq() {
		return tjfwlddyrq = tjfwlddyrq == null?"":tjfwlddyrq.trim();
	}
	public void setTjfwlddyrq(String tjfwlddyrq) {
		this.tjfwlddyrq = tjfwlddyrq;
	}
	public String getGxsj() {
		return gxsj = gxsj == null?"":gxsj.trim();
	}
	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}
}