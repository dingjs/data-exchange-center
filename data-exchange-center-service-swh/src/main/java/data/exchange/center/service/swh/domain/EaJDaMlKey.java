package data.exchange.center.service.swh.domain;

public class EaJDaMlKey {
    private String ajbs;

    private String mlbh;

    public String getAjbs() {
        return ajbs;
    }

    public void setAjbs(String ajbs) {
        this.ajbs = ajbs == null ? null : ajbs.trim();
    }

    public String getMlbh() {
        return mlbh;
    }

    public void setMlbh(String mlbh) {
        this.mlbh = mlbh == null ? null : mlbh.trim();
    }
}