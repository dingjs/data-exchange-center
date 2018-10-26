/**
 * data-governance-unstructured-agent-push
 * created by yuguang at 2017年5月24日
 */
package data.exchange.center.service.unstructured.node.domain;

import java.io.Serializable;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年5月24日 下午6:02:25</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class Ajxx implements Serializable {

    /**
     * 2017年5月24日下午6:02:27
     * yuguang
     */
    private static final long serialVersionUID = 1267904018536661191L;

    
    private String AH;
    private String AHDM;
    private String FYDM;
    private String AJLX;
    private String AJZT;
    private String LARQ;
	public String getAH() {
		return AH;
	}
	public void setAH(String aH) {
		AH = aH;
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
	public String getAJZT() {
		return AJZT;
	}
	public void setAJZT(String aJZT) {
		AJZT = aJZT;
	}
	public String getLARQ() {
		return LARQ;
	}
	public void setLARQ(String lARQ) {
		LARQ = lARQ;
	}
	@Override
	public String toString() {
		return "Ajxx [AH=" + AH + ", AHDM=" + AHDM + ", FYDM=" + FYDM + ", AJLX=" + AJLX + ", AJZT=" + AJZT + ", LARQ="
				+ LARQ + "]";
	}
}
