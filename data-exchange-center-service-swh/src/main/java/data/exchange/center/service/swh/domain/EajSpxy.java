package data.exchange.center.service.swh.domain;

public class EajSpxy extends EajSpxyKey {
    private String js;

    private String cy;

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js == null ? null : js.trim();
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy == null ? null : cy.trim();
    }
}