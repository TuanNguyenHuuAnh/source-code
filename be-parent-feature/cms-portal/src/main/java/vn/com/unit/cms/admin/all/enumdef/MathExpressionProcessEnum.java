package vn.com.unit.cms.admin.all.enumdef;

public enum MathExpressionProcessEnum {

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
    DESTROY("destroyed", "master.status.detroyed"),
    
    ;

    private String value;
    
    private String name;

    
    private MathExpressionProcessEnum(String value) {
        this.value = value;
    }
    
    private MathExpressionProcessEnum(String value, String name) {
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
