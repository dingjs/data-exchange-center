package data.exchange.center.ommp.util;

public enum RoleEnum {

	ADMIN("ROLE_ADMIN", 1), USER("ROLE_USER", 2);

	private String roleName;
	private int index;

	private RoleEnum(String roleName, int index) {  
        this.roleName = roleName;  
        this.index = index;  
    } 

	public static String getName(int index) {
		for (RoleEnum c : RoleEnum.values()) {
			if (c.getIndex() == index) {
				return c.roleName;
			}
		}
		return null;
	}

	public String getName() {
		return roleName;
	}

	public void setName(String roleName) {
		this.roleName = roleName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
