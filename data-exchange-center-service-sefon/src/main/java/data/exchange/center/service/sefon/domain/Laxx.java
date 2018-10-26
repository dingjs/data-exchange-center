package data.exchange.center.service.sefon.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Laxx{
	private BigDecimal ajbs;

    private String deptcode;
    
    private String lastupdate;

    private String ah;

    private String ajjzjd;

    private Date larq;

    private Date jarq;

	public BigDecimal getAjbs() {
		return ajbs;
	}

	public void setAjbs(BigDecimal ajbs) {
		this.ajbs = ajbs;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}

	public String getAjjzjd() {
		return ajjzjd;
	}

	public void setAjjzjd(String ajjzjd) {
		this.ajjzjd = ajjzjd;
	}

	public Date getLarq() {
		return larq;
	}

	public void setLarq(Date larq) {
		this.larq = larq;
	}

	public Date getJarq() {
		return jarq;
	}

	public void setJarq(Date jarq) {
		this.jarq = jarq;
	}
}