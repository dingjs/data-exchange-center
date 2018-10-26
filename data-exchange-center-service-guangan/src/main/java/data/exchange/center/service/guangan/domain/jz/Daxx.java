package data.exchange.center.service.guangan.domain.jz;

import java.math.BigDecimal;
import java.util.Date;

public class Daxx {
    private BigDecimal ajbs;

    private String id;

    private Date regTime;

    private Date updateTime;

    private String deptCode;

    private String appCode;

    private String ah;

    private Date larq;

    private String cbr;

    private BigDecimal ajlx;

    private BigDecimal zys;

    private String ajlb;

    private BigDecimal dahjz;

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

    public String getAh() {
        return ah;
    }

    public void setAh(String ah) {
        this.ah = ah == null ? null : ah.trim();
    }

    public Date getLarq() {
        return larq;
    }

    public void setLarq(Date larq) {
        this.larq = larq;
    }

    public String getCbr() {
        return cbr;
    }

    public void setCbr(String cbr) {
        this.cbr = cbr == null ? null : cbr.trim();
    }

    public BigDecimal getAjlx() {
        return ajlx;
    }

    public void setAjlx(BigDecimal ajlx) {
        this.ajlx = ajlx;
    }

    public BigDecimal getZys() {
        return zys;
    }

    public void setZys(BigDecimal zys) {
        this.zys = zys;
    }

    public String getAjlb() {
        return ajlb;
    }

    public void setAjlb(String ajlb) {
        this.ajlb = ajlb == null ? null : ajlb.trim();
    }

    public BigDecimal getDahjz() {
        return dahjz;
    }

    public void setDahjz(BigDecimal dahjz) {
        this.dahjz = dahjz;
    }
}