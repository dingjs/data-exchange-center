package data.exchange.center.service.swh.domain;

import java.math.BigDecimal;

public class EajFtsy extends EajFtsyKey {
    private String dtdsr;

    private String ktrq;

    private String ktft;

    private BigDecimal ptrs;

    private String gkkt;

    private BigDecimal tc;

    public String getDtdsr() {
        return dtdsr;
    }

    public void setDtdsr(String dtdsr) {
        this.dtdsr = dtdsr == null ? null : dtdsr.trim();
    }

    public String getKtrq() {
        return ktrq;
    }

    public void setKtrq(String ktrq) {
        this.ktrq = ktrq == null ? null : ktrq.trim();
    }

    public String getKtft() {
        return ktft;
    }

    public void setKtft(String ktft) {
        this.ktft = ktft == null ? null : ktft.trim();
    }

    public BigDecimal getPtrs() {
        return ptrs;
    }

    public void setPtrs(BigDecimal ptrs) {
        this.ptrs = ptrs;
    }

    public String getGkkt() {
        return gkkt;
    }

    public void setGkkt(String gkkt) {
        this.gkkt = gkkt == null ? null : gkkt.trim();
    }

    public BigDecimal getTc() {
        return tc;
    }

    public void setTc(BigDecimal tc) {
        this.tc = tc;
    }
}