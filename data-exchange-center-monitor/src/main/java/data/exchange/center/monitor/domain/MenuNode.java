package data.exchange.center.monitor.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月14日 下午3:54:54</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class MenuNode implements Serializable {

	/**
	 * 2017年7月14日下午3:54:58
	 * yuguang
	 */
	private static final long serialVersionUID = -613400058017356046L;

	/**
	 * 子节点id
	 */
	private String id;
	/**
	 * 名称
	 */
	private String label;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 选中图标
	 */
	private String selectedIcon;
	/**
	 * 颜色
	 */
	private String color;
	/**
	 * 背景颜色
	 */
	private String backColor;
	/**
	 * url
	 */
	private String url;
	/**
	 * 可选
	 */
	private boolean selectable;
	
	private List<Object> childNodes=new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSelectedIcon() {
		return selectedIcon;
	}

	public void setSelectedIcon(String selectedIcon) {
		this.selectedIcon = selectedIcon;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public List<Object> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Object> childNodes) {
		this.childNodes = childNodes;
	}
}
