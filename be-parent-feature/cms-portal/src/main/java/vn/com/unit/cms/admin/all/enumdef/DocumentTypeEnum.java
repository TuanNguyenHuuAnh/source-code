package vn.com.unit.cms.admin.all.enumdef;

public enum DocumentTypeEnum {

	STT("0"), CODE("1"), NAME("2"), DESCRIPTION("3"), STATUSNAME("4"), CREATEBY("5"), CREATEDATE("6");

	private String value;

	private DocumentTypeEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
