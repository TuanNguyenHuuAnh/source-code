package vn.com.unit.cms.core.module.events.imports.enumdef;

public enum EventsExportEnum {

	NO("0"),
	MESSAGEERROR("1"),
	MESSAGEWARNING("2"),
	TYPE("3"),
    AGENTCODE("4"),
    IDNUMBER("5"),
    NAME("6"),
    GENDER("7"),
    EMAIL("8"),
    TEL("9"),
    ADDRESS("10"),
    REFERCODE("11");
	
	private String value;

    private EventsExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
