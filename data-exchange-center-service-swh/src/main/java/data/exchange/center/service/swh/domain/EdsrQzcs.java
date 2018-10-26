package data.exchange.center.service.swh.domain;

public class EdsrQzcs extends EdsrQzcsKey {
    private String jycs;

    private String ssrq;

    private String zl;

    private String jg;

    public String getJycs() {
        return jycs;
    }

    public void setJycs(String jycs) {
        this.jycs = jycs == null ? null : jycs.trim();
    }

    public String getSsrq() {
        return ssrq;
    }

    public void setSsrq(String ssrq) {
        this.ssrq = ssrq == null ? null : ssrq.trim();
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl == null ? null : zl.trim();
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg == null ? null : jg.trim();
    }
}