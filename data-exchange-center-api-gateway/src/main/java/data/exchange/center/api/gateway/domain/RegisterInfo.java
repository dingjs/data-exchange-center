package data.exchange.center.api.gateway.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RegisterInfo implements Serializable {

	/**
	 * 2017年9月8日下午5:54:33 yuguang
	 */
	private static final long serialVersionUID = 1743904231738349880L;

	private BigDecimal nSrvid;

	private String cDeptcode;

	private String cUname;

	private String cPname;

	private String cMphone;

	private String cInnerip;

	private String cToken;

	private String cMethod;

	private String cUserid;

	private Date dCreate;

	private Date dUpdate;

	public BigDecimal getnSrvid() {
		return nSrvid;
	}

	public void setnSrvid(BigDecimal nSrvid) {
		this.nSrvid = nSrvid;
	}

	public String getcDeptcode() {
		return cDeptcode;
	}

	public void setcDeptcode(String cDeptcode) {
		this.cDeptcode = cDeptcode == null ? null : cDeptcode.trim();
	}

	public String getcUname() {
		return cUname;
	}

	public void setcUname(String cUname) {
		this.cUname = cUname == null ? null : cUname.trim();
	}

	public String getcPname() {
		return cPname;
	}

	public void setcPname(String cPname) {
		this.cPname = cPname == null ? null : cPname.trim();
	}

	public String getcMphone() {
		return cMphone;
	}

	public void setcMphone(String cMphone) {
		this.cMphone = cMphone == null ? null : cMphone.trim();
	}

	public String getcInnerip() {
		return cInnerip;
	}

	public void setcInnerip(String cInnerip) {
		this.cInnerip = cInnerip == null ? null : cInnerip.trim();
	}

	public String getcToken() {
		return cToken;
	}

	public void setcToken(String cToken) {
		this.cToken = cToken == null ? null : cToken.trim();
	}

	public String getcMethod() {
		return cMethod;
	}

	public void setcMethod(String cMethod) {
		this.cMethod = cMethod == null ? null : cMethod.trim();
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

	@Override
	public String toString() {
		return "RegisterInfo [nSrvid=" + nSrvid + ", cDeptcode=" + cDeptcode + ", cUname=" + cUname + ", cPname="
				+ cPname + ", cMphone=" + cMphone + ", cInnerip=" + cInnerip + ", cToken=" + cToken + ", cMethod="
				+ cMethod + ", cUserid=" + cUserid + ", dCreate=" + dCreate + ", dUpdate=" + dUpdate + "]";
	}
}