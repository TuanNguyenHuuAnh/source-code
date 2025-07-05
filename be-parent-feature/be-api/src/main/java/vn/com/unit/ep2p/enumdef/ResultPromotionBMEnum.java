package vn.com.unit.ep2p.enumdef;

public enum ResultPromotionBMEnum {

	BDTH("0")
	,BDAH("1")
	,BDOH("2")
	,agent("3")
	,OFFICE("4")
	,workingDay("5")
	,appointmentDate("6")
	,MONTHSBM("7")
	,K2PLUSSTR("8")
	,FYP6ALLMONTH("9")
	,afyp6AllMonth("10")
	,RECRUITMENT6MONTH("11")
	,SUBGROUPUMSUM("12")
	,SUBGROUPPUM("13")
	,GROUPBM("14")
	,EVALUATEPROMOTION("15")
	,K2PLUSADDSTR("16")
	,FYP6ALLMONTHADD("17")
	,afyp6AllMonthAdd("18")
	,RECRUITMENT6MONTHADD("19")
	,SUBGROUPUMSUMADD("20")
	,SUBGROUPPUMADD("21")
	,GROUPBMADD("22");
	private String value;
	
	private ResultPromotionBMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
