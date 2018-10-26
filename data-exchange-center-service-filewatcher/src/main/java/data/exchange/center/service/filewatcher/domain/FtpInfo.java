package data.exchange.center.service.filewatcher.domain;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年9月27日 下午3:05:48</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class FtpInfo implements Serializable {

	/**
	 * 2017年9月27日上午10:49:16
	 * yuguang
	 */
	private static final long serialVersionUID = 4247397447317653367L;

	private String cFtpname;

    private String cFtpalias;

    private String cUsername;

    private String cPassword;

    private String cHostname;

    private String cIp;

    private String nPort;

    private String cPath;

    private String cEncoding;

    private String nPassive;

    private String nPoolsize;

    private String nTranstype;

    private String nTimeout;

    private String nExternal;

    private String cDesc;

	public String getcFtpname() {
		return cFtpname;
	}

	public void setcFtpname(String cFtpname) {
		this.cFtpname = cFtpname;
	}

	public String getcFtpalias() {
		return cFtpalias;
	}

	public void setcFtpalias(String cFtpalias) {
		this.cFtpalias = cFtpalias;
	}

	public String getcUsername() {
		return cUsername;
	}

	public void setcUsername(String cUsername) {
		this.cUsername = cUsername;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}

	public String getcHostname() {
		return cHostname;
	}

	public void setcHostname(String cHostname) {
		this.cHostname = cHostname;
	}

	public String getcIp() {
		return cIp;
	}

	public void setcIp(String cIp) {
		this.cIp = cIp;
	}

	public String getnPort() {
		return nPort;
	}

	public void setnPort(String nPort) {
		this.nPort = nPort;
	}

	public String getcPath() {
		return cPath;
	}

	public void setcPath(String cPath) {
		this.cPath = cPath;
	}

	public String getcEncoding() {
		return cEncoding;
	}

	public void setcEncoding(String cEncoding) {
		this.cEncoding = cEncoding;
	}

	public String getnPassive() {
		return nPassive;
	}

	public void setnPassive(String nPassive) {
		this.nPassive = nPassive;
	}

	public String getnPoolsize() {
		return nPoolsize;
	}

	public void setnPoolsize(String nPoolsize) {
		this.nPoolsize = nPoolsize;
	}

	public String getnTranstype() {
		return nTranstype;
	}

	public void setnTranstype(String nTranstype) {
		this.nTranstype = nTranstype;
	}

	public String getnTimeout() {
		return nTimeout;
	}

	public void setnTimeout(String nTimeout) {
		this.nTimeout = nTimeout;
	}

	public String getnExternal() {
		return nExternal;
	}

	public void setnExternal(String nExternal) {
		this.nExternal = nExternal;
	}

	public String getcDesc() {
		return cDesc;
	}

	public void setcDesc(String cDesc) {
		this.cDesc = cDesc;
	}

	@Override
	public String toString() {
		return "FtpInfo [cFtpname=" + cFtpname + ", cFtpalias=" + cFtpalias + ", cUsername=" + cUsername
				+ ", cPassword=" + cPassword + ", cHostname=" + cHostname + ", cIp=" + cIp + ", nPort=" + nPort
				+ ", cPath=" + cPath + ", cEncoding=" + cEncoding + ", nPassive=" + nPassive + ", nPoolsize="
				+ nPoolsize + ", nTranstype=" + nTranstype + ", nTimeout=" + nTimeout + ", nExternal=" + nExternal
				+ ", cDesc=" + cDesc + "]";
	}
}
