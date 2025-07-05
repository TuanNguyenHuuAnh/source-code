package vn.com.unit.ep2p.enumdef;

public enum IncomeBonusReportEnumRHOH {
	OFFICE("0")
	, MANAGER("1")
	
	, FYPPASSEDMO("2")
	, FYCBONUSMO("3")
	, TVTCNOMO("4")
	, CONTRACTSNUMBERMO("5")
	, FYPNEXTMO("6")
	, FYPHIGHESTMO("7")
	
	, FYPQUARTERLYBONUSTQP("8")
	, K2PLUSTQP("9")
	, FYCBONUSTQP("10")
	, FYPNEXTTQP("11")
	, FYPHIGHESTTQP("12")
	
	, FYPQUARTERLYPASSEDTSM("13")
	, FCOPERATEQUARTERLYTSM("14")
	, RATINGTSM("15")
	, K2PLUSTSM("16")
	, FYPHIGHESTTSM("17")
	;
	
	private String value;
	
	private IncomeBonusReportEnumRHOH(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
