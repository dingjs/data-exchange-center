package data.exchange.center.service.swh.domain;

import java.math.BigDecimal;

public class EajSala {
    private String ahdm;

    private String jcjg;

    private String gsr;

    private String fdmspc;

    private String fdmsssqq;

    private String czfs;

    private BigDecimal slf;

    private String ajzlx;

    private String sdclrq;

    public String getAhdm() {
        return ahdm;
    }

    public void setAhdm(String ahdm) {
        this.ahdm = ahdm == null ? null : ahdm.trim();
    }

    public String getJcjg() {
        return jcjg;
    }

    public void setJcjg(String jcjg) {
        this.jcjg = jcjg == null ? null : jcjg.trim();
    }

    public String getGsr() {
        return gsr;
    }

    public void setGsr(String gsr) {
        this.gsr = gsr == null ? null : gsr.trim();
    }

    public String getFdmspc() {
        return fdmspc;
    }

    public void setFdmspc(String fdmspc) {
        this.fdmspc = fdmspc == null ? null : fdmspc.trim();
    }

    public String getFdmsssqq() {
        return fdmsssqq;
    }

    public void setFdmsssqq(String fdmsssqq) {
        this.fdmsssqq = fdmsssqq == null ? null : fdmsssqq.trim();
    }

    public String getCzfs() {
        return czfs;
    }

    public void setCzfs(String czfs) {
        this.czfs = czfs == null ? null : czfs.trim();
    }

    public BigDecimal getSlf() {
        return slf;
    }

    public void setSlf(BigDecimal slf) {
        this.slf = slf;
    }

    public String getAjzlx() {
        return ajzlx;
    }

    public void setAjzlx(String ajzlx) {
        this.ajzlx = ajzlx == null ? null : ajzlx.trim();
    }

    public String getSdclrq() {
        return sdclrq;
    }

    public void setSdclrq(String sdclrq) {
        this.sdclrq = sdclrq == null ? null : sdclrq.trim();
    }
}