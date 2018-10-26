package data.exchange.center.service.meishan.domain;

import java.io.Serializable;
import java.sql.Blob;

/**
 * 
 * Description:文书
 * <p>Company: xinya </p>
 * <p>Date:2017年9月13日 上午11:30:26</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class WsInfo implements Serializable {

	/**
	 * 2017年9月13日上午11:30:22
	 * yuguang
	 */
	private static final long serialVersionUID = -7861647991467192182L;

	private Blob NR;

	public Blob getNR() {
		return NR;
	}

	public void setNR(Blob nR) {
		NR = nR;
	}

	@Override
	public String toString() {
		return "WsInfo [NR=" + NR + "]";
	}
}
