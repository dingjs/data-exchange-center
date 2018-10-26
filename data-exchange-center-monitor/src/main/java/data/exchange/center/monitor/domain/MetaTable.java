package data.exchange.center.monitor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月20日 上午10:05:49</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class MetaTable implements Serializable {

	/**
	 * 2017年7月20日上午10:05:46
	 * yuguang
	 */
	private static final long serialVersionUID = 3018944916864754189L;

	private String cTableId;
	private String cTreeId;
	private String cNodeId;
	private String cEtbName;
	private String cCtbName;
	private String cDesc;
	private String cAjlxCode;
	private String cUserId;
	private Date   dCreate;
	private Date   dUpdate;
	public String getcTableId() {
		return cTableId;
	}
	public void setcTableId(String cTableId) {
		this.cTableId = cTableId;
	}
	public String getcTreeId() {
		return cTreeId;
	}
	public void setcTreeId(String cTreeId) {
		this.cTreeId = cTreeId;
	}
	public String getcNodeId() {
		return cNodeId;
	}
	public void setcNodeId(String cNodeId) {
		this.cNodeId = cNodeId;
	}
	public String getcEtbName() {
		return cEtbName;
	}
	public void setcEtbName(String cEtbName) {
		this.cEtbName = cEtbName;
	}
	public String getcCtbName() {
		return cCtbName;
	}
	public void setcCtbName(String cCtbName) {
		this.cCtbName = cCtbName;
	}
	public String getcDesc() {
		return cDesc;
	}
	public void setcDesc(String cDesc) {
		this.cDesc = cDesc;
	}
	public String getcAjlxCode() {
		return cAjlxCode;
	}
	public void setcAjlxCode(String cAjlxCode) {
		this.cAjlxCode = cAjlxCode;
	}
	public String getcUserId() {
		return cUserId;
	}
	public void setcUserId(String cUserId) {
		this.cUserId = cUserId;
	}
	public Date getdCreate() {
		return dCreate;
	}
	public void setdCreate(Date dCreate) {
		this.dCreate = dCreate;
	}
	public Date getdUpdate() {
		return dUpdate;
	}
	public void setdUpdate(Date dUpdate) {
		this.dUpdate = dUpdate;
	}
}
