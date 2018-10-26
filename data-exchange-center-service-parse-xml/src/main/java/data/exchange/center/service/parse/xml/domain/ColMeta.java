/**
 * data-governance-provider-parse
 * created by yuguang at 2017年3月4日
 */
package data.exchange.center.service.parse.xml.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: ColMeta.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月4日 下午11:42:56</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class ColMeta implements Serializable {

    /**
     * 2017年3月4日下午11:42:59
     * yuguang
     */
    private static final long serialVersionUID = -6658388577346605107L;

    private String  cEtbname;
    private String  cCtbname;
    private String  cTableid;
    private Integer nSn;
    private String  cEcolname;
    private String  cCcolname;
    private String  cDatatype;
    private Integer nDatalen;
    private Integer nPrecision;
    private String  cDesc;
    private String  cCodeid;
    private String  cNotnull;
    private String  cPucol;
    private String  cUserid;
    private Date    dCreate;
    private Date    dUpdate;
    public String getcEtbname() {
        return cEtbname;
    }
    public void setcEtbname(String cEtbname) {
        this.cEtbname = cEtbname;
    }
    public String getcCtbname() {
        return cCtbname;
    }
    public void setcCtbname(String cCtbname) {
        this.cCtbname = cCtbname;
    }
    public String getcTableid() {
        return cTableid;
    }
    public void setcTableid(String cTableid) {
        this.cTableid = cTableid;
    }
    public Integer getnSn() {
        return nSn;
    }
    public void setnSn(Integer nSn) {
        this.nSn = nSn;
    }
    public String getcEcolname() {
        return cEcolname;
    }
    public void setcEcolname(String cEcolname) {
        this.cEcolname = cEcolname;
    }
    public String getcCcolname() {
        return cCcolname;
    }
    public void setcCcolname(String cCcolname) {
        this.cCcolname = cCcolname;
    }
    public String getcDatatype() {
        return cDatatype;
    }
    public void setcDatatype(String cDatatype) {
        this.cDatatype = cDatatype;
    }
    public Integer getnDatalen() {
        return nDatalen;
    }
    public void setnDatalen(Integer nDatalen) {
        this.nDatalen = nDatalen;
    }
    public Integer getnPrecision() {
        return nPrecision;
    }
    public void setnPrecision(Integer nPrecision) {
        this.nPrecision = nPrecision;
    }
    public String getcDesc() {
        return cDesc;
    }
    public void setcDesc(String cDesc) {
        this.cDesc = cDesc;
    }
    public String getcCodeid() {
        return cCodeid;
    }
    public void setcCodeid(String cCodeid) {
        this.cCodeid = cCodeid;
    }
    public String getcNotnull() {
        return cNotnull;
    }
    public void setcNotnull(String cNotnull) {
        this.cNotnull = cNotnull;
    }
    public String getcPucol() {
        return cPucol;
    }
    public void setcPucol(String cPucol) {
        this.cPucol = cPucol;
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
}
