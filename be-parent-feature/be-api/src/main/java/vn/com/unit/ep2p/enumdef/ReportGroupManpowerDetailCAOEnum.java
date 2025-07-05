package vn.com.unit.ep2p.enumdef;

public enum ReportGroupManpowerDetailCAOEnum {
			PAREN("0")
	,		CHILD("1")
	,		countBmTypeAgentcode	("2")
	,		countUmTypeAgentcode	("3")
	,		countpumtypeagentcode	("4")
	,		countfctypeagentcode	("5")
	,		countsatypeagentcode	("6")
	,		countnewrecruitment	("7")
	,		countreinstate	("8")
	,		countactive	("9")
	,		countnewrecruitmentactive	("10")
	,		countschemefc	("11")
	,		countpfcfc	("12")
	,		lastcountnewrecruitment	("13")
	,		lastcountreinstate	("14")
	,		lastcountnewrecruitmentactive("15")
	,		lastcountactive("16");
	private String value;

	private ReportGroupManpowerDetailCAOEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
