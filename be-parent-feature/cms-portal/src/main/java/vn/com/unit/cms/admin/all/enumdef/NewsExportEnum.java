package vn.com.unit.cms.admin.all.enumdef;

public enum NewsExportEnum {

	STT("0"),
	CODE("1"),
	TITLE("2"), 
	TYPENAME("3"), 
	HEADLINES("4"), 
	PRESSRELEASE("5"), 
	LATESTNEWS("6"),
	PROMOTION("7"),
	ENABLED("8"),
	STATUSNAME("9"), 
	CREATEBY("10"), 
	CREATEDATE("11");

	private String value;

	private NewsExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
