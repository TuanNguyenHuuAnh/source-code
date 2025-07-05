package vn.com.unit.ep2p.enumdef;

public enum ReportGroupPremiumDetailUMEnum {

	CHILD("0")
,   fypdate("1")
,	policycountreceived("2")
,	fypreceived("3")
,	policycountissued("4")
,	fypissued("5")
, 	policycount("6")
,	fyp("7")
,	policycountcancel("8")
,	fypcancel("9")
,	rfyp("10")
,	ryp ("11")
,	K2	("12")
,	K2PLUS("13")
,	lm3policycount	("14")
,	lm3fyp	("15")
,	lm2policycount	("16")
,	lm2fyp	("17")
,	lm1policycount	("18")
,	lm1fyp	("19");




	private String value;

	private ReportGroupPremiumDetailUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
