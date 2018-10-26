package data.exchange.center.monitor.domain.modle;

import java.io.Serializable;

/**
 * 案件状态
 * 
 * @author baimaojun
 *
 */
public class Ajzt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fyjc;
	private String xs;
	private String jc;
	private String yj;
	private String wj;

	public String getFyjc() {
		return fyjc;
	}

	public void setFyjc(String fyjc) {
		this.fyjc = fyjc;
	}

	public String getXs() {
		return xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public String getYj() {
		return yj;
	}

	public void setYj(String yj) {
		this.yj = yj;
	}

	public String getWj() {
		return wj;
	}

	public void setWj(String wj) {
		this.wj = wj;
	}
}
