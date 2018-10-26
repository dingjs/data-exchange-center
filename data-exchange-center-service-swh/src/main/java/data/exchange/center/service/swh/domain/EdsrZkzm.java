package data.exchange.center.service.swh.domain;

public class EdsrZkzm extends EdsrZkzmKey {
    private String zmzs;

    public String getZmzs() {
        return zmzs;
    }

    public void setZmzs(String zmzs) {
        this.zmzs = zmzs == null ? null : zmzs.trim();
    }
}