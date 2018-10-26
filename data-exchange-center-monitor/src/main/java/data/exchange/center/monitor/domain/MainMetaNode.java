package data.exchange.center.monitor.domain;

import java.io.Serializable;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月19日 上午11:43:40</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class MainMetaNode implements Serializable {

	/**
	 * 2017年7月19日上午11:43:35
	 * yuguang
	 */
	private static final long serialVersionUID = 5623854403313862311L;

	private String treeId;
	private String tableId;
	private String nodeId;
	private String nodeType;
	private String eTbName;
	private String cTbName;
	private String desc;
	private String ajlx;
	private String userId;
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String geteTbName() {
		return eTbName;
	}
	public void seteTbName(String eTbName) {
		this.eTbName = eTbName;
	}
	public String getcTbName() {
		return cTbName;
	}
	public void setcTbName(String cTbName) {
		this.cTbName = cTbName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAjlx() {
		return ajlx;
	}
	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
