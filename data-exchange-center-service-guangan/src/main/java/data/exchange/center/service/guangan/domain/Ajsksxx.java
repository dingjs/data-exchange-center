package data.exchange.center.service.guangan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Ajsksxx implements Serializable{
    /**
	 * 2017年6月27日下午1:38:52
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal ajbs;

    private String id;

    private Date regTime;

    private Date updateTime;

    private String deptCode;

    private String appCode;

    private BigDecimal ssfw;

    private BigDecimal sslx;

    private String ksjg;

    private Date tqssrq;

    private Date chssrq;

    private Date ysajrq;

    private Date sjjsrq;

    private BigDecimal qtsslx;

    private String kssbh;

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

    public BigDecimal getSsfw() {
        return ssfw;
    }

    public void setSsfw(BigDecimal ssfw) {
        this.ssfw = ssfw;
    }

    public BigDecimal getSslx() {
        return sslx;
    }

    public void setSslx(BigDecimal sslx) {
        this.sslx = sslx;
    }

    public String getKsjg() {
        return ksjg;
    }

    public void setKsjg(String ksjg) {
        this.ksjg = ksjg == null ? null : ksjg.trim();
    }

    public Date getTqssrq() {
        return tqssrq;
    }

    public void setTqssrq(Date tqssrq) {
        this.tqssrq = tqssrq;
    }

    public Date getChssrq() {
        return chssrq;
    }

    public void setChssrq(Date chssrq) {
        this.chssrq = chssrq;
    }

    public Date getYsajrq() {
        return ysajrq;
    }

    public void setYsajrq(Date ysajrq) {
        this.ysajrq = ysajrq;
    }

    public Date getSjjsrq() {
        return sjjsrq;
    }

    public void setSjjsrq(Date sjjsrq) {
        this.sjjsrq = sjjsrq;
    }

    public BigDecimal getQtsslx() {
        return qtsslx;
    }

    public void setQtsslx(BigDecimal qtsslx) {
        this.qtsslx = qtsslx;
    }

    public String getKssbh() {
        return kssbh;
    }

    public void setKssbh(String kssbh) {
        this.kssbh = kssbh == null ? null : kssbh.trim();
    }
}