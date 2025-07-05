package vn.com.unit.cms.admin.all.enumdef;

public enum JobTypeSearchEnum {
	/** code */
    CODE("job.type.code"),

    /** label */
    NAME("job.type.title"),
    
    /** description */
    DESCRIPTION("job.type.description")
    ;

    private String value;

    private JobTypeSearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
