package data.exchange.center.service.unstructured.data.domain;

import java.io.Serializable;

/**
 * Description:
 * <p>Company: pelox </p>
 * <p>Date:2017年6月2日 下午6:13:12</p>
 * @author Wen.Yuguang
 * @version 1.0
 **/
public class Count implements Serializable {

    /**
     * 2017年6月2日下午6:13:14
     * yuguang
     */
    private static final long serialVersionUID = -1481747721161748032L;

    private String ftpName;

    public String getFtpName() {
        return ftpName;
    }

    public void setFtpName(String ftpName) {
        this.ftpName = ftpName;
    }
}
