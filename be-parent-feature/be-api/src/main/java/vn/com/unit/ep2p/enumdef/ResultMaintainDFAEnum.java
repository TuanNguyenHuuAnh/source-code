package vn.com.unit.ep2p.enumdef;

public enum ResultMaintainDFAEnum {
	BDTH("0")
	,BDAH("1")
	,BDOH("2")
	,LEADER("3")
	,AGENT("4")
	,OFFICE("5")
	,DATEJOINDFA("6")
	,MONTHJOINDFA("7")
	,SEGMENT("8")
	,DATEOFSEGMENT("9")
	,fyp3Month("10")
	,FYPYEAR("11")
	,DFASELECTEDBYDFA ("12")
	,TERM("13")
	,RANKUP("14")
	,RANKDOWN("15")
	;
	private String value;
	
	private ResultMaintainDFAEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
