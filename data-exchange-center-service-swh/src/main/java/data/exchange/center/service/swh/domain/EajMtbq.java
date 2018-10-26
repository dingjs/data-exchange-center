package data.exchange.center.service.swh.domain;

public class EajMtbq extends EajMtbqKey {
    private String zzsy;

    private String bqlx;

    public String getZzsy() {
        return zzsy;
    }

    public void setZzsy(String zzsy) {
        this.zzsy = zzsy == null ? null : zzsy.trim();
    }

    public String getBqlx() {
        return bqlx;
    }

    public void setBqlx(String bqlx) {
        this.bqlx = bqlx == null ? null : bqlx.trim();
    }
}