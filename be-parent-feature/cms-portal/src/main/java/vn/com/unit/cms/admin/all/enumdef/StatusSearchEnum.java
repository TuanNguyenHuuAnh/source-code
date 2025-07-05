package vn.com.unit.cms.admin.all.enumdef;


/**
 * AboutSerachEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author tuanh
 */
public enum StatusSearchEnum {
	
    APPROVED("master.status.approve"),
    
    SUBMITTED("master.status.submit"),
    
    REJECTED("master.status.reject"),
    
    DESTRPYED("master.status.detroyed"),
    
    SAVED("master.status.savedraff");

    
    private String value;
 
    private StatusSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
