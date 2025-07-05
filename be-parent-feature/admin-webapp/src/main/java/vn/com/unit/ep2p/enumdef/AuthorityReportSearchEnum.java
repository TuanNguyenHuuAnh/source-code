package vn.com.unit.ep2p.enumdef;

/**
 * AuthorityReportSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author TuyenTD
 */
public enum AuthorityReportSearchEnum {

	USERNAME("authority.detail.username"),    
    FULLNAME("authority.detail.fullname"),
    //EMAIL("authority.detail.email"),
    GROUP("authority.detail.group"),
    ROLE("authority.detail.role"),
    FUNCTION("authority.detail.function")
    ;
    
    private String value;

    private AuthorityReportSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
