package vn.com.unit.ep2p.enumdef;

public enum ResultMaintainBMEnum {
	BDTH("0")
	,BDAH("1")
	,BDOH("2")
	,LEADER("3")
	,OFFICE("4")
	,DATEREVIEWPROMOORDEMO("5")
	,MONTHSOFSERVICETOTERM("6")
	,K2PLUSSTR("7")
	,FYP6MONTH("8")
	,AFC6MONTH("9")
	,RECRUITMENT6MONTH("10")
	, SUBGROUPUMSUM("11")
	, SUBGROUPPUM("12")
	, EVALUATEDEMOTE("13")

	, K2PLUSADDSTR("14")
	, FYP6MONTHADD("15")
	, AFC6MONTHADD("16")
	, RECRUITMENT6MONTHADD("17")
	, SUBGROUPUMSUMADD("18")
	, SUBGROUPPUMADD("19")
	;
	
	private String value;
	
	private ResultMaintainBMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
