package data.exchange.center.service.guangan.domain;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年6月26日 下午5:23:21</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class AjbsInfo implements Serializable {

	/**
	 * 2017年6月26日下午5:23:24
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	private String ajbs;
	private String ah;
	public String getAjbs() {
		return ajbs;
	}
	public void setAjbs(String ajbs) {
		this.ajbs = ajbs;
	}
	public String getAh() {
		return ah;
	}
	public void setAh(String ah) {
		this.ah = ah;
	}
	@Override
	public String toString() {
		return "AjbsList [ajbs=" + ajbs + ", ah=" + ah + "]";
	}
}
