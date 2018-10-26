package data.exchange.center.api.gateway.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Description:服务信息
 * <p>Company: xinya </p>
 * <p>Date:2017年9月14日 下午4:55:13</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class ServiceInfo implements Serializable{
    /**
	 * 2017年9月14日下午4:55:10
	 * yuguang
	 */
	private static final long serialVersionUID = 2158110178997041707L;

	private BigDecimal nSrvid;

    private String cSrvcname;

    private String cSrvename;

    private String cSrvlang;

    private String cSrvtype;

    private String cSrvurl;

    private String cDesc;

    private BigDecimal nInterval;

    private String cEnable;

    private String cUserid;

    private Date dCreate;

    private Date dUpdate;

    private String nSrvmode;

	public BigDecimal getnSrvid() {
		return nSrvid;
	}

	public void setnSrvid(BigDecimal nSrvid) {
		this.nSrvid = nSrvid;
	}

	public String getcSrvcname() {
		return cSrvcname;
	}

	public void setcSrvcname(String cSrvcname) {
		this.cSrvcname = cSrvcname;
	}

	public String getcSrvename() {
		return cSrvename;
	}

	public void setcSrvename(String cSrvename) {
		this.cSrvename = cSrvename;
	}

	public String getcSrvlang() {
		return cSrvlang;
	}

	public void setcSrvlang(String cSrvlang) {
		this.cSrvlang = cSrvlang;
	}

	public String getcSrvtype() {
		return cSrvtype;
	}

	public void setcSrvtype(String cSrvtype) {
		this.cSrvtype = cSrvtype;
	}

	public String getcSrvurl() {
		return cSrvurl;
	}

	public void setcSrvurl(String cSrvurl) {
		this.cSrvurl = cSrvurl;
	}

	public String getcDesc() {
		return cDesc;
	}

	public void setcDesc(String cDesc) {
		this.cDesc = cDesc;
	}

	public BigDecimal getnInterval() {
		return nInterval;
	}

	public void setnInterval(BigDecimal nInterval) {
		this.nInterval = nInterval;
	}

	public String getcEnable() {
		return cEnable;
	}

	public void setcEnable(String cEnable) {
		this.cEnable = cEnable;
	}

	public String getcUserid() {
		return cUserid;
	}

	public void setcUserid(String cUserid) {
		this.cUserid = cUserid;
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

	public String getnSrvmode() {
		return nSrvmode;
	}

	public void setnSrvmode(String nSrvmode) {
		this.nSrvmode = nSrvmode;
	}

	@Override
	public String toString() {
		return "ServiceInfo [nSrvid=" + nSrvid + ", cSrvcname=" + cSrvcname + ", cSrvename=" + cSrvename + ", cSrvlang="
				+ cSrvlang + ", cSrvtype=" + cSrvtype + ", cSrvurl=" + cSrvurl + ", cDesc=" + cDesc + ", nInterval="
				+ nInterval + ", cEnable=" + cEnable + ", cUserid=" + cUserid + ", dCreate=" + dCreate + ", dUpdate="
				+ dUpdate + ", nSrvmode=" + nSrvmode + "]";
	}
}