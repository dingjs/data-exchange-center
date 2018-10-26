package data.exchange.center.service.pcaj.domain;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年12月26日 下午3:07:27</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class AjlxMeta implements Serializable {

    /**
     * 2017年3月4日下午10:46:49
     * yuguang
     */
    private static final long serialVersionUID = 4379765609916720862L;

    /**
     * id
     */
    private Integer nId;
    /**
     * 案件类型字符代表
     */
    private String cAjlx;
    /**
     * 案件类型中文名
     */
    private String cName;
    /**
     * 对应表前缀
     */
    private String cEprefix;
    /**
     * 案件类别
     */
    private String cAjlb;
    /**
     * 法标  0是，1不是
     */
    private String cFb;
    /**
     * 
     */
    private String cStat;
    public Integer getnId() {
        return nId;
    }
    public void setnId(Integer nId) {
        this.nId = nId;
    }
    public String getcAjlx() {
        return cAjlx;
    }
    public void setcAjlx(String cAjlx) {
        this.cAjlx = cAjlx;
    }
    public String getcName() {
        return cName;
    }
    public void setcName(String cName) {
        this.cName = cName;
    }
    public String getcEprefix() {
        return cEprefix;
    }
    public void setcEprefix(String cEprefix) {
        this.cEprefix = cEprefix;
    }
    public String getcAjlb() {
        return cAjlb;
    }
    public void setcAjlb(String cAjlb) {
        this.cAjlb = cAjlb;
    }
    public String getcFb() {
        return cFb;
    }
    public void setcFb(String cFb) {
        this.cFb = cFb;
    }
    public String getcStat() {
        return cStat;
    }
    public void setcStat(String cStat) {
        this.cStat = cStat;
    }
}
