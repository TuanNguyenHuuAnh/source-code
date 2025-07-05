package vn.com.unit.ep2p.enumdef;

public enum ReportGroupMANEnum {
			ORGNAME("0")
	,		AGENTNAME("1")
	,		countfctypeagentcode("2")
	,		newrecruitfc("3")
	,		countnewrecruitment("4")
	,		RCRATIO("5")
	, 		ACTIVEFC ("6")
	,		COUNTACTIVE("7")	
	,		ACTIONRATIO("8")
	,		ACTIONRATIOACTION("9")
	,       countNewRecruitmentActive("10")
	,		actionNewRaio("11")
	,		
	;

	private String value;

	private ReportGroupMANEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
