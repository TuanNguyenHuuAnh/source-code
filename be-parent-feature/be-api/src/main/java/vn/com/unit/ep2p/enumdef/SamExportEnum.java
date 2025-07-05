package vn.com.unit.ep2p.enumdef;

public enum SamExportEnum {
	NO("0")
	, ACTCODE("1")
	, PARTNER("2")
	, ZONE("3")
	, REGIONAL("4")
	, AREA("5")
	, CATEGORY("6")
	, SUBJECT("7")
	, CONTENT("8")
	, TYPE("9")
	, FORM("10")
	, PLANDATE("11")
	, PERSONNUMBERPLAN("12")
	, COSTAMTPLAN("13")
	, SALESAMTPLAN("14")
	, BANKER("15")
	, BUHEAD("16")
	, GDBRANCH("17")
	, IOIS("18")
	, ILSM("19")
	, ZD("20")
	, ACTUALDATE("21")
	, PERSONNUMBERACTUAL("22")
	, COSTAMTACTUAL("23")
	, SALESAMTACTUAL("24")
	, STATUS("25")
	, APPROVALDATE("26")
	, REPORT("27")
	, REPORTEDDATE("28")
	;

	private String value;
	
	private SamExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
