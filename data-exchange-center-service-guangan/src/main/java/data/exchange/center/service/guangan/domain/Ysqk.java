package data.exchange.center.service.guangan.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * Description:原审案件信息
 * <p>Company: xinya </p>
 * <p>Date:2017年6月30日 下午4:46:54</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class Ysqk implements Serializable {
    /**
	 * 2017年6月30日下午4:46:47
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal ajbs;

    private String ah;
    
    private String spcx;

	public BigDecimal getAjbs() {
		return ajbs;
	}

	public void setAjbs(BigDecimal ajbs) {
		this.ajbs = ajbs;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}

	public String getSpcx() {
		return spcx;
	}

	public void setSpcx(String spcx) {
		this.spcx = spcx;
	}

	@Override
	public String toString() {
		return "Ysqk [ajbs=" + ajbs + ", ah=" + ah + ", spcx=" + spcx + "]";
	}
}