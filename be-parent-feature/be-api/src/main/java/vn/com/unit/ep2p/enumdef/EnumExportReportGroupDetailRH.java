package vn.com.unit.ep2p.enumdef;

public enum EnumExportReportGroupDetailRH {
		PAREN("0")
,		CHILD("1")
,		gadName("2")
,		k2epp	("3")
,		k2app	("4")
,		k2	("5")
,		k2chargesissue	("6")
,		k2plusepp	("7")
,		k2plusapp	("8")
,		k2plus	("9")
,		kk2chargesissue	("10");

	
	private String value;
	
	private EnumExportReportGroupDetailRH(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
