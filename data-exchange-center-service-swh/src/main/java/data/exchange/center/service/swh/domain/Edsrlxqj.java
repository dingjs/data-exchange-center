package data.exchange.center.service.swh.domain;

public class Edsrlxqj extends EdsrlxqjKey {
    private String zl;

    private String qj;

    private byte[] fzss;

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl == null ? null : zl.trim();
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj == null ? null : qj.trim();
    }

    public byte[] getFzss() {
        return fzss;
    }

    public void setFzss(byte[] fzss) {
        this.fzss = fzss;
    }
}