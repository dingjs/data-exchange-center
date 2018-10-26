package data.exchange.center.service.swh.domain;

import java.math.BigDecimal;

public class EaJJZMl extends EaJDaMlKey {
    private String mlmc;

    private BigDecimal xh;

    private BigDecimal ksys;

    private BigDecimal jsys;

    private BigDecimal sfsyzq;
    private String ajbs;

    private String mlbh;

    public String getAjbs() {
        return ajbs;
    }

    public void setAjbs(String ajbs) {
        this.ajbs = ajbs == null ? null : ajbs.trim();
    }

    public String getMlbh() {
        return mlbh;
    }

    public void setMlbh(String mlbh) {
        this.mlbh = mlbh == null ? null : mlbh.trim();
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