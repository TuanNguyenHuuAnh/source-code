package vn.com.unit.cms.admin.all.enumdef.exports;

public enum NotifyExportEnum {
	
    NO("0")
    , AGENTCODE("1")
    , AGENTNAME("2")
    , POSITION("3")
    , NOTIFYTITLE("4");

	private String value;

	private NotifyExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

}
