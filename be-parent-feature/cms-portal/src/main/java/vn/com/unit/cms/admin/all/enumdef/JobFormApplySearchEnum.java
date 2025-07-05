package vn.com.unit.cms.admin.all.enumdef;

public enum JobFormApplySearchEnum {
	/** label */
    NAME("job.form.apply.name"),
    /** title */
	TITLE("job.form.apply.title"),
	
	EMAIL("job.form.apply.email"),
	
	PHONE("job.form.apply.telephone")
	;
	
	private String value;

    private JobFormApplySearchEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
