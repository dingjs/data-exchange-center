package data.exchange.center.service.guangan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月5日 上午11:39:10</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class Zzjg implements Serializable{
    /**
	 * 2017年7月5日上午11:39:26
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private Date regTime;

    private Date updateTime;

    private String deptCode;

    private String appCode;

    private String jgbz;

    private String jgmc;

    private BigDecimal jglx;

    private BigDecimal yx;

    private BigDecimal jgjb;

    private String sjjgbz;

    private String jgzn;

    private String bz;

    private BigDecimal zzjgbz;

    private BigDecimal fybz;

    private BigDecimal dm;

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

    public String getJgbz() {
        return jgbz;
    }

    public void setJgbz(String jgbz) {
        this.jgbz = jgbz == null ? null : jgbz.trim();
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc == null ? null : jgmc.trim();
    }

    public BigDecimal getJglx() {
        return jglx;
    }

    public void setJglx(BigDecimal jglx) {
        this.jglx = jglx;
    }

    public BigDecimal getYx() {
        return yx;
    }

    public void setYx(BigDecimal yx) {
        this.yx = yx;
    }

    public BigDecimal getJgjb() {
        return jgjb;
    }

    public void setJgjb(BigDecimal jgjb) {
        this.jgjb = jgjb;
    }

    public String getSjjgbz() {
        return sjjgbz;
    }

    public void setSjjgbz(String sjjgbz) {
        this.sjjgbz = sjjgbz == null ? null : sjjgbz.trim();
    }

    public String getJgzn() {
        return jgzn;
    }

    public void setJgzn(String jgzn) {
        this.jgzn = jgzn == null ? null : jgzn.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public BigDecimal getZzjgbz() {
        return zzjgbz;
    }

    public void setZzjgbz(BigDecimal zzjgbz) {
        this.zzjgbz = zzjgbz;
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
}