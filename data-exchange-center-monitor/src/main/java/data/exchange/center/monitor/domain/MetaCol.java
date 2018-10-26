package data.exchange.center.monitor.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月20日 上午10:50:59</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class MetaCol implements Serializable {

	/**
	 * 2017年7月20日上午10:51:03
	 * yuguang
	 */
	private static final long serialVersionUID = 5858391234741391042L;

	private String c_tableid;
	private String n_sn;
	private String c_ecolname;
	private String c_ccolname;
	private String c_datatype;
	private String n_datalen;
	private String n_precision;
	private String c_desc;
	private String c_codeid;
	private String c_notnull;
	private String c_pucol;
	private String c_userid;
	private Date d_create;
	private Date d_update;
	public String getC_tableid() {
		return c_tableid;
	}
	public void setC_tableid(String c_tableid) {
		this.c_tableid = c_tableid;
	}
	public String getN_sn() {
		return n_sn;
	}
	public void setN_sn(String n_sn) {
		this.n_sn = n_sn;
	}
	public String getC_ecolname() {
		return c_ecolname;
	}
	public void setC_ecolname(String c_ecolname) {
		this.c_ecolname = c_ecolname;
	}
	public String getC_ccolname() {
		return c_ccolname;
	}
	public void setC_ccolname(String c_ccolname) {
		this.c_ccolname = c_ccolname;
	}
	public String getC_datatype() {
		return c_datatype;
	}
	public void setC_datatype(String c_datatype) {
		this.c_datatype = c_datatype;
	}
	public String getN_datalen() {
		return n_datalen;
	}
	public void setN_datalen(String n_datalen) {
		this.n_datalen = n_datalen;
	}
	public String getN_precision() {
		return n_precision;
	}
	public void setN_precision(String n_precision) {
		this.n_precision = n_precision;
	}
	public String getC_desc() {
		return c_desc;
	}
	public void setC_desc(String c_desc) {
		this.c_desc = c_desc;
	}
	public String getC_codeid() {
		return c_codeid;
	}
	public void setC_codeid(String c_codeid) {
		this.c_codeid = c_codeid;
	}
	public String getC_notnull() {
		return c_notnull;
	}
	public void setC_notnull(String c_notnull) {
		this.c_notnull = c_notnull;
	}
	public String getC_pucol() {
		return c_pucol;
	}
	public void setC_pucol(String c_pucol) {
		this.c_pucol = c_pucol;
	}
	public String getC_userid() {
		return c_userid;
	}
	public void setC_userid(String c_userid) {
		this.c_userid = c_userid;
	}
	public Date getD_create() {
		return d_create;
	}
	public void setD_create(Date d_create) {
		this.d_create = d_create;
	}
	public Date getD_update() {
		return d_update;
	}
	public void setD_update(Date d_update) {
		this.d_update = d_update;
	}
}
