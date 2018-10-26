package data.exchange.center.service.swh.domain;

public class EfTzcdKey {
    private String ahdm;

    private String jnrxh;

    public String getAhdm() {
        return ahdm;
    }

    public void setAhdm(String ahdm) {
        this.ahdm = ahdm == null ? null : ahdm.trim();
    }

    public String getJnrxh() {
        return jnrxh;
    }

    public void setJnrxh(String jnrxh) {
        this.jnrxh = jnrxh == null ? null : jnrxh.trim();
    }
}