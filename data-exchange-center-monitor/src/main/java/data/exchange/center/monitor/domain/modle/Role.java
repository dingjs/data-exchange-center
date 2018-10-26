package data.exchange.center.monitor.domain.modle;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * 角色
 *
 */
public class Role implements Serializable {

	/**
	 * 2017年7月18日下午12:08:20
	 * yuguang
	 */
	private static final long serialVersionUID = 1652713408157458481L;

	private String id = UUID.randomUUID().toString();

	/** 角色名*/
	private String name;

	/** 描述 */
	private String description;

	/** 状态 是否禁用*/
	private boolean disabled;

	public Role(){}

	public Role(String id,String name,String desc){
		this.name = name;
		this.description = desc;
		this.id = id;
	}

	public Role setName(String name) {
		this.name = name;
		return this;
	}

	public Role setDescription(String description) {
		this.description = description;
		return this;
	}

	public void update(String name,String desc){
		this.setName(name).setDescription(desc);
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Role setId(String id) {
		this.id = id;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isDisabled() {
		return disabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Role role = (Role) o;
		return id != null ? id.equals(role.id) : role.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}

