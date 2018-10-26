package data.exchange.center.service.download.domain;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月12日 下午6:00:15</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class FilePath implements Serializable {

	/**
	 * 2017年7月12日下午6:00:12
	 * yuguang
	 */
	private static final long serialVersionUID = 4638851571151116758L;

	private String fileName;
	private String oldPath;
	private String cEpreFix;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOldPath() {
		return oldPath;
	}
	public void setOldPath(String oldPath) {
		this.oldPath = oldPath;
	}
	public String getcEpreFix() {
		return cEpreFix;
	}
	public void setcEpreFix(String cEpreFix) {
		this.cEpreFix = cEpreFix;
	}
	@Override
	public String toString() {
		return "FilePath [fileName=" + fileName + ", oldPath=" + oldPath + ", cEpreFix=" + cEpreFix + "]";
	}
}
