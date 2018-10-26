package data.exchange.center.service.businessdata.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * DC_MON_XML_OUT表
 * @author bmj
 *
 */
public class DcMonXmlOutAll implements Serializable{
	
	/** @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
     */   
    private static final long serialVersionUID = 1L;
    private String ajbs;
	private String ajlx;
	private String fydm;
	private String ajzt;
	private String ah;
	private Date larq;
	private Date jarq;
	private Object xmlnr;
	private Date createtime;
	private Date lastupdate;
	private String type;
	private String uuid;
	private Date sendtime;
	private String inputsrc;
	private String destschema;
	private String ajsource;
	private String xmltype;
	public String getAjbs() {
		return ajbs;
	}
	public void setAjbs(String ajbs) {
		this.ajbs = ajbs;
	}
	public String getAjlx() {
		return ajlx;
	}
	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	public String getFydm() {
		return fydm;
	}
	public void setFydm(String fydm) {
		this.fydm = fydm;
	}
	public String getAjzt() {
		return ajzt;
	}
	public void setAjzt(String ajzt) {
		this.ajzt = ajzt;
	}
	public String getAh() {
		return ah;
	}
	public void setAh(String ah) {
		this.ah = ah;
	}
	public Date getLarq() {
		return larq;
	}
	public void setLarq(Date larq) {
		this.larq = larq;
	}
	public Date getJarq() {
		return jarq;
	}
	public void setJarq(Date jarq) {
		this.jarq = jarq;
	}
	public Object getXmlnr() {
		return xmlnr;
	}
	public void setXmlnr(Object xmlnr) {
		this.xmlnr = xmlnr;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getLastupdate() {
		return lastupdate;
	}
	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public String getInputsrc() {
		return inputsrc;
	}
	public void setInputsrc(String inputsrc) {
		this.inputsrc = inputsrc;
	}
	public String getDestschema() {
		return destschema;
	}
	public void setDestschema(String destschema) {
		this.destschema = destschema;
	}
	public String getAjsource() {
		return ajsource;
	}
	public void setAjsource(String ajsource) {
		this.ajsource = ajsource;
	}
	public String getXmltype() {
		return xmltype;
	}
	public void setXmltype(String xmltype) {
		this.xmltype = xmltype;
	}
	
}
