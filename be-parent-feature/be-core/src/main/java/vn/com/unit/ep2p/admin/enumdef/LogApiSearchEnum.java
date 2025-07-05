package vn.com.unit.ep2p.admin.enumdef;

public enum LogApiSearchEnum {
	
    USERNAME("log.username"),
    ENDPOINT("log.endpoint"),
    STATUS("log.status"),
	CLIENT_IP("log.clientIp"),
	STORE_NAME("log.store"),
	MESSAGE("log.message"),
	;
    
	private String value;

    private LogApiSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
