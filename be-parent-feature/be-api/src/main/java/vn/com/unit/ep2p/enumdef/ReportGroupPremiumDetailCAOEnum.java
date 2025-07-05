package vn.com.unit.ep2p.enumdef;

public enum ReportGroupPremiumDetailCAOEnum {
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
	,		lastpolicycountreceived	("12")
	,		lastfypreceived	("13")
	,		lastpolicycountissued("14")
	,		lastfypissued	("15")
	,		lastpolicycount ("16")
	,		lastfyp	("17")
	,		lastryp	("18");



	private String value;

	private ReportGroupPremiumDetailCAOEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
