package data.exchange.center.monitor.domain;

import java.io.Serializable;

/**
 * 
 * Description:元数据二级菜单
 * <p>Company: xinya </p>
 * <p>Date:2017年7月19日 上午11:29:12</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class MainMeta implements Serializable {

	/**
	 * 2017年7月19日上午11:29:09
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	private String treeId;
	private String name;
	private String desc;
	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
