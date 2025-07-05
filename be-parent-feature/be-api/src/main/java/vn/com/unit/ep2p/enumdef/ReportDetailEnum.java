package vn.com.unit.ep2p.enumdef;

public enum ReportDetailEnum {
	NO("0")
	, policykey("1")
	, poname("2")
	, polissueeff("3")
	, polcstatcd("4")
	, polmprenamt("5")
	, polbillmodecd("6")
	, opm("7")
	, k2epp("8")
	, k2app("9")
	, k2chargesissue("10")
	, k2plusepp("11")
	, k2plusapp("12")
	, kk2chargesissue("13");

	
	private String value;
	
	private ReportDetailEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
