package vn.com.unit.cms.admin.all.enumdef;

public enum RoleChatEnum {
	
	ROLE_ADMIN("ROLE_ADMIN", "ROLE_ADMIN"),
	ROLE_AGENT("ROLE_AGENT", "ROLE_AGENT");
	
	private String code;
	private String value;
	
	private RoleChatEnum(String code, String value) {
		this.code = code;
		this.code = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
    public String toString() {
        return code;
    }
}
