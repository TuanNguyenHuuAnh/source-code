package vn.com.unit.cms.admin.all.enumdef;

public enum JobTypeSubSearchEnum {
	 /** code */
    CODE("job.type.sub.search.code"),

    /** label */
    NAME("job.type.sub.search.name"),
    
    /** description */
    DESCRIPTION("job.type.sub.search.description")
    ;

    private String value;

	private JobTypeSubSearchEnum(String value) {
		this.value = value;
	}
	 public String toString() {
	        return value;
	    }
	


}
