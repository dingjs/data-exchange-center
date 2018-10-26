package data.exchange.center.service.swh.domain;

public class EajGxyy extends EajGxyyKey {
    private String gxyycl;

    private String gxwtlx;

    public String getGxyycl() {
        return gxyycl;
    }

    public void setGxyycl(String gxyycl) {
        this.gxyycl = gxyycl == null ? null : gxyycl.trim();
    }

    public String getGxwtlx() {
        return gxwtlx;
    }

    public void setGxwtlx(String gxwtlx) {
        this.gxwtlx = gxwtlx == null ? null : gxwtlx.trim();
    }
}