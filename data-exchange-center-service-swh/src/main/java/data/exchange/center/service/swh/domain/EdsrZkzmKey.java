package data.exchange.center.service.swh.domain;

public class EdsrZkzmKey {
    private String ahdm;

    private String xh;

    private String bgr;

    public String getAhdm() {
        return ahdm;
    }

    public void setAhdm(String ahdm) {
        this.ahdm = ahdm == null ? null : ahdm.trim();
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh == null ? null : xh.trim();
    }

    public String getBgr() {
        return bgr;
    }

    public void setBgr(String bgr) {
        this.bgr = bgr == null ? null : bgr.trim();
    }
}