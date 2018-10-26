package data.exchange.center.service.unstructured.data.domain;

import java.io.Serializable;

/**
 * Description:FTPClient配置类，封装了FTPClient的相关配置
 * <p>Company: pelox </p>
 * <p>Date:2017年5月5日 下午6:11:28</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class FTPClientInfoConfigure implements Serializable{
    /**
     * 2017年5月17日上午11:12:13
     * yuguang
     */
    private static final long serialVersionUID = 8237266008781383862L;
    
    private String FYDM;
    private String FTPNAME;
    private String HOST;
    private String PORT;
    private String PASSWORD;
    private String NOWFTP;
    private String ROOT;
    private String USERNAME;
    private String LOCALPASSIVEMODE;
    private String ENCODING;
    private String CLIENTTIMEOUT;
    private String POOLSIZE;
    private String TRANSFERFILETYPE;
    public String getFYDM() {
        return FYDM;
    }
    public void setFYDM(String fYDM) {
        FYDM = fYDM;
    }
    public String getFTPNAME() {
        return FTPNAME;
    }
    public void setFTPNAME(String fTPNAME) {
        FTPNAME = fTPNAME;
    }
    public String getHOST() {
        return HOST;
    }
    public void setHOST(String hOST) {
        HOST = hOST;
    }
    public String getPORT() {
        return PORT;
    }
    public void setPORT(String pORT) {
        PORT = pORT;
    }
    public String getPASSWORD() {
        return PASSWORD;
    }
    public void setPASSWORD(String pASSWORD) {
        PASSWORD = pASSWORD;
    }
    public String getNOWFTP() {
        return NOWFTP;
    }
    public void setNOWFTP(String nOWFTP) {
        NOWFTP = nOWFTP;
    }
    public String getROOT() {
        return ROOT;
    }
    public void setROOT(String rOOT) {
        ROOT = rOOT;
    }
    public String getUSERNAME() {
        return USERNAME;
    }
    public void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }
    public String getLOCALPASSIVEMODE() {
        return LOCALPASSIVEMODE;
    }
    public void setLOCALPASSIVEMODE(String lOCALPASSIVEMODE) {
        LOCALPASSIVEMODE = lOCALPASSIVEMODE;
    }
    public String getENCODING() {
        return ENCODING;
    }
    public void setENCODING(String eNCODING) {
        ENCODING = eNCODING;
    }
    public String getCLIENTTIMEOUT() {
        return CLIENTTIMEOUT;
    }
    public void setCLIENTTIMEOUT(String cLIENTTIMEOUT) {
        CLIENTTIMEOUT = cLIENTTIMEOUT;
    }
    public String getPOOLSIZE() {
        return POOLSIZE;
    }
    public void setPOOLSIZE(String pOOLSIZE) {
        POOLSIZE = pOOLSIZE;
    }
    public String getTRANSFERFILETYPE() {
        return TRANSFERFILETYPE;
    }
    public void setTRANSFERFILETYPE(String tRANSFERFILETYPE) {
        TRANSFERFILETYPE = tRANSFERFILETYPE;
    }
    @Override
    public String toString() {
        return "FTPClientInfoConfigure [FYDM=" + FYDM + ", FTPNAME=" + FTPNAME + ", HOST=" + HOST + ", PORT=" + PORT
                + ", PASSWORD=" + PASSWORD + ", NOWFTP=" + NOWFTP + ", ROOT=" + ROOT + ", USERNAME=" + USERNAME
                + ", LOCALPASSIVEMODE=" + LOCALPASSIVEMODE + ", ENCODING=" + ENCODING + ", CLIENTTIMEOUT="
                + CLIENTTIMEOUT + ", POOLSIZE=" + POOLSIZE + ", TRANSFERFILETYPE=" + TRANSFERFILETYPE + "]";
    }
}
