package data.exchange.center.service.unstructured.node.domain;

import java.util.Date;

public class SubTask {
	private Integer ntaskid;
	private Integer norder;	
	private String 	cdesc;
	private String 	ctype;	
	private String 	cdetail;
	private String 	cretmsg;
	private Date 	dstamp;
	public Integer getNtaskid() {
		return ntaskid;
	}
	public void setNtaskid(Integer ntaskid) {
		this.ntaskid = ntaskid;
	}
	public Integer getNorder() {
		return norder;
	}
	public void setNorder(Integer norder) {
		this.norder = norder;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getCdetail() {
		return cdetail;
	}
	public void setCdetail(String cdetail) {
		this.cdetail = cdetail;
	}
	public String getCretmsg() {
		return cretmsg;
	}
	public void setCretmsg(String cretmsg) {
		this.cretmsg = cretmsg;
	}
	public Date getDstamp() {
		return dstamp;
	}
	public void setDstamp(Date dstamp) {
		this.dstamp = dstamp;
	}
	}
