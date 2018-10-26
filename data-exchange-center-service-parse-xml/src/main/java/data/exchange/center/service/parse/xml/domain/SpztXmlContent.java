package data.exchange.center.service.parse.xml.domain;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年3月27日 下午4:25:13</p>
 * @author Tony
 * @version 1.0
 *
 */
public class SpztXmlContent implements Serializable {

    private static final long serialVersionUID = -3948869652465112807L;

    private String DM;
    private String LX;
    private String FYDM;
    private Blob   XMLNR;
    private Date   LASTUPDATE;
    private String UUID;
    private Date   SENDTIME;
    private String INPUTSRC;
    private String DESTSCHEMA;
    private String AJSOURCE;
    private String XMLTYPE;
    public String getDM() {
        return DM;
    }
    public void setDM(String dM) {
        DM = dM;
    }
    public String getLX() {
        return LX;
    }
    public void setLX(String lX) {
        LX = lX;
    }
    public String getFYDM() {
        return FYDM;
    }
    public void setFYDM(String fYDM) {
        FYDM = fYDM;
    }
    public Blob getXMLNR() {
        return XMLNR;
    }
    public void setXMLNR(Blob xMLNR) {
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
        return "XmlContent [DM=" + DM + ", LX=" + LX + ", FYDM=" + FYDM + ", XMLNR=" + XMLNR + ", LASTUPDATE="
                + LASTUPDATE + ", UUID=" + UUID + ", SENDTIME=" + SENDTIME + ", INPUTSRC=" + INPUTSRC + ", DESTSCHEMA="
                + DESTSCHEMA + ", AJSOURCE=" + AJSOURCE + ", XMLTYPE=" + XMLTYPE + "]";
    }
}
