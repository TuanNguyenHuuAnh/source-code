package vn.com.unit.ep2p.enumdef;

public enum EnumExportReportGroupDetailUM {

		CHILD("0")
,		K2EPP("1")
,		K2APP("2")
,		K2("3")
,		K2CHARGESISSUE("4")
,		K2PLUSEPP("5")
,		K2PLUSAPP("6")
,		K2PLUS("7")
,		KK2CHARGESISSUE("8");

	
	private String value;
	
	private EnumExportReportGroupDetailUM(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
