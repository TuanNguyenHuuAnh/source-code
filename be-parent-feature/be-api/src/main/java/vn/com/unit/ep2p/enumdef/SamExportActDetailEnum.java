package vn.com.unit.ep2p.enumdef;

public enum SamExportActDetailEnum {
	NO("0")
	, ACTCODE("1")
	, BUCODE("2")
	, BU("3")
	, PARTNER("4")
	, ZONE("5")
	, REGIONAL("6")
	, AREA("7")
	, CATEGORY("8")
	, SUBJECT("9")
	, CONTENT("10")
	, TYPE("11")
	, FORM("12")
	, PLANDATE("13")
	, STATUS("14")
	;

	private String value;
	
	private SamExportActDetailEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
