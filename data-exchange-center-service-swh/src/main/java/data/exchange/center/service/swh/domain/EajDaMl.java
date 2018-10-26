package data.exchange.center.service.swh.domain;

import java.math.BigDecimal;

public class EajDaMl extends EaJzMlKey {
    private String mlbh;

    private String mlmc;

    private BigDecimal sfsyzq;

    private String ajbs;

    private BigDecimal xh;

    public String getAjbs() {
        return ajbs;
    }

    public void setAjbs(String ajbs) {
        this.ajbs = ajbs == null ? null : ajbs.trim();
    }

    public BigDecimal getXh() {
        return xh;
    }

    public void setXh(BigDecimal xh) {
        this.xh = xh;
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

    public BigDecimal getSfsyzq() {
        return sfsyzq;
    }

    public void setSfsyzq(BigDecimal sfsyzq) {
        this.sfsyzq = sfsyzq;
    }
}