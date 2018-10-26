package data.exchange.center.ommp.domain;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2018年4月20日 上午11:01:05</p>
 * @author Tony
 * @version 1.0
 *
 */
public class Permission {

	private int id;
	// 权限名称
	private String name;

	// 权限描述
	private String descritpion;

	// 授权链接
	private String url;

	// 父节点id
	private int pid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescritpion() {
		return descritpion;
	}

	public void setDescritpion(String descritpion) {
		this.descritpion = descritpion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
}