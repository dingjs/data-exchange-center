/**
 * data-governance-provider-parse
 * created by yuguang at 2017年3月4日
 */
package data.exchange.center.service.parse.xml.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: TableMeta.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月4日 下午11:04:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class TableMeta implements Serializable {

    /**
     * 2017年3月4日下午11:04:53
     * yuguang
     */
    private static final long serialVersionUID = -5901567733743221795L;

    private String cTableid;
    private String cTreeid;
    private String cNodeid;
    private String cEtbname;
    private String cCtbname;
    private String cDesc;
    private String cAjlxcode;
    private String cUserid;
    private Date   dCreate;
    private Date   dUpdate;
    public String getcTableid() {
        return cTableid;
    }
    public void setcTableid(String cTableid) {
        this.cTableid = cTableid;
    }
    public String getcTreeid() {
        return cTreeid;
    }
    public void setcTreeid(String cTreeid) {
        this.cTreeid = cTreeid;
    }
    public String getcNodeid() {
        return cNodeid;
    }
    public void setcNodeid(String cNodeid) {
        this.cNodeid = cNodeid;
    }
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
    public String getcDesc() {
        return cDesc;
    }
    public void setcDesc(String cDesc) {
        this.cDesc = cDesc;
    }
    public String getcAjlxcode() {
        return cAjlxcode;
    }
    public void setcAjlxcode(String cAjlxcode) {
        this.cAjlxcode = cAjlxcode;
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
