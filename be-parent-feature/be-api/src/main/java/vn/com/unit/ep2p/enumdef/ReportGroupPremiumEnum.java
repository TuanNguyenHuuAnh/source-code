package vn.com.unit.ep2p.enumdef;

public enum ReportGroupPremiumEnum {
			ORGNAME("0")
	,		AGENTNAME("1")
	,		FYPTARGET("2")
	,		FYP("3")
	,		RATEOFREACHINGFYP("4")
	,		RYPTARGET("5")
	, 		RYP("6")
	,		rateOfReachingRyp("7")		 
	,		POLICYCOUNTTARGET("8")
	,		POLICYCOUNT("9")
	,		INSURANCECONTRACTRATIO("10")
	;

	private String value;

	private ReportGroupPremiumEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
