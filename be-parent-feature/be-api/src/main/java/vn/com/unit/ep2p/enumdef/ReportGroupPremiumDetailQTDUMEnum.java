package vn.com.unit.ep2p.enumdef;

public enum ReportGroupPremiumDetailQTDUMEnum {

	CHILD("0")
,	policycountreceived("1")
,	fypreceived("2")
,	policycountissued("3")
,	fypissued("4")
, 	policycount("5")
,	fyp("6")
,	policycountcancel("7")
,	fypcancel("8")
,	rfyp("9")
,	ryp ("10")
;




	private String value;

	private ReportGroupPremiumDetailQTDUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
