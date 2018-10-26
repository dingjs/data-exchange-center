package data.exchange.center.service.guangan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Zxakxx implements Serializable {
    /**
	 * 2017年6月27日下午2:07:59
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal ajbs;

    private String id;

    private Date regTime;

    private Date updateTime;

    private String deptCode;

    private String appCode;

    private BigDecimal kwlx;

    private String kwsyrhjsr;

    private BigDecimal jnhffje;

    private String jfzhjzh;

    private String jnhffwp;

    private Date jnhffrq;

    public BigDecimal getAjbs() {
        return ajbs;
    }

    public void setAjbs(BigDecimal ajbs) {
        this.ajbs = ajbs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public BigDecimal getKwlx() {
        return kwlx;
    }

    public void setKwlx(BigDecimal kwlx) {
        this.kwlx = kwlx;
    }

    public String getKwsyrhjsr() {
        return kwsyrhjsr;
    }

    public void setKwsyrhjsr(String kwsyrhjsr) {
        this.kwsyrhjsr = kwsyrhjsr == null ? null : kwsyrhjsr.trim();
    }

    public BigDecimal getJnhffje() {
        return jnhffje;
    }

    public void setJnhffje(BigDecimal jnhffje) {
        this.jnhffje = jnhffje;
    }

    public String getJfzhjzh() {
        return jfzhjzh;
    }

    public void setJfzhjzh(String jfzhjzh) {
        this.jfzhjzh = jfzhjzh == null ? null : jfzhjzh.trim();
    }

    public String getJnhffwp() {
        return jnhffwp;
    }

    public void setJnhffwp(String jnhffwp) {
        this.jnhffwp = jnhffwp == null ? null : jnhffwp.trim();
    }

    public Date getJnhffrq() {
        return jnhffrq;
    }

    public void setJnhffrq(Date jnhffrq) {
        this.jnhffrq = jnhffrq;
    }
}