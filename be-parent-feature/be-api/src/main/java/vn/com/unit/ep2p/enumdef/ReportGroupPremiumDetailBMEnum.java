package vn.com.unit.ep2p.enumdef;

public enum ReportGroupPremiumDetailBMEnum {
	PAREN("0")
,	CHILD("1")
,   FYPDATE("2")
,	policycountreceived("3")
,	fypreceived("4")
,	policycountissued("5")
,	fypissued("6")
, 	policycount("7")
,	fyp("8")
,	policycountcancel("9")
,	fypcancel("10")
,	rfyp("11")
,	ryp ("12")
,	K2	("13")
,	K2PLUS("14")
,	lm3policycount	("15")
,	lm3fyp	("16")
,	lm2policycount	("17")
,	lm2fyp	("18")
,	lm1policycount	("19")
,	lm1fyp	("20");



	private String value;
	private ReportGroupPremiumDetailBMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
