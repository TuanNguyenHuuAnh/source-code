package vn.com.unit.cms.admin.all.enumdef;

/**
 * ServiceSearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author tungns <tungns@unit.com.vn>
 */
public enum ServiceSearchEnum {
	
    CODE("service.code"),//"code" value be used for conditions in query by TermSearchDto
    TITLE("service.lang.title"),
    DESC("service.descriptionAbv"),
    CUST("service.customerTypes")
    ;

    private String value;

    private ServiceSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
