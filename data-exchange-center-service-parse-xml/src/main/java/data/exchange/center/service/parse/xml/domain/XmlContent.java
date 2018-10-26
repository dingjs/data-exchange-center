/**
 * data-governance-provider-parse
 * created by yuguang at 2017年3月8日
 */
package data.exchange.center.service.parse.xml.domain;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

/**
 * <p>Title: XmlContent.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c) 2017</p>
 * <p>Company: pelox </p>
 * <p>CreateTime:2017年3月8日 下午11:52:59</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class XmlContent implements Serializable {

    /**
     * 2017年3月8日下午11:53:05
     * yuguang
     */
    private static final long serialVersionUID = 9008295836709175250L;

    private String AJBS;
    private String AJLX;
    private String FYDM;
    private String AJZT;
    private String AH;
    private Date   LARQ;
    private Date   JARQ;
    private Blob   XMLNR;
    private String CREATETIME;
    private Date   LASTUPDATE;
    private String DESTSCHEMA;
    private String AJSOURCE;
    private String INPUTSRC;
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
    public String getAJZT() {
        return AJZT;
    }
    public void setAJZT(String aJZT) {
        AJZT = aJZT;
    }
    public String getAH() {
        return AH;
    }
    public void setAH(String aH) {
        AH = aH;
    }
    public Date getLARQ() {
        return LARQ;
    }
    public void setLARQ(Date lARQ) {
        LARQ = lARQ;
    }
    public Date getJARQ() {
        return JARQ;
    }
    public void setJARQ(Date jARQ) {
        JARQ = jARQ;
    }
    public Blob getXMLNR() {
        return XMLNR;
    }
    public void setXMLNR(Blob xMLNR) {
        XMLNR = xMLNR;
    }
    public String getCREATETIME() {
        return CREATETIME;
    }
    public void setCREATETIME(String cREATETIME) {
        CREATETIME = cREATETIME;
    }
    public Date getLASTUPDATE() {
        return LASTUPDATE;
    }
    public void setLASTUPDATE(Date lASTUPDATE) {
        LASTUPDATE = lASTUPDATE;
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
    public String getINPUTSRC() {
        return INPUTSRC;
    }
    public void setINPUTSRC(String iNPUTSRC) {
        INPUTSRC = iNPUTSRC;
    }
    @Override
    public String toString() {
        return "XmlContent [AJBS=" + AJBS + ", AJLX=" + AJLX + ", FYDM=" + FYDM + ", AJZT=" + AJZT + ", AH=" + AH
                + ", LARQ=" + LARQ + ", JARQ=" + JARQ + ", XMLNR=" + XMLNR + ", CREATETIME=" + CREATETIME
                + ", LASTUPDATE=" + LASTUPDATE + ", DESTSCHEMA=" + DESTSCHEMA + ", AJSOURCE=" + AJSOURCE + "]";
    }
}
