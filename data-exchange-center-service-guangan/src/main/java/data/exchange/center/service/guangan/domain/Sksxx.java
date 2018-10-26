package data.exchange.center.service.guangan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Sksxx implements Serializable {
    /**
	 * 2017年6月28日下午4:41:28
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal ajbs;

    private Date updateTime;

    private String deptCode;

    private String appCode;

    private BigDecimal ssr1;

    private String id;

    private BigDecimal ssfw;

    private BigDecimal qtsslx;

    private BigDecimal ssajlx;

    private String kssbh;

    private Date tqssrq;

    private Date chssrq;

    private Date ysajrq;

    private Date sjjsrq;

    private Date chksrq;

    private Date regTime;

    private BigDecimal sslx;

    private String ksjg;

    private Date ssrq;
    
    private byte[] ssr;

    private byte[] sscl;

    private byte[] tqsscl;

    public byte[] getSsr() {
        return ssr;
    }

    public void setSsr(byte[] ssr) {
        this.ssr = ssr;
    }

    public byte[] getSscl() {
        return sscl;
    }

    public void setSscl(byte[] sscl) {
        this.sscl = sscl;
    }

    public byte[] getTqsscl() {
        return tqsscl;
    }

    public void setTqsscl(byte[] tqsscl) {
        this.tqsscl = tqsscl;
    }

    public BigDecimal getAjbs() {
        return ajbs;
    }

    public void setAjbs(BigDecimal ajbs) {
        this.ajbs = ajbs;
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

    public BigDecimal getSsr1() {
        return ssr1;
    }

    public void setSsr1(BigDecimal ssr1) {
        this.ssr1 = ssr1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public BigDecimal getSsfw() {
        return ssfw;
    }

    public void setSsfw(BigDecimal ssfw) {
        this.ssfw = ssfw;
    }

    public BigDecimal getQtsslx() {
        return qtsslx;
    }

    public void setQtsslx(BigDecimal qtsslx) {
        this.qtsslx = qtsslx;
    }

    public BigDecimal getSsajlx() {
        return ssajlx;
    }

    public void setSsajlx(BigDecimal ssajlx) {
        this.ssajlx = ssajlx;
    }

    public String getKssbh() {
        return kssbh;
    }

    public void setKssbh(String kssbh) {
        this.kssbh = kssbh == null ? null : kssbh.trim();
    }

    public Date getTqssrq() {
        return tqssrq;
    }

    public void setTqssrq(Date tqssrq) {
        this.tqssrq = tqssrq;
    }

    public Date getChssrq() {
        return chssrq;
    }

    public void setChssrq(Date chssrq) {
        this.chssrq = chssrq;
    }

    public Date getYsajrq() {
        return ysajrq;
    }

    public void setYsajrq(Date ysajrq) {
        this.ysajrq = ysajrq;
    }

    public Date getSjjsrq() {
        return sjjsrq;
    }

    public void setSjjsrq(Date sjjsrq) {
        this.sjjsrq = sjjsrq;
    }

    public Date getChksrq() {
        return chksrq;
    }

    public void setChksrq(Date chksrq) {
        this.chksrq = chksrq;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public BigDecimal getSslx() {
        return sslx;
    }

    public void setSslx(BigDecimal sslx) {
        this.sslx = sslx;
    }

    public String getKsjg() {
        return ksjg;
    }

    public void setKsjg(String ksjg) {
        this.ksjg = ksjg == null ? null : ksjg.trim();
    }

    public Date getSsrq() {
        return ssrq;
    }

    public void setSsrq(Date ssrq) {
        this.ssrq = ssrq;
    }
}