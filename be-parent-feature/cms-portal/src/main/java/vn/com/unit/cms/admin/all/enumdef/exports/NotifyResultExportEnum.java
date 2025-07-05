package vn.com.unit.cms.admin.all.enumdef.exports;

public enum NotifyResultExportEnum {
	
    NO("0")
    , agentcode("1")
    , title("2")
    , content("3")
    , messageerror("4")

;
	private String value;

	private NotifyResultExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

}
