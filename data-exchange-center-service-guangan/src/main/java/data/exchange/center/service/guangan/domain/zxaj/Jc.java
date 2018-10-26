package data.exchange.center.service.guangan.domain.zxaj;

import java.math.BigDecimal;
import java.util.Date;

public class Jc {
    private BigDecimal ajbs;

    private String id;

    private Date regTime;

    private Date updateTime;

    private String deptCode;

    private String appCode;

    private BigDecimal xh;

    private String bjcr;

    private String jcyy;

    private String jcdd;

    private Date kssj;

    private Date jssj;

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

    public BigDecimal getXh() {
        return xh;
    }

    public void setXh(BigDecimal xh) {
        this.xh = xh;
    }

    public String getBjcr() {
        return bjcr;
    }

    public void setBjcr(String bjcr) {
        this.bjcr = bjcr == null ? null : bjcr.trim();
    }

    public String getJcyy() {
        return jcyy;
    }

    public void setJcyy(String jcyy) {
        this.jcyy = jcyy == null ? null : jcyy.trim();
    }

    public String getJcdd() {
        return jcdd;
    }

    public void setJcdd(String jcdd) {
        this.jcdd = jcdd == null ? null : jcdd.trim();
    }

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }
}