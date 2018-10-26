package data.exchange.center.service.swh.domain;

import java.math.BigDecimal;

public class EdsrDzlx extends EdsrDzlxKey {
    private String dzzm;

    private String zxzl;

    private BigDecimal zyxqn;

    private BigDecimal zyxqy;

    private String hxzl;

    private BigDecimal hxxqn;

    private BigDecimal hxxqy;

    private String dcfjx;

    private String bcfjx;

    private String bdzzqlzs;

    private BigDecimal bdzzqln;

    private BigDecimal bdzzqly;

    private BigDecimal fjse;

    private BigDecimal msccje;

    public String getDzzm() {
        return dzzm;
    }

    public void setDzzm(String dzzm) {
        this.dzzm = dzzm == null ? null : dzzm.trim();
    }

    public String getZxzl() {
        return zxzl;
    }

    public void setZxzl(String zxzl) {
        this.zxzl = zxzl == null ? null : zxzl.trim();
    }

    public BigDecimal getZyxqn() {
        return zyxqn;
    }

    public void setZyxqn(BigDecimal zyxqn) {
        this.zyxqn = zyxqn;
    }

    public BigDecimal getZyxqy() {
        return zyxqy;
    }

    public void setZyxqy(BigDecimal zyxqy) {
        this.zyxqy = zyxqy;
    }

    public String getHxzl() {
        return hxzl;
    }

    public void setHxzl(String hxzl) {
        this.hxzl = hxzl == null ? null : hxzl.trim();
    }

    public BigDecimal getHxxqn() {
        return hxxqn;
    }

    public void setHxxqn(BigDecimal hxxqn) {
        this.hxxqn = hxxqn;
    }

    public BigDecimal getHxxqy() {
        return hxxqy;
    }

    public void setHxxqy(BigDecimal hxxqy) {
        this.hxxqy = hxxqy;
    }

    public String getDcfjx() {
        return dcfjx;
    }

    public void setDcfjx(String dcfjx) {
        this.dcfjx = dcfjx == null ? null : dcfjx.trim();
    }

    public String getBcfjx() {
        return bcfjx;
    }

    public void setBcfjx(String bcfjx) {
        this.bcfjx = bcfjx == null ? null : bcfjx.trim();
    }

    public String getBdzzqlzs() {
        return bdzzqlzs;
    }

    public void setBdzzqlzs(String bdzzqlzs) {
        this.bdzzqlzs = bdzzqlzs == null ? null : bdzzqlzs.trim();
    }

    public BigDecimal getBdzzqln() {
        return bdzzqln;
    }

    public void setBdzzqln(BigDecimal bdzzqln) {
        this.bdzzqln = bdzzqln;
    }

    public BigDecimal getBdzzqly() {
        return bdzzqly;
    }

    public void setBdzzqly(BigDecimal bdzzqly) {
        this.bdzzqly = bdzzqly;
    }

    public BigDecimal getFjse() {
        return fjse;
    }

    public void setFjse(BigDecimal fjse) {
        this.fjse = fjse;
    }

    public BigDecimal getMsccje() {
        return msccje;
    }

    public void setMsccje(BigDecimal msccje) {
        this.msccje = msccje;
    }
}