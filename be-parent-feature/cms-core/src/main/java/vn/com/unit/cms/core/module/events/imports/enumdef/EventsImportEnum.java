package vn.com.unit.cms.core.module.events.imports.enumdef;

public enum EventsImportEnum {

	NO("0"),
	TYPE("1"),
    AGENTCODE("2"),
    NAME("3"),
    GENDER("4"),
    EMAIL("5"),
    TEL("6"),
    ADDRESS("7"),
    REFERCODE("8");
	
	private String value;

    private EventsImportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
