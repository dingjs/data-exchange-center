package data.exchange.center.service.guangan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Fybm implements Serializable{
    /**
	 * 2017年7月5日上午11:39:37
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private Date regTime;

    private Date updateTime;

    private String deptCode;

    private String appCode;

    private BigDecimal fybz;

    private BigDecimal dm;

    private BigDecimal jglx;

    private String fymc;

    private BigDecimal yx;

    private String dqm;

    private String bz;

    private String fyjc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public BigDecimal getFybz() {
        return fybz;
    }

    public void setFybz(BigDecimal fybz) {
        this.fybz = fybz;
    }

    public BigDecimal getDm() {
        return dm;
    }

    public void setDm(BigDecimal dm) {
        this.dm = dm;
    }

    public BigDecimal getJglx() {
        return jglx;
    }

    public void setJglx(BigDecimal jglx) {
        this.jglx = jglx;
    }

    public String getFymc() {
        return fymc;
    }

    public void setFymc(String fymc) {
        this.fymc = fymc == null ? null : fymc.trim();
    }

    public BigDecimal getYx() {
        return yx;
    }

    public void setYx(BigDecimal yx) {
        this.yx = yx;
    }

    public String getDqm() {
        return dqm;
    }

    public void setDqm(String dqm) {
        this.dqm = dqm == null ? null : dqm.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getFyjc() {
        return fyjc;
    }

    public void setFyjc(String fyjc) {
        this.fyjc = fyjc == null ? null : fyjc.trim();
    }
}