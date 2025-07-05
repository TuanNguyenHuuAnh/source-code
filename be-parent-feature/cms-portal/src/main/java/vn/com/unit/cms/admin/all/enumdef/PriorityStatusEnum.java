package vn.com.unit.cms.admin.all.enumdef;

public enum PriorityStatusEnum {

	YES("1", "yes.title"), NO("0", "no.title");

	private String value;
	private String name;

	private PriorityStatusEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
