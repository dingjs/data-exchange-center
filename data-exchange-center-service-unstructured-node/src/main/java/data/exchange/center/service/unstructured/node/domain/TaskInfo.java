package data.exchange.center.service.unstructured.node.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TaskInfo implements Serializable {
    /**
	 * 2017年11月20日下午6:10:58
	 * yuguang
	 */
	private static final long serialVersionUID = 4973448272809856185L;

	private Integer nTaskid;

    private String cTaskname;

    private String cDesc;

    private String cAppcode;

    private BigDecimal nNodeid;

    private String cLoop;

    private Integer nTaskcnt;

    private Integer nTasklev;

    private BigDecimal nStatus;

    private String cEnable;

    private String cUserid;

    private Date dCreate;

    private Date dUpdate;
    
    private String cFydm;

    public String getcFydm() {
		return cFydm;
	}

	public void setcFydm(String cFydm) {
		this.cFydm = cFydm;
	}

	public Integer getnTaskid() {
        return nTaskid;
    }

    public void setnTaskid(Integer nTaskid) {
        this.nTaskid = nTaskid;
    }

    public String getcTaskname() {
        return cTaskname;
    }

    public void setcTaskname(String cTaskname) {
        this.cTaskname = cTaskname == null ? null : cTaskname.trim();
    }

    public String getcDesc() {
        return cDesc;
    }

    public void setcDesc(String cDesc) {
        this.cDesc = cDesc == null ? null : cDesc.trim();
    }

    public String getcAppcode() {
        return cAppcode;
    }

    public void setcAppcode(String cAppcode) {
        this.cAppcode = cAppcode == null ? null : cAppcode.trim();
    }

    public BigDecimal getnNodeid() {
        return nNodeid;
    }

    public void setnNodeid(BigDecimal nNodeid) {
        this.nNodeid = nNodeid;
    }

    public String getcLoop() {
        return cLoop;
    }

    public void setcLoop(String cLoop) {
        this.cLoop = cLoop == null ? null : cLoop.trim();
    }

    public Integer getnTaskcnt() {
        return nTaskcnt;
    }

    public void setnTaskcnt(Integer nTaskcnt) {
        this.nTaskcnt = nTaskcnt;
    }

    public Integer getnTasklev() {
        return nTasklev;
    }

    public void setnTasklev(Integer nTasklev) {
        this.nTasklev = nTasklev;
    }

    public BigDecimal getnStatus() {
        return nStatus;
    }

    public void setnStatus(BigDecimal nStatus) {
        this.nStatus = nStatus;
    }

    public String getcEnable() {
        return cEnable;
    }

    public void setcEnable(String cEnable) {
        this.cEnable = cEnable == null ? null : cEnable.trim();
    }

    public String getcUserid() {
        return cUserid;
    }

    public void setcUserid(String cUserid) {
        this.cUserid = cUserid == null ? null : cUserid.trim();
    }

    public Date getdCreate() {
        return dCreate;
    }

    public void setdCreate(Date dCreate) {
        this.dCreate = dCreate;
    }

    public Date getdUpdate() {
        return dUpdate;
    }

    public void setdUpdate(Date dUpdate) {
        this.dUpdate = dUpdate;
    }
}