package vn.com.unit.ep2p.enumdef;

public enum EnumExportReportGroupDetailCAO {
		PAREN("0")
,		CHILD("1")
,		k2epp	("2")
,		k2app	("3")
,		k2	("4")
,		k2chargesissue	("5")
,		k2plusepp	("6")
,		k2plusapp	("7")
,		k2plus	("8")
,		kk2chargesissue	("9");

	
	private String value;
	
	private EnumExportReportGroupDetailCAO(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
