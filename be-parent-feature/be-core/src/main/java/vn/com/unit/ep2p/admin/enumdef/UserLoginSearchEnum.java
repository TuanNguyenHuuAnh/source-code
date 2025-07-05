package vn.com.unit.ep2p.admin.enumdef;

public enum UserLoginSearchEnum {
    
	USERNAME("user.login.username"),
	TYPE("user.login.type"),
	STATUS("user.login.status"),
	DEVICE("user.login.device"),
	BROWSER("user.login.browser"),
	OS("user.login.os"),
	;
    
	private String value;

    private UserLoginSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
