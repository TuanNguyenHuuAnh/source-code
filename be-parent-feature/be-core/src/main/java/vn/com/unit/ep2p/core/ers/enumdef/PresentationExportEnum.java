package vn.com.unit.ep2p.core.ers.enumdef;

public enum PresentationExportEnum {
	
	NO("0"),
	CHANNELSTRING("1"),
	TYPELINKSTRING("2"),
	TITLE("3"),
	LINKURL("4"),
	TYPEOPENPAGESTRING("5"),
	ORDERONPAGESTRING("6"),
	CONTENT("7"),
	STATUSITEMSTRING("8"),
	CREATEDBY("9"),
	CREATEDDATE("10");
	
	private String value;

    private PresentationExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
