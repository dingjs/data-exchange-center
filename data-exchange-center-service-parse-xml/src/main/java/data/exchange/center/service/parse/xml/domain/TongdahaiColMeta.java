/**
 * data-governance-provider-parse
 * created by yuguang at 2017年3月18日
 */
package data.exchange.center.service.parse.xml.domain;

import java.io.Serializable;

/**
 * Description:<临时方案>游标返回实体类
 * <p>Company: pelox </p>
 * <p>Date:2017年3月18日 下午10:07:21</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class TongdahaiColMeta implements Serializable {

    /**
     * 2017年3月18日下午10:07:24
     * yuguang
     */
    private static final long serialVersionUID = 8717973638335411381L;
    /**
     * 表中文名称
     */
    private String cTbname;
    /**
     * 表英文名称
     */
    private String cEtbname;
    /**
     * 数据项中文名称
     */
    private String cColname;
    /**
     * 数据项英文名称
     */
    private String cEcolname;
    /**
     * 数据项排序号
     */
    private Integer nXh;
    /**
     * 数据项类型
     */
    private String cDatatype;
    /**
     * 数据项长度
     */
    private Integer nDatalen;
    /**
     * 数据项精度
     */
    private Integer nPrecesion;
    /**
     * 数据项是否非空 N表示非空 Y表示可以为空
     */
    private String cNotnull;
    /**
     * 数据项是否主键和唯一索引列  YES表示为主键列或者唯一索引列 NO表示不是
     */
    private String cPu;
    public String getcTbname() {
        return cTbname;
    }
    public void setcTbname(String cTbname) {
        this.cTbname = cTbname;
    }
    public String getcEtbname() {
        return cEtbname;
    }
    public void setcEtbname(String cEtbname) {
        this.cEtbname = cEtbname;
    }
    public String getcColname() {
        return cColname;
    }
    public void setcColname(String cColname) {
        this.cColname = cColname;
    }
    public String getcEcolname() {
        return cEcolname;
    }
    public void setcEcolname(String cEcolname) {
        this.cEcolname = cEcolname;
    }
    public Integer getnXh() {
        return nXh;
    }
    public void setnXh(Integer nXh) {
        this.nXh = nXh;
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
    public Integer getnPrecesion() {
        return nPrecesion;
    }
    public void setnPrecesion(Integer nPrecesion) {
        this.nPrecesion = nPrecesion;
    }
    public String getcNotnull() {
        return cNotnull;
    }
    public void setcNotnull(String cNotnull) {
        this.cNotnull = cNotnull;
    }
    public String getcPu() {
        return cPu;
    }
    public void setcPu(String cPu) {
        this.cPu = cPu;
    }

}
