/**
 * data-governance-unstructured-agent-push
 * created by yuguang at 2017年5月24日
 */
package data.exchange.center.service.unstructured.node.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年5月24日 下午4:34:55</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class TempEajJzwjAllNew implements Serializable {
        /**
     * 2017年5月24日下午4:35:31
     * yuguang
     */
    private static final long serialVersionUID = -7903205339952194195L;
    
    private String AHDM;
    private String FYDM;
    private String AJLX;
    private String XH;
    private Integer YXXH;
    private String FILENAME;
    private String FTPSERVER;
    private String FTPFILE;
    private String LOCALFILE;
    private Integer PXH;
    private Integer WJYS;
    private String SFZYXJ;
    private Date LASTUPDATE;
    private String ACTIONTYPE;
    public String getFTPFILE() {
		return FTPFILE;
	}
	public void setFTPFILE(String fTPFILE) {
		FTPFILE = fTPFILE;
	}
	public String getAHDM() {
        return AHDM;
    }
    public void setAHDM(String aHDM) {
        AHDM = aHDM;
    }
    public String getFYDM() {
        return FYDM;
    }
    public void setFYDM(String fYDM) {
        FYDM = fYDM;
    }
    public String getAJLX() {
        return AJLX;
    }
    public void setAJLX(String aJLX) {
        AJLX = aJLX;
    }
    public String getXH() {
        return XH;
    }
    public void setXH(String xH) {
        XH = xH;
    }
    public Integer getYXXH() {
        return YXXH;
    }
    public void setYXXH(Integer yXXH) {
        YXXH = yXXH;
    }
    public String getFILENAME() {
        return FILENAME;
    }
    public void setFILENAME(String fILENAME) {
        FILENAME = fILENAME;
    }
    public String getFTPSERVER() {
        return FTPSERVER;
    }
    public void setFTPSERVER(String fTPSERVER) {
        FTPSERVER = fTPSERVER;
    }
    public String getLOCALFILE() {
        return LOCALFILE;
    }
    public void setLOCALFILE(String lOCALFILE) {
        LOCALFILE = lOCALFILE;
    }
    public Integer getPXH() {
        return PXH;
    }
    public void setPXH(Integer pXH) {
        PXH = pXH;
    }
    public Integer getWJYS() {
        return WJYS;
    }
    public void setWJYS(Integer wJYS) {
        WJYS = wJYS;
    }
    public String getSFZYXJ() {
        return SFZYXJ;
    }
    public void setSFZYXJ(String sFZYXJ) {
        SFZYXJ = sFZYXJ;
    }
    public Date getLASTUPDATE() {
        return LASTUPDATE;
    }
    public void setLASTUPDATE(Date lASTUPDATE) {
        LASTUPDATE = lASTUPDATE;
    }
    public String getACTIONTYPE() {
        return ACTIONTYPE;
    }
    public void setACTIONTYPE(String aCTIONTYPE) {
        ACTIONTYPE = aCTIONTYPE;
    }
    
    
}
