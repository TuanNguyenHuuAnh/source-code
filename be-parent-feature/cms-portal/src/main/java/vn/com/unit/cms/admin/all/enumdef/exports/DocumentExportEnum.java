package vn.com.unit.cms.admin.all.enumdef.exports;

public enum DocumentExportEnum {

	STT("0")
	, CODE("1")
	, TYPENAME("2")
	, NAME("3")
	, STATUSNAME("4")
	, CREATEBY("5")
	, CREATEDATE("6");

	private String value;

	private DocumentExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
