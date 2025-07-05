package vn.com.unit.ep2p.enumdef;

public enum ResultMaintainSUMEnum {
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
	, recruitment("10")
	, quantityUm("11")
	, quantityPum("12")
	, EVALUATEDEMOTE("13")

	, K2PLUSADDSTR("14")
	, FYP3MONTHADD("15")
	, afcAdd("16")
	, RECRUITMENT3MONTHADD("17")
	, SUBGROUPUMSUMADD("18")
	, SUBGROUPPUMADD("19");
	private String value;
	
	private ResultMaintainSUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
