package data.exchange.center.service.guangan.domain.jz;

import java.math.BigDecimal;
import java.util.Date;

public class Mlxx {
    private BigDecimal ajbs;

    private String id;

    private Date regTime;

    private Date updateTime;

    private String deptCode;

    private String appCode;

    private String mlbh;

    private String fmlbh;

    private String mlmc;

    private BigDecimal xh;

    private BigDecimal ksys;

    private BigDecimal jsys;

    private BigDecimal sfsyzq;

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

    public String getMlbh() {
        return mlbh;
    }

    public void setMlbh(String mlbh) {
        this.mlbh = mlbh == null ? null : mlbh.trim();
    }

    public String getFmlbh() {
        return fmlbh;
    }

    public void setFmlbh(String fmlbh) {
        this.fmlbh = fmlbh == null ? null : fmlbh.trim();
    }

    public String getMlmc() {
        return mlmc;
    }

    public void setMlmc(String mlmc) {
        this.mlmc = mlmc == null ? null : mlmc.trim();
    }

    public BigDecimal getXh() {
        return xh;
    }

    public void setXh(BigDecimal xh) {
        this.xh = xh;
    }

    public BigDecimal getKsys() {
        return ksys;
    }

    public void setKsys(BigDecimal ksys) {
        this.ksys = ksys;
    }

    public BigDecimal getJsys() {
        return jsys;
    }

    public void setJsys(BigDecimal jsys) {
        this.jsys = jsys;
    }

    public BigDecimal getSfsyzq() {
        return sfsyzq;
    }

    public void setSfsyzq(BigDecimal sfsyzq) {
        this.sfsyzq = sfsyzq;
    }
}