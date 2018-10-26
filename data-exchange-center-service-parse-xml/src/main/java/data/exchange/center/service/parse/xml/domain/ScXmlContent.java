/**
 * data-governance-provider-parse
 * created by yuguang at 2017年3月8日
 */
package data.exchange.center.service.parse.xml.domain;

import java.io.Serializable;
import java.util.Date;

import oracle.sql.BLOB;

/**
 * <p>Title: XmlContent.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月8日 下午11:52:59</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class ScXmlContent implements Serializable {

    /**
     * 2017年4月27日下午10:30:42
     * yuguang
     */
    private static final long serialVersionUID = -5769349981514346789L;

    private String AJBS;
    private String AJLX;
    private String FYDM;
    private BLOB XMLNR;
    private Date LASTUPDATE;
    private String UUID;
    private Date SENDTIME;
    private String INPUTSRC;
    private String DESTSCHEMA;
    private String AJSOURCE;
    private String XMLTYPE;
    public String getAJBS() {
        return AJBS;
    }
    public void setAJBS(String aJBS) {
        AJBS = aJBS;
    }
    public String getAJLX() {
        return AJLX;
    }
    public void setAJLX(String aJLX) {
        AJLX = aJLX;
    }
    public String getFYDM() {
        return FYDM;
    }
    public void setFYDM(String fYDM) {
        FYDM = fYDM;
    }
    public BLOB getXMLNR() {
        return XMLNR;
    }
    public void setXMLNR(BLOB xMLNR) {
        XMLNR = xMLNR;
    }
    public Date getLASTUPDATE() {
        return LASTUPDATE;
    }
    public void setLASTUPDATE(Date lASTUPDATE) {
        LASTUPDATE = lASTUPDATE;
    }
    public String getUUID() {
        return UUID;
    }
    public void setUUID(String uUID) {
        UUID = uUID;
    }
    public Date getSENDTIME() {
        return SENDTIME;
    }
    public void setSENDTIME(Date sENDTIME) {
        SENDTIME = sENDTIME;
    }
    public String getINPUTSRC() {
        return INPUTSRC;
    }
    public void setINPUTSRC(String iNPUTSRC) {
        INPUTSRC = iNPUTSRC;
    }
    public String getDESTSCHEMA() {
        return DESTSCHEMA;
    }
    public void setDESTSCHEMA(String dESTSCHEMA) {
        DESTSCHEMA = dESTSCHEMA;
    }
    public String getAJSOURCE() {
        return AJSOURCE;
    }
    public void setAJSOURCE(String aJSOURCE) {
        AJSOURCE = aJSOURCE;
    }
    public String getXMLTYPE() {
        return XMLTYPE;
    }
    public void setXMLTYPE(String xMLTYPE) {
        XMLTYPE = xMLTYPE;
    }
    @Override
    public String toString() {
        return "ScXmlContent [AJBS=" + AJBS + ", AJLX=" + AJLX + ", FYDM=" + FYDM + ", XMLNR=" + XMLNR + ", LASTUPDATE="
                + LASTUPDATE + ", UUID=" + UUID + ", SENDTIME=" + SENDTIME + ", INPUTSRC=" + INPUTSRC + ", DESTSCHEMA="
                + DESTSCHEMA + ", AJSOURCE=" + AJSOURCE + ", XMLTYPE=" + XMLTYPE + "]";
    }
}
