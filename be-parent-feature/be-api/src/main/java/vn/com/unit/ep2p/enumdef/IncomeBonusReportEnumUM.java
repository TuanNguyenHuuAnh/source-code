package vn.com.unit.ep2p.enumdef;

public enum IncomeBonusReportEnumUM {
	TVTC("0")
	
	, FYPPASSEDMO("1")
	, FYCBONUSMO("2")
	, TVTCNOMO("3")
	, CONTRACTSNUMBERMO("4")
	, FYPNEXTMO("5")
	, FYPHIGHESTMO("6")
	
	, FYPQUARTERLYBONUSTQP("7")
	, K2PLUSTQP("8")
	, FYCBONUSTQP("9")
	, FYPNEXTTQP("10")
	, FYPHIGHESTTQP("11")
	
	, FYPQUARTERLYPASSEDTSM("12")
	, FCOPERATEQUARTERLYTSM("13")
	, RATINGTSM("14")
	, K2PLUSTSM("15")
	, FYPHIGHESTTSM("16")
	;
	
	private String value;
	
	private IncomeBonusReportEnumUM(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
