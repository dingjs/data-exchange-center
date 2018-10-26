package data.exchange.center.service.unstructured.data.domain;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月30日 下午12:25:27</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class UrlInfo implements Serializable {

    /**
     * 2017年5月17日下午1:15:45
     * yuguang
     */
    private static final long serialVersionUID = 1360996418354119943L;

    /**
     * 文件名称
     */
    private String FILENAME;
    private String OLDPATH;
    private String FILELENGTH;
    private String CZLX;
    private String XH;
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getOLDPATH() {
		return OLDPATH;
	}
	public void setOLDPATH(String oLDPATH) {
		OLDPATH = oLDPATH;
	}
	public String getFILELENGTH() {
		return FILELENGTH;
	}
	public void setFILELENGTH(String fILELENGTH) {
		FILELENGTH = fILELENGTH;
	}
	public String getCZLX() {
		return CZLX;
	}
	public void setCZLX(String cZLX) {
		CZLX = cZLX;
	}
	public String getXH() {
		return XH;
	}
	public void setXH(String xH) {
		XH = xH;
	}
}
