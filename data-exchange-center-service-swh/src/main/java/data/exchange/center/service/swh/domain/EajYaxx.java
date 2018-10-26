package data.exchange.center.service.swh.domain;

public class EajYaxx  {
	private String ahdm;
	
	private String xh;
	
    private String jbfy;

    private String yabs;

    private String ysah;

    private String ycbrbs;

    public String getJbfy() {
        return jbfy;
    }

    public String getAhdm() {
        return ahdm;
    }

    public void setAhdm(String ahdm) {
        this.ahdm = ahdm;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public void setJbfy(String jbfy) {
        this.jbfy = jbfy == null ? null : jbfy.trim();
    }

    public String getYabs() {
        return yabs;
    }

    public void setYabs(String yabs) {
        this.yabs = yabs == null ? null : yabs.trim();
    }

    public String getYsah() {
        return ysah;
    }

    public void setYsah(String ysah) {
        this.ysah = ysah == null ? null : ysah.trim();
    }

    public String getYcbrbs() {
        return ycbrbs;
    }

    public void setYcbrbs(String ycbrbs) {
        this.ycbrbs = ycbrbs == null ? null : ycbrbs.trim();
    }
}