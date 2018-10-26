package data.exchange.center.service.download.domain;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月13日 上午10:53:33</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class FileBlob implements Serializable {

	/**
	 * 2017年7月13日上午10:53:37
	 * yuguang
	 */
	private static final long serialVersionUID = -3278492975994181046L;

	private byte[] wjnr;

	public byte[] getWjnr() {
		return wjnr;
	}

	public void setWjnr(byte[] wjnr) {
		this.wjnr = wjnr;
	}
}
