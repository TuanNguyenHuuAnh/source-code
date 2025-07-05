package vn.com.unit.ep2p.enumdef;

public enum ResultMaintainDFAMEnum {
	BDTH("0")
	,BDAH("1")
	,BDOH("2")
	,mannger("3")
	,AGENT("4")
	,OFFICE("5")
	,DATEJOINDFA("6")
	,MONTHJOINDFA("7")
	,DATEOFSEGMENT("8")
	,monthsOfSegment("9")
	,FYP3PASTMONTH("10")
	,FYP6PASTMONTH("11")
	,FYPYEAR("12")
	,DFASELECTEDBYDFA("13")
	,DFAINGOURP("14")
	,  RESIGNEDFROMOFFICE("15")
	,  RANKUP("16")
	,  RANKDOWN("17")
	;
	private String value;
	
	private ResultMaintainDFAMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
