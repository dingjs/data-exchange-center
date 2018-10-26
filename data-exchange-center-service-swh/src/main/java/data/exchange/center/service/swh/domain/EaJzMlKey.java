package data.exchange.center.service.swh.domain;

import java.math.BigDecimal;

public class EaJzMlKey {
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
}