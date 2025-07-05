package vn.com.unit.cms.admin.all.enumdef;

/**
 * FaqsCategoryProcessEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author nhutnn
 */
public enum TermProcessEnum {

    /** create */
    CREATE("saved", "master.status.savedraff"),

    /** submit */
    SUBMIT("submitted", "master.status.submit"),

    /** approval */
    APPROVAL("approved", "master.status.approve"),

    /** reject */
    REJECT("rejected", "master.status.reject"),

    DESTROY("destroyed", "master.status.detroyed"),
    
    ;

    private String value;
    
    private String name;

    private TermProcessEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String toString() {
        return value;
    }
    
    public String getName() {
        return name;
    }

}
