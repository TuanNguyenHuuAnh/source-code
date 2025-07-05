package vn.com.unit.ep2p.enumdef;

public enum ResultPromotionDFAMEnum {
	BDTH("0")
	, BDAH("1")
	, BDOH("2")
	,LEADER("3")
	,AGENT("4")
	,OFFICE("5")
	,DATEJOINDFA("6")
	,MONTHJOINDFA("7")
	,FYP6PASTMONTH("8")
	,DFASELECTEDBYDFA("9")
	,GETPROMOTED("10")
	;
	
	private String value;
	
	private ResultPromotionDFAMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
