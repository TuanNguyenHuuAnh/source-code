package vn.com.unit.ep2p.enumdef;

public enum ResultMaintainSBMEnum {
	BDTH("0")
	,BDAH("1")
	,BDOH("2")
	, agent("3")
	, OFFICE("4")
	, dateReviewPromoOrDemo("5")
	, appointmentDate("6")
	, MONTHSBM("7")
	, K2PLUSSTR("8")
	, FYP6MONTH("9")
	, AFC("10")
	, RECRUITMENT6MONTH("11")
	, SUBGROUPUMSUM("12")
	, SUBGROUPPUM("13")
	, GROUPBM("14")
	, resultOfEvaluation("15")
	, K2PLUSADDSTR("16")
	, FYP6MONTHADD("17")
	, AFCADD("18")
	, RECRUITMENT6MONTHADD("19")
	, SUBGROUPUMSUMADD("20")
	, quantitySumUmAdd("21")
	, GROUPBMADD("22")
	;
	
	private String value;
	
	private ResultMaintainSBMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
