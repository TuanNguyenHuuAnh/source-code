package vn.com.unit.ep2p.enumdef;

public enum ResultMaintainUMEnum {
	BDTH("0")
	,BDAH("1")
	,BDOH("2")
	,AGENT("3")
	,OFFICE("4")
	, DATEREVIEWPROMOUM("5")
	, MONTHSOFSERVICETOTERM("6")
	, K2PLUSSTR("7")
	, FYP3MONTH("8")
	, afc("9")
	, RECRUITMENT3MONTH("10")
	, EVALUATEPROMOTION("11")
	, K2PLUSADDSTR("12")
	, FYP3MONTHADD("13")
	, afcAdd("14")
	, RECRUITMENT3MONTHADD("15");

	private String value;
	
	private ResultMaintainUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
