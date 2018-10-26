package data.exchange.center.monitor.domain.modle;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * Description:url资源
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午9:50:37</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class Resource implements Serializable {

	/**
	 * 2017年7月18日下午12:11:00
	 * yuguang
	 */
	private static final long serialVersionUID = 1L;

	/** 唯一资源编码 */
	private String tId = UUID.randomUUID().toString();

	/** 资源名称 */
	private String tTitle;

	/** 状态 是否禁用*/
	private boolean tDisabled;

	/** 地址 */
	private String tUrl;

	/** 描述 */
	private String tDescription;

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String gettTitle() {
		return tTitle;
	}

	public void settTitle(String tTitle) {
		this.tTitle = tTitle;
	}

	public boolean istDisabled() {
		return tDisabled;
	}

	public void settDisabled(boolean tDisabled) {
		this.tDisabled = tDisabled;
	}

	public String gettUrl() {
		return tUrl;
	}

	public void settUrl(String tUrl) {
		this.tUrl = tUrl;
	}

	public String gettDescription() {
		return tDescription;
	}

	public void settDescription(String tDescription) {
		this.tDescription = tDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Resource resource = (Resource) o;

		return tId != null ? tId.equals(resource.tId) : resource.tId == null;

	}

	@Override
	public int hashCode() {
		return tId != null ? tId.hashCode() : 0;
	}
}
