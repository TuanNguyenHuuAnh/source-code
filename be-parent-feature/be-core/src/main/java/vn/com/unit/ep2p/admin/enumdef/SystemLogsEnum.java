package vn.com.unit.ep2p.admin.enumdef;

public enum SystemLogsEnum {
    
    FUNCTION_CODE("system.logs.function.code"),
    LOG_SUMMARY("system.logs.log.summary"),
	LOG_TYPE("system.logs.log.type"),
	LOG_DETAIL("system.logs.log.detail"),
	IP("system.logs.ip"),
	USERNAME("system.logs.username")
	;
    
	private String value;

    private SystemLogsEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
