package vn.com.unit.cms.admin.all.enumdef;

public enum DocumentProcessEnum {

    /** create */
    CREATE("saved", "master.status.savedraff"),

    /** submit */
    SUBMIT("submitted", "master.status.submit"),

    /** approval */
    APPROVAL("approved", "master.status.approve"),

    /** reject */
    REJECT("rejected", "master.status.reject"),

    /** return */
    ASSIGN("assigned", "master.status.assign"),

    /** destroy */
    DESTROY("destroyed", "master.status.destroy"),
    
    BUSINESS_CODE("AD1")
    ;

    private String value;
    
    private String name;

    
    private DocumentProcessEnum(String value) {
        this.value = value;
    }
    
    private DocumentProcessEnum(String value, String name) {
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
