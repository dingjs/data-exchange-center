package data.exchange.center.service.factor.trial.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Laxx{
	private BigDecimal ajbs;
	
    private String ajlx;

    private String deptcode;

    private Date regdate;

    private Date lastupdate;

    private Date contentupd;

    private String ah;

    private String ajjzjd;

    private Date larq;

    private Date jarq;

    private BigDecimal cbsptbs;

    private String cbspt;

    private BigDecimal cbr;

    private BigDecimal laay;

    public BigDecimal getAjbs() {
		return ajbs;
	}

	public void setAjbs(BigDecimal ajbs) {
		this.ajbs = ajbs;
	}

	public BigDecimal getLaay() {
		return laay;
	}

	public void setLaay(BigDecimal laay) {
		this.laay = laay;
	}

	public void setCbr(BigDecimal cbr) {
		this.cbr = cbr;
	}

	public String getAjlx() {
        return ajlx;
    }

    public void setAjlx(String ajlx) {
        this.ajlx = ajlx == null ? null : ajlx.trim();
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode == null ? null : deptcode.trim();
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    public Date getContentupd() {
        return contentupd;
    }

    public void setContentupd(Date contentupd) {
        this.contentupd = contentupd;
    }

    public String getAh() {
        return ah;
    }

    public void setAh(String ah) {
        this.ah = ah == null ? null : ah.trim();
    }

    public String getAjjzjd() {
        return ajjzjd;
    }

    public void setAjjzjd(String ajjzjd) {
        this.ajjzjd = ajjzjd == null ? null : ajjzjd.trim();
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

    public BigDecimal getCbsptbs() {
        return cbsptbs;
    }

    public void setCbsptbs(BigDecimal cbsptbs) {
        this.cbsptbs = cbsptbs;
    }

    public String getCbspt() {
        return cbspt;
    }

    public void setCbspt(String cbspt) {
        this.cbspt = cbspt == null ? null : cbspt.trim();
    }

    public BigDecimal getCbr() {
        return cbr;
    }
}