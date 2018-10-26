package data.exchange.center.service.swh.domain;

public class EajZj extends EajZjKey {
    private String jzr;

    private String mc;

    private String lx;

    private String zjss;

    private String rzjg;
    private byte[] rzly;

    private byte[] brzly;

    public byte[] getRzly() {
        return rzly;
    }

    public void setRzly(byte[] rzly) {
        this.rzly = rzly;
    }

    public byte[] getBrzly() {
        return brzly;
    }

    public void setBrzly(byte[] brzly) {
        this.brzly = brzly;
    }
    public String getJzr() {
        return jzr;
    }

    public void setJzr(String jzr) {
        this.jzr = jzr == null ? null : jzr.trim();
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc == null ? null : mc.trim();
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx == null ? null : lx.trim();
    }

    public String getZjss() {
        return zjss;
    }

    public void setZjss(String zjss) {
        this.zjss = zjss == null ? null : zjss.trim();
    }

    public String getRzjg() {
        return rzjg;
    }

    public void setRzjg(String rzjg) {
        this.rzjg = rzjg == null ? null : rzjg.trim();
    }
}