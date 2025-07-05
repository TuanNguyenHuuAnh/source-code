package vn.com.unit.ep2p.enumdef;

public enum ReportGroupPremiumDetailCAOMTDEnum {
			PAREN("0")
	,		CHILD("1")
	,		policycountreceived("2")
	,		fypreceived("3")
	,		policycountissued("4")
	,		fypissued("5")
	, 		policycount("6")
	,		fyp("7")
	,		policycountcancel("8")
	,		fypcancel("9")
	,		rfyp("10")
	,	 	ryp ("11")
	,		K2	("12")
	,		K2PLUS("13")
	,		lastpolicycountreceived	("14")
	,		lastfypreceived	("15")
	,		lastpolicycountissued("16")
	,		lastfypissued	("17")
	,		lastpolicycount ("18")
	,		lastfyp	("19")
	,		lastryp	("20");



	private String value;

	private ReportGroupPremiumDetailCAOMTDEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
