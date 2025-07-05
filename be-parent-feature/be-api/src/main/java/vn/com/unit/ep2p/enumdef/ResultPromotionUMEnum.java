package vn.com.unit.ep2p.enumdef;

public enum ResultPromotionUMEnum {
  BDTH("0")
	, BDAH("1")
	, BDOH("2")
	, AGENT("3")
	, OFFICE("4")
	, workingDay("5")
	, dateReviewPromoOrDemo("6")
	, monthsOfServiceToTerm("7")
	, K2PLUSSTR("8")
	, FYP3MONTH("9")
	, AFYP3MONTH("10")
	, RECRUITMENT3MONTH("11")
	, SUBGROUPUMSUM("12")
	, SUBGROUPPUM("13")
	, EVALUATEPROMOTION("14")
	, K2PLUSADDSTR("15")
	, FYP3MONTHADD("16")
	, AFYP3MONTHADD("17")
	, RECRUITMENT3MONTHADD("18")
	, SUBGROUPUMSUMADD("19")
	, SUBGROUPPUMADD("20");
	
	
	private String value;
	
	private ResultPromotionUMEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
